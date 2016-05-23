/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   Productfamily.java
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
 * The persistent class for the productfamily database table.
 * 
 */
@Entity
@Table(name = "productfamily")
public class Productfamily extends BaseModel {

	private String description;

	private String name;

	// bi-directional many-to-one association to BusinessUnit
	@ManyToOne
	@JoinColumn(name = "businessUnitId")
	private BusinessUnit businessUnit;

	// bi-directional many-to-one association to Product
	@OneToMany(mappedBy = "productfamily")
	private List<Product> products;

	public Productfamily() {
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

	
	public BusinessUnit getBusinessunit() {
		return this.businessUnit;
	}

	public void setBusinessunit(BusinessUnit businessUnit) {
		this.businessUnit = businessUnit;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setProductfamily(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setProductfamily(null);

		return product;
	}

}