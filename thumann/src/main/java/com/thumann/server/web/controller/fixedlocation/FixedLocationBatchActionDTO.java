package com.thumann.server.web.controller.fixedlocation;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.Domain;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.web.exception.APIBadRequestException;
import com.thumann.server.web.exception.APIMissingFieldException;

public class FixedLocationBatchActionDTO
{
    private FixedLocationBatchActionMode mode;

    private long                         warehouseAreaId;

    private long                         locationTypeId;

    private String                       fromRow;

    private String                       toRow;

    private String                       fromColumn;

    private String                       toColumn;

    private String                       fromLevel;

    private String                       toLevel;

    private String                       fromFragment;

    private String                       toFragment;

    public void initValues( ObjectNode json )
    {
        try {
            String modeString = JsonUtil.getString( json, "mode" );
            modeString = modeString.toUpperCase();
            setMode( FixedLocationBatchActionMode.valueOf( modeString ) );
        }
        catch ( Exception e ) {
        }

        setWarehouseAreaId( JsonUtil.getLong( json, "warehouseAreaId", Domain.UNKOWN_ID ) );
        setLocationTypeId( JsonUtil.getLong( json, "locationTypeId", Domain.UNKOWN_ID ) );
        setFromRow( JsonUtil.get( json, "fromRow" ) );
        setToRow( JsonUtil.get( json, "toRow" ) );
        setFromColumn( JsonUtil.get( json, "fromColumn" ) );
        setToColumn( JsonUtil.get( json, "toColumn" ) );
        setFromLevel( JsonUtil.get( json, "fromLevel" ) );
        setToLevel( JsonUtil.get( json, "toLevel" ) );
        setFromFragment( JsonUtil.get( json, "fromFragment" ) );
        setToFragment( JsonUtil.get( json, "toFragment" ) );
    }

    public void check()
    {
        if ( mode == null ) {
            throw APIMissingFieldException.create( "mode" );
        }

        checkStringsPairs( "row", fromRow, toRow );
        checkStringsPairs( "column", fromLevel, toLevel );
        checkStringsPairs( "level", fromLevel, toLevel );

        if ( mode == FixedLocationBatchActionMode.CREATE ) {
            if ( warehouseAreaId == Domain.UNKOWN_ID ) {
                throw APIMissingFieldException.create( "warehouseAreaId" );
            }
        }
        else if ( mode == FixedLocationBatchActionMode.CREATE_AND_UPDATE ) {
            if ( warehouseAreaId == Domain.UNKOWN_ID ) {
                throw APIMissingFieldException.create( "warehouseAreaId" );
            }
        }
    }

    private void checkStringsPairs( String identifier, String string1, String string2 )
    {
        int emtpyCount = 0;
        emtpyCount = StringUtil.isEmpty( string1 ) ? emtpyCount + 1 : emtpyCount;
        emtpyCount = StringUtil.isEmpty( string2 ) ? emtpyCount + 1 : emtpyCount;

        if ( emtpyCount == 1 ) {
            throw APIBadRequestException.create( "The given values for " + identifier + " are not valid." );
        }
        else if ( emtpyCount == 0 ) {
            if ( !StringUtil.isLong( string1 ) ) {
                throw APIBadRequestException.create( "The given value " + string1 + " is not a number." );
            }
            if ( !StringUtil.isLong( string2 ) ) {
                throw APIBadRequestException.create( "The given value " + string1 + " is not a number." );
            }
        }

    }

    public String getFromRow()
    {
        return fromRow;
    }

    public void setFromRow( String fromRow )
    {
        this.fromRow = fromRow;
    }

    public String getToRow()
    {
        return toRow;
    }

    public void setToRow( String toRow )
    {
        this.toRow = toRow;
    }

    public String getFromColumn()
    {
        return fromColumn;
    }

    public void setFromColumn( String fromColumn )
    {
        this.fromColumn = fromColumn;
    }

    public String getToColumn()
    {
        return toColumn;
    }

    public void setToColumn( String toColumn )
    {
        this.toColumn = toColumn;
    }

    public String getFromLevel()
    {
        return fromLevel;
    }

    public void setFromLevel( String fromLevel )
    {
        this.fromLevel = fromLevel;
    }

    public String getToLevel()
    {
        return toLevel;
    }

    public void setToLevel( String toLevel )
    {
        this.toLevel = toLevel;
    }

    public String getFromFragment()
    {
        return fromFragment;
    }

    public void setFromFragment( String fromFragment )
    {
        this.fromFragment = fromFragment;
    }

    public String getToFragment()
    {
        return toFragment;
    }

    public void setToFragment( String toFragment )
    {
        this.toFragment = toFragment;
    }

    public FixedLocationBatchActionMode getMode()
    {
        return mode;
    }

    public void setMode( FixedLocationBatchActionMode mode )
    {
        this.mode = mode;
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
