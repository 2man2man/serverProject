package com.thumann.server.web.controller.warehouse.area;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.warehouse.area.WarehouseArea;
import com.thumann.server.web.response.CreateJsonInterface;

public class WarehouseAreaResponseDTO implements CreateJsonInterface
{
    private long   id;

    private long   warehouseId;

    private String number;

    private String name;

    public void initValues( WarehouseArea domain )
    {
        setId( domain.getId() );
        setName( domain.getName() );
        setNumber( domain.getNumber() );
        setWarehouseId( domain.getWarehouse().getId() );
    }

    @Override
    public JsonNode createJson()
    {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put( "id", getId() );
        objectNode.put( "warehouseId", getWarehouseId() );
        objectNode.put( "name", getName() );
        objectNode.put( "number", getNumber() );
        return objectNode;
    }

    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
    }

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
