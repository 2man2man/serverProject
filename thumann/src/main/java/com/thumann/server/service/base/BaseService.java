package com.thumann.server.service.base;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import com.thumann.server.domain.Domain;
import com.thumann.server.domain.DomainTenantInterface;
import com.thumann.server.domain.user.Person;

public interface BaseService
{
    EntityManager getMananger();

    Object executeWithTransaction( ApplicationRunnable runnable ) throws Exception;

    Object executeWithTransaction( ApplicationRunnable runnable, Object args ) throws Exception;

    <T extends Domain> T getById( long id, Class<T> clazz );

    <T extends Domain> T getById( long id, Class<T> clazz, Set<String> eagerProps );

    <T extends Domain> List<T> getByIds( Collection<Long> ids, Class<T> clazz );

    <T extends Domain> List<T> getByIds( Collection<Long> ids, Class<T> clazz, Set<String> eagerProps );

    <T> List<T> getObjectsByQuery( String query, Class<T> resultClazz );

    Set<Long> getCallerTenantIds();

    boolean checkTenant( DomainTenantInterface domain );

    void checkTenantAndThrow( DomainTenantInterface domain );

    Person getCaller();

}
