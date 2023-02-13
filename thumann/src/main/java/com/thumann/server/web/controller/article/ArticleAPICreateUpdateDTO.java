package com.thumann.server.web.controller.article;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.helper.json.JsonUtil;

public class ArticleAPICreateUpdateDTO
{
    private String name;

    private String number;

    protected void initValues( ObjectNode json )
    {
        setName( JsonUtil.getString( json, "name" ) );
        setNumber( JsonUtil.getString( json, "number" ) );
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber( String number )
    {
        this.number = number;
    }

}
