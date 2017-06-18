package com.edward.WeatherInquirySys.dao;

import java.util.List;

import com.edward.WeatherInquirySys.models.Weather;

public interface WeatherDao {

	public boolean isExist(Long id);
	
	public List<Weather> getList();
	
	public List<Weather> getWeatherByCityName(String cityName);
	
	public Weather save(Weather weather);
	
	public void delete(Long id);
	
}
