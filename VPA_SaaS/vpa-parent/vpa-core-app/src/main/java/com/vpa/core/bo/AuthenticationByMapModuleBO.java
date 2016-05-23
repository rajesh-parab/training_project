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
package com.vpa.core.bo;

public class AuthenticationByMapModuleBO {

	private String regionName;
	private int noOfAuthentication;
	private int entityCount;

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public int getNoOfAuthentication() {
		return noOfAuthentication;
	}

	public void setNoOfAuthentication(int noOfAuthentication) {
		this.noOfAuthentication = noOfAuthentication;
	}

	public int getEntityCount() {
		return entityCount;
	}

	public void setEntityCount(int entityCount) {
		this.entityCount = entityCount;
	}
}
