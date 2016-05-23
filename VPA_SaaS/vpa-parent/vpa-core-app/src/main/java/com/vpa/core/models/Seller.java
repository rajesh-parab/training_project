/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-Core
 ** File        :   Seller.java
 ** Version     :   1.0
 ** Description :   
 **
 ** Author      :   Rajesh Parab 
 ** Created Date :  Monday, 17 Aug 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.core.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "seller")

public class Seller extends BaseModel {

	private String name;

	private String sellerType;

	private String webAddress;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "cityId")
	private City city;

	private String state;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "countryMasterId")
	private CountryMaster countryMaster;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public String getWebAddress() {

		return (webAddress == null) ? "" : webAddress.toLowerCase();

	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public CountryMaster getCountryMaster() {
		return countryMaster;
	}

	public void setCountryMaster(CountryMaster countryMaster) {
		this.countryMaster = countryMaster;
	}

}
