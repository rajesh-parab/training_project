package com.vpa.saas.helpers.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.vpa.core.enums.Sorting;
import com.vpa.saas.dto.CounterfeitBuyingSellingEntityDTO;
import com.vpa.saas.helpers.CustomComparator;

@Service
public class CustomComparatorImpl implements CustomComparator{
	@Override
	public void sortCounterfeitBuyingSellingEntityDTO(final String orderBy, List<CounterfeitBuyingSellingEntityDTO> counterfeitEntities){
		if(StringUtils.equalsIgnoreCase(orderBy, Sorting.REVENUE.toString())){
			Collections.sort(counterfeitEntities, new Comparator<CounterfeitBuyingSellingEntityDTO>() {
				@Override
				public int compare(CounterfeitBuyingSellingEntityDTO o1, CounterfeitBuyingSellingEntityDTO o2) {
					return o2.getRevenueLoss().compareTo(o1.getRevenueLoss());
				}
			});			
		}else if(StringUtils.equalsIgnoreCase(orderBy, Sorting.SUSPECTAUTHENTICATION.toString())){
			Collections.sort(counterfeitEntities, new Comparator<CounterfeitBuyingSellingEntityDTO>() {
				@Override
				public int compare(CounterfeitBuyingSellingEntityDTO o1, CounterfeitBuyingSellingEntityDTO o2) {
					return o2.getNumberOfSuspectAuthentications().compareTo(o1.getNumberOfSuspectAuthentications());
				}
			});
		}else if(StringUtils.equalsIgnoreCase(orderBy, Sorting.TOTALAUTHENTICATION.toString())){
			Collections.sort(counterfeitEntities, new Comparator<CounterfeitBuyingSellingEntityDTO>() {
				@Override
				public int compare(CounterfeitBuyingSellingEntityDTO o1, CounterfeitBuyingSellingEntityDTO o2) {
					return o2.getTotalAuthentications().compareTo(o1.getTotalAuthentications());
				}
			});
		}else if(StringUtils.equalsIgnoreCase(orderBy, Sorting.SUSPECT_PRODUCTS.toString())){
			Collections.sort(counterfeitEntities, new Comparator<CounterfeitBuyingSellingEntityDTO>() {
				@Override
				public int compare(CounterfeitBuyingSellingEntityDTO o1, CounterfeitBuyingSellingEntityDTO o2) {
					return o2.getFlaggedProducts().compareTo(o1.getFlaggedProducts());
				}
			});
		}else if(StringUtils.equalsIgnoreCase(orderBy, Sorting.COUNTRY.toString())){
			Collections.sort(counterfeitEntities, new Comparator<CounterfeitBuyingSellingEntityDTO>() {
				@Override
				public int compare(CounterfeitBuyingSellingEntityDTO o1, CounterfeitBuyingSellingEntityDTO o2) {
					return o1.getSellerCountry().compareTo(o2.getSellerCountry());
				}
			});
		}else if(StringUtils.equalsIgnoreCase(orderBy, Sorting.ENTITY_TYPE.toString())){
			Collections.sort(counterfeitEntities, new Comparator<CounterfeitBuyingSellingEntityDTO>() {
				@Override
				public int compare(CounterfeitBuyingSellingEntityDTO o1, CounterfeitBuyingSellingEntityDTO o2) {
					if(null!=o1.getEntityType() && null != o2.getEntityType()){
						return o1.getEntityType().compareTo(o2.getEntityType());
					}else{
						return 0;
					}
				}
			});
		}else if(StringUtils.equalsIgnoreCase(orderBy, Sorting.ENTITY_NAME.toString())){
			Collections.sort(counterfeitEntities, new Comparator<CounterfeitBuyingSellingEntityDTO>() {
				@Override
				public int compare(CounterfeitBuyingSellingEntityDTO o1, CounterfeitBuyingSellingEntityDTO o2) {
					return o1.getEntityName().compareTo(o2.getEntityName());
				}
			});
		}
	}

}
