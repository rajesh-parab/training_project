/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   ProductScanDTO.java
 ** Version     :   1.0
 ** Description :   DTO class which will converted to JSON by Spring framework.
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
package com.vpa.saas.dto;

import static com.vpa.core.utils.VPASaaSConstant.INITIAL_VARIFY_SCAN_RESULT;
import static com.vpa.core.utils.VPASaaSUtil.isBetween;

import java.text.SimpleDateFormat;
import java.util.EnumMap;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.vpa.core.enums.UserEnteredKeyType;

@JsonInclude(Include.NON_EMPTY)
public class ProductScanDTO extends BaseDTO {

	private String securitySerialNumber;

	private BrandDTO brand = new BrandDTO();

	private ProductDTO product = new ProductDTO();

	private ProductInstanceDTO productInstance = new ProductInstanceDTO();

	private UserDTO user = new UserDTO();

	private SellerDTO seller = new SellerDTO();

	private Integer scanResultType = 999;

	private String scanTimeZone;

	private long userId;

	private String city;

	private String countryCode;
	
	private String countryName;

	private String latitude;

	private String longitude;

	private String userEnteredProductId;

	private Long scanId;

	private String scanResultDesc;

	private String level;

	private String companyName;

	private String createdDateString;

	private String regionName;

	private int scanVerificationFlag;

	private Map<UserEnteredKeyType, String> scanDetails = new EnumMap<>(
			UserEnteredKeyType.class);

	public String getSerialNumber() {
		return securitySerialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.securitySerialNumber = serialNumber;
	}

	public BrandDTO getBrand() {
		return brand;
	}

	public void setBrand(BrandDTO brand) {
		this.brand = brand;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public ProductInstanceDTO getProductInstance() {
		return productInstance;
	}

	public void setProductInstance(ProductInstanceDTO productInstance) {
		this.productInstance = productInstance;
	}

	public Integer getScanResultType() {
		return scanResultType;
	}

	public void setScanResultType(Integer scanResultType) {
		this.scanResultType = scanResultType;
	}

	public String getScanTimeZone() {
		if (!StringUtils.hasText(scanTimeZone)) {
			scanTimeZone = "GMT";
		}
		return scanTimeZone;

	}

	public void setScanTimeZone(String scanTimeZone) {
		this.scanTimeZone = scanTimeZone;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getSecuritySerialNumber() {
		return (securitySerialNumber != null) ? securitySerialNumber.trim()
				: "";
	}

	public void setSecuritySerialNumber(String securitySerialNumber) {
		this.securitySerialNumber = securitySerialNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountryCode() {
		return (this.countryCode != null) ? this.countryCode.trim() : null;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getUserEnteredProductId() {
		return userEnteredProductId;
	}

	public void setUserEnteredProductId(String userEnteredProductId) {
		this.userEnteredProductId = userEnteredProductId;
	}

	public Long getScanId() {
		return (scanId == null) ? 0L : scanId;
	}

	public void setScanId(Long scanId) {
		this.scanId = scanId;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getScanResultDesc() {
		return scanResultDesc;
	}

	public void setScanResultDesc(String scanResultDesc) {
		this.scanResultDesc = scanResultDesc;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Map<UserEnteredKeyType, String> getScanDetails() {
		return scanDetails;
	}

	public void setScanDetails(Map<UserEnteredKeyType, String> scanDetails) {
		this.scanDetails = scanDetails;
	}

	public String getCreatedDateString() {
		return createdDateString;
	}

	public void setCreatedDateString(String createdDateString) {
		this.createdDateString = createdDateString;
	}

	public String[] getScanDateTime() {

		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		TimeZone t = TimeZone.getTimeZone(this.getScanTimeZone());
		sf.setTimeZone(t);
		String scanDateTime = sf.format(super.getCreatedDate());
		return scanDateTime.split(" ");
	}

	public int getScanVerificationFlag() {
		return scanVerificationFlag;
	}

	public void setScanVerificationFlag(int scanVerificationFlag) {
		this.scanVerificationFlag = scanVerificationFlag;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public SellerDTO getSeller() {
		return seller;
	}

	public void setSeller(SellerDTO seller) {
		this.seller = seller;
	}

	/*
	 * Calculating values for field scanVerificationFlag in scan table. This is
	 * according to truth table. see design document in git repository.
	 * 
	 * @return verification flag value according to truth table.
	 */
	public int calculateVerificationFlag() {

		int calculatedValue = INITIAL_VARIFY_SCAN_RESULT;

		calculatedValue = verifyBrand(calculatedValue);

		// TODO need to change name PRODUCT_SERIAL_NUMBER
		// this should be product name ( product id/modle name in wireframe)
		calculatedValue = verifyProduct(calculatedValue);

		calculatedValue = verifySecuritySerialNumber(calculatedValue);

		calculatedValue = verifyLevelId(calculatedValue);

		updateScanResultType(calculatedValue);

		return calculatedValue;

	}

	private void updateScanResultType(final int calculatedValue) {

		if (calculatedValue == 31) {
			setScanResultType(ScanResultType.Genuine.getId());
		} else if (isBetween(calculatedValue, 28, 29)) {
			setScanResultType(ScanResultType.Serial_Number_Tampered.getId());
		} else if (isBetween(calculatedValue, 24, 27)) {
			setScanResultType(ScanResultType.Counterfeit_Upgrade.getId());
		} else if (isBetween(calculatedValue, 16, 23) || calculatedValue == 30) {
			setScanResultType(ScanResultType.Theft.getId());
		}

	}

	private int verifyLevelId(final int calculatedValue) {
		int returnValue = 0;
		String userLevelId = scanDetails.get(UserEnteredKeyType.LEVEL_ID);
		String actualLevelId = this.getProduct().getProductIdLabelLocation();
		returnValue = calculatedValue
				+ (byte) (actualLevelId.equalsIgnoreCase(userLevelId) ? INITIAL_VARIFY_SCAN_RESULT >> 4
						: 0);
		return returnValue;
	}

	private int verifySecuritySerialNumber(final int calculatedValue) {
		int returnValue = 0;
		String userSerialNumber = scanDetails
				.get(UserEnteredKeyType.SECURITY_SERIAL_NUMBER);
		String actualSerialNumber = this.getSecuritySerialNumber();
		returnValue = calculatedValue
				+ (byte) (actualSerialNumber.equalsIgnoreCase(userSerialNumber) ? INITIAL_VARIFY_SCAN_RESULT >> 3
						: 0);
		return returnValue;
	}

	private int verifyProduct(final int calculatedValue) {
		int returnValue = 0;
		String userProductName = scanDetails
				.get(UserEnteredKeyType.PRODUCT_SERIAL_NUMBER);
		String actualProductName = this.getProduct().getName();
		returnValue = calculatedValue
				+ (byte) (actualProductName.equalsIgnoreCase(userProductName) ? INITIAL_VARIFY_SCAN_RESULT >> 2
						: 0);
		return returnValue;
	}

	private int verifyBrand(final int calculatedValue) {
		int returnValue = 0;
		String userBrandId = scanDetails.get(UserEnteredKeyType.BRAND_ID);
		String actualBrandId = this.getBrand().getId().toString();
		returnValue = calculatedValue
				+ (byte) (actualBrandId.equalsIgnoreCase(userBrandId) ? INITIAL_VARIFY_SCAN_RESULT >> 1
						: 0);
		return returnValue;
	}

	public String getProductIdFromScanDetailsMap() {
		return scanDetails.get(UserEnteredKeyType.PRODUCT_SERIAL_NUMBER);
	 
	}

}
