/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   ProductAuthenticationService.java
 ** Version     :   1.0
 ** Description :   This interface define services for Mobile Apps  .
 **
 ** Author      :   Rajesh Parab
 ** Created Date :  Tuesday, 19 May 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.saas.services;

import com.vpa.core.models.SecurityLabel;
import com.vpa.saas.dto.ProductScanDTO;

public interface ProductAuthenticationService {

	/**
	 * get the genuine product information according to database
	 * 
	 * @param scannedProduct scanned product
	 * @return
	 */
	ProductScanDTO getGenuineProductInformation(ProductScanDTO scannedProduct);

	/**
	 * Save scan result. As soon as product was scanned it will save in scan table.
	 * @param scannedProduct
	 */
	void saveScanResult(ProductScanDTO scannedProduct);

	/**
	 * Update the scan table based on user input.
	 * 
	 * @param scannedProduct
	 * @return
	 */
	void updateScannedProduct(ProductScanDTO scannedProduct);

	/**
	 * Save the scan details in case of counterfeit or user input
	 * 
	 * @param scannedProduct
	 */
	void saveScanDetails(ProductScanDTO scannedProduct);

	/**
	 * Get the scan information.
	 * 
	 * @param scanId
	 * @return
	 */
	ProductScanDTO getScanDetails(Long scanId);

	void fetchProductInfo(ProductScanDTO scannedProduct,
			SecurityLabel securityLabel);
	
}
