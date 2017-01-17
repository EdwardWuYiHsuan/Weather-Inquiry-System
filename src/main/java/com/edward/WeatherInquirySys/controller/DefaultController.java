package com.edward.WeatherInquirySys.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.edward.WeatherInquirySys.response.DefaultResponse;
import com.edward.WeatherInquirySys.response.Result;


public class DefaultController {

	@Autowired
	private HttpServletRequest request;
	
	
	public DefaultResponse renderErrorResponse(Throwable causeEx)
	{
		System.out.printf("API [%s] Error:\t%s\n", request.getRequestURL().toString(), causeEx.getMessage());
		System.out.println(ExceptionUtils.getStackTrace(causeEx));
		
		return new DefaultResponse(Result.error, causeEx);
	}
}
