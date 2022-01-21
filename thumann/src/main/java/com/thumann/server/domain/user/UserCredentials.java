package com.thumann.server.domain.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.security.core.GrantedAuthority;

import com.thumann.server.domain.Domain;

@Entity
@NamedQueries( { @NamedQuery( name = UserCredentials.GET_BY_USERNAME, query = "SELECT user FROM UserCredentials user WHERE user.username = :username " ) } )
public class UserCredentials extends Domain implements Serializable
{

    private static final long            serialVersionUID       = 618293141162743083L;

    public static final String           GET_BY_USERNAME        = "GET_BY_USERNAME";

    @Column( unique = true, nullable = false )
    public String                        username;

    @Column( nullable = false )
    private String                       password;

    @OneToOne( fetch = FetchType.LAZY, mappedBy = "credentials" )
    private Employee                     employee;

    @Transient
    private Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        if ( password.length() < 2 ) {
            return;
        }

        this.password = password;
    }

    public Collection<GrantedAuthority> getGrantedAuthoritiesList()
    {
        return grantedAuthoritiesList;
    }

    public void setGrantedAuthoritiesList( Collection<GrantedAuthority> grantedAuthoritiesList )
    {
        this.grantedAuthoritiesList = grantedAuthoritiesList;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public Employee getEmployee()
    {
        return employee;
    }

    public void setEmployee( Employee employee )
    {
        this.employee = employee;
    }
}