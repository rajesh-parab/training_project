/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   GeoLocation.java
 ** Version     :   1.0
 ** Description :   This implementation model the  Google Map services response  .
 **
 ** Author      :   Rajesh Parab
 ** Created Date :  Monday, 19 Oct 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 *
 **************************************************************************************************************/
package com.vpa.saas.services.geocoding;

public class GeoLocation {

	private String countryName;

	private String cityName;
    // public to allow junit to use
	public static final String DEFAULT_LOCATION = "N.A.";

	// two letter ISO 3166-1 country code.
	private String isoCountryCode;

	public String getCountryName() {
		return ((countryName == null) ? DEFAULT_LOCATION : countryName);
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCityName() {
		return ((cityName == null) ? DEFAULT_LOCATION : cityName);
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getIsoCountryCode() {
		return ((isoCountryCode == null) ? DEFAULT_LOCATION : isoCountryCode);
	}

	public void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
	}

}
