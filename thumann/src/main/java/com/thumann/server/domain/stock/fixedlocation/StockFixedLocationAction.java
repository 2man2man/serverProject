package com.thumann.server.domain.stock.fixedlocation;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.thumann.server.domain.Domain;
import com.thumann.server.domain.article.Article;
import com.thumann.server.domain.logisticexecution.LogisticExecutionType;
import com.thumann.server.domain.stock.StockDetail;
import com.thumann.server.domain.user.Employee;
import com.thumann.server.domain.warehouse.location.FixedLocation;

@MappedSuperclass
public abstract class StockFixedLocationAction extends Domain
{
    private static final long     serialVersionUID = -1606200324285408879L;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    private Article               article;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    private FixedLocation         fixedLocation;

    @Column( columnDefinition = "BIGINT DEFAULT 0" )
    private long                  quantity         = 0;

    @OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    private StockDetail           stockDetail;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    private Employee              creator;

    @Column( nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Date                  creationDate;

    @Column( nullable = false )
    @Enumerated( EnumType.STRING )
    private LogisticExecutionType logisticExecutionType;

    @Column( columnDefinition = "BIGINT DEFAULT 0" )
    private long                  executionId      = Domain.UNKOWN_ID;

    public void initCreator( Employee creator )
    {
        this.creationDate = new Date();
        this.creator = creator;
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

    public Employee getCreator()
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

    public LogisticExecutionType getLogisticExecutionType()
    {
        return logisticExecutionType;
    }

    public void setLogisticExecutionType( LogisticExecutionType logisticExecutionType )
    {
        this.logisticExecutionType = logisticExecutionType;
    }

    public long getExecutionId()
    {
        return executionId;
    }

    public void setExecutionId( long executionId )
    {
        this.executionId = executionId;
    }

}
