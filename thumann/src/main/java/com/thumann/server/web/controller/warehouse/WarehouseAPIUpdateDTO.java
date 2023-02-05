package com.thumann.server.web.controller.warehouse;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.warehouse.Warehouse;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.service.warehouse.WarehouseService;
import com.thumann.server.service.warehouse.WarehouseUpdateDto;
import com.thumann.server.web.exception.APINumberConflictException;

public class WarehouseAPIUpdateDTO
{
    private long   warehouseId;

    private String name;

    private String number;

    public WarehouseUpdateDto toUpdateDto()
    {
        WarehouseUpdateDto dto = new WarehouseUpdateDto();
        dto.setWarehouseId( getWarehouseId() );
        dto.setName( getName() );
        dto.setNumber( getNumber() );
        return dto;
    }

    public void initValues( ObjectNode json )
    {
        setName( JsonUtil.getString( json, "name" ) );
        setNumber( JsonUtil.getString( json, "number" ) );
    }

    public void check( WarehouseService warehouseService )
    {
        checkNumberUnique( warehouseService );
    }

    private void checkNumberUnique( WarehouseService warehouseService )
    {
        String number = getNumber();
        if ( StringUtil.isEmpty( number ) ) {
            return;
        }
        Warehouse byNumber = warehouseService.getByNumber( number );
        if ( byNumber == null ) {
            return;
        }
        else if ( byNumber.getId() == warehouseId ) {
            return;
        }
        throw APINumberConflictException.create( Warehouse.class, "number", number );
    }

    public long getWarehouseId()
    {
        return warehouseId;
    }

    public void setWarehouseId( long warehouseId )
    {
        this.warehouseId = warehouseId;
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
