package com.thumann.server.configuration.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.service.helper.UserThreadHelper;

public class ExtractUserIdRequestInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			Exception exception) throws Exception
	{
		UserThreadHelper.removeUser();
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			ModelAndView modelAndView) throws Exception
	{
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		String requestUrl = request.getRequestURL().toString();
		if (requestUrl.endsWith("error")) {
			return true;
		}

		String token = request.getHeader("Authorization");
		if (!StringUtil.isEmpty(token)) {
			token = token.replace("Bearer ", "");
			JWT jwt = JWTParser.parse(token);
			long userId = Long.valueOf(String.valueOf(jwt.getJWTClaimsSet().getClaim("userId")));
			UserThreadHelper.addUser(userId);
		}
		return true;
	}
}
