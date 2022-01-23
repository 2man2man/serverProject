package com.thumann.server.service.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thumann.server.domain.Domain;
import com.thumann.server.domain.user.Employee;
import com.thumann.server.domain.user.Person;
import com.thumann.server.helper.collection.CollectionUtil;
import com.thumann.server.service.helper.UserThreadHelper;

@Service( "baseService" )
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
@Transactional
public class BaseServiceImpl implements BaseService
{
    @PersistenceContext
    private EntityManager entityManager;

    public BaseServiceImpl()
    {
    }

    @Override
    public EntityManager getMananger()
    {
        return entityManager;
    }

    @Override
    public Object executeWithTransaction( ApplicationRunnable runnable ) throws Exception
    {
        return executeWithTransaction( runnable, null );
    }

    @Override
    public Object executeWithTransaction( ApplicationRunnable runnable, Object args ) throws Exception
    {
        return runnable.run( args );
    }

    @Override
    public <T extends Domain> T getById( long id, Class<T> clazz )
    {
        return getById( id, clazz, new HashSet<String>() );
    }

    @Override
    public <T extends Domain> T getById( long id, Class<T> clazz, Set<String> eagerProps )
    {
        T domain = getMananger().find( clazz, id );

        for ( String prop : eagerProps ) {
            String[] splitted = prop.split( "." );
            Object o = domain;
            for ( String string : splitted ) {
                String method = "get" + string.substring( 0, 1 ).toUpperCase() + string.substring( 1 );
                try {
                    o = o.getClass().getMethod( method ).invoke( o );
                }
                catch ( Exception e ) {
                    throw new IllegalArgumentException( "eagerLoadFailed: " + e.getMessage(), e );
                }
                if ( o == null ) {
                    break;
                }
            }
        }
        return domain;
    }

    @Override
    public <T extends Domain> List<T> getByIds( Collection<Long> ids, Class<T> clazz )
    {
        return getByIds( ids, clazz, new HashSet<String>() );
    }

    @Override
    public <T extends Domain> List<T> getByIds( Collection<Long> ids, Class<T> clazz, Set<String> eagerProps )
    {
        List<T> result = new ArrayList<T>();
        for ( Long id : ids ) {
            result.add( getById( id, clazz, eagerProps ) );
        }
        return result;
    }

    @Override
    public <T> List<T> getObjectsByQuery( String query, Class<T> resultClazz )
    {
        return getMananger().createQuery( query, resultClazz ).getResultList();
    }

    @Override
    public Set<Long> getCallerTenantIds()
    {
        Set<Long> result = new HashSet<>();
        Person caller = getMananger().find( Employee.class, UserThreadHelper.getUser() );
        result.addAll( CollectionUtil.getIdsAsList( caller.getTenants() ) );
        return result;
    }

}