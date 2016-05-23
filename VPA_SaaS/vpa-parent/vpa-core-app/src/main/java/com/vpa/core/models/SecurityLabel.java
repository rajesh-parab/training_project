/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   SecurityLabel.java
 ** Version     :   1.0
 ** Description :   Model class  
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
package com.vpa.core.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the securitylabel database table.
 * 
 */
@Entity
@Table(name = "securitylabel")
 
public class SecurityLabel extends BaseModel {

	private String serialNumber;

	// bi-directional many-to-one association to Brand
	@ManyToOne
	@JoinColumn(name = "brandId")
	private Brand brand;

	// bi-directional many-to-one association to Productinstance
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "productInstanceId")
	private ProductInstance productInstance;

	public SecurityLabel() {
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Brand getBrand() {
		return this.brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public ProductInstance getProductInstance() {
		return productInstance;
	}

	public void setProductInstance(ProductInstance productInstance) {
		this.productInstance = productInstance;
	}

}