package com.thumann.server.service.logisticexecution.storage.dto;

import com.thumann.server.domain.warehouse.Warehouse;

public class StorageExecutionCreateDto
{
    private Warehouse warehouse;

    public Warehouse getWarehouse()
    {
        return warehouse;
    }

    public void setWarehouse( Warehouse warehouse )
    {
        this.warehouse = warehouse;
    }

}
