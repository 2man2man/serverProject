package com.thumann.server;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true )
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Bean
    public static PasswordEncoder encoder()
    {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource()
    {
        final CorsConfiguration configuration = new CorsConfiguration();

//        configuration.setAllowedOrigins( Arrays.asList( "https://www.yourdomain.com" ) ); // www - obligatory
        configuration.setAllowedOrigins( Arrays.asList( "*" ) ); // set access from all domains
        configuration.setAllowedMethods( Arrays.asList( "GET", "POST", "PUT", "DELETE" ) );
        configuration.setAllowCredentials( true );
        configuration.setAllowedHeaders( Arrays.asList( "Authorization", "Cache-Control", "Content-Type" ) );

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration( "/**", configuration );

        return source;
    }

    @Override
    @Autowired
    protected void configure( AuthenticationManagerBuilder auth ) throws Exception
    {
        auth.userDetailsService( userDetailsService ).passwordEncoder( encoder() );
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception
    {
        http.cors().configurationSource( request -> new CorsConfiguration().applyPermitDefaultValues() )
            .and().authorizeRequests()
            .antMatchers( HttpMethod.OPTIONS, "/oauth/token" ).permitAll()
            .anyRequest().authenticated()
            .and().sessionManagement().sessionCreationPolicy( SessionCreationPolicy.NEVER );
        http.requiresChannel().anyRequest().requiresSecure();
    }

    @Override
    public void configure( WebSecurity web ) throws Exception
    {
        web.ignoring().antMatchers( "/rest/signUp" );
        web.ignoring().antMatchers( "/rest/signUp/usernameAvailable" );
        web.ignoring().antMatchers( HttpMethod.OPTIONS, "/oauth/token" );
        web.ignoring().antMatchers( HttpMethod.OPTIONS, "/**" );
    }

}
