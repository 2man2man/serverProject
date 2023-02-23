package com.thumann.server.web.controller.warehouse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.warehouse.Warehouse;
import com.thumann.server.web.response.CreateJsonInterface;

public class WarehouseShortResponseDTO implements CreateJsonInterface
{
    private long   id;

    private String number;

    private String name;

    public void initValues( Warehouse domain )
    {
        setId( domain.getId() );
        setName( domain.getName() );
        setNumber( domain.getNumber() );
    }

    @Override
    public JsonNode createJson()
    {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put( "id", getId() );
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

}
