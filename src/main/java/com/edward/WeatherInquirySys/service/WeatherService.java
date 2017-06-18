package com.edward.WeatherInquirySys.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.edward.WeatherInquirySys.exception.ApiException;
import com.edward.WeatherInquirySys.models.Weather;

public interface WeatherService {

	public Map<String, Set<Weather>> getWeatherList() throws ApiException;
	
	public List<Weather> getWeatherByCityName(String cityName) throws ApiException;
	
	public Weather createWeather(Weather weather) throws ApiException;
	
	public void deleteWeather(Long weatherid) throws ApiException;
	
	public void inquireWeather(String cityName) throws ApiException;
	
}
