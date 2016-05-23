/**
 * 
 */
package com.vpa.saas.services;

import java.util.List;

import com.vpa.saas.dto.ProductDTO;


/**
 * @author PD42694
 *
 */
public interface ProductService {
	/**
	 * This method will return product list for given tenantId 
	 * @param tenantId
	 * @return
	 */
	List<ProductDTO> getProductByTenant(Integer tenantId);
	
}
