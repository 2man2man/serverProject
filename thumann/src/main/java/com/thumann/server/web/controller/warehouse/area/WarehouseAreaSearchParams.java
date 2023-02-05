package com.thumann.server.web.controller.warehouse.area;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.Domain;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.web.helper.search.ApiSearchParamDto;

public class WarehouseAreaSearchParams extends ApiSearchParamDto
{
    private WarehouseAreaControllerFactory dtofactory;

    private long                           warehouseId = Domain.UNKOWN_ID;

    public WarehouseAreaSearchParams( WarehouseAreaControllerFactory dtofactory )
    {
        this.dtofactory = dtofactory;
    }

    @Override
    public String buildQuery( BaseService baseService )
    {
        StringBuilder sb = new StringBuilder();

        sb.append( " SELECT domain.id " )
          .append( "   FROM WarehouseArea domain " )
          .append( " WHERE 1 = 1 " );

        if ( this.warehouseId != Domain.UNKOWN_ID ) {
            sb.append( " AND domain.warehouse.id = " ).append( this.warehouseId ).append( " " );
        }

        sb.append( " ORDER BY domain.id asc " );

        return sb.toString();
    }

    @Override
    public void initParams( ObjectNode givenJson )
    {
        super.initParams( givenJson );
        ObjectNode filter = JsonUtil.getJson( givenJson, "filters" );
        if ( filter != null ) {
            this.warehouseId = JsonUtil.getLong( filter, "warehouseId", Domain.UNKOWN_ID );
        }
    }

    @Override
    public List<JsonNode> createJson( List<Long> ids )
    {
        List<JsonNode> result = new ArrayList<JsonNode>();

        for ( Long id : ids ) {
            WarehouseAreaResponseDTO dto = dtofactory.createResponseDTO( id );
            result.add( dto.createJson() );
        }

        return result;
    }

}
