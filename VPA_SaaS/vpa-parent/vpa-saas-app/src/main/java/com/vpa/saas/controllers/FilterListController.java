/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   FilterListController.java
 ** Version     :   1.0
 ** Description :    
 **
 ** Author      :   Narayan Singh
 ** Created Date :  Tuesday, 15 Sep 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.saas.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.vpa.core.exceptions.CustomIllegalArgumentException;
import com.vpa.saas.dto.FilterDTO;
import com.vpa.saas.services.FilterService;

/**
 * Controller class for Filter Criteria.
 * 
 * @author NS60097
 *
 */
@RestController
public class FilterListController {

	private static final Logger logger = Logger
			.getLogger(FilterListController.class);

	@Autowired
	private FilterService filterService;

	/**
	 * This method gives the filter criteria lists for all filtered tier 1 pages.
	 * User will provide the tenant id and filter criteria will be displayed.
	 * 
	 * the rest url is http://localhost:8080/vpa-saas-app/filter/{tenantId} with get
	 * parameter.
	 * 
	 * @return FilterDTO
	 */
	@RequestMapping(value = "/filter/{tenantId}", method = RequestMethod.GET)
	public FilterDTO getFilterList(@PathVariable("tenantId") int tenantId) {
		logger.info("calling FilterListController ");

		if (tenantId == 0) {
			logger.error("Please provide valid tenant id. give tenantId ="
					+ tenantId);
			throw new CustomIllegalArgumentException("Please provide valid "
					+ "tenant id. give tenantId ="+ tenantId);
		}
		return filterService.getFilterList(tenantId);
	}

}
