package com.thumann.server.web.controller.stock.fixedlocation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.stock.fixedlocation.StockFixedLocation;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.web.controller.article.ArticleControllerFactory;
import com.thumann.server.web.controller.tenant.TenantControllerFactory;

@Configuration
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
public class StockFixedLocationControllerFactory
{
    @Autowired
    private TenantControllerFactory  tenantFactory;

    @Autowired
    private ArticleControllerFactory articleFactory;

    @Autowired
    private BaseService              baseService;

    public StockFixedLocationAPICreateDto createCreateDto( ObjectNode node )
    {
        StockFixedLocationAPICreateDto dto = new StockFixedLocationAPICreateDto();
        dto.initValues( node );
        dto.check( baseService );
        return dto;
    }

    public StockFixedLocationResponseDTO createResponseDTO( StockFixedLocation domain )
    {
        StockFixedLocationResponseDTO dto = new StockFixedLocationResponseDTO();
        dto.initValues( domain, articleFactory, tenantFactory );
        return dto;
    }

    public StockFixedLocationResponseDTO createResponseDTO( long id )
    {
        StockFixedLocation domain = baseService.getById( id, StockFixedLocation.class );
        return createResponseDTO( domain );
    }

}
