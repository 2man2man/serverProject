package com.thumann.server.web.controller.warehouse.area;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.Domain;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.web.exception.APIMissingFieldException;

public class WarehouseAreaCreateDTO extends WarehouseAreaCreateUpdateDTO
{
    private long warehouseId;

    @Override
    public void initValues( ObjectNode json )
    {
        super.initValues( json );
        setWarehouseId( JsonUtil.getLong( json, "warehouseId", Domain.UNKOWN_ID ) );
    }

    public void check()
    {
        if ( warehouseId == Domain.UNKOWN_ID ) {
            throw APIMissingFieldException.create( "warehouseId" );
        }
        if ( StringUtil.isEmpty( getNumber() ) ) {
            throw APIMissingFieldException.create( "number" );
        }
        if ( StringUtil.isEmpty( getName() ) ) {
            throw APIMissingFieldException.create( "name" );
        }
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
