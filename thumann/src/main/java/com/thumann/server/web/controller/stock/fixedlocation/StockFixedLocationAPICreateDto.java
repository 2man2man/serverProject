package com.thumann.server.web.controller.stock.fixedlocation;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.Domain;
import com.thumann.server.domain.article.Article;
import com.thumann.server.domain.warehouse.location.FixedLocation;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.stock.dto.StockFixedLocationCreateDto;
import com.thumann.server.web.exception.APIEntityNotFoundException;
import com.thumann.server.web.exception.APIMissingFieldException;

public class StockFixedLocationAPICreateDto
{
    private long   articleId;

    private long   fixedLocationId;

    private long   quantity;

//    private Date   expirationDate;

    private String lot;

    public StockFixedLocationCreateDto toCreateDto( BaseService baseService )
    {
        StockFixedLocationCreateDto result = new StockFixedLocationCreateDto();
        result.setArticle( baseService.getById( articleId, Article.class ) );
        result.setFixedLocation( baseService.getById( fixedLocationId, FixedLocation.class ) );
        result.setQuantity( quantity );
        result.setLot( lot );
        return result;
    }

    public void initValues( ObjectNode json )
    {
        this.articleId = JsonUtil.getLong( json, "articleId", Domain.UNKOWN_ID );
        this.fixedLocationId = JsonUtil.getLong( json, "fixedLocationId", Domain.UNKOWN_ID );
        this.quantity = JsonUtil.getLong( json, "quantity", Domain.UNKOWN_ID );
        this.lot = JsonUtil.getString( json, "lot" );
    }

    public void check( BaseService baseService )
    {
        if ( quantity <= 0 ) {
            throw APIMissingFieldException.create( "quantity" );
        }
        checkArticle( baseService );
        checkFixedLocation( baseService );
    }

    private void checkFixedLocation( BaseService baseService )
    {
        FixedLocation domain = baseService.getById( fixedLocationId, FixedLocation.class );
        if ( domain == null ) {
            throw APIEntityNotFoundException.create( FixedLocation.class, "id", fixedLocationId );
        }
    }

    private void checkArticle( BaseService baseService )
    {
        Article article = baseService.getById( articleId, Article.class );
        if ( article == null ) {
            throw APIEntityNotFoundException.create( Article.class, "id", articleId );
        }
        if ( !baseService.checkTenant( article ) ) {
            throw APIEntityNotFoundException.create( Article.class, "id", articleId );
        }
    }

}
