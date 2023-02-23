package com.thumann.server.service.fixedlocation;

import java.util.Set;

import com.thumann.server.domain.warehouse.location.FixedLocation;
import com.thumann.server.domain.warehouse.location.FixedLocationType;
import com.thumann.server.web.controller.fixedlocation.type.FixedLocationTypeCreateDTO;
import com.thumann.server.web.controller.fixedlocation.type.FixedLocationTypeUpdateDTO;

public interface FixedLocationService
{
    FixedLocationType create( FixedLocationTypeCreateDTO createDto );

    FixedLocationType update( FixedLocationTypeUpdateDTO updateDto );

    FixedLocationType getLocationTypeByNumber( String number );

    // -----------------------------------------------------------------------------------------

    FixedLocation getLocation( String row, String column, String level, String fragment );

    FixedLocation create( FixedLocationCreateDto dto );

    FixedLocation update( FixedLocationUpdateDto dto );

    void deleteOrArchive( FixedLocation fixedLocation );

    FixedLocation unArchive( FixedLocation fixedLocation );

    FixedLocation load( long id );

    FixedLocation load( long id, Set<String> eager );

}
