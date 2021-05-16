package com.thumann.server.service.user;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thumann.server.domain.pupil.Pupil;
import com.thumann.server.domain.user.UserCredentials;
import com.thumann.server.helper.string.StringUtil;

@Service("userCredentialsService")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Transactional
public class UserCredentialsServiceImpl implements UserCredentialsService {

	@PersistenceContext //
	private EntityManager entityManager;

	public UserCredentialsServiceImpl()
	{
	}

	@Override
	public UserCredentials create(UserCredentials credentials)
	{
		if (credentials == null) {
			throw new IllegalArgumentException("No user was given");
		}
		else if (!credentials.isUnknown()) {
			throw new IllegalArgumentException("No existing user can be given");
		}
		else if (StringUtil.isEmpty(credentials.getUsername())) {
			throw new IllegalArgumentException("No username given");
		}
		else if (StringUtil.isEmpty(credentials.getPassword())) {
			throw new IllegalArgumentException("No password given");
		}
		UserCredentials existingUser = getByUserName(credentials.getUsername());
		if (existingUser != null) {
			throw new IllegalArgumentException("UserCredentials with this username already exists");
		}
		return entityManager.merge(credentials);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public UserCredentials create(String userName)
	{
		if (StringUtil.isEmpty(userName)) {
			throw new IllegalArgumentException(userName + " must not be null");
		}

		Long count; // TODO: addCounts
		TypedQuery<Long> pupilCountQuery = entityManager.createNamedQuery(Pupil.GET_PUPIL_COUNT, Long.class);
		try {
			count = pupilCountQuery.getSingleResult();
		}
		catch (NoResultException e) {
			count = 1L;
		}

		UserCredentials credentials = new UserCredentials();
		credentials.setUsername(userName + count);
		String randomString = UUID.randomUUID().toString();
		credentials.setPassword(randomString.substring(randomString.length() - 6));

		return create(credentials);
	}

	@Override
	public UserCredentials getByUserName(String username)
	{
		if (StringUtil.isEmpty(username)) {
			throw new IllegalArgumentException("Username must not be null");
		}

		TypedQuery<UserCredentials> query =
				entityManager.createNamedQuery(UserCredentials.GET_BY_USERNAME, UserCredentials.class);
		query.setParameter("username", username);
		try {
			return query.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}

}
