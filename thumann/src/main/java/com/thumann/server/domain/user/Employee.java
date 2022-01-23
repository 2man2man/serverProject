package com.thumann.server.domain.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.thumann.server.helper.string.StringUtil;

@Entity
public class Employee extends Person implements Serializable
{
    private static final long  serialVersionUID = -4340150305661866300L;

    public final static String ADMIN            = "admin";

    @Column( nullable = false )
    private String             firstName;

    @Column( nullable = false )
    private String             lastName;

    @Temporal( TemporalType.DATE )
    private Date               dateOfBirth;

    @OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, optional = false )
    private UserCredentials    credentials;

    @OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, optional = false )
    private UserPrivilege      privilege;

    @Override
    public String getName()
    {
        List<String> names = new ArrayList<>();
        if ( !StringUtil.isEmpty( firstName ) ) {
            names.add( firstName );
        }
        if ( !StringUtil.isEmpty( lastName ) ) {
            names.add( lastName );
        }
        return StringUtil.combineWithSeparator( names, " " );
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth( Date dateOfBirth )
    {
        this.dateOfBirth = dateOfBirth;
    }

    public UserCredentials getCredentials()
    {
        return credentials;
    }

    public void setCredentials( UserCredentials credentials )
    {
        this.credentials = credentials;
    }

    public UserPrivilege getPrivilege()
    {
        return privilege;
    }

    public void setPrivilege( UserPrivilege privilege )
    {
        this.privilege = privilege;
    }

}
