package com.thumann.server.service.logisticexecution.storage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thumann.server.domain.logisticexecution.storage.StorageExecution;
import com.thumann.server.domain.logisticexecution.storage.StorageExecutionPosition;
import com.thumann.server.domain.user.Employee;
import com.thumann.server.domain.user.Person;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.logisticexecution.storage.dto.StorageExecutionAddPositionDto;
import com.thumann.server.service.logisticexecution.storage.dto.StorageExecutionCreateDto;
import com.thumann.server.service.stock.StockService;
import com.thumann.server.service.stock.dto.StockFixedLocationCreateResultDto;

@Service( "storageService" )
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
@Transactional
public class StorageServiceImpl implements StorageService
{
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BaseService   baseService;

    @Autowired
    private StockService  stockService;

    @Override
    public StorageExecution createExecution( StorageExecutionCreateDto dto )
    {
        StorageExecution domain = new StorageExecution();
        domain.init( (Employee) baseService.getCaller(), dto.getWarehouse() );
        return entityManager.merge( domain );
    }

    @Override
    public StorageExecutionPosition addPosition( StorageExecutionAddPositionDto dto )
    {
        StorageExecution execution = entityManager.find( StorageExecution.class, dto.getExecutionId() );
        checkCaller( execution );

        StockFixedLocationCreateResultDto createdStock = stockService.create( dto.getStockDto() );

        StorageExecutionPosition position = new StorageExecutionPosition();
        position.setAction( createdStock.getAction() );
        position.setExecution( execution );
        position = entityManager.merge( position );

        execution.addPosition( position );
        execution = entityManager.merge( execution );
        return position;
    }

    private void checkCaller( StorageExecution execution )
    {
        Person caller = baseService.getCaller();
        if ( execution.getCreatedBy().getId() != caller.getId() ) {
            throw new IllegalArgumentException( "Storage execution [" + execution.getId() + "] was not created by caller [" + caller.getId() + "]!" );
        }
    }

}