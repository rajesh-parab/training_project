/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   ReverseGeoCoding.java
 ** Version     :   1.0
 ** Description :   This is intrface define method to get Location based on input like latitude longitude
 **
 ** Author      :   Rajesh Parab
 ** Created Date :  Friday, 16 Oct 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 *
 **************************************************************************************************************/

package com.vpa.saas.services.geocoding;



public interface ReverseGeoCoding {
	/**
	 * This method get the location based on the latitude and longitude.
	 * 
	 * @param latitude
	 * @param longitude
	 * @return Address information matching latitude and longitude.
	 */
	GeoLocation getLocation(String latitude, String longitude);

	/**
	 * This is utility for data loading for update country code with ISO Code
	 * with 1 Alpha date. Not use in application but only for loading data.
	 */
	void updateISO2alphaCountryCode();
}
