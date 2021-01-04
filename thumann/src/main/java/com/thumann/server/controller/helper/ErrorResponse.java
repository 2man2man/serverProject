package com.thumann.server.controller.helper;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {

	private List<ErrorElement> errors = new ArrayList<ErrorElement>();

	public void addError(String errorMessage)
	{
		errors.add(new ErrorElement(errorMessage));
	}

	public List<ErrorElement> getErrors()
	{
		return errors;
	}

	public void setErrors(List<ErrorElement> errors)
	{
		this.errors = errors;
	}

}
