/**
 * 
 */
package com.vpa.saas.converter;

import java.util.List;

import com.vpa.core.dws.models.DimCompany;
import com.vpa.saas.dto.CounterfeitBuyingSellingEntityDTO;

/**
 * @author PD42694
 *
 */
public interface CounterfeitEntityConverter extends BaseConverter<DimCompany, CounterfeitBuyingSellingEntityDTO>{
	
	
	CounterfeitBuyingSellingEntityDTO convert(DimCompany dimCompany);
	
	/**
	 * This method will convert List<DimCompany> object into List<CounterfeitBuyingSellingEntityDTO>
	 * @param dimCompanies
	 * @return List<CounterfeitBuyingSellingEntityDTO>
	 */
	List<CounterfeitBuyingSellingEntityDTO> convert(List<DimCompany> dimCompanies);
}
