package com.thumann.server.web.controller.logisticexecution.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.logisticexecution.storage.StorageExecution;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.web.controller.stock.fixedlocation.StockFixedLocationControllerFactory;
import com.thumann.server.web.controller.warehouse.WarehouseControllerFactory;

@Configuration
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
public class StorageExecutionControllerFactory
{
    @Autowired
    private WarehouseControllerFactory          warehouseFactory;

    @Autowired
    private StockFixedLocationControllerFactory stockFactory;

    @Autowired
    private BaseService                         baseService;

    public StorageExecutionAPIAddPositionDto createAddPositionDTO( ObjectNode node, long executionId )
    {
        StorageExecutionAPIAddPositionDto dto = new StorageExecutionAPIAddPositionDto();
        dto.initValues( executionId, node, stockFactory );
        dto.check( baseService );
        return dto;
    }

    public StorageExecutionAPICreateDto createCreateDTO( ObjectNode node )
    {
        StorageExecutionAPICreateDto dto = new StorageExecutionAPICreateDto();
        dto.initValues( node, baseService );
        dto.check( baseService );
        return dto;
    }

    public StorageExecutionResponseDTO createResponseDTO( StorageExecution domain )
    {
        StorageExecutionResponseDTO dto = new StorageExecutionResponseDTO();
        dto.initValues( domain, warehouseFactory );
        return dto;
    }

    public StorageExecutionResponseDTO createResponseDTO( long id )
    {
        StorageExecution domain = baseService.getById( id, StorageExecution.class );
        return createResponseDTO( domain );
    }

}
