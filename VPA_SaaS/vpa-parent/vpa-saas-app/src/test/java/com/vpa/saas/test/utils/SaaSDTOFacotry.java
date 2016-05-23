/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   SaaSDTOFacotry.java
 ** Version     :   1.0
 ** Description :   Factory class for creating test doubles.
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
package com.vpa.saas.test.utils;

import java.util.EnumMap;

import com.vpa.core.enums.UserEnteredKeyType;
import com.vpa.saas.dto.ProductScanDTO;

public class SaaSDTOFacotry {

	private static final String LONGITUDE = "73.8567";
	private static final String LATITUDE = "18.5203";
	//country name should be match with lat long above to find out fire following RESTFul  Service. 
	//https://maps.googleapis.com/maps/api/geocode/json?latlng=18.5203,73.8567
	private static final String CITY = "Pune";
	private static final String COUNTRY_CODE = "IN";
	private static final String COUNTRY = "INDIA";
	
	private static final String ZJLKJIL_INDIA_PVT_LTD = "Zjlkjil india pvt ltd.";

	public static ProductScanDTO createGenuineScannedProductDTO(
			String securityLabel, long userId) {
		ProductScanDTO scannedProduct = new ProductScanDTO();
		scannedProduct.setSerialNumber(securityLabel);
		scannedProduct.setScanTimeZone("UTC");

		scannedProduct.setUserId(userId);
		scannedProduct.setCity(CITY);
 
		scannedProduct.setCountryCode(COUNTRY_CODE);
 		scannedProduct.setCountryName(COUNTRY);
 
		scannedProduct.setLatitude(LATITUDE);
		scannedProduct.setLongitude(LONGITUDE);
		scannedProduct.setScanTimeZone("UTC/GMT +5:30 hours");
		scannedProduct.setScanVerificationFlag(31);
		return scannedProduct;
	}

	public static ProductScanDTO createScannedProductDTO(String securityLabel,
			long userId) {
		ProductScanDTO scannedProduct = new ProductScanDTO();
		scannedProduct.setSerialNumber(securityLabel);
		scannedProduct.setScanTimeZone("UTC");

		scannedProduct.setUserId(userId);
		scannedProduct.setCity(CITY);

		scannedProduct.setCountryCode(COUNTRY_CODE);
 		scannedProduct.setCountryName(COUNTRY);
		scannedProduct.setLatitude(LATITUDE);
		scannedProduct.setLongitude(LONGITUDE);
		scannedProduct.setScanTimeZone("UTC/GMT +5:30 hours");
		scannedProduct.setScanVerificationFlag(0);
		return scannedProduct;
	}

	public static ProductScanDTO createSuspectedScannedProduct(long brandId,
			long userId) {
		ProductScanDTO scannedProduct = new ProductScanDTO();
		scannedProduct.setSecuritySerialNumber("DSDFSD");
		scannedProduct.setScanTimeZone("UTC");
		scannedProduct.getBrand().setId(brandId);
		scannedProduct.setUserEnteredProductId("FLEX34343");
		scannedProduct.getProduct().setId(userId);
		scannedProduct.getProduct().setProductIdLabelLocation("BOX");
		scannedProduct.setUserId(userId);
		scannedProduct.setCity(CITY);

		scannedProduct.setCountryCode(COUNTRY_CODE);
 		scannedProduct.setCountryName(COUNTRY);
		scannedProduct.setLatitude(LATITUDE);
		scannedProduct.setLongitude(LONGITUDE);
		scannedProduct.setScanTimeZone("UTC/GMT +5:30 hours");

		return scannedProduct;
	}

	public static ProductScanDTO createGenuineProductWithNewSellerDetails(
			ProductScanDTO scannedProduct) {

		EnumMap<UserEnteredKeyType, String> scanDetails = new EnumMap<>(
				UserEnteredKeyType.class);

		scanDetails.put(UserEnteredKeyType.SELLER_ID, ZJLKJIL_INDIA_PVT_LTD);
		scannedProduct.getSeller().setSellerType("online");
		scannedProduct.getSeller().setName(ZJLKJIL_INDIA_PVT_LTD);
		scannedProduct.getSeller().setState("TX");
		scannedProduct.getSeller().setWebAddress("www.sdfs.com");

		scannedProduct.setScanDetails(scanDetails);
		return scannedProduct;
	}

	public static ProductScanDTO createSuspectedScannedProductWithNewSellerDetails(
			long brandId, long userId) {
		ProductScanDTO scannedProduct = new ProductScanDTO();
		scannedProduct.setSecuritySerialNumber("DSDFSD");
		scannedProduct.setScanTimeZone("UTC");
		scannedProduct.getBrand().setId(brandId);
		scannedProduct.setUserEnteredProductId("FLEX34343");
		scannedProduct.getProduct().setId(1L);
		scannedProduct.getProduct().setProductIdLabelLocation("BOX");
		scannedProduct.setUserId(userId);
		scannedProduct.setCity(CITY);

		scannedProduct.setCountryCode(COUNTRY_CODE);
 		scannedProduct.setCountryName(COUNTRY);
		scannedProduct.setLatitude(LATITUDE);
		scannedProduct.setLongitude(LONGITUDE);
		scannedProduct.setScanTimeZone("UTC/GMT +5:30 hours");

		EnumMap<UserEnteredKeyType, String> scanDetails = new EnumMap<>(
				UserEnteredKeyType.class);
		scanDetails.put(UserEnteredKeyType.SECURITY_SERIAL_NUMBER,
				scannedProduct.getSecuritySerialNumber());
		scanDetails.put(UserEnteredKeyType.PRODUCT_SERIAL_NUMBER,
				scannedProduct.getUserEnteredProductId());

		scannedProduct.getSeller().setSellerType("online");
		scannedProduct.getSeller().setName(ZJLKJIL_INDIA_PVT_LTD);
		scannedProduct.getSeller().setState("TX");
		scannedProduct.getSeller().setWebAddress("www.sdfs.com");

		scanDetails.put(UserEnteredKeyType.SELLER_ID, ZJLKJIL_INDIA_PVT_LTD);

		scanDetails.put(UserEnteredKeyType.ENTITY_TYPE, "Retail");
		scanDetails.put(UserEnteredKeyType.LEVEL_ID, "Box");
		scannedProduct.setScanDetails(scanDetails);
		return scannedProduct;
	}

