package com.thumann.server.web.controller.article;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.article.Article;
import com.thumann.server.web.controller.tenant.TenantControllerFactory;
import com.thumann.server.web.controller.tenant.TenantShortResponseDTO;
import com.thumann.server.web.response.APIResponseBuilderHelper;
import com.thumann.server.web.response.CreateJsonInterface;

public class ArticleResponseDTO extends APIResponseBuilderHelper implements CreateJsonInterface
{
    private long                   id;

    private String                 number;

    private String                 name;

    private TenantShortResponseDTO tenantShortResponseDTO;

    public void initValues( Article article, TenantControllerFactory tenantFactory )
    {
        setId( article.getId() );
        setName( article.getName() );
        setNumber( article.getNumber() );
        if ( isFieldincluded( "tenant" ) ) {
            setTenantShortResponseDTO( tenantFactory.createShortResponseDTO( article.getTenant() ) );
        }
    }

    @Override
    public JsonNode createJson()
    {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        addValue( objectNode, "id", getId() );
        addValue( objectNode, "name", getName() );
        addValue( objectNode, "number", getNumber() );
        addValue( objectNode, "tenant", getTenantShortResponseDTO() );
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

    public TenantShortResponseDTO getTenantShortResponseDTO()
    {
        return tenantShortResponseDTO;
    }

    public void setTenantShortResponseDTO( TenantShortResponseDTO tenantShortResponseDTO )
    {
        this.tenantShortResponseDTO = tenantShortResponseDTO;
    }

}
