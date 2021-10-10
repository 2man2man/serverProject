package com.thumann.server.service.base;

import javax.persistence.EntityManager;

public interface BaseService
{
    EntityManager getMananger();

    Object executeWithTransaction( ApplicationRunnable runnable ) throws Exception;

    Object executeWithTransaction( ApplicationRunnable runnable, Object args ) throws Exception;
}
