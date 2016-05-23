/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   RevenueLossServiceImpl.java
 ** Version     :   1.0
 ** Description :   
 **
 ** Author      :   Narayan Singh,Partha Bhowmick
 ** Created Date :  Thursday, 1 Oct 2015
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

import com.vpa.core.bo.AuthenticationDashboardAdditionalFilterBO;
import com.vpa.core.bo.AuthenticationDashboardFilterBO;
import com.vpa.core.bo.AuthenticationOverTimeResponseBO;
import com.vpa.core.bo.RevenueLossBO;
import com.vpa.core.bo.RevenueLossByCountryBO;
import com.vpa.core.bo.RevenueLossByEntityTypeBO;
import com.vpa.core.bo.RevenueLossByRegionBO;
import com.vpa.core.bo.RevenueLossDashboardAdditionalFilterBO;
import com.vpa.core.bo.RevenueLossOverTimeResponseBO;
import com.vpa.core.dws.dao.RevenueLossDao;
import com.vpa.saas.dto.AuthenticationDashboardAdditionalFilterDTO;
import com.vpa.saas.dto.AuthenticationDashboardFilterForm;
import com.vpa.saas.dto.RevenueLossDashboardAdditionalFilterDTO;
import com.vpa.saas.services.RevenueLossService;


/**
 * Implementation class of Service Interface for Revenue Loss. 
 * @author NS60097
 *
 */
@Service
public class RevenueLossServiceImpl implements RevenueLossService {

	@Autowired
	RevenueLossDao revenueLossDao;

	/**
	 * @see RevenueLossService#getRevenueDetails(AuthenticationDashboardFilterForm)
	 */
	
	@Override
	public RevenueLossBO getRevenueDetails(
			AuthenticationDashboardFilterForm filter) {
		AuthenticationDashboardFilterBO filterBO = new AuthenticationDashboardFilterBO();
		BeanUtils.copyProperties(filter, filterBO);
		return revenueLossDao.getRevenueDetails(filterBO);
	}
	
	/**
	 * @see RevenueLossService#revenueLossByRegion(AuthenticationDashboardFilterForm)
	 */

	@Override
	public List<RevenueLossByRegionBO> revenueLossByRegion(
			AuthenticationDashboardFilterForm filter) {
		AuthenticationDashboardFilterBO filterBO = new AuthenticationDashboardFilterBO();
		BeanUtils.copyProperties(filter, filterBO);
		
		return revenueLossDao.revenueLossByRegion(filterBO);
	}

	/**
	 * @see RevenueLossService#revenueLossByEntityType(AuthenticationDashboardFilterForm)
	 */
	@Override
	public List<RevenueLossByEntityTypeBO> revenueLossByEntityType(
			AuthenticationDashboardFilterForm filter) {
		AuthenticationDashboardFilterBO filterBO = new AuthenticationDashboardFilterBO();
		BeanUtils.copyProperties(filter, filterBO);
		
		return revenueLossDao.revenueLossByEntityType(filterBO);
	}

	/**
	 * @see RevenueLossService#revenueLossByCountry(AuthenticationDashboardFilterForm)
	 */
	@Override
	public List<RevenueLossByCountryBO> revenueLossByCountry(
			AuthenticationDashboardFilterForm filter) {
		AuthenticationDashboardFilterBO filterBO = new AuthenticationDashboardFilterBO();
		BeanUtils.copyProperties(filter, filterBO);
		return revenueLossDao.revenueLossByCountry(filterBO);
	}


	@Override
	public List<RevenueLossOverTimeResponseBO> revenueLossByTime (RevenueLossDashboardAdditionalFilterDTO filter){
		RevenueLossDashboardAdditionalFilterBO revenueLossDashboardAdditionalFilterBO=new RevenueLossDashboardAdditionalFilterBO();
		BeanUtils.copyProperties(filter,
				revenueLossDashboardAdditionalFilterBO);
		return revenueLossDao.revenueLossByTime(revenueLossDashboardAdditionalFilterBO);
	}
}
