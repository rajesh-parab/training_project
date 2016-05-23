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

import com.vpa.core.dws.dao.DimProductDao;
import com.vpa.core.dws.models.DimProduct;
import com.vpa.core.exceptions.ResourceNotFoundException;
import com.vpa.saas.converter.ProductConverter;
import com.vpa.saas.dto.ProductDTO;
import com.vpa.saas.services.ProductService;

/**
 * @author PD42694
 *
 */
@Service
@Transactional(value = "transactionManagerDWS", propagation = Propagation.SUPPORTS)
public class ProductServiceImpl implements ProductService{
	
	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private DimProductDao dimProductDao;
	
	@Autowired
	private ProductConverter productConverter;
	
	@Override
	public List<ProductDTO> getProductByTenant(Integer tenantId) {
		
		logger.info("Calling getProductByTenant method for tenantId : "+tenantId);
		
		List<DimProduct> dimProducts = dimProductDao.getProductListByTenant(tenantId);
		List<ProductDTO> productDTOs = productConverter.convert(dimProducts);
		if(null == productDTOs){
			logger.info("No record found for tenantId : "+tenantId);
			throw new ResourceNotFoundException("No product found for tenant: " + tenantId);
		}
		return productConverter.convert(dimProducts);
	}

}
