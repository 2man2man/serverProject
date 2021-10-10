package com.thumann.server.domain.tenant;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.thumann.server.domain.Domain;

@Entity
public class Tenant extends Domain implements Serializable {

	private static final long serialVersionUID = 9098209729795499941L;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String number;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

}
