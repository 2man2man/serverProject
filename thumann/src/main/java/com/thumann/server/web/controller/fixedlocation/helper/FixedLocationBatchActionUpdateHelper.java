package com.thumann.server.web.controller.fixedlocation.helper;

import java.util.ArrayList;
import java.util.List;

import com.thumann.server.domain.warehouse.location.FixedLocation;
import com.thumann.server.service.fixedlocation.FixedLocationService;
import com.thumann.server.service.fixedlocation.FixedLocationUpdateDto;
import com.thumann.server.web.controller.fixedlocation.FixedLocationBatchActionDTO;

public class FixedLocationBatchActionUpdateHelper extends FixedLocationBatchActionAbstractHelper
{

    public FixedLocationBatchActionUpdateHelper( FixedLocationBatchActionDTO dto, FixedLocationService service )
    {
        super( dto, service );
        initRanges();
    }

    @Override
    public void execute()
    {
        List<String> fragments = new ArrayList<>( this.fragments );
        if ( fragments.isEmpty() ) {
            fragments.add( null );
        }

        for ( String column : columns ) {
            for ( String row : rows ) {
                for ( String level : levels ) {
                    for ( String fragment : fragments ) {
                        FixedLocation location = fixedLocationService.getLocation( row, column, level, fragment );
                        if ( location == null ) {
                            continue;
                        }
                        FixedLocationUpdateDto dto = new FixedLocationUpdateDto();
                        dto.setFixedLocationId( location.getId() );
                        dto.setWarehouseAreaId( this.dto.getWarehouseAreaId() );
                        dto.setLocationTypeId( this.dto.getLocationTypeId() );
                        fixedLocationService.update( dto );
                    }
                }
            }
        }
    }
}
