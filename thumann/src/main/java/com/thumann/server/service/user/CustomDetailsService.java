package com.thumann.server.service.user;

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

@Component("userDetailsService")
public class CustomDetailsService implements UserDetailsService {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private UserCredentialsService userService;

	@Override
	public CustomUser loadUserByUsername(final String username) throws UsernameNotFoundException
	{
		UserCredentials userEntity = userService.getByUserName(username);
		if (userEntity == null) {
			throw new UsernameNotFoundException("UserCredentials " + username + " was not found in the database");
		}

		PasswordEncoder encoder = appContext.getBean(PasswordEncoder.class);
		userEntity.setUsername(userEntity.getUsername());
		userEntity.setPassword(encoder.encode(userEntity.getPassword()));
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_SYSTEMADMIN");
		userEntity.getGrantedAuthoritiesList().add(grantedAuthority);
		CustomUser customUser = new CustomUser(userEntity);
		return customUser;
	}
}