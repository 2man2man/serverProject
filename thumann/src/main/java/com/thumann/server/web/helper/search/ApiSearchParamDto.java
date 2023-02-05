package com.thumann.server.web.helper.search;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.service.base.BaseService;

public abstract class ApiSearchParamDto
{
    private long page  = 0;

    private long limit = 50;

    public abstract String buildQuery( BaseService baseService );

    public abstract List<JsonNode> createJson( List<Long> ids );

    public void initParams( ObjectNode givenJson )
    {
        this.page = JsonUtil.getLong( givenJson, "page", 0L );
        this.limit = JsonUtil.getLong( givenJson, "limit", 50L );
    }

    public long getPage()
    {
        return page;
    }

    public long getLimit()
    {
        return limit;
    }

}
