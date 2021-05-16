package com.thumann.server.web.controller.pupil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.pupil.Pupil;
import com.thumann.server.service.pupil.PupilService;
import com.thumann.server.web.response.ReponseFactory;

@RestController
@RequestMapping(value = "/pupil", consumes = "application/json", produces = "application/json")
public class PupilController {

	@Autowired
	private ReponseFactory responseFactory;

	@Autowired
	private PupilService pupilService;

	@Autowired
	private PupilControllerFactory factory;

	@PostMapping
	public @ResponseBody ResponseEntity<?> createPupil(@RequestBody ObjectNode json)
	{
		PupilCreateDTO createDto = factory.createCreateDTO(json);
		Pupil pupil = pupilService.createPupil(createDto);
		PupilResponseDTO reponseDto = factory.createResponseDTO(pupil);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseFactory.createResponse(reponseDto));
	}

}