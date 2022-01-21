package com.thumann.server.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.thumann.server.domain.user.UserCredentials;
import com.thumann.server.helper.user.CustomUser;

@Component( "userDetailsService" )
public class CustomDetailsService implements UserDetailsService
{
    @Autowired
    private ApplicationContext     appContext;

    @Autowired
    private UserCredentialsService userCredentialsService;

    @Override
    public CustomUser loadUserByUsername( final String username ) throws UsernameNotFoundException
    {
        UserCredentials userEntity = userCredentialsService.getByUserName( username );
        if ( userEntity == null ) {
            throw new UsernameNotFoundException( "UserCredentials " + username + " was not found in the database" );
        }

        PasswordEncoder encoder = appContext.getBean( PasswordEncoder.class );
        String encodedPassword = encoder.encode( userEntity.getPassword() );

        List<GrantedAuthority> grantedAuthoritiesList = new ArrayList<GrantedAuthority>();
        grantedAuthoritiesList.add( new SimpleGrantedAuthority( "ROLE_SYSTEMADMIN" ) );

        CustomUser customUser = new CustomUser( userEntity.getUsername(), encodedPassword, grantedAuthoritiesList, userEntity.getEmployee().getId() );
        return customUser;
    }
}