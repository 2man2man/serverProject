package com.thumann.server.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.thumann.server.domain.City;

@Component("cityService")
@Transactional
class CityServiceImpl implements CityService {

	@PersistenceContext //
	private EntityManager entityManager;

	public CityServiceImpl() {
	}

	@Override
	public City getCity(String name, String country) {
		Assert.notNull(name, "Name must not be null");
		Assert.notNull(country, "Country must not be null");
		City city = new City("Berlin", "TestJurra");
		entityManager.merge(city);
		return city;
	}

	@Override
	public List<City> findAll() {
		return new ArrayList<City>();
	}

}