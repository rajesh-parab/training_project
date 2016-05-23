/**
 * 
 */
package com.vpa.saas.services.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.vpa.core.exceptions.ResourceNotFoundException;
import com.vpa.core.mes.dao.TenantDao;
import com.vpa.core.models.Tenant;
import com.vpa.saas.converter.TenantConverter;
import com.vpa.saas.dto.TenantDTO;
import com.vpa.saas.services.TenantBrandService;

/**
 * @author PD42694
 *
 */
@Service

@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class TenantBrandServiceImpl implements TenantBrandService{

	private final static Logger logger = Logger.getLogger(TenantBrandServiceImpl.class);
	
	@Autowired
	private TenantDao tenantDao;
	
	@Autowired
	private TenantConverter tenantConverter;
	
	@Override
	public List<TenantDTO> getAllTenantAndBrand() {
		
		logger.info("Calling getAllTenantAndBrand method");
		
		List<Tenant> tenants = tenantDao.findAllTenants();
		List<TenantDTO> tenantDTOs = tenantConverter.convert(tenants);
		 
		if(CollectionUtils.isEmpty(tenantDTOs)){
			throw new ResourceNotFoundException("No record found");
		}		
		return tenantDTOs;
	}

	@Override
	public TenantDTO getBrandsByTenant(final Long tenantId) {
		
		logger.info("Calling getBrandsByTenant method for tenantId :"+tenantId);
		
		final Tenant tenant = tenantDao.getOne(tenantId);
		TenantDTO tenantDTO = tenantConverter.convert(tenant);
		if(null == tenantDTO){
			throw new ResourceNotFoundException("No record found");
		}
		return tenantDTO;
	}

	@Override
	public TenantDTO getBrandsByTenant(String tenantName) {
		
		return null;
	}

}
