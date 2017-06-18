package com.edward.WeatherInquirySys.dao;

import java.util.List;

import com.edward.WeatherInquirySys.models.City;

public interface CityDao {

	public boolean isExist(Long id);
	
	public List<City> getList();
	
	public City save(City city);
	
	public void delete(Long id);
	
}
