package com.vpa.saas.services;

import com.vpa.saas.dto.FilterDTO;

public interface FilterService {

	/**
	 * This method get all the filter criteria(region,country,entity type ,
	 * level and product list) based on the given tenant and dashboard page(which dashboard is displaying the filter list)
	 * in one DTO class FilterDTO.
	 * 
	 * @return
	 * 
	 */
	public FilterDTO getFilterList(int tenantId);

}
