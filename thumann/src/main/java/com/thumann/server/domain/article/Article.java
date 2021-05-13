package com.thumann.server.domain.article;

import java.io.Serializable;

import javax.persistence.Entity;

import com.thumann.server.domain.Domain;

@Entity
public class Article extends Domain implements Serializable {

	private static final long serialVersionUID = 9098209729795499941L;

	private String name;

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
