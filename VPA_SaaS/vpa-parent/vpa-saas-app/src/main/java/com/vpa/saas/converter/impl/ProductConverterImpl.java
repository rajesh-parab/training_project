/**
 * 
 */
package com.vpa.saas.converter.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.vpa.core.dws.models.DimProduct;
import com.vpa.saas.converter.ProductConverter;
import com.vpa.saas.dto.ProductDTO;

/**
 * @author PD42694
 *
 */
@Service
public class ProductConverterImpl implements ProductConverter{

	private static final Logger logger = Logger.getLogger(ProductConverterImpl.class);
	
	@Override
	public ProductDTO convert(DimProduct dimProduct) {
		logger.info("Calling convert method..");
		if(null == dimProduct){
			return null;
		}
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(dimProduct.getId());
		productDTO.setName(dimProduct.getProduct());
		productDTO.setGlp(dimProduct.getGlp());
		productDTO.setDescription(dimProduct.getProductDescription());
		productDTO.setEnable(dimProduct.getActive());
		return productDTO;
	}

	@Override
	public List<ProductDTO> convert(List<DimProduct> dimProducts) {
		logger.info("Calling convert method..");
		if(CollectionUtils.isEmpty(dimProducts)){
			return Collections.emptyList();
		}
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		for (DimProduct dimProduct : dimProducts) {
			ProductDTO productDTO = convert(dimProduct);
			productDTOs.add(productDTO);
		}
		return productDTOs;
	}

}
