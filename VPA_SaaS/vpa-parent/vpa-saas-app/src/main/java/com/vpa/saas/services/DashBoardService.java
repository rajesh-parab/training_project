package com.vpa.saas.services;

import java.util.List;

import com.vpa.core.bo.ProductRequestBO;
import com.vpa.core.bo.Top3ProductBO;

import com.vpa.saas.dto.AuthenticationDashboardFilterForm;
import com.vpa.saas.dto.CompromisedProductDTO;
import com.vpa.saas.dto.UnProtectedProductDTO;

public interface DashBoardService {

	/**
	 * this method return the object contain all the details for unprotected
	 * product dashboard.
	 * 
	 * @param tenantId
	 * @return
	 */
	public UnProtectedProductDTO unProtectedProductDashboard(String tenantId);

	/**
	 * this method return the object contain all the details for flagged product
	 * dashboard.
	 * 
	 * @param requestBO
	 * @return
	 */
	public CompromisedProductDTO compromisedProductDashboard(ProductRequestBO requestBO);

	/**
	 * this method return the top 3 compromised product based on filter
	 * criteria. for the authentication dashboard page. *
	 * 
	 * @param filter
	 * @return
	 */
	public List<Top3ProductBO> compromisedProduct(AuthenticationDashboardFilterForm filter);

}
