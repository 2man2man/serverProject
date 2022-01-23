package com.thumann.server.service.user;

import java.util.HashSet;
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
import com.thumann.server.web.controller.employee.EmployeeUpdateDTO;

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
        Boolean systemConfigurationPrivilege = createDTO.isSystemConfigurationPrivilege();
        if ( systemConfigurationPrivilege == null ) {
            systemConfigurationPrivilege = true;
        }
        employee.getPrivilege().setSystemConfiguration( systemConfigurationPrivilege );

        
        Boolean articleModifyPrivilege = createDTO.getArticleModifyPrivilege();
        if ( articleModifyPrivilege == null ) {
            articleModifyPrivilege = true;
        }
        employee.getPrivilege().setArticleModify( articleModifyPrivilege );

        return entityManager.merge( employee );
    }

    @Override
    public Employee updateEmployee( EmployeeUpdateDTO updateDto )
    {
        Employee employee = entityManager.find( Employee.class, updateDto.getEmployeeId() );

        if ( !StringUtil.isEmpty( updateDto.getFirstName() ) ) {
            employee.setFirstName( updateDto.getFirstName() );
        }
        if ( !StringUtil.isEmpty( updateDto.getLastName() ) ) {
            employee.setLastName( updateDto.getLastName() );
        }
        if ( updateDto.getDateOfBirth() != null ) {
            employee.setDateOfBirth( updateDto.getDateOfBirth() );
        }
        if ( !updateDto.getTenants().isEmpty() ) {
            employee.setTenants( new HashSet<>( updateDto.getTenants() ) );
        }
        if ( !StringUtil.isEmpty( updateDto.getUserName() ) ) {
            Employee existingEmployeeByUsername = getByUsername( updateDto.getUserName() );
            if ( existingEmployeeByUsername != null && existingEmployeeByUsername.getId() != employee.getId() ) {
                throw new IllegalStateException( "There already exists a user with username " + updateDto.getUserName() );
            }
            employee.getCredentials().setUsername( updateDto.getUserName() );
        }
        if ( !StringUtil.isEmpty( updateDto.getPassword() ) ) {
            employee.getCredentials().setPassword( updateDto.getPassword() );
        }
        if ( updateDto.isSystemConfigurationPrivilege() != null ) {
            employee.getPrivilege().setSystemConfiguration( updateDto.isSystemConfigurationPrivilege() );
        }
        if ( updateDto.getArticleModifyPrivilege() != null ) {
            employee.getPrivilege().setArticleModify( updateDto.getArticleModifyPrivilege() );
        }

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
        dto.setUserName( Employee.ADMIN );
        dto.setPassword( Employee.ADMIN );
        dto.getTenants().addAll( tenants );
        dto.setFirstName( Employee.ADMIN );
        dto.setLastName( Employee.ADMIN );
        dto.setSystemConfigurationPrivilege( true );

        createEmployee( dto );
    }

}