package com.thumann.server.web.controller.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.tenant.TenantService;

@Configuration
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
public class TenantControllerFactory
{
    @Autowired
    private BaseService baseService;

    public TenantCreateDTO createCreateDTO( ObjectNode node, TenantService tenantService )
    {
        TenantCreateDTO dto = new TenantCreateDTO();
        dto.initValues( node );
        dto.check( tenantService );
        return dto;
    }

    public TenantResponseDTO createResponseDTO( Tenant tenant )
    {
        TenantResponseDTO dto = new TenantResponseDTO();
        dto.initValues( tenant );
        return dto;
    }

    public TenantResponseDTO createShortResponseDTO( Tenant tenant )
    {
        TenantResponseDTO dto = new TenantResponseDTO();
        dto.initValues( tenant );
        return dto;
    }

}
