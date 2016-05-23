/**
 * 
 */
package com.vpa.saas.converter.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.vpa.core.dws.models.DimCompany;
import com.vpa.saas.converter.CounterfeitEntityConverter;
import com.vpa.saas.dto.CounterfeitBuyingSellingEntityDTO;
import com.vpa.saas.formatters.CurrencyFormatter;

/**
 * @author PD42694
 *
 */
@Service
public class CounterfeitEntityConverterImpl implements CounterfeitEntityConverter{
	
 	
	@Override
	public CounterfeitBuyingSellingEntityDTO convert(DimCompany dimCompany) {
		 
		if(null==dimCompany){
			return null;
		}		
		CounterfeitBuyingSellingEntityDTO buyingEntityDTO = new CounterfeitBuyingSellingEntityDTO();
		if(null!=dimCompany.getId()){
			buyingEntityDTO.setEntityId(dimCompany.getId());
		}
		buyingEntityDTO.setEntityName(dimCompany.getEntityName());
		
		double revenueLoss =0D;
		if(null != dimCompany.getRevenueLoss()){
			revenueLoss = dimCompany.getRevenueLoss();
			buyingEntityDTO.setRevenueLoss(revenueLoss);
			buyingEntityDTO.setFormattedRevenueLoss(CurrencyFormatter
					.currencyFormatter(revenueLoss, 0));
		}
		buyingEntityDTO.setNumberOfSuspectAuthentications(dimCompany
				.getNumberOfSuspectAuthentications());
		buyingEntityDTO.setEntityType(dimCompany.getEntityType());
		buyingEntityDTO.setSellerCity(dimCompany.getSellerCity());
		buyingEntityDTO.setSellerState(dimCompany.getSellerState());
		buyingEntityDTO.setSellerCountry(dimCompany.getSellerCountry());
		buyingEntityDTO.setSellerType(dimCompany.getSellerType());
		buyingEntityDTO.setSellerWebAddress(dimCompany.getSellerWebAddress());
		buyingEntityDTO.setTotalAuthentications(dimCompany.getTotalNumberOfAuthentications());
		buyingEntityDTO.setRegion(dimCompany.getRegion());
		buyingEntityDTO.setTimePeriod(dimCompany.getTimePeriod());
		buyingEntityDTO.setLevel(dimCompany.getLevel());
		buyingEntityDTO.setFlaggedProducts(dimCompany.getFlaggedProducts());
		buyingEntityDTO.setActiveUsers(dimCompany.getActiveUsers());
		return buyingEntityDTO;
	}

	@Override
	public List<CounterfeitBuyingSellingEntityDTO> convert(
			List<DimCompany> dimCompanies) {
		 
		if(CollectionUtils.isEmpty(dimCompanies)){
			return Collections.emptyList();
		}
		List<CounterfeitBuyingSellingEntityDTO> cfBuyingEntities = new ArrayList<>();
		for (DimCompany dimCompany : dimCompanies) { 			
			cfBuyingEntities.add(convert(dimCompany));
		}
		
		return cfBuyingEntities;
	}

}
