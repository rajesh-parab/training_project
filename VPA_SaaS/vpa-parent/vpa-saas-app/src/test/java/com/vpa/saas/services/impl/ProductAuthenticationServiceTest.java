/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   ProductAuthenticationServiceTest.java
 ** Version     :   1.0
 ** Description :   Unit test for mobile apps services using mokito.
 **
 ** Author      :   Rajesh Parab
 ** Created Date :  Tuesday, 05 May 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.saas.services.impl;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpa.core.exceptions.UnprocessableResourceException;
import com.vpa.core.mes.dao.SecurityLabelDao;
import com.vpa.core.models.Brand;
import com.vpa.core.models.ProductInstance;
import com.vpa.core.models.SecurityLabel;
import com.vpa.core.utils.VPASaaSConstant;
import com.vpa.saas.dto.ProductScanDTO;

public class ProductAuthenticationServiceTest extends AbstractMockUnitTest {
	@Autowired
	private SecurityLabelDao securityLabelDao;

	@Test(expected = UnprocessableResourceException.class)
	public void productInstanceNull() {
		ProductAuthenticationServiceImpl productAuthenticationService = new ProductAuthenticationServiceImpl();

		ProductScanDTO scannedProduct = new ProductScanDTO();
		SecurityLabel securityLabel = new SecurityLabel();
		securityLabel.setBrand(new Brand());
		scannedProduct.setSecuritySerialNumber("fsdf");
		when(
				securityLabelDao.getActiveSecurityLabel(scannedProduct
						.getSecuritySerialNumber())).thenReturn(securityLabel);
		productAuthenticationService.setSecurityLabelDao(securityLabelDao);
		productAuthenticationService
				.getGenuineProductInformation(scannedProduct);

	}

	@Test(expected = UnprocessableResourceException.class)
	public void productInstanceNotEnabled() {
		ProductAuthenticationServiceImpl productAuthenticationService = new ProductAuthenticationServiceImpl();

		ProductScanDTO scannedProduct = new ProductScanDTO();
		SecurityLabel securityLabel = new SecurityLabel();
		securityLabel.setBrand(new Brand());
		ProductInstance productInstance = new ProductInstance();
		productInstance.setEnable(VPASaaSConstant.FALSE);
		securityLabel.setProductInstance(productInstance);
		scannedProduct.setSecuritySerialNumber("fsdf");
		when(
				securityLabelDao.getActiveSecurityLabel(scannedProduct
						.getSecuritySerialNumber())).thenReturn(securityLabel);
		when(
				securityLabelDao.getActiveSecurityLabel(scannedProduct
						.getSecuritySerialNumber())).thenReturn(securityLabel);
		productAuthenticationService.setSecurityLabelDao(securityLabelDao);
		productAuthenticationService
				.getGenuineProductInformation(scannedProduct);

	}

}
