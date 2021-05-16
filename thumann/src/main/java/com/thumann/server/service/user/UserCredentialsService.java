package com.thumann.server.service.user;

import com.thumann.server.domain.user.UserCredentials;

public interface UserCredentialsService {

	UserCredentials getByUserName(String username);

	UserCredentials create(UserCredentials user);

	UserCredentials create(String userName);

}