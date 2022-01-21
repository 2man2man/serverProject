package com.thumann.server.web.helper.search;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.service.base.BaseService;

public abstract class ApiSearchParamDto
{
    private int page  = 0;

    private int limit = 50;

    public abstract String buildQuery( BaseService baseService );

    public abstract List<JsonNode> createJson( List<Long> ids );

    public void initParams( ObjectNode givenJson )
    {
        this.page = JsonUtil.getInteger( givenJson, "page", 0 );
        this.limit = JsonUtil.getInteger( givenJson, "limit", 50 );
    }

    public int getPage()
    {
        return page;
    }

    public int getLimit()
    {
        return limit;
    }

}
