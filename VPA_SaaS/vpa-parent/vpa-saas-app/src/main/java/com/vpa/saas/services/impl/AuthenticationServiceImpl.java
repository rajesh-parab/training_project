/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   AuthenticationServiceImpl.java
 ** Version     :   1.0
 ** Description :    
 **
 ** Author      :   Paratha Bhowmick
 ** Created Date :  Monday, 3 Aug 2015
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

import com.vpa.core.bo.AuthenticationByEntityResponseBO;
import com.vpa.core.bo.AuthenticationByLayerResponseBO;
import com.vpa.core.bo.AuthenticationByMapModuleBO;
import com.vpa.core.bo.AuthenticationDashboardAdditionalFilterBO;
import com.vpa.core.bo.AuthenticationLast24HrsResponseBO;
import com.vpa.core.bo.AuthenticationOverTimeResponseBO;
import com.vpa.core.bo.AuthenticationTop3CountriesResponseBO;
import com.vpa.core.mes.dao.AuthenticationDao;
import com.vpa.saas.dto.AuthenticationDashboardAdditionalFilterDTO;
import com.vpa.saas.services.AuthenticationService;

@Service
@Transactional(value = "transactionManagerDWS", propagation = Propagation.SUPPORTS)
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthenticationDao authenticationDao;

	

	
	@Override
	public List<AuthenticationTop3CountriesResponseBO> top3CountriesOnAuthentication(
			AuthenticationDashboardAdditionalFilterDTO filter) {
		AuthenticationDashboardAdditionalFilterBO authenticationDashboardAdditionalFilterBO = new AuthenticationDashboardAdditionalFilterBO();

		BeanUtils.copyProperties(filter,
				authenticationDashboardAdditionalFilterBO);

		List<AuthenticationTop3CountriesResponseBO> result = authenticationDao
				.top3CountriesOnAuthentication(authenticationDashboardAdditionalFilterBO);

		return result;

	}

	@Override
	public List<AuthenticationByLayerResponseBO> authenticationByLayer(
			AuthenticationDashboardAdditionalFilterDTO filter) {

		AuthenticationDashboardAdditionalFilterBO authenticationDashboardAdditionalFilterBO = new AuthenticationDashboardAdditionalFilterBO();

		BeanUtils.copyProperties(filter,
				authenticationDashboardAdditionalFilterBO);

		return authenticationDao
				.authenticationByLayer(authenticationDashboardAdditionalFilterBO);

	}
 
	@Override
 	public List<AuthenticationByEntityResponseBO> authenticationEntityType(
			AuthenticationDashboardAdditionalFilterDTO filter) {
		AuthenticationDashboardAdditionalFilterBO authenticationDashboardAdditionalFilterBO = new AuthenticationDashboardAdditionalFilterBO();
		BeanUtils.copyProperties(filter,
				authenticationDashboardAdditionalFilterBO);

		return  authenticationDao
				.authenticationEntityType(authenticationDashboardAdditionalFilterBO);


		

	}
	
	@Override
	public List<AuthenticationByMapModuleBO> authenticationByMapModule(AuthenticationDashboardAdditionalFilterDTO filter){
		AuthenticationDashboardAdditionalFilterBO authenticationDashboardAdditionalFilterBO = new AuthenticationDashboardAdditionalFilterBO();
		BeanUtils.copyProperties(filter,
				authenticationDashboardAdditionalFilterBO);
		return authenticationDao.authenticationByMapModule(authenticationDashboardAdditionalFilterBO);
	
	}
	
	@Override
	public List<AuthenticationLast24HrsResponseBO> authenticationLast24hrs(AuthenticationDashboardAdditionalFilterDTO filter){
		
		AuthenticationDashboardAdditionalFilterBO authenticationDashboardAdditionalFilterBO = new AuthenticationDashboardAdditionalFilterBO();
		BeanUtils.copyProperties(filter,
				authenticationDashboardAdditionalFilterBO);
		
		return authenticationDao.authenticationLast24hrs(authenticationDashboardAdditionalFilterBO);
	}

	@Override
	public List<AuthenticationOverTimeResponseBO> authenticationByTime(AuthenticationDashboardAdditionalFilterDTO filter){
		
		AuthenticationDashboardAdditionalFilterBO authenticationDashboardAdditionalFilterBO = new AuthenticationDashboardAdditionalFilterBO();
		BeanUtils.copyProperties(filter,
				authenticationDashboardAdditionalFilterBO);
		return authenticationDao.authenticationByTime(authenticationDashboardAdditionalFilterBO);
	}
	
	
}

