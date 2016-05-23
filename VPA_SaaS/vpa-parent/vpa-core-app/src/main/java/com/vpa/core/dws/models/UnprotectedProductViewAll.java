/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :  UnprotectedProductViewAll.java
 ** Version     :   1.0
 ** Description :   entity  class for unprotected Product having view All functionality .
 **
 ** Author      :   Narayan Singh
 ** Created Date :  Wednesday, 24 June 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.core.dws.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

@Entity
@Table(name = "vpadws.dim_product")
@SqlResultSetMapping(name = "UnprotectedProductViewAll", entities = { @EntityResult(entityClass = UnprotectedProductViewAll.class, fields = {}) })
public class UnprotectedProductViewAll {

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "product_family")
	private String productFamily;

	@Column(name = "business_unit")
	private String businessUnit;

	@Column(name = "revenue_risk")
	private int revenueRisk;

	private String glp;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getRevenueRisk() {
		return revenueRisk;
	}

	public void setRevenueRisk(int revenueRisk) {
		this.revenueRisk = revenueRisk;
	}

	public String getProductFamily() {
		return productFamily;
	}

	public void setProductFamily(String productFamily) {
		this.productFamily = productFamily;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getGlp() {
		return glp;
	}

	public void setGlp(String glp) {
		this.glp = glp;
	}

}
