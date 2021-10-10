package com.thumann.server.service.base;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}