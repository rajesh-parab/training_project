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
 ** Created Date :  Monday, 5 OCT 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.saas.dto;

public class AuthenticationDashboardAdditionalFilterDTO extends AuthenticationDashboardFilterForm{

	String authType;
	String suspectAuthType;
	int timeGraphIndicator;
	
	// parameters for country page time graph
	Long city;

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getSuspectAuthType() {
		return suspectAuthType;
	}

	public void setSuspectAuthType(String suspectAuthType) {
		this.suspectAuthType = suspectAuthType;
	}

	public int getTimeGraphIndicator() {
		return timeGraphIndicator;
	}

	public void setTimeGraphIndicator(int timeGraphIndicator) {
		this.timeGraphIndicator = timeGraphIndicator;
	}

	public Long getCity() {
		return city;
	}

	public void setCity(Long city) {
		this.city = city;
	}

	
}
