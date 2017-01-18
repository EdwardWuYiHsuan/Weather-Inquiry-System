package com.edward.WeatherInquirySys.dao;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.edward.WeatherInquirySys.models.Weather;


@Repository
@Transactional
public class WeatherDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	public boolean isExist(Long id)
	{
		return null != getSession().get(Weather.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Weather> getList()
	{
		Criteria criteria = getSession().createCriteria(Weather.class);
		
		return (List<Weather>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Weather> getWeatherByCityName(String cityName)
	{
		Criteria criteria = getSession().createCriteria(Weather.class)
										.add(Restrictions.eq("cityName", cityName));
		
		return (List<Weather>) criteria.list();
	}
	
	public Weather save(Weather weather)
	{
		Serializable primaryKey = getSession().save(weather);
		weather.setId((Long) primaryKey);
		
		return weather;
	}
	
	public void delete(Long id)
	{
		getSession().delete(new Weather(id));
	}
	
	private Session getSession()
	{
		return sessionFactory.getCurrentSession();
	}
	
	
}


