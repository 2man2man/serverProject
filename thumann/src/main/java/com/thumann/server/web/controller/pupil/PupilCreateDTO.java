package com.thumann.server.web.controller.pupil;

import java.util.Date;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.web.exception.APIMissingFieldException;

public class PupilCreateDTO {

	private String firstName;

	private String lastName;

	private Date dateOfBirth;

	public void initValues(ObjectNode json)
	{
		setFirstName(JsonUtil.getString(json, "firstName"));
		setLastName(JsonUtil.getString(json, "lastName"));
		setDateOfBirth(JsonUtil.getDate(json, "dateOfBirth"));
	}

	public void checkRequiredFields()
	{
		if (StringUtil.isEmpty(getFirstName())) {
			throw APIMissingFieldException.create("firstName");
		}
		else if (StringUtil.isEmpty(getLastName())) {
			throw APIMissingFieldException.create("lastName");
		}
		else if (getDateOfBirth() == null) {
			throw APIMissingFieldException.create("dateOfBirth");
		}
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public Date getDateOfBirth()
	{
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth)
	{
		this.dateOfBirth = dateOfBirth;
	}

}
