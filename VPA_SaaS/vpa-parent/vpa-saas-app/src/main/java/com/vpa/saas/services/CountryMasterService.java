/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   CountryMasterService.java
 ** Version     :   1.0
 ** Description :   Interface to seller related operations.
 **
 ** Author      :   Rajesh Parab 
 ** Created Date :  Tuesday, 27 Oct 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.saas.services;

import com.vpa.core.models.CountryMaster;

public interface CountryMasterService {
    /**
     * Add new country name in database table if one not exist.
     * 
     * @param countryName
     * @param countryCode
     * @return
     */
	 CountryMaster addNewCountryInDatabase(String countryName,String countryCode);
}
