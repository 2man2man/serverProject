package com.thumann.server.web.controller.warehouse.area;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.warehouse.area.WarehouseArea;
import com.thumann.server.service.base.BaseService;

@Configuration
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
public class WarehouseAreaControllerFactory
{
    @Autowired
    private BaseService baseService;

    public WarehouseAreaUpdateDTO createUpdateDTO( WarehouseArea toUpdate, ObjectNode node )
    {
        WarehouseAreaUpdateDTO dto = new WarehouseAreaUpdateDTO();
        dto.setWarehouseAreaId( toUpdate.getId() );
        dto.initValues( node );
        return dto;
    }

    public WarehouseAreaCreateDTO createCreateDTO( ObjectNode node )
    {
        WarehouseAreaCreateDTO dto = new WarehouseAreaCreateDTO();
        dto.initValues( node );
        dto.check();
        return dto;
    }

    public WarehouseAreaResponseDTO createResponseDTO( long id )
    {
        WarehouseArea domain = baseService.getById( id, WarehouseArea.class );
        WarehouseAreaResponseDTO dto = new WarehouseAreaResponseDTO();
        dto.initValues( domain );
        return dto;
    }

}
