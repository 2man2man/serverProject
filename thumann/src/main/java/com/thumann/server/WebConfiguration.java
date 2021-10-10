package com.thumann.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.thumann.server.web.interceptor.ExtractUserIdRequestInterceptor;
import com.thumann.server.web.interceptor.InclExcludedFieldsRequestInterceptor;

@Configuration
public class WebConfiguration implements WebMvcConfigurer
{

    /**
     * Total customization - see below for explanation.
     */
    @Override
    public void configureContentNegotiation( ContentNegotiationConfigurer configurer )
    {
        configurer.ignoreAcceptHeader( true ).defaultContentType( MediaType.APPLICATION_JSON );
    }

//
    @Override
    public void addInterceptors( InterceptorRegistry registry )
    {
        registry.addInterceptor( new ExtractUserIdRequestInterceptor() );
        registry.addInterceptor( new InclExcludedFieldsRequestInterceptor() );
    }
}
