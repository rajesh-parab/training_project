/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   AuthenticationController.java
 ** Version     :   1.0
 ** Description :    
 **
 ** Author      :   Partha Bhowmick,Narayan Singh
 ** Created Date :  Monday, 3 Aug 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.saas.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vpa.core.bo.AuthenticationByEntityResponseBO;
import com.vpa.core.bo.AuthenticationByLayerResponseBO;
import com.vpa.core.bo.AuthenticationByMapModuleBO;
import com.vpa.core.bo.AuthenticationLast24HrsResponseBO;
import com.vpa.core.bo.AuthenticationOverTimeResponseBO;
import com.vpa.core.bo.AuthenticationTop3CountriesResponseBO;
import com.vpa.core.bo.Top3ProductBO;
import com.vpa.core.exceptions.CustomIllegalArgumentException;
import com.vpa.core.exceptions.ResourceNotFoundException;
import com.vpa.saas.dto.AuthenticationDashboardAdditionalFilterDTO;
import com.vpa.saas.dto.AuthenticationDashboardFilterForm;
import com.vpa.saas.services.AuthenticationService;
import com.vpa.saas.services.DashBoardService;
import com.vpa.saas.services.UtilityService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private static final Logger logger = Logger
			.getLogger(AuthenticationController.class);

	@Autowired
	AuthenticationService authenticationService;

	@Autowired
	private UtilityService utilityService;

	@Autowired
	DashBoardService dashBoardService;

	@RequestMapping(value = "/currentMonth", method = RequestMethod.GET)
	public Map<String, String> getCurrentMonth() {
		Map<String, String> hm = new HashMap<String, String>();
		hm.put("currentMonthYear", utilityService.getCurrentMonthYear());
		return hm;

	}

	/**
	 * This method get the list the details which are required to show the
	 * filter criteria in Authentication dashboard page.
	 * 
	 * @param filterCriteria
	 * @return List<Top3ProductBO>
	 * 
	 */
	@RequestMapping(value = "/compromised", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<Top3ProductBO> compromisedProductForAuthentication(
			@RequestBody AuthenticationDashboardFilterForm filterCriteria) {

		if (filterCriteria.getTenantId() == 0) {
			logger.error("Please provide valid tenant id. give tenantId ="
					+ filterCriteria.getTenantId());
			throw new CustomIllegalArgumentException(
					"Please provide valid tenant id. give tenantId ="
							+ filterCriteria.getTenantId());
		}

		if (filterCriteria.getBrandId() == 0) {
			logger.error("Please provide valid brand id. give brandId ="
					+ filterCriteria.getBrandId());
			throw new CustomIllegalArgumentException(
					"Please provide valid brand id. give brandId ="
							+ filterCriteria.getBrandId());
		}
		if (filterCriteria.getDashboardPage().isEmpty()) {
			logger.error("Please provide valid dashboard page.it should not be empty");
			throw new CustomIllegalArgumentException(
					"Please provide valid dashboard page.it should not be empty");
		}

		return dashBoardService
				.compromisedProduct(filterCriteria);
	}

	@RequestMapping(value = "/top3Countries", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<AuthenticationTop3CountriesResponseBO> top3CountriesOnAuthentication(
			@RequestBody AuthenticationDashboardAdditionalFilterDTO filterCriteria) {

		List<AuthenticationTop3CountriesResponseBO> top3CountriesResponseBOList = authenticationService
				.top3CountriesOnAuthentication(filterCriteria);

		if (CollectionUtils.isEmpty(top3CountriesResponseBOList)) {
			logger.info("No Record found for tenantId: "
					+ filterCriteria.getTenantId() + " brandId: "
					+ filterCriteria.getBrandId());
			throw new ResourceNotFoundException("no record found for tenant"
					+ filterCriteria.getTenantId() + "brand"
					+ filterCriteria.getBrandId());

		}
		return top3CountriesResponseBOList;
	}

	@RequestMapping(value = "/byLayer", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<AuthenticationByLayerResponseBO> authenticationByLayer(
			@RequestBody AuthenticationDashboardAdditionalFilterDTO filterCriteria) {

		List<AuthenticationByLayerResponseBO> authenticationDashboardFilterBOList = authenticationService
				.authenticationByLayer(filterCriteria);

		if (CollectionUtils.isEmpty(authenticationDashboardFilterBOList)) {
			logger.info("No Record found for tenantId: "
					+ filterCriteria.getTenantId() + " brandId: "
					+ filterCriteria.getBrandId());
			throw new ResourceNotFoundException("no record found for tenant"
					+ filterCriteria.getTenantId() + "brand"
					+ filterCriteria.getBrandId());

		}
		return authenticationDashboardFilterBOList;
	}

	@RequestMapping(value = "/byEntityType", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<AuthenticationByEntityResponseBO> authenticationEntityType(
			@RequestBody AuthenticationDashboardAdditionalFilterDTO filterCriteria) {

		List<AuthenticationByEntityResponseBO> authenticationByEntityResponseBOList = authenticationService
				.authenticationEntityType(filterCriteria);

		if (CollectionUtils.isEmpty(authenticationByEntityResponseBOList)) {
			logger.info("No Record found for tenantId: "
					+ filterCriteria.getTenantId() + " brandId: "
					+ filterCriteria.getBrandId());
			throw new ResourceNotFoundException("no record found for tenant"
					+ filterCriteria.getTenantId() + "brand"
					+ filterCriteria.getBrandId());

		}
		return authenticationByEntityResponseBOList;
	}

	@RequestMapping(value = "/byMapModule", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<AuthenticationByMapModuleBO> authenticationMapModule(
			@RequestBody AuthenticationDashboardAdditionalFilterDTO filterCriteria) {

		List<AuthenticationByMapModuleBO> authenticationByMapModuleBOList = authenticationService
				.authenticationByMapModule(filterCriteria);

		if (CollectionUtils.isEmpty(authenticationByMapModuleBOList)) {
			logger.info("No Record found for tenantId: "
					+ filterCriteria.getTenantId() + " brandId: "
					+ filterCriteria.getBrandId());
			throw new ResourceNotFoundException("no record found for tenant"
					+ filterCriteria.getTenantId() + "brand"
					+ filterCriteria.getBrandId());

		}
		return authenticationByMapModuleBOList;
	}

	@RequestMapping(value = "/last24hrs", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<AuthenticationLast24HrsResponseBO> authenticationLast24hrs(
			@RequestBody AuthenticationDashboardAdditionalFilterDTO filterCriteria) {

		List<AuthenticationLast24HrsResponseBO> authenticationLast24HrsResponseBOList = authenticationService
				.authenticationLast24hrs(filterCriteria);

		if (CollectionUtils.isEmpty(authenticationLast24HrsResponseBOList)) {
			logger.info("No Record found for tenantId: "
					+ filterCriteria.getTenantId() + " brandId: "
					+ filterCriteria.getBrandId());
			throw new ResourceNotFoundException("no record found for tenant"
					+ filterCriteria.getTenantId() + "brand"
					+ filterCriteria.getBrandId());

		}
		return authenticationLast24HrsResponseBOList;

	}

	@RequestMapping(value = "/byTime", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<AuthenticationOverTimeResponseBO> authenticationByTime(
			@RequestBody AuthenticationDashboardAdditionalFilterDTO filterCriteria) {

		List<AuthenticationOverTimeResponseBO> authenticationOverTimeResponseBOList = authenticationService
				.authenticationByTime(filterCriteria);

		if (CollectionUtils.isEmpty(authenticationOverTimeResponseBOList)) {
			logger.info("No Record found for tenantId: "
					+ filterCriteria.getTenantId() + " brandId: "
					+ filterCriteria.getBrandId());
			throw new ResourceNotFoundException("no record found for tenant"
					+ filterCriteria.getTenantId() + "brand"
					+ filterCriteria.getBrandId());

		}
		return authenticationOverTimeResponseBOList;

	}

}
