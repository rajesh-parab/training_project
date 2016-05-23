/**
 * 
 */
package com.vpa.saas.services;

import java.util.List;

import com.vpa.saas.dto.TenantDTO;

/**
 * @author PD42694
 *
 */
public interface TenantBrandService {
	
	/**
	 * This method returns Map <tenantId, TenantDTO>. The TenantDTO object contains the list of brands.
	 * @return
	 */
	public List<TenantDTO> getAllTenantAndBrand();  
	
	
	/**
	 * The TenantDTO object contains the list of brands for given id.
	 * @param tenantId
	 * @return
	 */
	public TenantDTO getBrandsByTenant(Long tenantId); 
	
	/**
	 * The TenantDTO object contains the list of brands for given tenantName.
	 * @param tenantName
	 * @return
	 */
	public TenantDTO getBrandsByTenant(String tenantName); 
}
