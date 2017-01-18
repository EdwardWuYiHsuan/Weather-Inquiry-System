package com.edward.WeatherInquirySys.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;


@Entity
@Table(name = "weather", indexes = {
		@Index(columnList = "cityName", name = "city_name_idx")
})
public class Weather implements Comparable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "weather_id_seq_gen")
	@SequenceGenerator(name="weather_id_seq_gen", sequenceName="WEATHER_ID_SEQUENCE")
	private Long id;
	
	@NotNull
	@Size(max = 60)
	private String cityName;
	private Long timestamps;
	private String status;
	private String temperature;
	private String speed;
	private String humidity;
	private String pressure;
	private String iconPath;
	
	
	public Weather() {
	}
	
	public Weather(Long id) {
		this.setId(id);
	}
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCityName() {
		return cityName;
	}
	
	public void setCityName(String cityName) {
		if (StringUtils.isBlank(cityName))
			throw new IllegalArgumentException("invalid-city-name");
		
		this.cityName = cityName;
	}
	
	public Long getTimestamps() {
		return timestamps;
	}
	
	public void setTimestamps(Long timestamps) {
		this.timestamps = timestamps;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getTemperature() {
		return temperature;
	}
	
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
	public String getSpeed() {
		return speed;
	}
	
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	
	public String getHumidity() {
		return humidity;
	}
	
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	
	public String getPressure() {
		return pressure;
	}
	
	public void setPressure(String pressure) {
		this.pressure = pressure;
	}
	
	public String getIconPath() {
		return iconPath;
	}
	
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	
	@Override
	public int hashCode() 
	{
		return this.getId().intValue();
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if (null == obj)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final Weather weather = (Weather) obj;
		return this.getId().longValue() == weather.getId().longValue() && 
			   this.getTimestamps().longValue() == weather.getTimestamps().longValue();
	}
	
	@Override
	public int compareTo(Object obj) 
	{
		if (this.getClass() != obj.getClass())
			return 0;
		
		final Weather weather = (Weather) obj;
		if (this.getTimestamps().longValue() < weather.getTimestamps().longValue())
			return 1;
		else if (this.getTimestamps().longValue() > weather.getTimestamps().longValue())
			return -1;
		else
			return 0;
	}
	
}


