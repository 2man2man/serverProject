package com.thumann.server.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Domain implements Serializable
{
    private static final long serialVersionUID = -8635160726177505425L;

    public static final long  UNKOWN_ID        = 0;

    @Id
    @GeneratedValue
    private Long              id;

    public Long getId()
    {
        if ( id == null ) {
            return UNKOWN_ID;
        }
        return id;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        Domain other = (Domain) obj;
        if ( id == null ) {
            if ( other.id != null )
                return false;
        }
        else if ( !id.equals( other.id ) )
            return false;
        return true;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public boolean isUnknown()
    {
        return id == null || id.longValue() == UNKOWN_ID;
    }

}
