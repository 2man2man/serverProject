package com.thumann.server.domain.logisticexecution.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.thumann.server.domain.Domain;
import com.thumann.server.domain.user.Employee;
import com.thumann.server.domain.warehouse.Warehouse;

@Entity
public class StorageExecution extends Domain
{
    private static final long              serialVersionUID   = -5594832816487509877L;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    private Employee                       createdBy;

    @Column( nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Date                           creationDate;

    @Temporal( TemporalType.TIMESTAMP )
    private Date                           lastStorageDate;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    private Warehouse                      warehouse;

    @OneToMany( fetch = FetchType.LAZY, mappedBy = "execution" )
    private List<StorageExecutionPosition> positions          = new ArrayList<>();

    @Column( columnDefinition = "BIGINT DEFAULT 0" )
    private long                           totalPositionCount = 0;

    @Column( columnDefinition = "BIGINT DEFAULT 0" )
    private long                           totalQuantityCount = 0;

    public void init( Employee employee, Warehouse warehouse )
    {
        this.creationDate = new Date();
        this.createdBy = employee;
        this.warehouse = warehouse;
    }

    public void addPosition( StorageExecutionPosition position )
    {
        this.positions.add( position );
        this.totalPositionCount = this.totalPositionCount + 1;
        this.totalQuantityCount = this.totalQuantityCount + position.getAction().getQuantity();
    }

    public Employee getCreatedBy()
    {
        return createdBy;
    }

    public Date getCreationDate()
    {
        if ( creationDate == null ) {
            return null;
        }
        return new Date( creationDate.getTime() );
    }

    public Date getLastStorageDate()
    {
        if ( lastStorageDate == null ) {
            return null;
        }
        return new Date( lastStorageDate.getTime() );
    }

    public Warehouse getWarehouse()
    {
        return warehouse;
    }

    public List<StorageExecutionPosition> getPositions()
    {
        return new ArrayList<>( positions );
    }

    public long getTotalPositionCount()
    {
        return totalPositionCount;
    }

    public long getTotalQuantityCount()
    {
        return totalQuantityCount;
    }

}
