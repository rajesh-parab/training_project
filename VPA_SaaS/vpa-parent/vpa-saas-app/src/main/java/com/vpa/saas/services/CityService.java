/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   CityService.java
 ** Version     :   1.0
 ** Description :   Interface to city related operations.
 **
 ** Author      :   Rajesh Parab 
 ** Created Date :  Tuesday, 27 Oct 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.saas.services;

import com.vpa.core.models.City;
import com.vpa.core.models.CountryMaster;

public interface CityService {
	 
	/**
	 * add city in city table if one not exist.
	 * 
	 * @param country - country name city belong to.
	 * @param cityName - city name to  add
	 * @return
	 */
	City addNewCityInDatabase(CountryMaster country, String cityName);
}
