package com.thumann.server.web.controller.pupil;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.pupil.Pupil;

@Configuration
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PupilControllerFactory {

	public PupilCreateDTO createCreateDTO(ObjectNode node)
	{
		PupilCreateDTO dto = new PupilCreateDTO();
		dto.initValues(node);
		dto.checkRequiredFields();
		return dto;
	}

	public PupilResponseDTO createResponseDTO(Pupil pupil)
	{
		PupilResponseDTO dto = new PupilResponseDTO();
		dto.initValues(pupil);
		return dto;
	}

}
