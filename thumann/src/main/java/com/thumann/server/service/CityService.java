package com.thumann.server.service;

import java.util.List;

import com.thumann.server.domain.City;

public interface CityService {

	City getCity(String name, String country);

	List<City> findAll();
	
	// testNewNewVersion2

}
