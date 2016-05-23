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

import java.util.Map;

import com.vpa.core.dws.models.ProductDetails;
import com.vpa.core.bo.ProductRequestBO;
import com.vpa.core.dws.dao.DimProductDao;
import com.vpa.core.dws.dao.ProductDetailsDao;
import com.vpa.core.exceptions.ResourceNotFoundException;
import com.vpa.saas.dto.CompromisedProductDTO;
import com.vpa.saas.dto.UnProtectedProductDTO;
import com.vpa.saas.services.DashBoardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.vpa.saas.dto.BrandDTO;
import com.vpa.saas.services.ProductService;
import com.vpa.saas.services.UtilityService;

/**
 * @author PD42694
 *
 */
@RestController
@RequestMapping("/product")
public class ProductController {
	private static final Logger logger = Logger.getLogger(ProductController.class);

	@Autowired
	DashBoardService dashBoardService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UtilityService utilityService;

	@Autowired
	private DimProductDao dimProductDao;

	@Autowired
	private ProductDetailsDao productDetailsDao;

	@RequestMapping(value = "/unprotected", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public UnProtectedProductDTO unProtectedProductDashBoard(
			@RequestBody ProductRequestBO requestBO) {
		logger.info("Calling unProtectedProductDashBoard" );
		String tenantId = requestBO.getTenantId();
		UnProtectedProductDTO unprotectedProduct = dashBoardService
				.unProtectedProductDashboard(tenantId);
		if (null == unprotectedProduct) {
			logger.error("No record found for tenantId :" + tenantId);
			throw new ResourceNotFoundException(
					"No record found for tenantId :" + tenantId);
		}
		return unprotectedProduct;
	}

	@RequestMapping(value = "/compromised", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public CompromisedProductDTO compromisedProductDashBoard(
			@RequestBody ProductRequestBO requestBO) {
		logger.info("Calling compromisedProductDashBoard" );
		String tenantId = requestBO.getTenantId();
		CompromisedProductDTO compromisedProduct = dashBoardService
				.compromisedProductDashboard(requestBO);
		if (null == compromisedProduct) {
			logger.error("No record found for tenantId :" + tenantId);
			throw new ResourceNotFoundException(
					"No record found for tenantId :" + tenantId);
		}
		return compromisedProduct;
	}

	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public ProductDetails getProductById(
			@PathVariable("productId") Long productId) {
		logger.info("Calling getProductById method for productId: "+ productId);

		ProductDetails currentProduct = productDetailsDao.getOne(productId);

		if (null == currentProduct) {
			logger.error("No record found for productId :" + productId);
			throw new ResourceNotFoundException(
					"No record found for productId :" + productId);
		}
		return currentProduct;
	}

	@RequestMapping(value = "/brand/{tenantId}", method = RequestMethod.GET)
	public Map<String,BrandDTO> getProductsByTenant(
			@PathVariable("tenantId") Long tenantId) {
		logger.info("Calling getProductsByTenant method for tenantId: "+ tenantId);
		return utilityService.getBrandProductMappings();
	 
	}
}
