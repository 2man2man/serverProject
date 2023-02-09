package com.thumann.server.service.fixedlocation;

public class FixedLocationCreateUpdateDto
{
    private long locationTypeId;

    private long warehouseAreaId;

    public long getLocationTypeId()
    {
        return locationTypeId;
    }

    public void setLocationTypeId( long locationTypeId )
    {
        this.locationTypeId = locationTypeId;
    }

    public long getWarehouseAreaId()
    {
        return warehouseAreaId;
    }

    public void setWarehouseAreaId( long warehouseAreaId )
    {
        this.warehouseAreaId = warehouseAreaId;
    }

}
