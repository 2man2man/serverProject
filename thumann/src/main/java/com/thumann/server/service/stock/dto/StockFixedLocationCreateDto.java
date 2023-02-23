package com.thumann.server.service.stock.dto;

import java.util.Date;

import com.thumann.server.domain.article.Article;
import com.thumann.server.domain.warehouse.location.FixedLocation;

public class StockFixedLocationCreateDto
{
    private Article       article;

    private FixedLocation fixedLocation;

    private long          quantity;

    private Date          expirationDate;

    private String        lot;

    public Article getArticle()
    {
        return article;
    }

    public void setArticle( Article article )
    {
        this.article = article;
    }

    public FixedLocation getFixedLocation()
    {
        return fixedLocation;
    }

    public void setFixedLocation( FixedLocation fixedLocation )
    {
        this.fixedLocation = fixedLocation;
    }

    public long getQuantity()
    {
        return quantity;
    }

    public void setQuantity( long quantity )
    {
        this.quantity = quantity;
    }

    public Date getExpirationDate()
    {
        return expirationDate;
    }

    public void setExpirationDate( Date expirationDate )
    {
        this.expirationDate = expirationDate;
    }

    public String getLot()
    {
        return lot;
    }

    public void setLot( String lot )
    {
        this.lot = lot;
    }

}
