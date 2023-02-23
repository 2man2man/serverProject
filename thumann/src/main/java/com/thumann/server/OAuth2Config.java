package com.thumann.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.thumann.server.helper.user.CustomUser;
import com.thumann.server.web.configuration.AuthorisationDetailsHolder;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter
{
    @Autowired
    private ApplicationContext    appContext;

    private static final String   CLIENT_ID     = "GUESS_ME";

    private static final String   CLIENT_SECRET = "ITS_A_SECRET";

    @Autowired
    @Qualifier( "authenticationManagerBean" )
    private AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter tokenConverter()
    {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey( AuthorisationDetailsHolder.getPrivateKey() );
        converter.setVerifierKey( AuthorisationDetailsHolder.getPublicKey() );
        converter.setAccessTokenConverter( authExtractor() );
        return converter;
    }

    @Bean
    public TokenStore tokenStore()
    {
        return new JwtTokenStore( tokenConverter() );
    }

    private TokenEnhancer tokenEnhancer()
    {
        return ( accessToken, authentication ) -> {
            if ( authentication != null && authentication.getPrincipal() instanceof CustomUser ) {
                CustomUser authUser = (CustomUser) authentication.getPrincipal();
                Map<String, Object> additionalInfo = new HashMap<>();
                additionalInfo.put( "userId", authUser.getUserId() );
                ( (DefaultOAuth2AccessToken) accessToken ).setAdditionalInformation( additionalInfo );
            }
            return accessToken;
        };
    }

    @Override
    public void configure( AuthorizationServerEndpointsConfigurer endpoints ) throws Exception
    {
        TokenEnhancerChain chain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancerList = new ArrayList<TokenEnhancer>();
        enhancerList.add( tokenEnhancer() );
        enhancerList.add( tokenConverter() );

        chain.setTokenEnhancers( enhancerList );

        endpoints.tokenStore( tokenStore() )
                 .reuseRefreshTokens( false )
                 .tokenEnhancer( chain )
                 .authenticationManager( authenticationManager );
    }

    @Override
    public void configure( AuthorizationServerSecurityConfigurer security ) throws Exception
    {
        security.tokenKeyAccess( "permitAll()" ).checkTokenAccess( "isAuthenticated()" );
    }

    @Override
    public void configure( ClientDetailsServiceConfigurer clients ) throws Exception
    {
        PasswordEncoder encoder = appContext.getBean( PasswordEncoder.class );

        clients.inMemory()
               .withClient( CLIENT_ID )
               .secret( encoder.encode( CLIENT_SECRET ) )
               .scopes( "read", "write" )
               .authorizedGrantTypes( "password", "refresh_token" )
               .accessTokenValiditySeconds( 6000 ) // TODO
               .refreshTokenValiditySeconds( 7200 );
    }

    @Bean
    public DefaultAccessTokenConverter authExtractor()
    {
        return new DefaultAccessTokenConverter() {
            @Override
            public OAuth2Authentication extractAuthentication( Map<String, ?> claims )
            {
                OAuth2Authentication authentication = super.extractAuthentication( claims );
                authentication.setDetails( claims );
                return authentication;
            }
        };
    }

}