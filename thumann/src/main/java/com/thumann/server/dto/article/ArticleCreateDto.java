package com.thumann.server.dto.article;

import com.thumann.server.domain.tenant.Tenant;

public class ArticleCreateDto {

	private String name;

	private String number;

	private Tenant tenant;

	public String getName()
	{
		return name;
	}

	public ArticleCreateDto setName(String name)
	{
		this.name = name;
		return this;
	}

	public String getNumber()
	{
		return number;
	}

	public ArticleCreateDto setNumber(String number)
	{
		this.number = number;
		return this;
	}

	public Tenant getTenant()
	{
		return tenant;
	}

	public void setTenant(Tenant tenant)
	{
		this.tenant = tenant;
	}

}
