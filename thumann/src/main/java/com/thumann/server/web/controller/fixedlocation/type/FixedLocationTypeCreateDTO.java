package com.thumann.server.web.controller.fixedlocation.type;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.warehouse.location.FixedLocationType;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.service.fixedlocation.FixedLocationService;
import com.thumann.server.web.exception.APIMissingFieldException;
import com.thumann.server.web.exception.APINumberConflictException;

public class FixedLocationTypeCreateDTO extends FixedLocationTypeCreateUpdateDTO
{
    @Override
    public void initValues( ObjectNode json )
    {
        super.initValues( json );
    }

    public void check( FixedLocationService locationService )
    {
        if ( StringUtil.isEmpty( getNumber() ) ) {
            throw APIMissingFieldException.create( "number" );
        }
        if ( StringUtil.isEmpty( getName() ) ) {
            throw APIMissingFieldException.create( "name" );
        }

        FixedLocationType byNumber = locationService.getLocationTypeByNumber( getNumber() );
        if ( byNumber != null ) {
            throw APINumberConflictException.create( FixedLocationType.class, "number", getNumber() );
        }

    }

}
