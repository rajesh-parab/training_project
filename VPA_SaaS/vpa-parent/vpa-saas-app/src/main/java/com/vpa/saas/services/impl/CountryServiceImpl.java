/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   CountryServiceImpl.java
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
package com.vpa.saas.services.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpa.core.bo.AuthenticationDashboardFilterBO;
import com.vpa.core.bo.CountryListBO;
import com.vpa.core.dws.dao.CountryListDao;
import com.vpa.core.dws.dao.DimProductDao;
import com.vpa.core.enums.Sorting;
import com.vpa.saas.dto.AuthenticationDashboardFilterForm;
import com.vpa.saas.services.CountryService;

/**
 * @author NS60097
 *
 */
@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	CountryListDao countryDao;
	
	@Autowired
	DimProductDao dimProductDao;

	
	
	/**
	 * @see CountryService#getCountryList(AuthenticationDashboardFilterForm)
	 */
	@Override
	public List<CountryListBO> getCountryList(AuthenticationDashboardFilterForm filter) {
		AuthenticationDashboardFilterBO filterBO = new AuthenticationDashboardFilterBO();
		BeanUtils.copyProperties(filter, filterBO);
		filterBO.setOrderBy(Sorting.sortedBy(filter.getOrderBy()));
		return countryDao.getCountryList(filterBO);
	}

	

}
