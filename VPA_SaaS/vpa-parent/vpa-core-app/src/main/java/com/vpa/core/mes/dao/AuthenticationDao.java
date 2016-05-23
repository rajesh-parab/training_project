/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   AuthenticationServiceImpl.java
 ** Version     :   1.0
 ** Description :    
 **
 ** Author      :   Paratha Bhowmick
 ** Created Date :  Monday, 3 Aug 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.core.mes.dao;

import java.util.List;

import com.vpa.core.bo.AuthenticationByEntityResponseBO;
import com.vpa.core.bo.AuthenticationByLayerResponseBO;
import com.vpa.core.bo.AuthenticationByMapModuleBO;
import com.vpa.core.bo.AuthenticationDashboardAdditionalFilterBO;
import com.vpa.core.bo.AuthenticationLast24HrsResponseBO;
import com.vpa.core.bo.AuthenticationOverTimeResponseBO;
import com.vpa.core.bo.AuthenticationTop3CountriesResponseBO;

public interface AuthenticationDao {

	public List<AuthenticationTop3CountriesResponseBO> top3CountriesOnAuthentication(AuthenticationDashboardAdditionalFilterBO authenticationDashboardAdditionalFilterBO);
	
	public List<AuthenticationByLayerResponseBO> authenticationByLayer(AuthenticationDashboardAdditionalFilterBO authenticationDashboardAdditionalFilterBO);

	public List<AuthenticationByEntityResponseBO> authenticationEntityType(AuthenticationDashboardAdditionalFilterBO authenticationDashboardAdditionalFilterBO) ;
	public List<AuthenticationByMapModuleBO> authenticationByMapModule(AuthenticationDashboardAdditionalFilterBO authenticationDashboardAdditionalFilterBO);
	
	public List<AuthenticationLast24HrsResponseBO> authenticationLast24hrs(AuthenticationDashboardAdditionalFilterBO authenticationDashboardAdditionalFilterBO);

	public List<AuthenticationOverTimeResponseBO> authenticationByTime(AuthenticationDashboardAdditionalFilterBO authenticationDashboardAdditionalFilterBO);
}

