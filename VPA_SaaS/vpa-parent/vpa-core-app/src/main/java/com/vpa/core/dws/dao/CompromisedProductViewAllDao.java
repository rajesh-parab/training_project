package com.vpa.core.dws.dao;

import java.util.List;

import com.vpa.core.bo.ProductRequestBO;
import org.springframework.stereotype.Repository;

import com.vpa.core.bo.AuthenticationDashboardFilterBO;
import com.vpa.core.bo.Top3ProductBO;
import com.vpa.core.dws.models.CompromisedProductViewAll;

@Repository
public interface CompromisedProductViewAllDao {
	/**
	 * get the list from dataware house DB for the compromised product view All.
	 *  
	 * @param tenantId
	 * @return
	 */
	public List<CompromisedProductViewAll> viewAll(ProductRequestBO requestBO);
	
	/**
	 * this method return the compromised product details like revenue loss , projected review loss details
	 *  and glp details.
	 * 
	 * @param tenantId
	 * @return
	 */
	
	public Object[] compromisedProductDetails(String tenantId);
	
	/**
	 * this method return the top 3 compromised product based on the filter criteria on authentication page.
	 * 
	 * @param filter
	 * @return
	 */
	
	public List<Top3ProductBO> topCompromisedProduct(AuthenticationDashboardFilterBO filter);
}
