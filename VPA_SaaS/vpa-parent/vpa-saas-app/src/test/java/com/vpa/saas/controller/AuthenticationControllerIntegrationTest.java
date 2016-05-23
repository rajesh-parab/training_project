package com.vpa.saas.controller;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.vpa.saas.dto.AuthenticationDashboardFilterForm;
import com.vpa.saas.dto.Top3ProductDTO;
import com.vpa.saas.services.AbstractVPADashBoardTest;

public class AuthenticationControllerIntegrationTest extends
		AbstractVPADashBoardTest {

	private static final String HOST_ENDPOINT = "http://localhost:8080/vpa-saas-app";

	private static final String AUTHENTICATION_TOP_3_COMPROMISED_PRODUCT_URL = HOST_ENDPOINT
			+ "/auth/compromised";

	private static RestTemplate restTemplate;

	@BeforeClass
	public static void initialized() {
		restTemplate = new RestTemplate();
	}

	@Test
	public void getTop3CompromisedProductSuccessScenario() {
		StringBuilder request = new StringBuilder(
				AUTHENTICATION_TOP_3_COMPROMISED_PRODUCT_URL);
		List<AuthenticationDashboardFilterForm> listofRecords = Arrays.asList(restTemplate
				.postForObject(request.toString(),getResult(),
						AuthenticationDashboardFilterForm[].class));
		assertTrue(null != listofRecords);
		assertTrue(listofRecords.size()<=3);

	}
	
	@Test(expected=RestClientException.class)
	public void getTop3CompromisedProductFailScenario() throws IOException {
		AuthenticationDashboardFilterForm result = new AuthenticationDashboardFilterForm();
		StringBuilder request = new StringBuilder(
				AUTHENTICATION_TOP_3_COMPROMISED_PRODUCT_URL);
		@SuppressWarnings("unchecked")
		List<Top3ProductDTO> listofRecords = (List<Top3ProductDTO>) restTemplate
				.postForObject(request.toString(),result,
						AuthenticationDashboardFilterForm.class);
		assertTrue(null == listofRecords);
	}

	@Ignore
	private AuthenticationDashboardFilterForm getResult() {
		AuthenticationDashboardFilterForm filter = new AuthenticationDashboardFilterForm();
		filter.setRegion(34L);
		filter.setCountryId(34L);
		filter.setEntityType(2L);
		filter.setLevel(3L);
		int tenant = getTenantId().intValue();
		int brand = getBrandId().intValue();
		filter.setBrandId(brand);
		filter.setTenantId(tenant);
		filter.setNoOfRecord(3);
		//filter.setPageNumber(0);
		return filter;

	}

}
