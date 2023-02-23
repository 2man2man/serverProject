package com.thumann.server.web.controller.stock.fixedlocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.web.helper.search.ApiSearchParamDto;

public class StockFixedLocationSearchParams extends ApiSearchParamDto
{
    private StockFixedLocationControllerFactory dtofactory;

    public StockFixedLocationSearchParams( StockFixedLocationControllerFactory dtofactory )
    {
        this.dtofactory = dtofactory;
    }

    @Override
    public String buildQuery( BaseService baseService )
    {
        Set<Long> callerTenantIds = baseService.getCallerTenantIds();

        StringBuilder sb = new StringBuilder();

        sb.append( " SELECT domain.id " )
          .append( "   FROM StockFixedLocation domain " )
          .append( "  WHERE domain.tenant.id IN (" ).append( StringUtil.combineWithSeparator( callerTenantIds, "," ) ).append( ") " );

        sb.append( " ORDER BY domain.id asc " );

        return sb.toString();
    }

    @Override
    public void initParams( ObjectNode givenJson )
    {
        super.initParams( givenJson );
        ObjectNode filter = JsonUtil.getJson( givenJson, "filters" );
        if ( filter == null ) {
            return;
        }
    }

    @Override
    public List<JsonNode> createJson( List<Long> ids )
    {
        List<JsonNode> result = new ArrayList<JsonNode>();

        for ( Long id : ids ) {
            StockFixedLocationResponseDTO dto = dtofactory.createResponseDTO( id );
            result.add( dto.createJson() );
        }
        return result;
    }

}
