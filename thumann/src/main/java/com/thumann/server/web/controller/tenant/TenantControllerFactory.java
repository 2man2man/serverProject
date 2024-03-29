package com.thumann.server.web.controller.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.service.base.ApplicationRunnable;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.tenant.TenantService;

@Configuration
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
public class TenantControllerFactory
{
    @Autowired
    private BaseService baseService;

    public TenantCreateDto createCreateDTO( ObjectNode node, TenantService tenantService )
    {
        TenantCreateDto dto = new TenantCreateDto();
        dto.initValues( node );
        dto.check( tenantService );
        return dto;
    }

    public TenantUpdateDto createUpdateDTO( Tenant tenantToUpdate, ObjectNode node, TenantService tenantService )
    {
        TenantUpdateDto dto = new TenantUpdateDto();
        dto.initValues( node, tenantToUpdate );
        dto.check( tenantService );
        return dto;
    }

    public TenantResponseDTO createResponseDTO( long id )
    {
        Tenant tenant = baseService.getById( id, Tenant.class );
        TenantResponseDTO dto = new TenantResponseDTO();
        dto.initValues( tenant );
        return dto;
    }

    public TenantShortResponseDTO createShortResponseDTO( Tenant tenant )
    {
        try {
            return (TenantShortResponseDTO) baseService.executeWithTransaction( new CreateShortTenantRunnable(), tenant.getId() );
        }
        catch ( Exception e ) {
            throw new IllegalArgumentException( "Programming error: " + e.getMessage() );
        }
    }

    private class CreateShortTenantRunnable implements ApplicationRunnable
    {
        @Override
        public TenantShortResponseDTO run( Object tenantIdObj ) throws Exception
        {
            Long tenantId = (Long) tenantIdObj;
            Tenant tenant = baseService.getMananger().find( Tenant.class, tenantId );

            TenantShortResponseDTO dto = new TenantShortResponseDTO();
            dto.initValues( tenant );
            return dto;
        }
    }

}
