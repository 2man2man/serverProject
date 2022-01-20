package com.thumann.server.helper.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User
{

    private static final long serialVersionUID = -935375528857350551L;

    private final long        userId;

    public CustomUser( String userName, String password, Collection<GrantedAuthority> grantedAuthoritiesList, long userId )
    {
        super( userName, password, grantedAuthoritiesList );
        this.userId = userId;
    }

    public long getUserId()
    {
        return userId;
    }

}