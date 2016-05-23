/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   BusinessUnit.java
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

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the BusinessUnit database table.
 * 
 */
@Entity
@Table(name = "businessunit")
public class BusinessUnit extends BaseModel {

	private String description;

	private String name;

	// bi-directional many-to-one association to Brand
	@ManyToOne
	@JoinColumn(name = "brandId")
	private Brand brand;

	// bi-directional many-to-one association to Productfamily
	@OneToMany(mappedBy = "businessUnit")
	private List<Productfamily> productfamilies;

	public BusinessUnit() {
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public Brand getBrand() {
		return this.brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public List<Productfamily> getProductfamilies() {
		return this.productfamilies;
	}

	public void setProductfamilies(List<Productfamily> productfamilies) {
		this.productfamilies = productfamilies;
	}

	public Productfamily addProductfamily(Productfamily productfamily) {
		getProductfamilies().add(productfamily);
		productfamily.setBusinessunit(this);

		return productfamily;
	}

	public Productfamily removeProductfamily(Productfamily productfamily) {
		getProductfamilies().remove(productfamily);
		productfamily.setBusinessunit(null);

		return productfamily;
	}

}