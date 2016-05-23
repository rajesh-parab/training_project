/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   SelectBoxService.java
 ** Version     :   1.0
 ** Description :   Implementing class for SelectBoxService.
 **                 Call respective Dao operations for fetching data in form of id and names wrapped in Wrapper class LabelValueBean.
 **
 ** Author      :   Rajesh Parab 
 ** Created Date :  Monday, 07 Sep 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.saas.services.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vpa.core.mes.dao.CityDao;
import com.vpa.core.mes.dao.CompanyDao;
import com.vpa.core.mes.dao.CountryMasterDao;
import com.vpa.core.mes.dao.LevelDao;
import com.vpa.core.mes.dao.SellerDao;
import com.vpa.core.models.Company;
import com.vpa.core.models.LabelValueBean;
import com.vpa.core.models.Seller;
import com.vpa.saas.dto.CompanyDTO;
import com.vpa.saas.dto.EntityType;
import com.vpa.saas.dto.SellerDTO;
import com.vpa.saas.services.SelectBoxService;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
public class SelectBoxServicesImpl implements SelectBoxService {
	private static final String LIKE_OPERATOR = "%";

	@Autowired
	private SellerDao sellerDao;

	@Autowired
	private CountryMasterDao countryMasterDao;

	@Autowired
	private CityDao cityDao;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private LevelDao levelDao;

	/**
	 * @see SelectBoxService#findAutoSelectSellerNamesStartWith(String)
	 */
	@Override
	public List<LabelValueBean> findAutoSelectSellerNamesStartWith(
			String sellerName) {
		return sellerDao.findAutoSelectSellerName(sellerName + LIKE_OPERATOR);

	}

	/**
	 * @see SelectBoxService#findAutoSelectCountryNamesStartWith(String)
	 */
	@Override
	public List<LabelValueBean> findAutoSelectCountryNamesStartWith(
			String countryName) {
		return countryMasterDao.findAutoSelectCountryNames(countryName
				+ LIKE_OPERATOR);

	}

	/**
	 * @see SelectBoxService#findAutoSelectCityNamesStartWith(String)
	 */
	@Override
	public List<LabelValueBean> findAutoSelectCityNamesStartWith(String cityName) {
		return cityDao.findAutoSelectCityNames(cityName + LIKE_OPERATOR);

	}

	/**
	 * @see SelectBoxService#findAutoSelectCompanyNamesStartWith(String)
	 */
	@Override
	public List<LabelValueBean> findAutoSelectCompanyNamesStartWith(
			String companyName) {
		return companyDao.findAutoSelectCompanyNames(companyName
				+ LIKE_OPERATOR);

	}

	/**
	 * @see SelectBoxService#findAllLevelsIdAndNames(String)
	 */
	@Override
	public List<LabelValueBean> findAllLevelsIdAndNames() {
		return levelDao.findAllLavelsIdAndNames();
	}

	/**
	 * @see SelectBoxService#findCompanyDetails(String)
	 */
	@Override
	public CompanyDTO findCompanyDetails(Long companyId) {
		Company company = companyDao.findOne(companyId);
		CompanyDTO companyDTO = new CompanyDTO();
		BeanUtils.copyProperties(company, companyDTO);
		companyDTO.setType(EntityType.getType(company.getEntityType().getId()));

		return companyDTO;
	}

	/**
	 * @see SelectBoxService#findAutoSelectOnlineSellerNamesStartWith(String)
	 */
	@Override
	public List<LabelValueBean> findAutoSelectOnlineSellerNamesStartWith(
			String sellerName) {
		return sellerDao.findAutoSelectOnlineSellerName(sellerName
				+ LIKE_OPERATOR);
	}

	@Override
	public SellerDTO findSellerDetails(Long sellerId) {
		Seller seller = sellerDao.findOne(sellerId);
		SellerDTO sellerDTO = new SellerDTO();
		BeanUtils.copyProperties(seller, sellerDTO);
		if (!seller.getSellerType().equalsIgnoreCase("ONLINE")) {
			sellerDTO.setCity(seller.getCity().getName());
			sellerDTO.setCountry(seller.getCountryMaster().getName());
		}

		return sellerDTO;
	}

}
