/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   LiveScanDetailDTO.java
 ** Version     :   1.0
 ** Description :   DTO class which will converted to JSON by Spring framework.
 **
 ** Author      :   Rajesh Parab
 ** Created Date :  Wednesday, 23 Sep 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.saas.dto;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class LiveScanDetailDTO extends BaseDTO {

	private UserDTO user = new UserDTO();

	private String companyName;

	private String scanResultDesc;

	private String userEnteredProductId;

	private ProductDTO product = new ProductDTO();

	private String scanTimeZone;

	private String level;

	private String city;

	private String country;

	private String createdDateString;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getScanResultDesc() {
		return scanResultDesc;
	}

	public void setScanResultDesc(String scanResultDesc) {
		this.scanResultDesc = scanResultDesc;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public String getScanTimeZone() {
		return scanTimeZone;
	}

	public void setScanTimeZone(String scanTimeZone) {
		this.scanTimeZone = scanTimeZone;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCreatedDateString() {
		return createdDateString;
	}

	public void setCreatedDateString(String createdDateString) {
		this.createdDateString = createdDateString;
	}

	public String getUserEnteredProductId() {
		return userEnteredProductId;
	}

	public void setUserEnteredProductId(String userEnteredProductId) {
		this.userEnteredProductId = userEnteredProductId;
	}

	public String[] getScanDateTime() {

		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		TimeZone t = TimeZone.getTimeZone(this.getScanTimeZone());
		sf.setTimeZone(t);
		String scanDateTime = sf.format(super.getCreatedDate());
		return scanDateTime.split(" ");
	}

}
