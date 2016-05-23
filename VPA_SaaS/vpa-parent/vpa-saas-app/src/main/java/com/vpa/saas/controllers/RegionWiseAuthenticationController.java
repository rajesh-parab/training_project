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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vpa.saas.dto.DimAuthenticationDTO;
import com.vpa.saas.services.DimAuthenticationService;

@RestController
@RequestMapping("/dashboard")
public class RegionWiseAuthenticationController {

   
    @Autowired
    DimAuthenticationService dimAuthenticationService;

    @RequestMapping(value = "/authentication/kpi/{tenantId}/{brandId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public DimAuthenticationDTO getWorldWideAuthenticationKPI(@PathVariable("tenantId") Long tenantId,@PathVariable("brandId") Long brandId) {
        return dimAuthenticationService
                .getWorldWideAuthenticationKPI(tenantId,brandId);


    }

    @RequestMapping(value = "/authentication/regions/{tenantId}/{brandId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public DimAuthenticationDTO getRegionWiseAuthentication(@PathVariable("tenantId") Long tenantId,@PathVariable("brandId") Long brandId) {
        return dimAuthenticationService
                .getRegionWideAuthenticationTotals(tenantId,brandId);

    }

}
