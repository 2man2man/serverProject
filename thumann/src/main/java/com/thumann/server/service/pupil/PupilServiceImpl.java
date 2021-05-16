package com.thumann.server.service.pupil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thumann.server.domain.pupil.Pupil;
import com.thumann.server.domain.user.UserCredentials;
import com.thumann.server.service.user.UserCredentialsService;
import com.thumann.server.web.controller.pupil.PupilCreateDTO;

@Service("pupilService")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Transactional
class PupilServiceImpl implements PupilService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UserCredentialsService userCredentialsService;

	public PupilServiceImpl()
	{
	}

	@Override
	public Pupil createPupil(PupilCreateDTO createDTO)
	{
		Pupil pupil = new Pupil();
		pupil.setFirstName(createDTO.getFirstName());
		pupil.setLastName(createDTO.getLastName());
		pupil.setDateOfBirth(createDTO.getDateOfBirth());

		UserCredentials credentials = userCredentialsService.create(pupil.getFirstName());
		pupil.setCredentials(credentials);

		return entityManager.merge(pupil);
	}

}