/**
 * 
 */
package com.vpa.saas.converter;

import java.util.List;

import com.vpa.core.dws.models.DimProduct;
import com.vpa.saas.dto.ProductDTO;

/**
 * @author PD42694
 *
 */
public interface ProductConverter extends BaseConverter<DimProduct, ProductDTO>{
	
	@Override
	public ProductDTO convert(DimProduct source);
	
	/**
	 * Convert the source of type List<<code>DimProduct</code>> to target type List<<code>ProductDTO</code>>.
	 * @param List<<code>DimProduct</code>>
	 * @return List<<code>ProductDTO</code>>
	 */
	public List<ProductDTO> convert(List<DimProduct> source);
}
