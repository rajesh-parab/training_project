/**
 * 
 */
package com.vpa.saas.converter;

import java.util.List;

import com.vpa.core.models.Tenant;
import com.vpa.saas.dto.TenantDTO;

/**
 * @author PD42694
 *
 */
public interface TenantConverter extends BaseConverter<Tenant, TenantDTO> {

	/* (non-Javadoc)
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	TenantDTO convert(Tenant tenant);

	/**
	 * This method convert <code>Tenant</code> object to <code>TenantDTO</code>
	 * 
	 * @param tenant
	 * @param tenantDTO
	 */
	void convert(Tenant tenant, TenantDTO tenantDTO);

	/**
	 * This method convert <code>List<Tenant></code> object to <code>List<TenantDTO></code>
	 * @param tenants
	 * @return <code>List<TenantDTO></code>
	 */
	List<TenantDTO> convert(List<Tenant> tenants);
}
