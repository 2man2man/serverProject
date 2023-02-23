package com.thumann.server.domain.logisticexecution.storage;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.thumann.server.domain.Domain;
import com.thumann.server.domain.stock.fixedlocation.StockFixedLocationCreateAction;

@Entity
public class StorageExecutionPosition extends Domain
{
    private static final long              serialVersionUID = -7883891138940585159L;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    private StorageExecution               execution;

    @OneToOne( fetch = FetchType.LAZY, optional = false )
    private StockFixedLocationCreateAction action;

    public StorageExecution getExecution()
    {
        return execution;
    }

    public void setExecution( StorageExecution execution )
    {
        this.execution = execution;
    }

    public StockFixedLocationCreateAction getAction()
    {
        return action;
    }

    public void setAction( StockFixedLocationCreateAction action )
    {
        this.action = action;
    }

}
