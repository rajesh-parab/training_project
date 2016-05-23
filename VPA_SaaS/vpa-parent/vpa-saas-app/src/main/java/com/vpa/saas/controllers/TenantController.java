/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   UserServiceImpl.java
 ** Version     :   1.0
 ** Description :   Service class implementation of UserService Interface to server the user controller and user related use cases.
 **
 ** Author      :   Rajesh Parab,Narayan Singh,Paratha Bhowmick
 ** Created Date :  Tuesday, 05 May 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/

package com.vpa.saas.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vpa.saas.dto.TenantDTO;
import com.vpa.saas.services.TenantBrandService;

/**
 * @author PD42694
 *
 */
@RestController
@RequestMapping("/tenant")
public class TenantController {
	private static final Logger logger = Logger
			.getLogger(TenantController.class);
	@Autowired
	private TenantBrandService tenantBrandService;

	/**
	 * Return all enabled tenants and corresponding brands. Use below end point
	 * to call this service http://localhost:8080/vpa-saas-app/tenant/all
	 * 
	 * @return <code>List</code><<code>TenantDTO</code>>
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<TenantDTO> getAllTenantAndBrand() {
		logger.info("Calling getAllTenantAndBrand method");
		return tenantBrandService.getAllTenantAndBrand();
	}
}
