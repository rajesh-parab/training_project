/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   AuthenticationDashboardIntegrationTest.java
 ** Version     :   1.0
 ** Description :   
 **
 ** Author      :   Partha Bhowmick,Narayan Singh
 ** Created Date :  Tuesday, 11 Aug 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/

package com.vpa.saas.services;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpa.core.bo.AuthenticationByEntityResponseBO;
import com.vpa.core.bo.AuthenticationByLayerResponseBO;
import com.vpa.core.bo.AuthenticationByMapModuleBO;
import com.vpa.core.bo.AuthenticationLast24HrsResponseBO;
import com.vpa.core.bo.AuthenticationOverTimeResponseBO;
import com.vpa.core.bo.AuthenticationTop3CountriesResponseBO;
import com.vpa.core.bo.Top3ProductBO;
import com.vpa.saas.dto.AuthenticationDashboardAdditionalFilterDTO;
import com.vpa.saas.dto.AuthenticationDashboardFilterForm;


public class AuthenticationDashboardIntegrationTest extends
		AbstractVPADashBoardTest {

	public static Logger logger = Logger
			.getLogger(AuthenticationDashboardIntegrationTest.class);

	@Autowired
	AuthenticationService authenticationService;

	@Autowired
	DashBoardService dashBoardService;

	

	@Ignore
	public void geAuthOverlast24Hrs() {
		try {
			List<AuthenticationLast24HrsResponseBO> list  =authenticationService.authenticationLast24hrs(getDummyFilter());
			assertTrue("Record found", list.size() > 0);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(System.out, list);
		} catch (JsonGenerationException | JsonMappingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
	}
	
	@Test
	public void top3CountriesOnAuthentication() {
		try {
			List<AuthenticationTop3CountriesResponseBO> list  =authenticationService.top3CountriesOnAuthentication(getDummyFilter());
			assertTrue("Record found", list.size() > 0);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(System.out, list);
		} catch (JsonGenerationException | JsonMappingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
	}

	@Ignore
	public void authenticationByLayer() {
		try {
			List<AuthenticationByLayerResponseBO> list  =authenticationService.authenticationByLayer(getDummyFilter());
			assertTrue("Record found", list.size() > 0);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(System.out, list);
		} catch (JsonGenerationException | JsonMappingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
	}

	@Ignore
	public void authenticationEntityType() {
		try {
			List<AuthenticationByEntityResponseBO> list  =authenticationService.authenticationEntityType(getDummyFilter());
			assertTrue("Record found", list.size() > 0);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(System.out, list);
		} catch (JsonGenerationException | JsonMappingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
	}

	@Ignore
	public void authenticationByMapModule() {
		try {
			List<AuthenticationByMapModuleBO> list  =authenticationService.authenticationByMapModule(getDummyFilter());
			assertTrue("Record found", list.size() > 0);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(System.out, list);
		} catch (JsonGenerationException | JsonMappingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
	}
	
	@Ignore
	public void authenticationByTime() {
		try {
			List<AuthenticationOverTimeResponseBO> list  =authenticationService.authenticationByTime(getDummyFilter());
			assertTrue("Record found", list.size() > 0);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(System.out, list);
		} catch (JsonGenerationException | JsonMappingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
	}
	

	public void authenticationByTimeForCountryPage() {
		try {
			List<AuthenticationOverTimeResponseBO> list  =authenticationService.authenticationByTime(getDummyFilter());
			assertTrue("Record found", list.size() > 0);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(System.out, list);
		} catch (JsonGenerationException | JsonMappingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
	}

	
	@Ignore
	public void CompromisedProductForAuthentication() {

		try {
			List<Top3ProductBO> list =dashBoardService.compromisedProduct(getResult());
			assertTrue("Record found", list.size() > 0);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(System.out, list);
		} catch (JsonGenerationException | JsonMappingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	@Ignore
	public void top3CountriesOnRevenue() {
		try {
			List<AuthenticationTop3CountriesResponseBO> list  =authenticationService.top3CountriesOnAuthentication(resultForRevenueLoss());
			assertTrue("Record found", list.size() > 0);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(System.out, list);
		} catch (JsonGenerationException | JsonMappingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
	}
	
	@Ignore
	private AuthenticationDashboardFilterForm getResult(){
		
		AuthenticationDashboardFilterForm filter = new AuthenticationDashboardFilterForm();
		filter.setRegion(3L);
		filter.setCountryId(50L);
		filter.setEntityType(2L);
		//filter.setLevel(3L);
		//filter.setStartDate(20150601);
	//	filter.setEndDate(20150830);
		int tenant = getTenantId().intValue();
		int brand = getBrandId().intValue();
		filter.setBrandId(brand);
		filter.setTenantId(tenant);
		filter.setNoOfRecord(3);
		filter.setPageNumber(0);
		filter.setDashboardPage("authentications");
		return filter;
		
	}
	
	@Ignore
	private AuthenticationDashboardAdditionalFilterDTO resultForRevenueLoss(){
		
		AuthenticationDashboardAdditionalFilterDTO filter = new AuthenticationDashboardAdditionalFilterDTO();
		filter.setRegion(3L);
		//filter.setCountryId(50L);
		filter.setEntityType(2L);
		//filter.setLevel(3L);
		//filter.setStartDate(20150601);
	//	filter.setEndDate(20150830);
		int tenant = getTenantId().intValue();
		int brand = getBrandId().intValue();
		filter.setBrandId(brand);
		filter.setTenantId(tenant);
		filter.setNoOfRecord(3);
		filter.setPageNumber(0);
		filter.setDashboardPage("revenueloss");
		return filter;
		
	}
	
	@Ignore
	private AuthenticationDashboardAdditionalFilterDTO getDummyFilter(){
		AuthenticationDashboardAdditionalFilterDTO filter = new AuthenticationDashboardAdditionalFilterDTO();
		filter.setTenantId(9);
		filter.setBrandId(9);
		filter.setRegion(3L);
		filter.setCountryId(23L);
		filter.setEntityType(3L);
		filter.setLevel(3L);
		filter.setStartDate(20150601);
		filter.setEndDate(20150830);
		//filter.setAuthType("Genuine");
		filter.setSuspectAuthType("Secondary");
		filter.setNoOfRecord(3);
		filter.setPageNumber(0);
		filter.setDashboardPage("authentications");
		
		// for country page city 725 country 166 region 5 
		filter.setCity(725L);
		return filter;
		
	}
}
