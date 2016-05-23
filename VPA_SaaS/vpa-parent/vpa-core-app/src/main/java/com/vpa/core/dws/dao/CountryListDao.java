package com.vpa.core.dws.dao;

import java.util.List;

import com.vpa.core.bo.AuthenticationDashboardFilterBO;
import com.vpa.core.bo.CountryListBO;


/**
 * @author NS60097
 *
 */
public interface CountryListDao  {

	/**
	 *  this method return record from data ware house DB based on filter criteria.
	 * @param filter
	 * @return
	 */
	public List<CountryListBO> getCountryList(AuthenticationDashboardFilterBO filter);
	
}
