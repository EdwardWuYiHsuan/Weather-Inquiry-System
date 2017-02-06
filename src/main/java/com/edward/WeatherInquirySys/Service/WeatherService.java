package com.edward.WeatherInquirySys.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.edward.WeatherInquirySys.dao.WeatherDao;
import com.edward.WeatherInquirySys.exception.APICode;
import com.edward.WeatherInquirySys.exception.ApiException;
import com.edward.WeatherInquirySys.models.Weather;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Service
public class WeatherService {
	
	public static final String OPEN_WEATHER_MAP_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s";
	
	@Autowired
	private WeatherDao weatherDao;
	@Value("${openWeatherMap.appid}")
	private String openWeatherMapAppid;
	
	
	public Map<String, Set<Weather>> getWeatherList()
	{
		List<Weather> weathers = weatherDao.getList();
		
		Map<String, Set<Weather>> sortingWeatherList = new TreeMap<>();
		for (Weather weather : weathers) 
		{
			if (sortingWeatherList.containsKey(weather.getCityName()))
				sortingWeatherList.get(weather.getCityName()).add(weather);
			else
				sortingWeatherList.put(weather.getCityName(), new TreeSet<>(Arrays.asList(weather)));
		}
		
		return sortingWeatherList;
	}
	
	public List<Weather> getWeatherByCityName(String cityName)
	{
		return weatherDao.getWeatherByCityName(cityName);
	}
	
	public Weather createWeather(Weather weather)
	{
		return weatherDao.save(weather);
	}
	
	public void deleteWeather(Long weatherid) throws ApiException
	{
		if (weatherDao.isExist(weatherid))
			weatherDao.delete(weatherid);
		else
			throw new ApiException(APICode.InvalidParameter, "invalid-weather-id");
	}

	public void inquireWeather(String cityName) throws ApiException 
	{
		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			
			String url = String.format(OPEN_WEATHER_MAP_URL, cityName, openWeatherMapAppid);
			HttpGet httpGet = new HttpGet(url);
			response = client.execute(httpGet);
			
			if (null != response) 
			{
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 200)
				{
					HttpEntity entity = response.getEntity();
					String result = IOUtils.toString(entity.getContent());
					
					JsonObject jObject = new JsonParser().parse(result).getAsJsonObject();
					JsonObject weatherObj = jObject.get("weather").getAsJsonArray().get(0).getAsJsonObject();
					JsonObject mainObj = jObject.get("main").getAsJsonObject();
					JsonObject windObj = jObject.get("wind").getAsJsonObject();
					
					Weather weather = new Weather();
					weather.setCityName(cityName);
					weather.setTimestamps(jObject.get("dt").getAsLong());
					weather.setTemperature(mainObj.get("temp").getAsString());
					weather.setStatus(weatherObj.get("main").getAsString());
					weather.setSpeed(windObj.get("speed").getAsString());
					weather.setHumidity(mainObj.get("humidity").getAsString());
					weather.setPressure(mainObj.get("pressure").getAsString());
					weather.setIconPath(weatherObj.get("icon").getAsString());
					weatherDao.save(weather);
					
					EntityUtils.consume(entity);
				}
			}
		} catch (Exception e) {
			throw new ApiException(APICode.HttpConnectionFailed, e.getMessage());
		} finally {
			try {
				if (null != response)
					response.close();
			} catch (IOException ioEx) {}
		}
	}
	
	
}


