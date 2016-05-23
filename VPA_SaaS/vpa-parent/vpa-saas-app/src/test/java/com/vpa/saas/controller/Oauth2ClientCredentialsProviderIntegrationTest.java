package com.vpa.saas.controller;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.vpa.saas.dto.BrandDTO;
import com.vpa.saas.dto.TenantDTO;
import com.vpa.saas.dto.VPAOAuth2AccessToken;

public class Oauth2ClientCredentialsProviderIntegrationTest {
	
	private static RestTemplate restTemplate;
	
	private static final String HOST_ENDPOINT = "http://localhost:8080/vpa-saas-app";
	private static final String TENANT_URL = HOST_ENDPOINT + "/tenant/all";
	private static final String ACCESST_TOKEN_URL=HOST_ENDPOINT+"/oauth/token?grant_type=password&client_id=brandowneruser&client_secret=f90453ec712ce4505cc425e7e881e1d58ea274c3&username=samip.bhavsar@vantagepointanalytics.com&password=2c85e572271c0f8faae073a65ed1b363c2e4474f1d32fc0d8fb546594234a0dc";
	
	
	@BeforeClass
	public static void loadAllTenantsAndBrands() {
		restTemplate = new RestTemplate();
	}

	
	@Test
	public void postForTokenTest(){
		final VPAOAuth2AccessToken accessToken = restTemplate.getForObject(ACCESST_TOKEN_URL, VPAOAuth2AccessToken.class);
		assertTrue(StringUtils.isNotBlank(accessToken.getValue()));
		assertTrue(StringUtils.isNotBlank(accessToken.getRefreshToken().getValue()));
	}
	
	@Test
	public void getTenantsAndBrandTest() {
		final VPAOAuth2AccessToken accessToken = restTemplate.getForObject(ACCESST_TOKEN_URL, VPAOAuth2AccessToken.class);
		final List<TenantDTO> tenantDTOs =  Arrays.asList(restTemplate.getForObject(TENANT_URL+"?access_token="+accessToken.getValue(),TenantDTO[].class));
		assertTrue(null!=tenantDTOs);
		assertTrue(tenantDTOs.size()>0);
		for (TenantDTO tenantDTO : tenantDTOs) {
			assertTrue(tenantDTO.getId()>0);
			assertTrue(StringUtils.isNotBlank(tenantDTO.getTenantName()));
			for (BrandDTO brandDTO : tenantDTO.getBrands()) {
				assertTrue(brandDTO.getId()>0);
				assertTrue(StringUtils.isNotBlank(brandDTO.getName()));
			}
		}
	}
	
	
	
}
