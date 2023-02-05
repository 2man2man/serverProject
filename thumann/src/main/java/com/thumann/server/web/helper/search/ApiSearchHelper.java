package com.thumann.server.web.helper.search;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.helper.collection.CollectionUtil;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.service.base.BaseService;

public class ApiSearchHelper
{
    private BaseService baseService;

    public ApiSearchHelper( BaseService baseService )
    {
        this.baseService = baseService;
    }

    public JsonNode executeSearch( ApiSearchParamDto seachparam )
    {
        List<Long> ids = getIds( seachparam );

        long fromIndex = seachparam.getPage() * seachparam.getLimit();
        long toIndex = ( seachparam.getPage() + 1 ) * seachparam.getLimit();
        List<Long> subList = CollectionUtil.safeSubList( ids, fromIndex, toIndex );

        List<JsonNode> jsons = seachparam.createJson( subList );

        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put( "page", seachparam.getPage() );
        objectNode.put( "limit", seachparam.getLimit() );
        objectNode.put( "totalResults", ids.size() );

        JsonUtil.putArray( objectNode, "results", jsons );

        return objectNode;

    }

    private List<Long> getIds( ApiSearchParamDto seachparam )
    {
        String queryString = seachparam.buildQuery( baseService );
        return baseService.getObjectsByQuery( queryString, Long.class );
    }

}
