package com.thumann.server.exception;

public class MissingFieldException extends Exception {

	private static final long serialVersionUID = -7789053608690028444L;

	private String missingField;

	private String missingFieldReadable;

	public MissingFieldException(String missingField, String missingFieldReadable)
	{
		super();
		this.missingField = missingField;
		this.missingFieldReadable = missingFieldReadable;
	}

	public String getMissingField()
	{
		return missingField;
	}

	public void setMissingField(String missingField)
	{
		this.missingField = missingField;
	}

	public String getMissingFieldReadable()
	{
		return missingFieldReadable;
	}

	public void setMissingFieldReadable(String missingFieldReadable)
	{
		this.missingFieldReadable = missingFieldReadable;
	}

}
