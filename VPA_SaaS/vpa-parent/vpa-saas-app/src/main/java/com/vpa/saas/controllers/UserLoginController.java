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

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vpa.saas.dto.UserDTO;

@RestController
public class UserLoginController extends UserBaseController {

	@RequestMapping(value = "/business/login", method = RequestMethod.POST, consumes = "application/json")
	public UserDTO login(@RequestBody UserDTO user) {
		UserDTO userDTO = getBusinessUserService().login(
				user.getEmailAddress(), user.getPassword());

		userDTO.getBusinessUser().setBrandProductMapping(
				getUtilityService().getBrandProductMappings());

		return userDTO;
	}

	@RequestMapping(value = "/brandowner/login", method = RequestMethod.POST, consumes = "application/json")
	public UserDTO brandOwnerUserLogin(@RequestBody UserDTO user) {
		UserDTO userDTO = getBrandOwnerUserService().login(
				user.getEmailAddress(), user.getPassword());

		if (userDTO != null)
			userDTO.setCurrentTimeStamp(getUtilityService()
					.getCurrentTimeStamp());

		return userDTO;
	}

	@RequestMapping(value = "/business/password/forgot", method = RequestMethod.POST, consumes = "application/json")
	public UserDTO forgotPassword(@RequestBody UserDTO user) {
		return getBusinessUserService().forgotPassword(user);
	}
 
	/// Need to change here as well like userregistrationController
	@RequestMapping(value = "/business/password/reset", method = RequestMethod.POST, consumes = "application/json")
	public UserDTO resetPassword(@RequestBody UserDTO user) {
		return getBusinessUserService().resetPassword(user);
	}
}
