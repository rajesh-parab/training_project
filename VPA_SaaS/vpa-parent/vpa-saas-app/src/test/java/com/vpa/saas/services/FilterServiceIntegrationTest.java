/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   FilterServiceIntegrationTest.java
 ** Version     :   1.0
 ** Description :    
 **
 ** Author      :   Narayan Singh
 ** Created Date :  Monday, 21 Sep 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.saas.services;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpa.saas.dto.FilterDTO;
import com.vpa.saas.dto.RegionWithCountryDTO;

/**
 * this test class will do junit testing for Filter Criteria Service layer.
 * 
 * @author NS60097
 *
 */
public class FilterServiceIntegrationTest extends AbstractVPADashBoardTest {

	@Autowired
	private FilterService filterService;

	private int tenantId;

	@Before
	public void init() {
		this.tenantId = getTenantId().intValue();
	}

	@Test
	public void filterList() {
		FilterDTO list = filterService.getFilterList(tenantId);
		assertTrue(list != null);
		assertTrue(list.getRegions().size() != 0);
		for (RegionWithCountryDTO regionWithCountry : list.getRegions()) {
			assertTrue(regionWithCountry.getCountryList().size() != 0);
		}
		assertTrue(list.getLevel().size() != 0);
		assertTrue(list.getEntityTypes().size() != 0);
		assertTrue(list.getProducts().size() != 0);
	}
}
