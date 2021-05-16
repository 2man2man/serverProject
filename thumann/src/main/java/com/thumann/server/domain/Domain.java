package com.thumann.server.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Domain {

	public static final long UNKOWN_ID = 0;

	@Id
	@GeneratedValue
	private Long id;

	public Long getId()
	{
		if (id == null) {
			return UNKOWN_ID;
		}
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public boolean isUnknown()
	{
		return id == null || id.longValue() == UNKOWN_ID;
	}

}
