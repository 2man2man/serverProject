package com.thumann.server.web.controller.article;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.article.Article;
import com.thumann.server.web.response.APIResponseBuilderHelper;
import com.thumann.server.web.response.CreateJsonInterface;

public class ArticleResponseDTO extends APIResponseBuilderHelper implements CreateJsonInterface
{
    private long   id;

    private String number;

    private String name;

    public void initValues( Article article )
    {
        setId( article.getId() );
        setName( article.getName() );
        setNumber( article.getNumber() );
    }

    @Override
    public JsonNode createJson()
    {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        addValue( objectNode, "id", getId() );
        addValue( objectNode, "name", getName() );
        addValue( objectNode, "number", getNumber() );
        return objectNode;
    }

    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber( String number )
    {
        this.number = number;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

}
