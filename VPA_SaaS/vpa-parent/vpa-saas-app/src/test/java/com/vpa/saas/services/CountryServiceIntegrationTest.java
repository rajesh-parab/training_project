package com.vpa.saas.services;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpa.core.bo.CountryListBO;
import com.vpa.saas.dto.AuthenticationDashboardFilterForm;

public class CountryServiceIntegrationTest extends AbstractVPADashBoardTest {

	@Autowired
	CountryService countryService;

	@Test
	public void getCountryList() throws JsonGenerationException, JsonMappingException, IOException {
		List<CountryListBO> list =countryService.getCountryList(getDummyFilter());
		assertTrue(list.size() > 0);
		ObjectMapper object = new ObjectMapper();
		object.writeValue(System.out, list);
	}

	@Ignore
	private AuthenticationDashboardFilterForm getDummyFilter() {
		AuthenticationDashboardFilterForm filter = new AuthenticationDashboardFilterForm();
		int tenant = getTenantId().intValue();
		int brand = getBrandId().intValue();
		filter.setTenantId(tenant);
		filter.setBrandId(brand);
		filter.setRegion(3L);
		filter.setPageNumber(1);
		filter.setNoOfRecord(15);
		//filter.setOrderBy("REVENUE");
		//filter.setOrderBy("SUSPECTAUTHENTICATION");
		filter.setOrderBy("TOTALAUTHENTICATION");
	    filter.setStartDate(20150601);
		filter.setEndDate(20150830);
		filter.setProductId(705);
		return filter;

	}

}
