/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   CityServiceImpl.java
 ** Version     :   1.0
 ** Description :   Implementation class for city table related operations.
 **
 ** Author      :   Rajesh Parab 
 ** Created Date :  Tuesday, 27 Oct 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.saas.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.vpa.core.mes.dao.SellerDao;
import com.vpa.core.models.City;
import com.vpa.core.models.CountryMaster;
import com.vpa.core.models.Seller;
import com.vpa.saas.dto.SellerDTO;
import com.vpa.saas.services.SellerService;
@Service
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerDao sellerDao;
	
	@Override
	public Long addNewSellerInDB(SellerDTO sellerDTO,String sellerName,CountryMaster country,City city){
		Long sellerId = 0L;
		if (StringUtils.hasText(sellerName)) {
			Seller seller = sellerDao.findByNameIgnoringCase(sellerName);
			if (null == seller) {
				seller = new Seller();
				seller.setCountryMaster(country);
				seller.setCity(city);
				BeanUtils.copyProperties(sellerDTO, seller);
				seller = sellerDao.save(seller);

			}
			sellerId = seller.getId();
		}
		return sellerId;
	}
}
