package com.edward.WeatherInquirySys.exception;

public enum APICode {
	
	OK(0),
	InvalidParameter(101),
	UniqueParameter(102),
	AccessFailed(103),
	Other(999);
	
	
	private int code;
	APICode(int code) 
	{
		this.code = code;
	}
	
	public int getCode()
	{
		return code;
	}

}
