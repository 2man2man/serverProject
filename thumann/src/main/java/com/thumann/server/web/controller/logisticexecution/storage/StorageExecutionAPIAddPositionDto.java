package com.thumann.server.web.controller.logisticexecution.storage;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.logisticexecution.storage.StorageExecution;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.logisticexecution.storage.dto.StorageExecutionAddPositionDto;
import com.thumann.server.web.controller.stock.fixedlocation.StockFixedLocationAPICreateDto;
import com.thumann.server.web.controller.stock.fixedlocation.StockFixedLocationControllerFactory;
import com.thumann.server.web.exception.APIEntityNotFoundException;

public class StorageExecutionAPIAddPositionDto
{
    private long                           executionId;

    private StockFixedLocationAPICreateDto stockCreateDto;

    public StorageExecutionAddPositionDto toCreateDto( BaseService baseService )
    {
        StorageExecutionAddPositionDto result = new StorageExecutionAddPositionDto();
        result.setExecutionId( this.executionId );
        result.setStockDto( stockCreateDto.toCreateDto( baseService ) );
        return result;
    }

    public void initValues( long executionId,
                            ObjectNode json,
                            StockFixedLocationControllerFactory stockFactory )
    {
        this.executionId = executionId;
        this.stockCreateDto = stockFactory.createCreateDto( json );
    }

    public void check( BaseService baseService )
    {
        StorageExecution execution = baseService.getById( executionId, StorageExecution.class );
        if ( execution == null ) {
            throw APIEntityNotFoundException.create( StorageExecution.class, "id", executionId );
        }
    }

}
