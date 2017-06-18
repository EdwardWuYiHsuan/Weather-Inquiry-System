package com.edward.WeatherInquirySys.service.impl;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.edward.WeatherInquirySys.dao.CityDao;
import com.edward.WeatherInquirySys.exception.APICode;
import com.edward.WeatherInquirySys.exception.ApiException;
import com.edward.WeatherInquirySys.models.City;
import com.edward.WeatherInquirySys.service.CityService;


@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityDao cityDao;
	
	
	@Override
	public List<City> getCityList()
	{
		return cityDao.getList();
	}
	
	@Override
	public City createCity(City city) throws ApiException
	{
		try {
			return cityDao.save(city);
		} catch (DataIntegrityViolationException e) {
			if (e.getCause() instanceof ConstraintViolationException)
				throw new ApiException(APICode.UniqueParameter, "city-already-exist");
			else 
				throw new ApiException(APICode.AccessFailed);
		}
	}
	
	@Override
	public void deleteCity(Long cityId) throws ApiException
	{
		if (cityDao.isExist(cityId)) {
			cityDao.delete(cityId);
		} else {
			throw new ApiException(APICode.InvalidParameter, "invalid-city-id");
		}
	}
	
}
