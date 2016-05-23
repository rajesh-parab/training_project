/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   RevenueLossController.java
 ** Version     :   1.0
 ** Description :   
 **
 ** Author      :   Narayan Singh,Partha Bhowmick
 ** Created Date :  Thursday, 1 Oct 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.saas.controllers;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vpa.core.bo.RevenueLossBO;
import com.vpa.core.bo.RevenueLossByCountryBO;
import com.vpa.core.bo.RevenueLossByEntityTypeBO;
import com.vpa.core.bo.RevenueLossByRegionBO;
import com.vpa.core.bo.RevenueLossOverTimeResponseBO;
import com.vpa.core.bo.Top3ProductBO;
import com.vpa.core.exceptions.CustomIllegalArgumentException;
import com.vpa.core.exceptions.ResourceNotFoundException;
import com.vpa.core.exceptions.ResourcePreconditionException;
import com.vpa.saas.dto.AuthenticationDashboardAdditionalFilterDTO;
import com.vpa.saas.dto.AuthenticationDashboardFilterForm;
import com.vpa.saas.dto.RevenueLossDashboardAdditionalFilterDTO;
import com.vpa.saas.services.AuthenticationService;
import com.vpa.saas.services.DashBoardService;
import com.vpa.saas.services.RevenueLossService;

/**
 * Controller class for Revenue Loss.
 * 
 * @author NS60097
 *
 */
@RestController
@RequestMapping("/revenueloss")
public class RevenueLossController {

	private static final Logger logger = Logger
			.getLogger(RevenueLossController.class);

	@Autowired
	RevenueLossService revenueLossService;

	@Autowired
	DashBoardService dashBoardService;

	@Autowired
	AuthenticationService authenticationService;

	private final String TENANT_ERROR_MSG = "Please provide valid tenant id. give tenantId = ";
	private final String BRAND_ERROR_MSG = "Please provide valid brand id. give brandId = ";

	/**
	 * This method give Revenue Loss detail based on user selected filter
	 * criteria. the rest url is
	 * http://localhost:8080/vpa-saas-app/revenueloss/revenueDetails with set
	 * parameter.
	 *
	 * @param filterCriteria
	 * @return RevenueLossBO
	 */

	@RequestMapping(value = "/details", method = RequestMethod.POST, consumes = "application/json")
	public RevenueLossBO getRevenueDetails(
			@RequestBody AuthenticationDashboardFilterForm filterCriteria) {
		logger.info("calling getRevenueDetails ");
		validateInputParameter(filterCriteria);
		return revenueLossService.getRevenueDetails(filterCriteria);
	}

	/**
	 * This method get the list of compromised products based on filter criteria
	 * in revenue loss dashboard page.
	 * 
	 * @param filterCriteria
	 * @return List<Top3ProductBO>
	 * 
	 */
	@RequestMapping(value = "/compromised", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<Top3ProductBO> compromisedProductForRevenue(
			@RequestBody AuthenticationDashboardFilterForm filterCriteria) {
		logger.info("calling compromisedProductForRevenue ");
		validateInputParameter(filterCriteria);
		if (filterCriteria.getDashboardPage().isEmpty()) {
			logger.error("Please provide valid dashboard page.it should not be empty");
			throw new CustomIllegalArgumentException(
					"Please provide valid dashboard page.it should not be empty");
		}

		return dashBoardService.compromisedProduct(filterCriteria);
	}

	/**
	 * This method get the list of top countries which having max revenue loss
	 * based on filter criteria.
	 * 
	 * @param filterCriteria
	 * @return List<RevenueLossByCountryBO>
	 * 
	 */
	@RequestMapping(value = "/topCountries", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<RevenueLossByCountryBO> topCountriesRevenueLoss(
			@RequestBody AuthenticationDashboardFilterForm filterCriteria) {
		logger.info("calling topCountriesRevenueLoss ");
		validateInputParameter(filterCriteria);
		return revenueLossService.revenueLossByCountry(filterCriteria);
	}

	/**
	 * This method get the details of revenue loss by region based on on filter
	 * criteria.revene loss include how many company,in which region ,how much
	 * loss.
	 * 
	 * @param filterCriteria
	 * @return List<RevenueLossByRegionBO>
	 * 
	 */
	@RequestMapping(value = "/region", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<RevenueLossByRegionBO> reveneuByRegion(
			@RequestBody AuthenticationDashboardFilterForm filterCriteria) {
		logger.info("calling topCountriesRevenueLoss ");
		validateInputParameter(filterCriteria);
		return revenueLossService.revenueLossByRegion(filterCriteria);
	}

	/**
	 * This method get the details of revenue loss by entity type based on
	 * filter criteria in revenue loss dashboard page.revene loss include how
	 * many company loss revenue in which region.
	 * 
	 * @param filterCriteria
	 * @return List<RevenueLossByEntityTypeBO>
	 * 
	 */
	@RequestMapping(value = "/entity", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<RevenueLossByEntityTypeBO> reveneuByEntity(
			@RequestBody AuthenticationDashboardFilterForm filterCriteria) {
		logger.info("calling topCountriesRevenueLoss ");
		validateInputParameter(filterCriteria);
		return revenueLossService.revenueLossByEntityType(filterCriteria);
	}

	private void validateInputParameter(
			AuthenticationDashboardFilterForm filterCriteria) {
		if (filterCriteria.getTenantId() == 0) {
			throw new ResourcePreconditionException(TENANT_ERROR_MSG
					+ filterCriteria.getTenantId());
		}
		if (filterCriteria.getBrandId() == 0) {
			throw new ResourcePreconditionException(BRAND_ERROR_MSG
					+ filterCriteria.getBrandId());
		}
	}


 @RequestMapping(value = "/byTime", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<RevenueLossOverTimeResponseBO> revenueLossByTime(
			@RequestBody RevenueLossDashboardAdditionalFilterDTO filterCriteria) {

		List<RevenueLossOverTimeResponseBO> revenueLossnOverTimeResponseBOList = revenueLossService
				.revenueLossByTime(filterCriteria);

		
		return revenueLossnOverTimeResponseBOList;

	}
 

}
