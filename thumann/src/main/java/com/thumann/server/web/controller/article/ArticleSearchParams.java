package com.thumann.server.web.controller.article;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.Domain;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.web.helper.search.ApiSearchParamDto;

public class ArticleSearchParams extends ApiSearchParamDto
{
    private ArticleControllerFactory dtofactory;

    private String                   articleNumber;

    private long                     tenantId = Domain.UNKOWN_ID;

    public ArticleSearchParams( ArticleControllerFactory dtofactory )
    {
        this.dtofactory = dtofactory;
    }

    @Override
    public String buildQuery( BaseService baseService )
    {
        Set<Long> callerTenantIds = baseService.getCallerTenantIds();

        StringBuilder sb = new StringBuilder();

        sb.append( " SELECT domain.id " )
          .append( "   FROM Article domain " )
          .append( "  WHERE domain.tenant.id IN (" ).append( StringUtil.combineWithSeparator( callerTenantIds, "," ) ).append( ") " );

        if ( tenantId != Domain.UNKOWN_ID ) {
            sb.append( " AND domain.tenant.id  = " ).append( this.tenantId ).append( " " );
        }
        if ( !StringUtil.isEmpty( articleNumber ) ) {
            sb.append( " AND domain.number  = '" ).append( this.articleNumber ).append( "' " );
        }

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
        this.tenantId = JsonUtil.getLong( filter, "tenantId", Domain.UNKOWN_ID );
        this.articleNumber = JsonUtil.getString( filter, "articleNumber" );
    }

    @Override
    public List<JsonNode> createJson( List<Long> ids )
    {
        List<JsonNode> result = new ArrayList<JsonNode>();

        for ( Long id : ids ) {
            ArticleResponseDTO dto = dtofactory.createResponseDTO( id );
            result.add( dto.createJson() );
        }

        return result;
    }

}
