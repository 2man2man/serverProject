package com.thumann.server.domain.warehouse.location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.thumann.server.domain.Domain;
import com.thumann.server.domain.warehouse.Warehouse;
import com.thumann.server.domain.warehouse.area.WarehouseArea;
import com.thumann.server.helper.string.StringUtil;

@Entity
public class FixedLocation extends Domain implements Serializable
{
    private static final long serialVersionUID = 80179892317930879L;

    @Column( columnDefinition = "BOOLEAN DEFAULT FALSE" )
    private boolean           archived         = false;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    private Warehouse         warehouse;

    @ManyToOne( fetch = FetchType.LAZY )
    private WarehouseArea     warehouseArea;

    @ManyToOne( fetch = FetchType.LAZY )
    private FixedLocationType locationType;

    private String            barcode;

    @Column( nullable = false, unique = true )
    private String            number;

    private String            rrow;

    private String            ccolumn;

    private String            level;

    private String            fragment;

    public void initNumberAndBarcode()
    {
        List<String> strings = new ArrayList<String>();
        strings.add( getRow() );
        strings.add( getColumn() );
        strings.add( getLevel() );
        if ( !StringUtil.isEmpty( getFragment() ) ) {
            strings.add( getFragment() );
        }
        String combined = StringUtil.combineWithSeparator( strings, "-" );
        this.number = combined;
        this.barcode = combined;
    }

    public Warehouse getWarehouse()
    {
        return warehouse;
    }

    public void setWarehouse( Warehouse warehouse )
    {
        this.warehouse = warehouse;
    }

    public WarehouseArea getWarehouseArea()
    {
        return warehouseArea;
    }

    public void setWarehouseArea( WarehouseArea warehouseArea )
    {
        this.warehouseArea = warehouseArea;
    }

    public FixedLocationType getLocationType()
    {
        return locationType;
    }

    public void setLocationType( FixedLocationType locationType )
    {
        this.locationType = locationType;
    }

    public String getBarcode()
    {
        return barcode;
    }

    public void setBarcode( String barcode )
    {
        this.barcode = barcode;
    }

    public String getNumber()
    {
        return number;
    }

    public String getRow()
    {
        return rrow;
    }

    public void setRow( String row )
    {
        this.rrow = row;
    }

    public String getColumn()
    {
        return ccolumn;
    }

    public void setColumn( String column )
    {
        this.ccolumn = column;
    }

    public String getLevel()
    {
        return level;
    }

    public void setLevel( String level )
    {
        this.level = level;
    }

    public String getFragment()
    {
        return fragment;
    }

    public void setFragment( String fragment )
    {
        this.fragment = fragment;
    }

    public boolean isArchived()
    {
        return archived;
    }

    public void setArchived( boolean archived )
    {
        this.archived = archived;
    }

}
