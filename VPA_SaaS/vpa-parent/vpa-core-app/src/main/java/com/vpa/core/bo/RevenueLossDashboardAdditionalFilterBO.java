/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   RevenueLossDaoImpl.java
 ** Version     :   1.0
 ** Description :   
 **
 ** Author      :   Narayan Singh,Partha Bhowmick
 ** Created Date :  Thursday, 1 Oct 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date 		Author     			Version 	Description:
 ** -------- 	--------   			-------- 	-------------
 **  27-Oct		Narayan Singh		1.0			review comments incorporate.review comments given by hunter
 **************************************************************************************************************/
package com.vpa.core.bo;

public class RevenueLossDashboardAdditionalFilterBO extends AuthenticationDashboardFilterBO {
	int timeGraphIndicator;
	// need this as overtime has some additional filters
	public int getTimeGraphIndicator() {
		return timeGraphIndicator;
	}

	public void setTimeGraphIndicator(int timeGraphIndicator) {
		this.timeGraphIndicator = timeGraphIndicator;
	}
}
