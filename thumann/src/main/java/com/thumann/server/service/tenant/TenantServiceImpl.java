package com.thumann.server.service.tenant;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.web.controller.tenant.TenantCreateDTO;

@Service( "tenantService" )
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
@Transactional
public class TenantServiceImpl implements TenantService
{

    @PersistenceContext
    private EntityManager entityManager;

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

        Tenant existingTenant = getByNumber( createDTO.getNumber() );
        if ( existingTenant != null ) {
            throw new IllegalArgumentException( "There already exists a tenant with number '" + createDTO.getNumber() + "'" );
        }

        Tenant tenant = new Tenant();
        tenant.setNumber( createDTO.getNumber() );
        tenant.setName( createDTO.getName() );

        return entityManager.merge( tenant );
    }

    @Override
    public Tenant getByNumber( String number )
    {
        if ( StringUtil.isEmpty( number ) ) {
            throw new IllegalArgumentException( "number must not be null" );
        }

        StringBuilder sb = new StringBuilder();
        sb.append( "SELECT domain " );
        sb.append( "FROM Tenant domain " );
        sb.append( "WHERE domain.number =:number" );

        TypedQuery<Tenant> query = entityManager.createQuery( sb.toString(), Tenant.class );
        query.setParameter( "number", number );
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

}