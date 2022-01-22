package com.thumann.server.web.controller.tenant;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.web.helper.search.ApiSearchParamDto;

public class TenantSearchParams extends ApiSearchParamDto
{
    private TenantControllerFactory dtofactory;

    public TenantSearchParams( TenantControllerFactory dtofactory )
    {
        this.dtofactory = dtofactory;
    }

    @Override
    public String buildQuery( BaseService baseService )
    {
        StringBuilder sb = new StringBuilder();

        sb.append( " SELECT domain.id " )
          .append( " FROM Tenant domain " )
          .append( " ORDER BY domain.id asc " );

        return sb.toString();
    }

    @Override
    public List<JsonNode> createJson( List<Long> ids )
    {
        List<JsonNode> result = new ArrayList<JsonNode>();

        for ( Long id : ids ) {
            TenantResponseDTO dto = dtofactory.createResponseDTO( id );
            result.add( dto.createJson() );
        }

        return result;
    }

}
