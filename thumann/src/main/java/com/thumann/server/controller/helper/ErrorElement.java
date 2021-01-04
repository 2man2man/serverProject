package com.thumann.server.controller.helper;

public class ErrorElement {

	private String errorMessage;

	public ErrorElement(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

}
