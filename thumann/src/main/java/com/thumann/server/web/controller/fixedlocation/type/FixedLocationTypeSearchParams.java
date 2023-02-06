package com.thumann.server.web.controller.fixedlocation.type;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.web.helper.search.ApiSearchParamDto;

public class FixedLocationTypeSearchParams extends ApiSearchParamDto
{
    private FixedLocationTypeControllerFactory dtofactory;

    public FixedLocationTypeSearchParams( FixedLocationTypeControllerFactory dtofactory )
    {
        this.dtofactory = dtofactory;
    }

    @Override
    public String buildQuery( BaseService baseService )
    {
        StringBuilder sb = new StringBuilder();

        sb.append( " SELECT domain.id " )
          .append( "   FROM FixedLocationType domain " )
          .append( " WHERE 1 = 1 " );

        sb.append( " ORDER BY domain.id asc " );

        return sb.toString();
    }

    @Override
    public void initParams( ObjectNode givenJson )
    {
        super.initParams( givenJson );
//        ObjectNode filter = JsonUtil.getJson( givenJson, "filters" );
    }

    @Override
    public List<JsonNode> createJson( List<Long> ids )
    {
        List<JsonNode> result = new ArrayList<JsonNode>();

        for ( Long id : ids ) {
            FixedLocationTypeResponseDTO dto = dtofactory.createResponseDTO( id );
            result.add( dto.createJson() );
        }

        return result;
    }

}
