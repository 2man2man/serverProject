package com.thumann.server.web.controller.fixedlocation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.warehouse.location.FixedLocation;
import com.thumann.server.web.response.CreateJsonInterface;

public class FixedLocationResponseDTO implements CreateJsonInterface
{
    private long   id;

    private long   warehouseAreaId;

    private String warehouseAreaString;

    private long   locationTypeId;

    private String locationTypeString;

    private String barcode;

    private String number;

    private String row;

    private String column;

    private String level;

    private String fragment;

    public void initValues( FixedLocation domain )
    {
        setId( domain.getId() );
        setNumber( domain.getNumber() );
        setRow( domain.getRow() );
        setColumn( domain.getColumn() );
        setLevel( domain.getLevel() );
        setFragment( domain.getFragment() );

        if ( domain.getWarehouseArea() != null ) {
            setWarehouseAreaId( domain.getWarehouseArea().getId() );
            setWarehouseAreaString( domain.getWarehouseArea().getNumber() + " " + domain.getWarehouseArea().getName() );
        }
        if ( domain.getLocationType() != null ) {
            setLocationTypeId( domain.getLocationType().getId() );
            setLocationTypeString( domain.getLocationType().getNumber() + " " + domain.getLocationType().getName() );
        }
    }

    @Override
    public JsonNode createJson()
    {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put( "id", getId() );
        objectNode.put( "number", getNumber() );
        objectNode.put( "warehouseAreaId", getWarehouseAreaId() );
        objectNode.put( "warehouseAreaString", getWarehouseAreaString() );
        objectNode.put( "locationTypeId", getLocationTypeId() );
        objectNode.put( "locationTypeString", getLocationTypeString() );
        objectNode.put( "row", getRow() );
        objectNode.put( "column", getColumn() );
        objectNode.put( "level", getLevel() );
        objectNode.put( "fragment", getFragment() );
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

    public String getWarehouseAreaString()
    {
        return warehouseAreaString;
    }

    public void setWarehouseAreaString( String warehouseAreaString )
    {
        this.warehouseAreaString = warehouseAreaString;
    }

    public String getLocationTypeString()
    {
        return locationTypeString;
    }

    public void setLocationTypeString( String locationTypeString )
    {
        this.locationTypeString = locationTypeString;
    }

    public String getBarcode()
    {
        return barcode;
    }

    public void setBarcode( String barcode )
    {
        this.barcode = barcode;
    }

    public String getRow()
    {
        return row;
    }

    public void setRow( String row )
    {
        this.row = row;
    }

    public String getColumn()
    {
        return column;
    }

    public void setColumn( String column )
    {
        this.column = column;
    }

    public String getLevel()
    {
        return level;
    }

    public void setLevel( String level )
    {
        this.level = level;
    }

    public String getFragment()
    {
        return fragment;
    }

    public void setFragment( String fragment )
    {
        this.fragment = fragment;
    }

    public long getWarehouseAreaId()
    {
        return warehouseAreaId;
    }

    public void setWarehouseAreaId( long warehouseAreaId )
    {
        this.warehouseAreaId = warehouseAreaId;
    }

    public long getLocationTypeId()
    {
        return locationTypeId;
    }

    public void setLocationTypeId( long locationTypeId )
    {
        this.locationTypeId = locationTypeId;
    }

}
