package com.vpa.saas.helpers;

import java.util.List;

import com.vpa.saas.dto.CounterfeitBuyingSellingEntityDTO;

public interface CustomComparator {
	
	public void sortCounterfeitBuyingSellingEntityDTO(final String orderBy, List<CounterfeitBuyingSellingEntityDTO> counterfeitEntities);
}
