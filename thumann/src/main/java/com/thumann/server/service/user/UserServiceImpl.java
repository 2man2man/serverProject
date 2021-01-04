package com.thumann.server.service.user;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thumann.server.domain.user.User;
import com.thumann.server.helper.string.StringUtil;

@Component("userService")
@Transactional
class UserServiceImpl implements UserService {

	@PersistenceContext //
	private EntityManager entityManager;

	public UserServiceImpl()
	{
	}

	@Override
	public User signUp(User user)
	{
		if (user == null) {
			throw new IllegalArgumentException("No user was given");
		}
		else if (!user.isUnknown()) {
			throw new IllegalArgumentException("No existing user can be given");
		}
		else if (StringUtil.isEmpty(user.getUsername())) {
			throw new IllegalArgumentException("No username given");
		}
		else if (StringUtil.isEmpty(user.getPassword())) {
			throw new IllegalArgumentException("No password given");
		}
		User existingUser = getByUserName(user.getUsername());
		if (existingUser != null) {
			throw new IllegalArgumentException("User with this username already exists");
		}
		return entityManager.merge(user); // TODO
	}

	@Override
	public User getByUserName(String username)
	{
		if (StringUtil.isEmpty(username)) {
			throw new IllegalArgumentException("Username must not be null");
		}

		TypedQuery<User> query = entityManager.createNamedQuery(User.GET_BY_USERNAME, User.class);
		query.setParameter("username", username);
		try {
			return query.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}

}