	public static ProductScanDTO createSuspectedScannedProductWithExistingSellerDetails(
			long brandId, long userId, String sellerName) {
		ProductScanDTO scannedProduct = new ProductScanDTO();
		scannedProduct.setSecuritySerialNumber("DSDFSD");
		scannedProduct.setScanTimeZone("UTC");
		scannedProduct.getBrand().setId(brandId);
		scannedProduct.setUserEnteredProductId("FLEX34343");
		scannedProduct.getProduct().setId(1L);
		scannedProduct.getProduct().setProductIdLabelLocation("BOX");
		scannedProduct.setUserId(userId);
		scannedProduct.setCity(CITY);

		scannedProduct.setCountryCode(COUNTRY_CODE);
 		scannedProduct.setCountryName(COUNTRY);
		scannedProduct.setLatitude(LATITUDE);
		scannedProduct.setLongitude(LONGITUDE);
		scannedProduct.setScanTimeZone("UTC/GMT +5:30 hours");

		EnumMap<UserEnteredKeyType, String> scanDetails = new EnumMap<>(
				UserEnteredKeyType.class);
		scanDetails.put(UserEnteredKeyType.SECURITY_SERIAL_NUMBER,
				scannedProduct.getSecuritySerialNumber());
		scanDetails.put(UserEnteredKeyType.PRODUCT_SERIAL_NUMBER,
				scannedProduct.getUserEnteredProductId());

		scannedProduct.getSeller().setName(sellerName);

		scanDetails.put(UserEnteredKeyType.SELLER_ID, sellerName);

		scanDetails.put(UserEnteredKeyType.ENTITY_TYPE, "Retail");
		scanDetails.put(UserEnteredKeyType.LEVEL_ID, "Box");
		scannedProduct.setScanDetails(scanDetails);
		return scannedProduct;
	}

	public static ProductScanDTO createTamperedProductScanDto(long userId,
			Long brandId, String productName, String securitySerialNumber,
			String levelId,  Long productId) {
		ProductScanDTO scannedProduct = new ProductScanDTO();
		scannedProduct.setSecuritySerialNumber(securitySerialNumber);
		scannedProduct.setScanTimeZone("UTC");
		scannedProduct.getBrand().setId(brandId);
		scannedProduct.setUserEnteredProductId("FLEX34343");
		scannedProduct.getProduct().setId(productId);
		scannedProduct.getProduct().setName(productName);
		scannedProduct.getProduct().setProductIdLabelLocation(levelId);
		scannedProduct.setUserId(userId);
		scannedProduct.setCity(CITY);
		scannedProduct.setCountryCode(COUNTRY_CODE);
 		scannedProduct.setCountryName(COUNTRY);
		scannedProduct.getProduct().setProductIdLabelLocation("BOX");

		return scannedProduct;
	}

	public static EnumMap<UserEnteredKeyType, String> getScanDetailsForSerialNumberTampered(
			Long brandId, String productName, String levelId) {
		EnumMap<UserEnteredKeyType, String> scanDetails = new EnumMap<>(
				UserEnteredKeyType.class);
		scanDetails.put(UserEnteredKeyType.BRAND_ID, brandId.toString());
		scanDetails.put(UserEnteredKeyType.PRODUCT_SERIAL_NUMBER, productName);
		scanDetails.put(UserEnteredKeyType.SECURITY_SERIAL_NUMBER, "XXX");
		scanDetails.put(UserEnteredKeyType.LEVEL_ID, levelId);
		return scanDetails;
	}

	public static EnumMap<UserEnteredKeyType, String> getScanDetailsForGenuineProduct(
			Long brandId, String productName, String securitySerialNumber,
			String levelId) {
		EnumMap<UserEnteredKeyType, String> scanDetails = new EnumMap<>(
				UserEnteredKeyType.class);
		scanDetails.put(UserEnteredKeyType.BRAND_ID, brandId.toString());
		scanDetails.put(UserEnteredKeyType.PRODUCT_SERIAL_NUMBER, productName);
		scanDetails.put(UserEnteredKeyType.SECURITY_SERIAL_NUMBER,
				securitySerialNumber);
		scanDetails.put(UserEnteredKeyType.LEVEL_ID, levelId);
		return scanDetails;
	}

	public static ProductScanDTO createProductScanDtoForTruthTable(
			Long brandId, String productName, String securitySerialNumber,
			String levelId) {
		ProductScanDTO scannedProduct = new ProductScanDTO();
		scannedProduct.setSecuritySerialNumber(securitySerialNumber);
		scannedProduct.setScanTimeZone("UTC");
		scannedProduct.getBrand().setId(brandId);
		scannedProduct.setUserEnteredProductId("FLEX34343");
		scannedProduct.getProduct().setId(1L);
		scannedProduct.getProduct().setName(productName);
		scannedProduct.getProduct().setProductIdLabelLocation(levelId);
		scannedProduct.setCity("Pune");
		EnumMap<UserEnteredKeyType, String> scanDetails = getScanDetailsForGenuineProduct(
				brandId, productName, securitySerialNumber, levelId);
		scannedProduct.setScanDetails(scanDetails);
		return scannedProduct;
	}
}
