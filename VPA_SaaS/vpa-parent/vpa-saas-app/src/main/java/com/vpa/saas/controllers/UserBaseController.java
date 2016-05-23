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

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vpa.saas.services.TenantBrandService;
import com.vpa.saas.services.TokenService;
import com.vpa.saas.services.UserService;
import com.vpa.saas.services.UtilityService;

@RequestMapping("/user")
public class UserBaseController {

	@Resource(name = "businessUserService")
	private UserService businessUserService;

	@Resource(name = "brandOwnerUserService")
	private UserService brandOwnerUserService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private TenantBrandService tenantBrandService;

	@Autowired
	private UtilityService utilityService;

	public UserService getBusinessUserService() {
		return businessUserService;
	}

	public UserService getBrandOwnerUserService() {
		return brandOwnerUserService;
	}

	public TenantBrandService getTenantBrandService() {
		return tenantBrandService;
	}

	public UtilityService getUtilityService() {
		return utilityService;
	}

	public TokenService getTokenService() {
		return tokenService;
	}

}
