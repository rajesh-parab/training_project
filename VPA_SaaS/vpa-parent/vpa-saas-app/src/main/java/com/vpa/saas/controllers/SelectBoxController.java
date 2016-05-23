/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   SelectBoxController.java
 ** Version     :   1.0
 ** Description :   Controller for mapping select box data to mobile or web client in form of label value. i.e.Id and Names.
 **                 Most of the data is from master table.
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
package com.vpa.saas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vpa.core.models.LabelValueBean;
import com.vpa.saas.dto.CompanyDTO;
import com.vpa.saas.dto.SellerDTO;
import com.vpa.saas.services.SelectBoxService;

@RestController
@RequestMapping("/selectbox")
public class SelectBoxController {

	@Autowired
	private SelectBoxService selectBoxService;

	/**
	 * This service will return physical seller id and names to populate auto select box
	 * by UI client. User may type letter a then he will get all the seller
	 * names starts with a. If he type letter am then he will get sellers starts
	 * with am for example amazon,amdocs etc.
	 * 
	 * @param sellerName
	 * @return list of id's and seller names seller master table.
	 */
	@RequestMapping(value = "/sellers/{sellerName}", method = RequestMethod.GET)
	public List<LabelValueBean> findAutoSelectSellerName(
			@PathVariable("sellerName") String sellerName) {
		return selectBoxService.findAutoSelectSellerNamesStartWith(sellerName);
	}
	/**
	 * This service will return online seller id and names to populate auto select box
	 * by UI client. User may type letter a then he will get all the seller
	 * names starts with a. If he type letter am then he will get sellers starts
	 * with am for example amazon,amdocs etc.
	 * 
	 * @param sellerName
	 * @return list of id's and seller names seller master table.
	 */
	@RequestMapping(value = "/sellers/online/{sellerName}", method = RequestMethod.GET)
	public List<LabelValueBean> findAutoSelectOnlineSellerName(
			@PathVariable("sellerName") String sellerName) {
		return selectBoxService.findAutoSelectOnlineSellerNamesStartWith(sellerName);
	}
	/**
	 * This service will return country id and names to populate auto select box
	 * by UI client. User may type letter a then he will get all the countries
	 * names starts with a. If he type letter am then he will get countries
	 * starts with am for India,Iran etc.
	 * 
	 * @param countryName
	 * @return list of id's and country names form country tables
	 */
	@RequestMapping(value = "/countries/{countryName}", method = RequestMethod.GET)
	public List<LabelValueBean> findAutoSelectCountryName(
			@PathVariable("countryName") String countryName) {
		return selectBoxService
				.findAutoSelectCountryNamesStartWith(countryName);
	}

	/**
	 * This service will return city id and names to populate auto select box by
	 * UI client. User may type letter a then he will get all the cities names
	 * starts with a. If he type letter am then he will get cities starts with
	 * am for example Mumbai,Munich etc.
	 * 
	 * @param cityName
	 * @return list of id's and city names from city table
	 */
	@RequestMapping(value = "/cities/{cityName}", method = RequestMethod.GET)
	public List<LabelValueBean> findAutoSelectCityNames(
			@PathVariable("cityName") String cityName) {
		return selectBoxService.findAutoSelectCityNamesStartWith(cityName);
	}

	/**
	 * This service will return company id and names to populate auto select box
	 * by UI client. User may type letter a then he will get all the companies
	 * names starts with a. If he type letter am then he will get companies
	 * starts with am for example CISCO,CIDCO etc.
	 * 
	 * @param companyName
	 * @return list of id's and company names from company tables
	 */
	@RequestMapping(value = "/companies/{companyName}", method = RequestMethod.GET)
	public List<LabelValueBean> findAutoSelectCompanyNames(
			@PathVariable("companyName") String companyName) {
		return selectBoxService
				.findAutoSelectCompanyNamesStartWith(companyName);
	}
	 /**
	  * This service will return the single company details on the server.
	  * based on company id. When user select company name details of the company will be filled.
	  * @param companyId
	  * @return
	  */
	@RequestMapping(value = "/company/{companyId}", method = RequestMethod.GET)
	public CompanyDTO getCompanyDetails(
			@PathVariable("companyId") Long companyId) {
		return selectBoxService
				.findCompanyDetails(companyId);
	}
	/**
	 * This service will return level id and names to populate select box by UI
	 * client.
	 * 
	 * @param levels
	 * @return return id's and names from level master tables.
	 */
	@RequestMapping(value = "/levels", method = RequestMethod.GET)
	public List<LabelValueBean> getLevels() {
		return selectBoxService.findAllLevelsIdAndNames();
	}
	 /**
	  * This service will return the single seller details on the server.
	  * based on seller id. When user select seller name details of the seller will be filled.
	  * @param sellerId
	  * @return
	  */
	@RequestMapping(value = "/seller/{sellerId}", method = RequestMethod.GET)
	public SellerDTO getSellereDetails(
			@PathVariable("sellerId") Long sellerId) {
		return selectBoxService
				.findSellerDetails(sellerId);
	}

}
