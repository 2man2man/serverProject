package com.thumann.server.domain.article;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.thumann.server.domain.Domain;
import com.thumann.server.domain.DomainTenantInterface;
import com.thumann.server.domain.tenant.Tenant;

@Entity
public class Article extends Domain implements DomainTenantInterface
{
    private static final long serialVersionUID = 9098209729795499941L;

    @Column( nullable = false )
    private String            name;

    @Column( nullable = false )
    private String            number;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    private Tenant            tenant;

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber( String number )
    {
        this.number = number;
    }

    public Tenant getTenant()
    {
        return tenant;
    }

    public void setTenant( Tenant tenant )
    {
        this.tenant = tenant;
    }

}
