package com.thumann.server.web.controller.fixedlocation.helper;

import java.util.ArrayList;
import java.util.List;

import com.thumann.server.domain.warehouse.location.FixedLocation;
import com.thumann.server.service.fixedlocation.FixedLocationCreateDto;
import com.thumann.server.service.fixedlocation.FixedLocationService;
import com.thumann.server.service.fixedlocation.FixedLocationUpdateDto;
import com.thumann.server.web.controller.fixedlocation.FixedLocationBatchActionDTO;

public class FixedLocationBatchActionCreateHelper extends FixedLocationBatchActionAbstractHelper
{

    public FixedLocationBatchActionCreateHelper( FixedLocationBatchActionDTO dto, FixedLocationService service )
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

        FixedLocationCreateDto dto = new FixedLocationCreateDto();
        dto.setWarehouseAreaId( this.dto.getWarehouseAreaId() );
        dto.setLocationTypeId( this.dto.getLocationTypeId() );

        for ( String column : columns ) {
            dto.setColumn( column );

            for ( String row : rows ) {
                dto.setRow( row );

                for ( String level : levels ) {
                    dto.setLevel( level );

                    for ( String fragment : fragments ) {
                        dto.setFragment( fragment );

                        FixedLocation location = fixedLocationService.getLocation( row, column, level, fragment );
                        if ( location != null && location.isArchived() ) {
                            location = fixedLocationService.unArchive( location );
                            FixedLocationUpdateDto updateDto = new FixedLocationUpdateDto();
                            updateDto.setFixedLocationId( location.getId() );
                            updateDto.setWarehouseAreaId( this.dto.getWarehouseAreaId() );
                            updateDto.setLocationTypeId( this.dto.getLocationTypeId() );
                            fixedLocationService.update( updateDto );
                        }
                        else if ( location != null && !location.isArchived() ) {
                            continue;
                        }
                        else {
                            fixedLocationService.create( dto );
                        }

                    }
                }
            }
        }
    }
}
