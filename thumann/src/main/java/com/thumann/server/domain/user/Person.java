package com.thumann.server.domain.user;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.thumann.server.domain.Domain;
import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.domain.warehouse.Warehouse;

@MappedSuperclass
public abstract class Person extends Domain implements Serializable
{
    private static final long serialVersionUID = -7318430226362976826L;

    @ManyToMany( fetch = FetchType.LAZY )
    private Set<Tenant>       tenants          = new HashSet<>();

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    private Warehouse         warehouse;

    public abstract String getName();

    public Set<Tenant> getTenants()
    {
        return tenants;
    }

    public void setTenants( Set<Tenant> tenants )
    {
        this.tenants = tenants;
    }

    public Warehouse getWarehouse()
    {
        return warehouse;
    }

    public void setWarehouse( Warehouse warehouse )
    {
        this.warehouse = warehouse;
    }

}
