package com.vpa.saas.services;

import java.io.IOException;
import java.util.TimeZone;

import com.vpa.core.bo.ProductRequestBO;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpa.saas.dto.CompromisedProductDTO;
import com.vpa.saas.dto.ProductScanDTO;
import com.vpa.saas.dto.UnProtectedProductDTO;

public class ProductDashBoardIntegrationTest extends AbstractVPADashBoardTest {

	private static final String UNPROTECTED_PRODUCT = "http://localhost:8080/vpa-saas-app/dashboard/unprotected";
	private static final String COMPROMISED_PRODUCT = "http://localhost:8080/vpa-saas-app/dashboard/compromised";
	@Autowired
	DashBoardService dashboardService;

	@Ignore
	public void unProtectedDashboard() throws JsonGenerationException,
			JsonMappingException, IOException {
		String tenantId = getTenantId().toString();
		UnProtectedProductDTO unProtectedProduct = dashboardService
				.unProtectedProductDashboard(tenantId);
		System.out.println("JSON Format for unprotected product :"
				+ unProtectedProduct);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(System.out, unProtectedProduct);
	}

	@Ignore
	public void wrongTimeZone() throws JsonGenerationException,
			JsonMappingException, IOException {

		ProductScanDTO dto = new ProductScanDTO();

		dto.setScanTimeZone("");

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(System.out, dto);

	}

	@Ignore
	public void timeZones() throws JsonGenerationException,
			JsonMappingException, IOException {

		ProductScanDTO dto = new ProductScanDTO();

		String[] timeZones = TimeZone.getAvailableIDs();
		System.out
				.println("############## total timezones " + timeZones.length);
		for (int i = 0; i < timeZones.length; i++) {
			dto.setScanTimeZone(timeZones[i]);
			System.out.println("TIMEZONE " + timeZones[i] + "\n DATE "
					+ dto.getScanDateTime()[0] + "\n TIME "
					+ dto.getScanDateTime()[1] + dto.getScanDateTime()[2]);
			System.out.println("___________");
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(System.out, dto);
			System.out.println("___________");
		}
		System.out.println("##############");

	}

	@Ignore
	public void unProtectedDashboardController() {
		String tenantId = "5";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForObject(UNPROTECTED_PRODUCT, tenantId,
				UnProtectedProductDTO.class);

	}

	@Test
	public void compromisedProductDashboard() throws JsonGenerationException,
			JsonMappingException, IOException {
		String tenantId = getTenantId().toString();

		ProductRequestBO requestBO = new ProductRequestBO();
		requestBO.setTenantId(tenantId);

		CompromisedProductDTO compromisedProduct = dashboardService
				.compromisedProductDashboard(requestBO);
		System.out.println("JSON Format for compromised product :"
				+ compromisedProduct);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(System.out, compromisedProduct);

	}


	@Test
	public void compromisedProductDashboardController() {
		String tenantId = getTenantId().toString();
		RestTemplate restTemplate = new RestTemplate();

		String requestJson = "{\"tenantId\":" + tenantId + "}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		restTemplate.postForObject(COMPROMISED_PRODUCT, entity, CompromisedProductDTO.class);

	}

}
