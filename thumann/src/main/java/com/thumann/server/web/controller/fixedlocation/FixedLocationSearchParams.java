package com.thumann.server.web.controller.fixedlocation;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.web.helper.search.ApiSearchParamDto;

public class FixedLocationSearchParams extends ApiSearchParamDto
{
    private FixedLocationControllerFactory dtofactory;

    // TODO: pass this as parameter
    private boolean                        showArchived = false;

    private String                         barcode;

    public FixedLocationSearchParams( FixedLocationControllerFactory dtofactory )
    {
        this.dtofactory = dtofactory;
    }

    @Override
    public String buildQuery( BaseService baseService )
    {
        StringBuilder sb = new StringBuilder();

        sb.append( " SELECT domain.id " )
          .append( "   FROM FixedLocation domain " )
          .append( " WHERE 1 = 1 " );
        if ( !showArchived ) {
            sb.append( " AND domain.archived IS FALSE" );
        }
        if ( !StringUtil.isEmpty( barcode ) ) {
            sb.append( " AND domain.barcode = '" ).append( barcode ).append( "' " );
        }

        sb.append( " ORDER BY " )
          .append( " length(domain.rrow) asc,  domain.rrow  asc, " )
          .append( " length(domain.ccolumn) asc,  domain.ccolumn  asc, " )
          .append( " length(domain.level) asc,  domain.level asc, " )
          .append( " length(domain.fragment) asc,  domain.fragment asc " );

        return sb.toString();
    }

    @Override
    public void initParams( ObjectNode givenJson )
    {
        super.initParams( givenJson );
        ObjectNode filter = JsonUtil.getJson( givenJson, "filters" );
        if ( filter != null ) {
            this.barcode = JsonUtil.getString( filter, "barcode" );
        }
    }

    @Override
    public List<JsonNode> createJson( List<Long> ids )
    {
        List<JsonNode> result = new ArrayList<JsonNode>();

        for ( Long id : ids ) {
            FixedLocationResponseDTO dto = dtofactory.createResponseDTO( id );
            result.add( dto.createJson() );
        }

        return result;
    }

}
