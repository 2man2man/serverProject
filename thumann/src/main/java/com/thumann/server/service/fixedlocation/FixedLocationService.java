package com.thumann.server.service.fixedlocation;

import com.thumann.server.domain.warehouse.location.FixedLocationType;
import com.thumann.server.web.controller.fixedlocation.type.FixedLocationTypeCreateDTO;
import com.thumann.server.web.controller.fixedlocation.type.FixedLocationTypeUpdateDTO;

public interface FixedLocationService
{
    FixedLocationType create( FixedLocationTypeCreateDTO createDto );

    FixedLocationType update( FixedLocationTypeUpdateDTO updateDto );

    FixedLocationType getLocationTypeByNumber( String number );
}
