/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   SellerService.java
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

import com.vpa.core.models.City;
import com.vpa.core.models.CountryMaster;
import com.vpa.saas.dto.SellerDTO;

public interface SellerService {
	/**
	 * add seller in seller table if not exist or return if exist one.
	 * 
	 * @param sellerDTO
	 * @param sellerName
	 * @param country
	 * @param city
	 * @return
	 */
	Long addNewSellerInDB(SellerDTO sellerDTO, String sellerName,
			CountryMaster country, City city);
}
