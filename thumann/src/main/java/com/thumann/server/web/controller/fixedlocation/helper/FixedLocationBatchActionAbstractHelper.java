package com.thumann.server.web.controller.fixedlocation.helper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.service.fixedlocation.FixedLocationService;
import com.thumann.server.web.controller.fixedlocation.FixedLocationBatchActionDTO;
import com.thumann.server.web.controller.fixedlocation.FixedLocationBatchActionMode;

public abstract class FixedLocationBatchActionAbstractHelper
{
    public static FixedLocationBatchActionAbstractHelper create( FixedLocationBatchActionDTO dto, FixedLocationService service )
    {
        FixedLocationBatchActionMode mode = dto.getMode();
        if ( mode == FixedLocationBatchActionMode.CREATE ) {
            return new FixedLocationBatchActionCreateHelper( dto, service );
        }
        if ( mode == FixedLocationBatchActionMode.UPDATE ) {
            return new FixedLocationBatchActionUpdateHelper( dto, service );
        }
        if ( mode == FixedLocationBatchActionMode.CREATE_AND_UPDATE ) {
            return new FixedLocationBatchActionCreateAndUpdateHelper( dto, service );
        }
        else if ( mode == FixedLocationBatchActionMode.DELETE ) {
            return new FixedLocationBatchActionDeleteHelper( dto, service );
        }
        throw new IllegalArgumentException( "Mode [" + mode + "] is not implemented!" );
    }

    protected FixedLocationService        fixedLocationService;

    protected FixedLocationBatchActionDTO dto;

    protected List<String>                columns   = new ArrayList<String>();

    protected List<String>                rows      = new ArrayList<String>();

    protected List<String>                levels    = new ArrayList<String>();

    protected List<String>                fragments = new ArrayList<String>();

    protected FixedLocationBatchActionAbstractHelper( FixedLocationBatchActionDTO dto, FixedLocationService service )
    {
        this.dto = dto;
        this.fixedLocationService = service;
    }

    public abstract void execute();

    protected void initRanges()
    {
        if ( !StringUtil.isEmpty( dto.getFromColumn() ) && !StringUtil.isEmpty( dto.getToColumn() ) ) {
            this.columns.addAll( getStringRange( dto.getFromColumn(), dto.getToColumn() ) );
        }
        if ( !StringUtil.isEmpty( dto.getFromRow() ) && !StringUtil.isEmpty( dto.getToRow() ) ) {
            this.rows.addAll( getStringRange( dto.getFromRow(), dto.getToRow() ) );
        }
        if ( !StringUtil.isEmpty( dto.getFromLevel() ) && !StringUtil.isEmpty( dto.getToLevel() ) ) {
            this.levels.addAll( getStringRange( dto.getFromLevel(), dto.getToLevel() ) );
        }
        if ( !StringUtil.isEmpty( dto.getFromFragment() ) && !StringUtil.isEmpty( dto.getToFragment() ) ) {
            fragments.addAll( getStringRange( dto.getFromFragment(), dto.getToFragment() ) );
        }
    }

    public BigInteger getTotalCount()
    {
        int columnsCount = Math.max( 1, columns.size() );
        int rowCount = Math.max( 1, rows.size() );
        int levelCount = Math.max( 1, levels.size() );
        int fragmentCount = Math.max( 1, fragments.size() );

        return BigInteger.valueOf( columnsCount )
                         .multiply( BigInteger.valueOf( rowCount ) )
                         .multiply( BigInteger.valueOf( levelCount ) )
                         .multiply( BigInteger.valueOf( fragmentCount ) );
    }

    private List<String> getStringRange( String string1, String string2 )
    {
        List<String> result = new ArrayList<String>();
        if ( StringUtil.isLong( string1 ) && StringUtil.isLong( string2 ) ) {
            long long1 = Long.valueOf( string1 );
            long long2 = Long.valueOf( string2 );
            for ( long l = long1; l <= long2; l++ ) {
                result.add( String.valueOf( l ) );
            }
        }
        else {
            throw new IllegalArgumentException( "Not implemented!" );
        }
        return result;
    }
}