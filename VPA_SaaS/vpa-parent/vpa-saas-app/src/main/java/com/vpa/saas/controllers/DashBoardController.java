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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vpa.saas.dto.LiveScanDetailDTO;
import com.vpa.saas.services.UtilityService;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController {

	private static final Logger  logger = Logger.getLogger(DashBoardController.class);
	
	@Autowired
	UtilityService utilitySerivce;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/authentication/flag/count/{tenantId}/{brandId}/{timeStamp}", method = RequestMethod.GET)
	public Map liveAuthenticationFlag(@PathVariable("tenantId")long tenantId,@PathVariable("brandId") long brandId,
			@PathVariable("timeStamp") String timeStamp) {

		Map hm = new HashMap();
		hm.put("count", utilitySerivce.getLiveFlagCount(tenantId,brandId,timeStamp));
		return hm;

	}

	////Start of Pilot enhancement
	@RequestMapping(value = "/authentication/live/details/{tenantId}/{brandId}/{loginTime}", method = RequestMethod.GET)
	public List<LiveScanDetailDTO> getLiveAuthDetails(@PathVariable("tenantId") long tenantId,@PathVariable("brandId") long brandId,
			@PathVariable("loginTime") String loginTime) {
		return utilitySerivce.getLiveAuthenticationDetails(tenantId,brandId,loginTime);

	}

	////End of Pilot enhancement
}
