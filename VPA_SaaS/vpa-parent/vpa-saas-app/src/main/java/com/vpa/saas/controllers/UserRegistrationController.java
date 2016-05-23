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

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.vpa.saas.dto.UserDTO;
import com.vpa.saas.services.UserService;

@RestController
public class UserRegistrationController extends UserBaseController {

	@RequestMapping(value = "/business/register", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO register(@RequestBody UserDTO user) {
		getBusinessUserService().registerUser(user);
		user.setSuccessNotification("Thank you for creating an account");
		user.setSuccessMessage("You should now receive an email to verify your account.");

		return user;
	}

	@RequestMapping(value = "/brandowner/register", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO registerBrandOwnerUser(@RequestBody UserDTO user) {

		getBrandOwnerUserService().registerUser(user);
		user.setSuccessNotification("Thank you for creating an account");
		user.setSuccessMessage("You should now receive an email to verify your account.");

		return user;
	}

	// Changed/created for intgration with Web-frontend
	@RequestMapping(value = "/business/activate/{userId}/{tokenId}", method = RequestMethod.GET)
	public ModelAndView activateBusinessUser(
			@PathVariable("userId") long userId,
			@PathVariable("tokenId") String tokenId) {
		return completeRegistraion(userId, tokenId, getBusinessUserService());
	}

	@RequestMapping(value = "/brandowner/activate/{userId}/{tokenId}", method = RequestMethod.GET)
	public ModelAndView activateBrandOwner(@PathVariable("userId") long userId,
			@PathVariable("tokenId") String tokenId) {
		return completeRegistraion(userId, tokenId, getBrandOwnerUserService());
	}

	private ModelAndView completeRegistraion(long userId, String tokenId,
			UserService userService) {
		UserDTO user = new UserDTO();
		user.setId(userId);
		user.setTokenId(tokenId);
		user = userService.completeRegistration(user);
		String view = "registration-success";
		if (user.isTokenExpired()) {
			view = "registration-failed";
		}
		if (user.isUserNotFound()) {
			view = "registration-userNotFound";
		}
		return new ModelAndView(view, "user", user);
	}

	
	
}
