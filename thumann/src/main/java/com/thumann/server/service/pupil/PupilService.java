package com.thumann.server.service.pupil;

import com.thumann.server.domain.pupil.Pupil;
import com.thumann.server.web.controller.pupil.PupilCreateDTO;

public interface PupilService {

	Pupil createPupil(PupilCreateDTO createDTO);

}
