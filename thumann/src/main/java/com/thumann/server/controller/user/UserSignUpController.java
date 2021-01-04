package com.thumann.server.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thumann.server.controller.helper.ErrorResponse;
import com.thumann.server.domain.user.User;
import com.thumann.server.service.user.UserService;

@RestController
@RequestMapping("/rest")
public class UserSignUpController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/signUp")
	public @ResponseBody ResponseEntity<?> signUp(@RequestBody User user)
	{
		try {
			User existingUser = userService.getByUserName(user.getUsername());
			if (existingUser != null) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
			user = userService.signUp(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(user);
		}
		catch (Exception e) {
			ErrorResponse response = new ErrorResponse();
			response.addError(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@PostMapping(value = "/signUp/usernameAvailable")
	public @ResponseBody ResponseEntity<?> checkUsernameAvailable(@RequestBody User user)
	{
		try {
			String username = user.getUsername();
			User existingUser = userService.getByUserName(username);
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
