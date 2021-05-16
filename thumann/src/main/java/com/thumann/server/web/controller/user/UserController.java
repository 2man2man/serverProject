package com.thumann.server.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thumann.server.controller.helper.ErrorResponse;
import com.thumann.server.controller.helper.StringResponse;
import com.thumann.server.domain.user.UserCredentials;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.service.user.UserCredentialsService;

@RestController
@RequestMapping("/rest/user")
public class UserController {

	@Autowired
	private UserCredentialsService userService;

	@GetMapping(value = "/getIdByUsername")
	public @ResponseBody ResponseEntity<?> getIdByUsername(@RequestParam String username)
	{
		try {
			if (StringUtil.isEmpty(username)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			UserCredentials user = userService.getByUserName(username);
			if (user != null) {
				return ResponseEntity.status(HttpStatus.OK).body(new StringResponse(String.valueOf(user.getId())));
			}
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		}
		catch (Exception e) {
			ErrorResponse response = new ErrorResponse();
			response.addError(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping(value = "/signUp/usernameAvailable")
	public @ResponseBody ResponseEntity<?> checkUsernameAvailable(@RequestBody UserCredentials user)
	{
		try {
			String username = user.getUsername();
			UserCredentials existingUser = userService.getByUserName(username);
			if (existingUser != null) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
			else {
				return ResponseEntity.status(HttpStatus.OK).build();
			}
		}
		catch (Exception e) {
			ErrorResponse response = new ErrorResponse();
			response.addError(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

}
