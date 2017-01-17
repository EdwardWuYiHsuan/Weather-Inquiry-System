package com.edward.WeatherInquirySys.dao;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.edward.WeatherInquirySys.models.City;


@Repository
@Transactional
public class CityDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	public boolean isExist(Long id)
	{
		return null != getSession().get(City.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<City> getList()
	{
		Criteria criterial = getSession().createCriteria(City.class).addOrder(Order.asc("id"));
		
		return (List<City>) criterial.list();
	}
	
	public City save(City city)
	{
		Serializable primaryKey = getSession().save(city);
		city.setId((Long) primaryKey);
		
		return city;
	}
	
	public void delete(Long id)
	{
		getSession().delete(new City(id));
	}
	
	private Session getSession()
	{
		return sessionFactory.getCurrentSession();
	}
	
	
}
