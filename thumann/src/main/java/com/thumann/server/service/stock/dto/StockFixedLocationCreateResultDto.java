package com.thumann.server.service.stock.dto;

import com.thumann.server.domain.stock.fixedlocation.StockFixedLocation;
import com.thumann.server.domain.stock.fixedlocation.StockFixedLocationCreateAction;

public class StockFixedLocationCreateResultDto
{
    private StockFixedLocation             stock;

    private StockFixedLocationCreateAction action;

    public StockFixedLocationCreateResultDto( StockFixedLocation stock, StockFixedLocationCreateAction action )
    {
        this.stock = stock;
        this.action = action;
    }

    public StockFixedLocation getStock()
    {
        return stock;
    }

    public StockFixedLocationCreateAction getAction()
    {
        return action;
    }

}
