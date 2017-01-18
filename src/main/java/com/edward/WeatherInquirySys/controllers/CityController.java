package com.edward.WeatherInquirySys.controllers;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.edward.WeatherInquirySys.Service.CityService;
import com.edward.WeatherInquirySys.Service.WeatherService;
import com.edward.WeatherInquirySys.exception.APICode;
import com.edward.WeatherInquirySys.exception.ApiException;
import com.edward.WeatherInquirySys.models.City;
import com.edward.WeatherInquirySys.response.DefaultResponse;


@RestController
@RequestMapping("city")
public class CityController extends DefaultController {

	@Autowired
	private CityService cityService;
	@Autowired
	private WeatherService weatherService;
	
	
	@RequestMapping(value = "getCityList", method = RequestMethod.GET)
	public DefaultResponse getCityList()
	{
		DefaultResponse response;
		try {
			List<City> cityList = cityService.getCityList();
			response = new DefaultResponse().setData(cityList);
			
			return response;
			
		} catch (Exception e) {
			response = renderErrorResponse(e);
		}
		
		return response;
	}
	
	@RequestMapping(value = "createCity", method = RequestMethod.POST)
	public DefaultResponse createCity(@RequestParam(value = "name") String name)
	{
		DefaultResponse response;
		try {
			if (StringUtils.isBlank(name))
				throw new ApiException(APICode.InvalidParameter, "invalid-name");
			
			City city = new City();
			city.setName(name);
			city = cityService.createCity(city);
			
			weatherService.inquireWeather(city.getName());
			
			response = new DefaultResponse();
			
		} catch (Exception e) {
			response = renderErrorResponse(e);
		}
		
		return response;
	}
	
	@RequestMapping(value = "deleteCity/{cityId}", method = RequestMethod.DELETE)
	public DefaultResponse deleteCity(@PathVariable Long cityId)
	{
		DefaultResponse response;
		try {
			if (null == cityId || cityId.longValue() < 0)
				throw new ApiException(APICode.InvalidParameter, "invalid-city-id");
			
			cityService.deleteCity(cityId);
			response = new DefaultResponse();
			
		} catch (Exception e) {
			response = renderErrorResponse(e);
		}
		
		return response;
	}
	
	
}

