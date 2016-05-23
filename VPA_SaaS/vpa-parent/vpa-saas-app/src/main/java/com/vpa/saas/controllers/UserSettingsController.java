/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module       :   VPA-SaaS
 ** File         :   UserSettings.java
 ** Version      :   1.0
 ** Description  :   REST controller class exposes RESTFul web services to server the HTTP request from client application.
 **                  This controller handle all user settings related call from the client.                       
 **
 ** Author       :   Rajesh Parab
 ** Created Date :   Wednesday, 07 Oct 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/

package com.vpa.saas.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vpa.saas.dto.UserDTO;

@RestController
public class UserSettingsController extends UserBaseController {

	@RequestMapping(value = "/business/settings", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO saveBusinessUserSettings(@RequestBody UserDTO user) {
		return super.getBusinessUserService().updateUserSettings(user);

	}

	@RequestMapping(value = "/brandowner/settings", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO saveBrandOwnerSettings(@RequestBody UserDTO user) {
		return super.getBrandOwnerUserService().updateUserSettings(user);

	}
	
	@RequestMapping(value = "/password/change", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO changePasword(@RequestBody UserDTO user) {
		return super.getBusinessUserService().updatePassword(user);

	}

}
