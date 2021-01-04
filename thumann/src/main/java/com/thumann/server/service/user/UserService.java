package com.thumann.server.service.user;

import com.thumann.server.domain.user.User;

public interface UserService {

	User getByUserName(String username);

	User signUp(User user);

}