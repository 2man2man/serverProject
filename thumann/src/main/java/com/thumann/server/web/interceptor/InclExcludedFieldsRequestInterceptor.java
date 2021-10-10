package com.thumann.server.web.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.thumann.server.service.helper.InclExclFieldsHelper;

public class InclExcludedFieldsRequestInterceptor extends HandlerInterceptorAdapter
{

    @Override
    public void afterCompletion( HttpServletRequest request,
                                 HttpServletResponse response,
                                 Object handler,
                                 Exception exception )
        throws Exception
    {
        InclExclFieldsHelper.remove();
    }

    @Override
    public void postHandle( HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler,
                            ModelAndView modelAndView )
        throws Exception
    {
    }

    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception
    {
        String requestUrl = request.getRequestURL().toString();
        if ( requestUrl.endsWith( "error" ) ) {
            return true;
        }
        Enumeration<String> fields = request.getHeaders( "field" );
        InclExclFieldsHelper.addFields( fields );
        return true;

    }
}
