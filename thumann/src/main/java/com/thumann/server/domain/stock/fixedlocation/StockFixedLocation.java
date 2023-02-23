package com.thumann.server.domain.stock.fixedlocation;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.thumann.server.domain.Domain;
import com.thumann.server.domain.article.Article;
import com.thumann.server.domain.stock.StockDetail;
import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.domain.user.Employee;
import com.thumann.server.domain.user.Person;
import com.thumann.server.domain.warehouse.location.FixedLocation;

@Entity
public class StockFixedLocation extends Domain
{
    private static final long serialVersionUID = 839003715402121480L;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    private Tenant            tenant;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    private Article           article;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    private FixedLocation     fixedLocation;

    @Column( columnDefinition = "BIGINT DEFAULT 0" )
    private long              quantity         = 0;

    @OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false )
    private StockDetail       stockDetail;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    private Employee          creator;

    @Column( nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Date              creationDate;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    private Employee          lastUpdatedBy;

    @Column( nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Date              lastUpdatedDate;

    public void initCreation( Employee caller )
    {
        this.creator = caller;
        this.lastUpdatedBy = caller;
        this.creationDate = new Date();
        this.lastUpdatedDate = new Date( this.creationDate.getTime() );
    }

    public void initUpdate( Employee updater )
    {
        this.lastUpdatedBy = updater;
        this.lastUpdatedDate = new Date();
    }

    public Tenant getTenant()
    {
        return tenant;
    }

    public void setTenant( Tenant tenant )
    {
        this.tenant = tenant;
    }

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

    public StockDetail getStockDetail()
    {
        return stockDetail;
    }

    public void setStockDetail( StockDetail stockDetail )
    {
        this.stockDetail = stockDetail;
    }

    public Person getCreator()
    {
        return creator;
    }

    public Date getCreationDate()
    {
        if ( creationDate == null ) {
            return null;
        }
        return new Date( this.creationDate.getTime() );
    }

    public Person getLastUpdatedBy()
    {
        return lastUpdatedBy;
    }

    public Date getLastUpdatedDate()
    {
        if ( lastUpdatedDate == null ) {
            return null;
        }
        return new Date( this.lastUpdatedDate.getTime() );
    }

}
