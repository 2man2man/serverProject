package com.thumann.server.web.controller.tenant;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.web.response.CreateJsonInterface;

public class TenantShortResponseDTO implements CreateJsonInterface
{
    private long   id;

    private String number;

    private String name;

    public void initValues( Tenant tenant )
    {
        setId( tenant.getId() );
        setName( tenant.getName() );
        setNumber( tenant.getNumber() );
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
