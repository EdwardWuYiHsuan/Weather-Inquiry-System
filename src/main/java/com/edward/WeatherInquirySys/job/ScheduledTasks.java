package com.edward.WeatherInquirySys.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.edward.WeatherInquirySys.Service.WeatherService;
import com.edward.WeatherInquirySys.dao.CityDao;
import com.edward.WeatherInquirySys.exception.ApiException;
import com.edward.WeatherInquirySys.models.City;

/**
 * Scheduled Tasks
 * 
 * cron = "秒 分 時 日 月 星期(MON)"
 * Reference website : http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html
 * 
 * @author ed29172812@hotmail.com
 */
@Component
public class ScheduledTasks {
	
	@Autowired
	private CityDao cityDao;
	@Autowired
	private WeatherService weatherService;
	
	
	@Scheduled(cron="0 0 3 * * *")
	public void retrieveWeatherData()
	{
		System.out.println("[Scheduled Task] Retrieve Weather Data");
		
		List<City> cityList = cityDao.getList();
		
		for (City city : cityList) 
		{
			System.out.printf("City[%s] Inquire weather data\n", city.getName());
			try {
				weatherService.inquireWeather(city.getName());
			} catch (ApiException e) {
				System.out.printf("When inquire weather data, cause error : %s\n", e.getMessage());
			}
		}
	}
	
	
}

