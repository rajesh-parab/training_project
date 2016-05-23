/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   CountryMasterServiceImpl.java
 ** Version     :   1.0
 ** Description :   Implementation class for country master table relaed operations.
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

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vpa.core.mes.dao.CountryMasterDao;
import com.vpa.core.models.CountryMaster;
import com.vpa.saas.services.CountryMasterService;
@Service
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
public class CountryMasterServiceImpl implements CountryMasterService {


	@Autowired
	private CountryMasterDao countryMasterDao;
	
	@Override
	public CountryMaster addNewCountryInDatabase(String countryName,
			String countryCode) {
		CountryMaster countryMaster;
		countryMaster = countryMasterDao.findByIsoCodeIgnoringCase(countryCode);
		if (null == countryMaster) {
			countryMaster = new CountryMaster();
			countryMaster.setIsoCode(countryCode);
			countryMaster.setName(countryName);
			countryMaster.setCreatedDate(Calendar.getInstance().getTime());
			countryMaster.setUpdatedDate(Calendar.getInstance().getTime());
			countryMasterDao.save(countryMaster);
		}
		return countryMaster;
	}
}
