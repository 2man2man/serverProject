package com.thumann.server.web.controller.fixedlocation.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.warehouse.location.FixedLocationType;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.fixedlocation.FixedLocationService;

@Configuration
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
public class FixedLocationTypeControllerFactory
{
    @Autowired
    private BaseService          baseService;

    @Autowired
    private FixedLocationService fixedLocationService;

    public FixedLocationTypeUpdateDTO createUpdateDTO( FixedLocationType toUpdate, ObjectNode node )
    {
        FixedLocationTypeUpdateDTO dto = new FixedLocationTypeUpdateDTO();
        dto.setLocationTypeId( toUpdate.getId() );
        dto.initValues( node );
        dto.check( fixedLocationService );
        return dto;
    }

    public FixedLocationTypeCreateDTO createCreateDTO( ObjectNode node )
    {
        FixedLocationTypeCreateDTO dto = new FixedLocationTypeCreateDTO();
        dto.initValues( node );
        dto.check( fixedLocationService );
        return dto;
    }

    public FixedLocationTypeResponseDTO createResponseDTO( long id )
    {
        FixedLocationType domain = baseService.getById( id, FixedLocationType.class );
        FixedLocationTypeResponseDTO dto = new FixedLocationTypeResponseDTO();
        dto.initValues( domain );
        return dto;
    }

}
