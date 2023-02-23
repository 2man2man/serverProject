package com.thumann.server.web.controller.logisticexecution.storage;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.warehouse.Warehouse;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.logisticexecution.storage.dto.StorageExecutionCreateDto;

public class StorageExecutionAPICreateDto
{
    private long warehouseId;

    public StorageExecutionCreateDto toCreateDto( BaseService baseService )
    {
        StorageExecutionCreateDto result = new StorageExecutionCreateDto();
        result.setWarehouse( baseService.getById( warehouseId, Warehouse.class ) );
        return result;
    }

    public void initValues( ObjectNode json, BaseService baseService )
    {
        this.warehouseId = baseService.getCaller().getWarehouse().getId();
    }

    public void check( BaseService baseService )
    {
    }

}
