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
import com.vpa.core.bo.AuthenticationOverTimeResponseBO;
import com.vpa.core.bo.RevenueLossBO;
import com.vpa.core.bo.RevenueLossByCountryBO;
import com.vpa.core.bo.RevenueLossByEntityTypeBO;
import com.vpa.core.bo.RevenueLossByRegionBO;
import com.vpa.core.bo.RevenueLossOverTimeResponseBO;
import com.vpa.core.bo.Top3ProductBO;
import com.vpa.saas.dto.AuthenticationDashboardAdditionalFilterDTO;
import com.vpa.saas.dto.AuthenticationDashboardFilterForm;
import com.vpa.saas.dto.RevenueLossDashboardAdditionalFilterDTO;

/**
 * Test class for Revenue Loss functionality.
 * 
 * @author NS60097
 *
 */
public class RevenueLossDashboardIntegrationTest extends
		AbstractVPADashBoardTest {

	@Autowired
	RevenueLossService revenueLossService;

	@Autowired
	DashBoardService dashBoardService;

	
	public static Logger logger = Logger
			.getLogger(AuthenticationDashboardIntegrationTest.class);
	
	@Test
	public void getRevenueLossDetails() {

		try {
			RevenueLossBO revenueLoss = revenueLossService
					.getRevenueDetails(getResult());
			assertTrue(revenueLoss.getProjectedRevenueLoss() != 0);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(System.out, revenueLoss);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void RevenueLossByRegion() {

		try {
			List<RevenueLossByRegionBO> revenueLossByRegion = revenueLossService
					.revenueLossByRegion(getResult());
			assertTrue(revenueLossByRegion.size() != 0);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(System.out, revenueLossByRegion);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void compromisedProduct() {
		try {
			List<Top3ProductBO> list = dashBoardService
					.compromisedProduct(getResult());
			assertTrue(list.size() != 0);
			assertTrue(list.size() <= getResult().getNoOfRecord());
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(System.out, list);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void revenuelossByEntity() {
		try {
			List<RevenueLossByEntityTypeBO> revnueLossByEntity = revenueLossService
					.revenueLossByEntityType(getResult());
			assertTrue(revnueLossByEntity.size() != 0);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(System.out, revnueLossByEntity);
		} catch (JsonGenerationException | JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void revenueLossByCountry() {
		try {
			List<RevenueLossByCountryBO> list = revenueLossService
					.revenueLossByCountry(getResult());
			assertTrue(list.size() != 0);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(System.out, list);
		} catch (JsonGenerationException | JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Ignore
	private AuthenticationDashboardFilterForm getResult() {
		AuthenticationDashboardFilterForm filter = new AuthenticationDashboardFilterForm();
		filter.setRegion(3L);
		filter.setCountryId(0L);
		filter.setEntityType(0L);
		filter.setStartDate(0);
		filter.setEndDate(0);
		// filter.setStartDate(20150601);
		// filter.setEndDate(20150830);
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
	private RevenueLossDashboardAdditionalFilterDTO getDummyFilter(){
		RevenueLossDashboardAdditionalFilterDTO filter = new RevenueLossDashboardAdditionalFilterDTO();
		filter.setTenantId(9);
		filter.setBrandId(9);
		filter.setRegion(3L);
		filter.setCountryId(23L);
		filter.setEntityType(3L);
		// filter.setLevel(3L);
		filter.setStartDate(20150601);
		filter.setEndDate(20150830);
		// filter.setNoOfRecord(3);
		// filter.setPageNumber(0);
		//filter.setDashboardPage("authentications");
		return filter;
		
	}
	
	@Ignore
	public void revenueLossByTime() {
		try {
			 List<RevenueLossOverTimeResponseBO> list  =revenueLossService.revenueLossByTime(getDummyFilter());
			assertTrue("Record found", list.size() > 0);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(System.out, list);
		} catch (JsonGenerationException | JsonMappingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
	}

}
