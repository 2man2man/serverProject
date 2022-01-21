package com.thumann.server.service.user;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.domain.user.Employee;
import com.thumann.server.domain.user.UserCredentials;
import com.thumann.server.domain.user.UserPrivilege;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.web.controller.employee.EmployeeCreateDTO;

@Service( "employeeService" )
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
@Transactional
class EmployeeServiceImpl implements EmployeeService
{
    @PersistenceContext
    private EntityManager          entityManager;

    @Autowired
    private UserCredentialsService userCredentialsService;

    public EmployeeServiceImpl()
    {
    }

    @Override
    public Employee createEmployee( EmployeeCreateDTO createDTO )
    {
        if ( createDTO.getTenants().isEmpty() ) {
            throw new IllegalArgumentException( "tenants must not be empty" );
        }

        Employee employee = new Employee();
        employee.setFirstName( createDTO.getFirstName() );
        employee.setLastName( createDTO.getLastName() );
        employee.setDateOfBirth( createDTO.getDateOfBirth() );
        employee.getTenants().addAll( createDTO.getTenants() );

        UserCredentials credentials = userCredentialsService.create( createDTO.getUserName(), createDTO.getPassword() );
        employee.setCredentials( credentials );

        employee.setPrivilege( new UserPrivilege() );
        employee.getPrivilege().setSystemConfiguration( createDTO.isSystemConfigurationPrivilege() );

        return entityManager.merge( employee );
    }

    @Override
    public Employee getByUsername( String username )
    {
        if ( StringUtil.isEmpty( username ) ) {
            throw new IllegalArgumentException( "username must not be null" );
        }

        StringBuilder sb = new StringBuilder();
        sb.append( "SELECT domain " )
          .append( " FROM Employee domain " )
          .append( " JOIN domain.credentials cred " )
          .append( " WHERE cred.username = :username " );

        List<Employee> result = entityManager.createQuery( sb.toString(), Employee.class )
                                             .setParameter( "username", username )
                                             .getResultList();
        if ( result.isEmpty() ) {
            return null;
        }
        else if ( result.size() > 1 ) {
            throw new IllegalStateException( "username is supposed to be unique" );
        }
        return result.get( 0 );
    }

    @Override
    public void createAdmin()
    {
        Employee user = getByUsername( "admin" );
        if ( user != null ) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append( "SELECT domain " )
          .append( " FROM Tenant domain " );

        List<Tenant> tenants = entityManager.createQuery( sb.toString(), Tenant.class ).getResultList();

        EmployeeCreateDTO dto = new EmployeeCreateDTO();
        dto.setUserName( "admin" );
        dto.setPassword( "admin" );
        dto.getTenants().addAll( tenants );
        dto.setFirstName( "admin" );
        dto.setLastName( "admin" );
        dto.setSystemConfigurationPrivilege( true );

        createEmployee( dto );
    }

}