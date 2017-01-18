package com.edward.WeatherInquirySys.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edward.WeatherInquirySys.Service.WeatherService;
import com.edward.WeatherInquirySys.exception.APICode;
import com.edward.WeatherInquirySys.exception.ApiException;
import com.edward.WeatherInquirySys.models.Weather;
import com.edward.WeatherInquirySys.response.DefaultResponse;


@RestController
@RequestMapping("weather")
public class WeatherController extends DefaultController {

	@Autowired
	private WeatherService weatherService;
	
	
	@RequestMapping(value = "getWeatherList", method = RequestMethod.GET)
	public DefaultResponse getWeatherList()
	{
		DefaultResponse response;
		try {
			List<Weather> weatherList = weatherService.getWeatherList();
			
			response = new DefaultResponse().setData(weatherList);
			
		} catch (Exception e) {
			response = renderErrorResponse(e);
		}
		
		return response;
	}
	
	@RequestMapping(value = "getWeatherByCityName", method = RequestMethod.GET)
	public DefaultResponse getWeatherByCityName(@RequestParam String cityName)
	{
		DefaultResponse response;
		try {
			if (StringUtils.isBlank(cityName))
				throw new ApiException(APICode.InvalidParameter, "invalid-city-name");
			
			List<Weather> weatherList = weatherService.getWeatherByCityName(cityName);
			
			response = new DefaultResponse().setData(weatherList);
			
		} catch (Exception e) {
			response = renderErrorResponse(e);
		}
		
		return response;
	}
	
	@RequestMapping(value = "deleteWeather/{weatherId}", method = RequestMethod.DELETE)
	public DefaultResponse deleteWeather(@PathVariable Long weatherId)
	{
		DefaultResponse response;
		try {
			if (null == weatherId || weatherId.longValue() < 0)
				throw new ApiException(APICode.InvalidParameter, "invalid-weather-id");
			
			weatherService.deleteWeather(weatherId);
			response = new DefaultResponse();
			
		} catch (Exception e) {
			response = renderErrorResponse(e);
		}
		
		return response;
	}
	
	
}

