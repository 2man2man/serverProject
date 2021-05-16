package com.thumann.server.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MissingFieldException extends ResponseStatusException {

	private static final long serialVersionUID = 7744327270117631068L;

	private String missingField;

	public static MissingFieldException create(String missingField)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("The required field '").append(missingField).append("' is missing.");
		return new MissingFieldException(sb.toString());
	}

	private MissingFieldException(String missingField)
	{
		super(HttpStatus.BAD_REQUEST, missingField);
	}

	public String getMissingField()
	{
		return missingField;
	}

	public void setMissingField(String missingField)
	{
		this.missingField = missingField;
	}

}
