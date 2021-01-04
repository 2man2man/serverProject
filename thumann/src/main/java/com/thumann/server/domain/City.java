package com.thumann.server.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class City extends Domain implements Serializable {

	private static final long serialVersionUID = 9075117168431549988L;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String country;

	protected City() {
	}

	public City(String name, String country) {
		super();
		this.name = name;
		this.country = country;
	}

	public String getName() {
		return this.name;
	}

	public String getCountry() {
		return this.country;
	}

	@Override
	public String toString() {
		return getName() + "," + getCountry();
	}
}