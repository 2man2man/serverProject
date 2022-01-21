package com.thumann.server.web.controller.employee;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.web.helper.search.ApiSearchParamDto;

public class EmployeeSearchParams extends ApiSearchParamDto
{
    private EmployeeControllerFactory dtofactory;

    public EmployeeSearchParams( EmployeeControllerFactory dtofactory )
    {
        this.dtofactory = dtofactory;
    }

    @Override
    public String buildQuery( BaseService baseService )
    {
        StringBuilder sb = new StringBuilder();

        sb.append( " SELECT domain.id " )
          .append( " FROM Employee domain " )
          .append( " ORDER BY domain.id asc " );

        return sb.toString();
    }

    @Override
    public List<JsonNode> createJson( List<Long> ids )
    {
        List<JsonNode> result = new ArrayList<JsonNode>();

        for ( Long id : ids ) {
            EmployeeResponseDTO dto = dtofactory.createResponseDTO( id );
            result.add( dto.createJson() );
        }

        return result;
    }

}
