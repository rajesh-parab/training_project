/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   CountryController.java
 ** Version     :   1.0
 ** Description :   
 **
 ** Author      :   Narayan Singh
 ** Created Date :  Wednesday, 7 Oct 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.saas.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vpa.core.bo.CountryListBO;
import com.vpa.core.exceptions.CustomIllegalArgumentException;
import com.vpa.saas.dto.AuthenticationDashboardFilterForm;
import com.vpa.saas.services.CountryService;

/**
 * @author NS60097
 *
 */
@RestController
@RequestMapping("/countries")
public class CountryController {

	private static final Logger logger = Logger
			.getLogger(CountryController.class);
	@Autowired
	CountryService countryService;

	/**
	 * This method get the list of country based on filter criteria.
	 * 
	 * @param filterCriteria
	 * @return List<CountryListBO>
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST, consumes = "application/json")
	public List<CountryListBO> getCountryList(
			@RequestBody AuthenticationDashboardFilterForm filterCriteria) {
		logger.info("calling getCountryList ");
		if (filterCriteria.getTenantId() == 0) {
			logger.error("Please provide valid tenant id. give tenantId ="
					+ filterCriteria.getTenantId());
			throw new CustomIllegalArgumentException(
					"Please provide valid tenant id. give tenantId ="
							+ filterCriteria.getTenantId());
		}

		if (filterCriteria.getBrandId() == 0) {
			logger.error("Please provide valid brand id. give brandId ="
					+ filterCriteria.getBrandId());
			throw new CustomIllegalArgumentException(
					"Please provide valid brand id. give brandId ="
							+ filterCriteria.getBrandId());
		}

		return countryService.getCountryList(filterCriteria);
	}
}
