/**
 * 
 */
package com.vpa.saas.converter.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.vpa.core.models.Brand;
import com.vpa.core.models.Tenant;
import com.vpa.saas.converter.TenantConverter;
import com.vpa.saas.dto.BrandDTO;
import com.vpa.saas.dto.TenantDTO;

/**
 * @author PD42694
 *
 */
@Service
public class TenantConverterImpl implements TenantConverter {

	private static final Logger logger = Logger
			.getLogger(TenantConverterImpl.class);

	@Override
	public TenantDTO convert(Tenant tenant) {

		logger.info("Calling convert method...");

		TenantDTO tenantDTO = new TenantDTO();
		if (null == tenant) {
			return null;
		}

		tenantDTO.setId(tenant.getId());
		tenantDTO.setTenantName(tenant.getName());
		tenantDTO.setId(tenant.getId());
		tenantDTO.setTenantName(tenant.getName());

		List<BrandDTO> brandDTOs = new ArrayList<BrandDTO>();
		for (Iterator<Brand> iterator2 = tenant.getBrands().iterator(); iterator2
				.hasNext();) {
			Brand brand = iterator2.next();
			if (brand.getEnable() == 1) {
				BrandDTO brandDTO = new BrandDTO();
				// setting brandDTO value
				brandDTO.setId(brand.getId());
				brandDTO.setName(brand.getName());
				brandDTO.setEnable(brand.getEnable());
				brandDTO.setDescription(brand.getDescription());
				brandDTO.setCreatedDate(brand.getCreatedDate());
				brandDTO.setUpdatedDate(brand.getUpdatedDate());
				// setting brandDTO into list
				brandDTOs.add(brandDTO);
			}
		}
		// setting brandDTO into brand list
		tenantDTO.setBrands(brandDTOs);

		return tenantDTO;
	}

	@Override
	public void convert(Tenant tenant, TenantDTO tenantDTO) {
		// TODO provide implementation

	}

	@Override
	public List<TenantDTO> convert(List<Tenant> tenants) {

		List<TenantDTO> tenantDTOs = new ArrayList<TenantDTO>();

		if (CollectionUtils.isEmpty(tenants) == false) {
			for (Tenant tenant : tenants) {
				TenantDTO tenantDTO = convert(tenant);
				tenantDTOs.add(tenantDTO);
			}
		}
		return tenantDTOs;
	}

}
