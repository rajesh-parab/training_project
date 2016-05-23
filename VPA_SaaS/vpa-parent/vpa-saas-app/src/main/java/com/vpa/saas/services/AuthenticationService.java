/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   AuthenticationService.java
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
package com.vpa.saas.services;

import java.util.List;

import com.vpa.core.bo.AuthenticationByEntityResponseBO;
import com.vpa.core.bo.AuthenticationByLayerResponseBO;
import com.vpa.core.bo.AuthenticationByMapModuleBO;
import com.vpa.core.bo.AuthenticationLast24HrsResponseBO;
import com.vpa.core.bo.AuthenticationOverTimeResponseBO;
import com.vpa.core.bo.AuthenticationTop3CountriesResponseBO;
import com.vpa.saas.dto.AuthenticationDashboardAdditionalFilterDTO;

public interface AuthenticationService {

	public List<AuthenticationTop3CountriesResponseBO> top3CountriesOnAuthentication(AuthenticationDashboardAdditionalFilterDTO filter) ;
	public List<AuthenticationByLayerResponseBO> authenticationByLayer(AuthenticationDashboardAdditionalFilterDTO filter) ;
	public List<AuthenticationByEntityResponseBO> authenticationEntityType(AuthenticationDashboardAdditionalFilterDTO filter) ;
	public List<AuthenticationByMapModuleBO> authenticationByMapModule(AuthenticationDashboardAdditionalFilterDTO filter);
	public List<AuthenticationLast24HrsResponseBO> authenticationLast24hrs(AuthenticationDashboardAdditionalFilterDTO filter);
	public List<AuthenticationOverTimeResponseBO> authenticationByTime(AuthenticationDashboardAdditionalFilterDTO filter);
}
