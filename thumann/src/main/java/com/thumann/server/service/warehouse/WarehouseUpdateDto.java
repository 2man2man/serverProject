package com.thumann.server.service.warehouse;

public class WarehouseUpdateDto
{
    private long   warehouseId;

    private String number;

    private String name;

    public String getNumber()
    {
        return number;
    }

    public void setNumber( String number )
    {
        this.number = number;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public long getWarehouseId()
    {
        return warehouseId;
    }

    public void setWarehouseId( long warehouseId )
    {
        this.warehouseId = warehouseId;
    }

}
