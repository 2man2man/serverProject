package com.thumann.server.web.controller.warehouse.area;

import com.thumann.server.domain.Domain;
import com.thumann.server.web.exception.APIMissingFieldException;

public class WarehouseAreaUpdateDTO extends WarehouseAreaCreateUpdateDTO
{
    private long warehouseAreaId;

    public void check()
    {
        if ( warehouseAreaId == Domain.UNKOWN_ID ) {
            throw APIMissingFieldException.create( "warehouseAreaId" );
        }
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
