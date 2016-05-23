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

package com.vpa.core.mes.dao;

import java.util.List;

import com.vpa.core.bo.AuthenticationDashboardFilterBO;
import com.vpa.core.bo.RevenueLossBO;
import com.vpa.core.bo.RevenueLossByRegionBO;
import com.vpa.core.bo.RevenueLossDashboardAdditionalFilterBO;
import com.vpa.core.bo.RevenueLossOverTimeResponseBO;

/**
 * Dao Interface for Revenue Loss functionality
 * 
 * @author NS60097
 *
 */
public interface RevenueLossDao {

	/**
	 * 
	 * This method get the revenue loss , projected revenue loss and glp updated
	 * date from data ware house DB based on the user selected filter criteria
	 * and convert the raw object to RevenueLossBO .
	 * 
	 * @param boObject
	 * @return RevenueLossBO
	 */

	public RevenueLossBO getRevenueDetails(
			AuthenticationDashboardFilterBO boObject);

	/**
	 * This method get the number of company's in particular region,how much revenue loss.
	 * 
	 * @param filter
	 * @return List<RevenueLossByRegionBO>
	 */
	public List<RevenueLossByRegionBO> revenueLossByRegion(
			AuthenticationDashboardFilterBO filter);
	
}
