package com.thumann.server.service.tenant;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.domain.user.Employee;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.helper.UserThreadHelper;
import com.thumann.server.service.user.EmployeeService;
import com.thumann.server.web.controller.employee.EmployeeUpdateDTO;
import com.thumann.server.web.controller.tenant.TenantCreateDTO;

@Service( "tenantService" )
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
@Transactional
public class TenantServiceImpl implements TenantService
{
    @PersistenceContext
    private EntityManager   entityManager;

    @Autowired
    private BaseService     baseService;

    @Autowired
    private EmployeeService employeeService;

    public TenantServiceImpl()
    {
    }

    @Override
    public Tenant createTenant( TenantCreateDTO createDTO )
    {
        if ( createDTO == null ) {
            throw new IllegalArgumentException( "createDTO must not be null" );
        }
        else if ( StringUtil.isEmpty( createDTO.getNumber() ) ) {
            throw new IllegalArgumentException( "number must not be null" );
        }
        else if ( StringUtil.isEmpty( createDTO.getName() ) ) {
            throw new IllegalArgumentException( "name must not be null" );
        }

        Tenant exitingTenant = getByNumber( createDTO.getNumber(), false );
        if ( exitingTenant != null ) {
            throw new IllegalArgumentException( "There already exists a tenant with number '" + createDTO.getNumber() + "'" );
        }

        Tenant tenant = new Tenant();
        tenant.setNumber( createDTO.getNumber() );
        tenant.setName( createDTO.getName() );

        tenant = entityManager.merge( tenant );

        Employee adminUser = employeeService.getByUsername( Employee.ADMIN );
        EmployeeUpdateDTO updateDto = new EmployeeUpdateDTO();
        updateDto.setEmployeeId( adminUser.getId() );
        updateDto.getTenants().addAll( adminUser.getTenants() );
        updateDto.getTenants().add( tenant );
        employeeService.updateEmployee( updateDto );

        long callerId = UserThreadHelper.getUser();
        if ( callerId != adminUser.getId() ) {
            Employee caller = entityManager.find( Employee.class, callerId );

            updateDto = new EmployeeUpdateDTO();
            updateDto.setEmployeeId( caller.getId() );
            updateDto.getTenants().addAll( caller.getTenants() );
            updateDto.getTenants().add( tenant );
            employeeService.updateEmployee( updateDto );
        }

        return tenant;
    }

    @Override
    public Tenant getByNumber( String number, boolean onlyCallerTenants )
    {
        if ( StringUtil.isEmpty( number ) ) {
            throw new IllegalArgumentException( "number must not be null" );
        }

        StringBuilder sb = new StringBuilder();
        sb.append( "SELECT domain " );
        sb.append( "FROM Tenant domain " );
        sb.append( "WHERE domain.number =:number " );
        if ( onlyCallerTenants ) {
            sb.append( "  AND domain.id in (:ids) " );
        }

        TypedQuery<Tenant> query = entityManager.createQuery( sb.toString(), Tenant.class );
        query.setParameter( "number", number );
        if ( onlyCallerTenants ) {
            query.setParameter( "ids", baseService.getCallerTenantIds() );
        }

        List<Tenant> result = query.getResultList();
        if ( result.isEmpty() ) {
            return null;
        }
        else if ( result.size() == 1 ) {
            return result.get( 0 );
        }
        else {
            throw new IllegalStateException( "More than one tenant with number " + number );
        }
    }

    @Override
    public void createMainTenant()
    {
        StringBuilder sb = new StringBuilder();
        sb.append( "SELECT domain.id " )
          .append( " FROM Tenant domain " );

        List<Long> tenants = entityManager.createQuery( sb.toString(), Long.class ).getResultList();
        if ( !tenants.isEmpty() ) {
            return;
        }

        Tenant tenant = new Tenant();
        tenant.setNumber( "M1" );
        tenant.setName( "Main tenant" );
        entityManager.merge( tenant );
    }

}