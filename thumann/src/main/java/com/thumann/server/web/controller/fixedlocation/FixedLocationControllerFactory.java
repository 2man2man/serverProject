package com.thumann.server.web.controller.fixedlocation;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.warehouse.location.FixedLocation;
import com.thumann.server.helper.collection.CollectionUtil;
import com.thumann.server.service.base.BaseService;

@Configuration
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
public class FixedLocationControllerFactory
{
    @Autowired
    private BaseService baseService;

    public FixedLocationBatchActionDTO createBatchActionDTO( ObjectNode node )
    {
        FixedLocationBatchActionDTO dto = new FixedLocationBatchActionDTO();
        dto.initValues( node );
        dto.check();
        return dto;
    }

    public FixedLocationResponseDTO createResponseDTO( long id )
    {
        Set<String> eager = CollectionUtil.setOf( "warehouseArea", "locationType" );
        FixedLocation domain = baseService.getById( id, FixedLocation.class, eager );
        FixedLocationResponseDTO dto = new FixedLocationResponseDTO();
        dto.initValues( domain );
        return dto;
    }

}
