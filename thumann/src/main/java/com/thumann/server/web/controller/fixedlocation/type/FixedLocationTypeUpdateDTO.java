package com.thumann.server.web.controller.fixedlocation.type;

import com.thumann.server.domain.Domain;
import com.thumann.server.domain.warehouse.location.FixedLocationType;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.service.fixedlocation.FixedLocationService;
import com.thumann.server.web.exception.APIMissingFieldException;
import com.thumann.server.web.exception.APINumberConflictException;

public class FixedLocationTypeUpdateDTO extends FixedLocationTypeCreateUpdateDTO
{
    private long locationTypeId;

    public void check( FixedLocationService locationService )
    {
        if ( locationTypeId == Domain.UNKOWN_ID ) {
            throw APIMissingFieldException.create( "locationTypeId" );
        }

        if ( !StringUtil.isEmpty( getNumber() ) ) {
            FixedLocationType byNumber = locationService.getLocationTypeByNumber( getNumber() );
            if ( byNumber != null && byNumber.getId() != getLocationTypeId() ) {
                throw APINumberConflictException.create( FixedLocationType.class, "number", getNumber() );
            }
        }

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
