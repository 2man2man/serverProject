package com.thumann.server.domain.warehouse.location;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.thumann.server.domain.Domain;

@Entity
public class FixedLocationType extends Domain implements Serializable
{
    private static final long serialVersionUID = 3043270621063774584L;

    @Column( nullable = false, unique = true )
    private String            number;

    @Column( nullable = false )
    private String            name;

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

}
