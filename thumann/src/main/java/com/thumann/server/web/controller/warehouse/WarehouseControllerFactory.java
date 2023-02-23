package com.thumann.server.web.controller.warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.warehouse.Warehouse;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.warehouse.WarehouseService;

@Configuration
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
public class WarehouseControllerFactory
{
    @Autowired
    private BaseService baseService;

    public WarehouseAPIUpdateDTO createUpdateDTO( Warehouse toUpdate, ObjectNode node, WarehouseService service )
    {
        WarehouseAPIUpdateDTO dto = new WarehouseAPIUpdateDTO();
        dto.setWarehouseId( toUpdate.getId() );
        dto.initValues( node );
        dto.check( service );
        return dto;
    }

    public WarehouseResponseDTO createResponseDTO( long id )
    {
        Warehouse domain = baseService.getById( id, Warehouse.class );
        WarehouseResponseDTO dto = new WarehouseResponseDTO();
        dto.initValues( domain );
        return dto;
    }

    public WarehouseShortResponseDTO createShortResponseDTO( Warehouse domain )
    {
        WarehouseShortResponseDTO dto = new WarehouseShortResponseDTO();
        dto.initValues( domain );
        return dto;
    }

}
