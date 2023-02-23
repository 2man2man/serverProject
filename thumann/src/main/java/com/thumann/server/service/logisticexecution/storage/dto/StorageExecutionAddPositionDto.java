package com.thumann.server.service.logisticexecution.storage.dto;

import com.thumann.server.service.stock.dto.StockFixedLocationCreateDto;

public class StorageExecutionAddPositionDto
{
    private long                        executionId;

    private StockFixedLocationCreateDto stockDto;

    public long getExecutionId()
    {
        return executionId;
    }

    public void setExecutionId( long executionId )
    {
        this.executionId = executionId;
    }

    public StockFixedLocationCreateDto getStockDto()
    {
        return stockDto;
    }

    public void setStockDto( StockFixedLocationCreateDto stockDto )
    {
        this.stockDto = stockDto;
    }

}
