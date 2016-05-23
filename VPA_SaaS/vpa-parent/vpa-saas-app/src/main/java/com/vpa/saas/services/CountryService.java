package com.vpa.saas.services;

import java.util.List;

import com.vpa.core.bo.CountryListBO;
import com.vpa.saas.dto.AuthenticationDashboardFilterForm;

public interface CountryService {

	/**
	 * This method return the country list based on filter criteria 
	 * @param filter
	 * @return list of CountryListBO
	 */
	public List<CountryListBO> getCountryList(AuthenticationDashboardFilterForm filter);
	
	
}
