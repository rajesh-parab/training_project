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
package com.vpa.saas.services;

import java.util.List;

import com.vpa.core.bo.RevenueLossBO;
import com.vpa.core.bo.RevenueLossByCountryBO;
import com.vpa.core.bo.RevenueLossByEntityTypeBO;
import com.vpa.core.bo.RevenueLossByRegionBO;
import com.vpa.core.bo.RevenueLossOverTimeResponseBO;
import com.vpa.saas.dto.AuthenticationDashboardFilterForm;
import com.vpa.saas.dto.RevenueLossDashboardAdditionalFilterDTO;

public interface RevenueLossService {

	/**
	 * This method get the revenue loss , projected revenue loss and glp updated
	 * date based on the user selected filter criteria.
	 * 
	 * @param filter
	 * @return RevenueLossBO
	 */
	public RevenueLossBO getRevenueDetails(
			AuthenticationDashboardFilterForm filter);
	/**
	 * This method return the revenue loss details based on region from DAO
	 * layers.
	 * 
	 * @param AuthenticationDashboardFilterForm
	 * @return List<RevenueLossByRegionBO>
	 */
	public List<RevenueLossByRegionBO> revenueLossByRegion(AuthenticationDashboardFilterForm filter);
	
	/**
	 * This method return the revenue loss details based on entity type from DAO
	 * layers.
	 * 
	 * @param AuthenticationDashboardFilterForm
	 * @return List<RevenueLossByEntityTypeBO>
	 */
	public List<RevenueLossByEntityTypeBO> revenueLossByEntityType(AuthenticationDashboardFilterForm filter);
	
	/**
	 * This method return the revenue loss details based on country from DAO
	 * layers.
	 * 
	 * @param AuthenticationDashboardFilterForm
	 * @return List<RevenueLossByCountryBO>

	 */
	public List<RevenueLossByCountryBO> revenueLossByCountry(AuthenticationDashboardFilterForm filter);
	
	 /** This method return the revenue loss details based on time from DAO
	 * layers.
	 * 
	 * @param RevenueLossDashboardAdditionalFilterDTO
	 * @return List<RevenueLossOverTimeResponseBO>
	 */
	public List<RevenueLossOverTimeResponseBO> revenueLossByTime(RevenueLossDashboardAdditionalFilterDTO filter);
}
