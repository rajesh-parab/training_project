/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   CityServiceImpl.java
 ** Version     :   1.0
 ** Description :   Implementation class for city table related operations.
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
package com.vpa.saas.services.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.vpa.core.mes.dao.CityDao;
import com.vpa.core.models.City;
import com.vpa.core.models.CountryMaster;
import com.vpa.saas.services.CityService;
@Service
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
public class CityServiceImpl implements CityService {

	@Autowired
	private CityDao cityDao;
	
	/**
	 * @see CityService#addNewCityInDatabase(CountryMaster, String)
	 */
	@Override
	public City addNewCityInDatabase(CountryMaster country, String cityName) {
		List<City> cities = cityDao.findByNameIgnoringCase(cityName);
		City city = null;
		if (CollectionUtils.isEmpty(cities)) {
			city = new City();
			city.setName(cityName);
			city.setCreatedDate(Calendar.getInstance().getTime());
			city.setUpdatedDate(Calendar.getInstance().getTime());
			city.setCountryMaster(country);
			city = cityDao.save(city);

		} else {
			city = cities.get(0);
		}
		return city;
	}
}
