/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   SelectBoxService.java
 ** Version     :   1.0
 ** Description :   supporting interface for SelectBoxController RESTFul services.
 **
 ** Author      :   Rajesh Parab 
 ** Created Date :  Monday, 07 Sep 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.saas.services;

import java.util.List;

import com.vpa.core.models.LabelValueBean;
import com.vpa.saas.dto.CompanyDTO;
import com.vpa.saas.dto.SellerDTO;

public interface SelectBoxService {
	/**
	 * Get all the names and ids of the sellers started by the name using like
	 * operator in SQL
	 * 
	 * @param sellerName
	 * @return
	 */
	List<LabelValueBean> findAutoSelectSellerNamesStartWith(String sellerName);

	/**
	 * Get all the names and id of the countries started by the name using like
	 * operator in SQL
	 * 
	 * @param countryName
	 * @return
	 */
	List<LabelValueBean> findAutoSelectCountryNamesStartWith(String countryName);

	/**
	 * Get all the names and id of the cities started by the name using like
	 * operator in SQL
	 * 
	 * @param cityName
	 * @return
	 */
	List<LabelValueBean> findAutoSelectCityNamesStartWith(String cityName);

	/**
	 * Get all the names and id of the companies started by the name using like
	 * operator in SQL
	 * 
	 * @param companyName
	 * @return
	 */
	List<LabelValueBean> findAutoSelectCompanyNamesStartWith(String companyName);

	/**
	 * Find all the levels ids and names
	 * 
	 * @return
	 */
	List<LabelValueBean> findAllLevelsIdAndNames();

	/**
	 * Return single details of single company based on id.
	 * 
	 * @param companyId
	 * @return
	 */
	CompanyDTO findCompanyDetails(Long companyId);
	/**
	 * Get all the online names and ids of the sellers started by the name using like
	 * operator in SQL
	 * 
	 * @param sellerName
	 * @return
	 */
	List<LabelValueBean> findAutoSelectOnlineSellerNamesStartWith(
			String sellerName);

	/**
	 * Return single details of single seller based on id.
	 * 
	 * @param sellerId
	 * @return
	 */
	SellerDTO findSellerDetails(Long sellerId);
}
