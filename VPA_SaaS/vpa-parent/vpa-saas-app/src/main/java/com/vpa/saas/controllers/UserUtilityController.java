/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module       :   VPA-SaaS
 ** File         :   UserController.java
 ** Version      :   1.0
 ** Description  :   REST controller class exposes RESTFul web services to server the HTTP request from client application.
 **
 ** Author       :   Rajesh Parab
 ** Created Date :  Tuesday, 05 May 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/

package com.vpa.saas.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vpa.saas.dto.UserDTO;

@RestController
public class UserUtilityController extends UserBaseController {

	@RequestMapping(value = "/token/regenerate/{emailAddress}", method = RequestMethod.GET)
	public Map regenerateToken(@PathVariable("emailAddress") String emailAddress) {
		UserDTO userDTO = getTokenService().regenerateTokenToActivate(
				emailAddress);
		Map hm = new HashMap();
		hm.put("notification", userDTO.getSuccessNotification());
		hm.put("message", userDTO.getSuccessMessage());
		return hm;

	}

	@RequestMapping(value = "/mail/image", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void sendimage(@RequestBody UserDTO user) {
		getBrandOwnerUserService().sendImage(user);
	}

	 
}
