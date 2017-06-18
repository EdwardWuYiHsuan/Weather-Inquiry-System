package com.edward.WeatherInquirySys.service;

import java.util.List;

import com.edward.WeatherInquirySys.exception.ApiException;
import com.edward.WeatherInquirySys.models.City;

public interface CityService {

	public List<City> getCityList() throws ApiException;
	
	public City createCity(City city) throws ApiException;
	
	public void deleteCity(Long cityId) throws ApiException;
	
}
