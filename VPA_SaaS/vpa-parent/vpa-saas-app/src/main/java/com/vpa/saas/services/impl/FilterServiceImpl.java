/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   FilterServiceImpl.java
 ** Version     :   1.0
 ** Description :    
 **
 ** Author      :   Narayan Singh
 ** Created Date :  Tuesday, 15 Sep 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.saas.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.vpa.core.dws.dao.DimProductDao;
import com.vpa.core.dws.dao.FilterDao;
import com.vpa.core.exceptions.ResourceNotFoundException;
import com.vpa.core.models.LabelValueBean;
import com.vpa.saas.dto.FilterDTO;
import com.vpa.saas.dto.RegionWithCountryDTO;
import com.vpa.saas.services.FilterService;

/**
 * Implementation class of Service Interface of Filter Criteria.
 * 
 * @author NS60097
 *
 */
@Service
@Transactional(value = "transactionManagerDWS", propagation = Propagation.SUPPORTS)
public class FilterServiceImpl implements FilterService {

	private static final Logger logger = Logger
			.getLogger(FilterServiceImpl.class);

	@Autowired
	private FilterDao filterDao;

	@Autowired
	DimProductDao dimProductDao;

	private static final String ONLY_CHASISS = "3";

	@Override
	public FilterDTO getFilterList(int tenantId) {
		logger.info("calling getFilterList ");

		FilterDTO.Builder dtoBuilder = FilterDTO.Builder.filterDTO();

		//Get regions and countries
		dtoBuilder.withRegions(regionWithCountryList(tenantId));

		//Get levels
		dtoBuilder.withLevels(
				ValidateList(filterDao.getLevelList(), " level(layer) "));

		//Get entity types
		dtoBuilder.withEntityTypes(
				ValidateList(filterDao.getEntityTypeList(), " entity type "));

		//Get products
		dtoBuilder.withProducts(
				dimProductDao.getCompromisedProductListForCountry(
						tenantId, ONLY_CHASISS));

		return dtoBuilder.build();
	}

	private List<RegionWithCountryDTO> regionWithCountryList(int tenantId) {
		List<LabelValueBean> regionList = filterDao.getRegionList(tenantId);
		List<RegionWithCountryDTO> regionWithCountry = new ArrayList<>();
		if (CollectionUtils.isEmpty(regionList)) {
			logger.error("error while fetching region in data ware house DB.");
			throw new ResourceNotFoundException(
					"error while fetching region in data warehouse DB.");
		}
		for (LabelValueBean idValue : regionList) {
			RegionWithCountryDTO region = new RegionWithCountryDTO();
			BeanUtils.copyProperties(idValue, region);
			Integer id = (int) (long) idValue.getId();
			List<LabelValueBean> country = filterDao
					.getCountryByRegiont(id);
			if (CollectionUtils.isEmpty(country)) {
				logger.error("error while fetching country list based on region :"
						+ idValue.getId() + "in data ware house DB.");
				throw new ResourceNotFoundException("error while fetching "
						+ "country list based on region :"
						+ idValue.getId() + "in data ware house DB.");
			}
			region.setCountryList(country);
			regionWithCountry.add(region);
		}
		return regionWithCountry;
	}

	private List<LabelValueBean> ValidateList(List<LabelValueBean> list, String listType) {
		if (CollectionUtils.isEmpty(list)) {
			logger.error("error while fetching " + listType
					+ " in data ware house DB.");
			throw new ResourceNotFoundException("error while fetching "
					+ listType + " in data ware house DB.");
		}
		return list;
	}
}
