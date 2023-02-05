package com.thumann.server.domain.warehouse.area;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.thumann.server.domain.Domain;
import com.thumann.server.domain.warehouse.Warehouse;

@Entity
public class WarehouseArea extends Domain implements Serializable
{
    private static final long serialVersionUID = 3043270621063774584L;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    private Warehouse         warehouse;

    @Column( nullable = false )
    private String            number;

    @Column( nullable = false )
    private String            name;

    public Warehouse getWarehouse()
    {
        return warehouse;
    }

    public void setWarehouse( Warehouse warehouse )
    {
        this.warehouse = warehouse;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber( String number )
    {
        this.number = number;
    }

}
