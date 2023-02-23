package com.thumann.server.web.controller.stock.fixedlocation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.stock.fixedlocation.StockFixedLocation;
import com.thumann.server.web.controller.article.ArticleControllerFactory;
import com.thumann.server.web.controller.article.ArticleShortResponseDTO;
import com.thumann.server.web.controller.tenant.TenantControllerFactory;
import com.thumann.server.web.controller.tenant.TenantShortResponseDTO;
import com.thumann.server.web.response.APIResponseBuilderHelper;
import com.thumann.server.web.response.CreateJsonInterface;

public class StockFixedLocationResponseDTO extends APIResponseBuilderHelper implements CreateJsonInterface
{
    private long                    id;

    private String                  fixedLocationNumber;

    private ArticleShortResponseDTO articleResponseDTO;

    private long                    quantity;

    private TenantShortResponseDTO  tenantShortResponseDTO;

    public void initValues( StockFixedLocation stock,
                            ArticleControllerFactory articleFactory,
                            TenantControllerFactory tenantFactory )
    {
        setId( stock.getId() );
        setFixedLocationNumber( stock.getFixedLocation().getNumber() );
        setArticleResponseDTO( articleFactory.createShortResponseDTO( stock.getArticle() ) );
        setTenantShortResponseDTO( tenantFactory.createShortResponseDTO( stock.getTenant() ) );
        this.quantity = stock.getQuantity();

    }

    @Override
    public JsonNode createJson()
    {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        addValue( objectNode, "id", getId() );
        addValue( objectNode, "fixedLocationNumber", getFixedLocationNumber() );
        addValue( objectNode, "article", getArticleResponseDTO() );
        addValue( objectNode, "tenant", getTenantShortResponseDTO() );
        addValue( objectNode, "quantity", this.quantity );
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

    public String getFixedLocationNumber()
    {
        return fixedLocationNumber;
    }

    public void setFixedLocationNumber( String fixedLocationNumber )
    {
        this.fixedLocationNumber = fixedLocationNumber;
    }

    public ArticleShortResponseDTO getArticleResponseDTO()
    {
        return articleResponseDTO;
    }

    public void setArticleResponseDTO( ArticleShortResponseDTO articleResponseDTO )
    {
        this.articleResponseDTO = articleResponseDTO;
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
