package com.thumann.server.helper.user;

import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

	private static final long serialVersionUID = -935375528857350551L;

	private final long userId;

	public CustomUser(com.thumann.server.domain.user.User user)
	{
		super(user.getUsername(), user.getPassword(), user.getGrantedAuthoritiesList());
		this.userId = user.getId();
	}

	public long getUserId()
	{
		return userId;
	}

}