/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   ProductAuthenticationController.java
 ** Version     :   1.0
 ** Description :   RESTFul API for mobile app.
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

package com.vpa.saas.controllers;

import static com.vpa.core.utils.VPASaaSConstant.INCOMPLETE_SCAN_TRUTH_TABLE_VALUE;
import static com.vpa.core.utils.VPASaaSConstant.SUSPECT_COUNTERFEIT_TRUTH_TABLE_VALUE;
import static com.vpa.core.utils.VPASaaSConstant.TRUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vpa.core.exceptions.ResourcePreconditionException;
import com.vpa.saas.dto.ProductScanDTO;
import com.vpa.saas.dto.ScanResultType;
import com.vpa.saas.services.ProductAuthenticationService;
import com.vpa.saas.services.geocoding.GeoLocation;
import com.vpa.saas.services.geocoding.ReverseGeoCoding;

@RestController
@RequestMapping("/product")
public class ProductAuthenticationController {

	@Autowired
	private ProductAuthenticationService productAuthenticationService;

	@Autowired
	private ReverseGeoCoding reverseGeoCoding;
	
	/**
	 * When Person scan from mobile and it showing genuine. Service return
	 * detail by input from mobiles.
	 * 
	 * @param scannedProduct
	 * @return
	 */
	@RequestMapping(value = "/scan/verify", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ProductScanDTO saveScan(@RequestBody ProductScanDTO scannedProduct) {

		if (!StringUtils.hasText(scannedProduct.getScanTimeZone())) {
			throw new ResourcePreconditionException(
					"No scantimezone has been provided");
		}
		ProductScanDTO genuineProduct = productAuthenticationService
				.getGenuineProductInformation(scannedProduct);

		genuineProduct.setScanResultType(ScanResultType.Incomplete.getId());

		genuineProduct
				.setScanVerificationFlag(INCOMPLETE_SCAN_TRUTH_TABLE_VALUE);
		setScanLocation(genuineProduct);
		productAuthenticationService.saveScanResult(genuineProduct);

		return genuineProduct;

	}

	private void setScanLocation(ProductScanDTO genuineProduct) {
		GeoLocation geoLocation = reverseGeoCoding.getLocation(genuineProduct.getLatitude(), genuineProduct.getLongitude());
		genuineProduct.setCountryCode(geoLocation.getIsoCountryCode());
		genuineProduct.setCountryName(geoLocation.getCountryName());
		genuineProduct.setCity(geoLocation.getCityName());
	}

	/**
	 * When user scan from mobile and product is genuine product information
	 * return to user. User check manually if information is correct or not. If
	 * return information by scan/verify service is matched with users product
	 * user click YES button and this service will make product genuine in scan
	 * table.
	 * 
	 * @param scanId
	 * @return
	 */

	@RequestMapping(value = "/scan/genuine/{scanId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ProductScanDTO saveGenuineScan(@PathVariable("scanId") Long scanId) {

		ProductScanDTO scannedProduct = new ProductScanDTO();
		scannedProduct.setScanId(scanId);
		productAuthenticationService.updateScannedProduct(scannedProduct);
		return scannedProduct;

	}

	/**
	 * Save optional information about purchase in case scan is genuine after
	 * user click YES button.
	 * 
	 * @param scannedProduct
	 * @return
	 */
	@RequestMapping(value = "/scan/genuine/info ", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ProductScanDTO saveGenuineScanDetails(
			@RequestBody ProductScanDTO scannedProduct) {

		productAuthenticationService.saveScanDetails(scannedProduct);

		return scannedProduct;

	}

	/**
	 * Save tampered product information when mobile able to scan the product
	 * but information not matched.
	 * 
	 * @param scannedProduct
	 * @return
	 */
	@RequestMapping(value = "/scan/tampered", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ProductScanDTO saveTamperedProductScan(
			@RequestBody ProductScanDTO scannedProduct) {

		scannedProduct.getProduct().setIsCompromised(TRUE);

		productAuthenticationService.updateScannedProduct(scannedProduct);
		productAuthenticationService.saveScanDetails(scannedProduct);

		return scannedProduct;

	}

	/**
	 * Counterfeit product when mobile not able to scan the label.
	 * 
	 * @param scannedProduct
	 * @return
	 */
	@RequestMapping(value = "/scan/suspect", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ProductScanDTO saveSuspectScan(
			@RequestBody ProductScanDTO scannedProduct) {

		scannedProduct.setScanResultType(ScanResultType.Counterfeit.getId());
		scannedProduct.getProduct().setIsCompromised(TRUE);
		scannedProduct
				.setScanVerificationFlag(SUSPECT_COUNTERFEIT_TRUTH_TABLE_VALUE);
		setScanLocation(scannedProduct);
	//	productAuthenticationService.fetchProductInfo(scannedProduct, null);
		productAuthenticationService.saveScanResult(scannedProduct);
		productAuthenticationService.saveScanDetails(scannedProduct);

		return scannedProduct;

	}

	/**
	 * Return scan information.
	 * 
	 * @param scanId
	 * @return
	 */
	@RequestMapping(value = "/scan/{scanId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ProductScanDTO getScanDetails(@PathVariable("scanId") Long scanId) {
		return productAuthenticationService.getScanDetails(scanId);

	}

}
