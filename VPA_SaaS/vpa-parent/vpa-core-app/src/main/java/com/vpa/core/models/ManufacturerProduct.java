/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   ManufacturerProduct.java
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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the manufacturerproduct database table.
 * 
 */
@Entity
@Table(name = "manufacturerproduct")
public class ManufacturerProduct extends BaseModel {

	private String manufacturerProductId;

	// bi-directional many-to-one association to Product
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "productId")
	private Product product;

	// bi-directional many-to-one association to Manufacturer
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "manufacturerId")
	private Manufacturer manufacturer;

	// bi-directional many-to-one association to ProductInstance
	@OneToMany(mappedBy = "manufacturerProduct")
	private List<ProductInstance> productInstances;

	public ManufacturerProduct() {
	}

	public List<ProductInstance> getProductInstances() {
		return productInstances;
	}

	public void setProductInstances(List<ProductInstance> productInstances) {
		this.productInstances = productInstances;
	}

	public String getManufacturerProductId() {
		return this.manufacturerProductId;
	}

	public void setManufacturerProductId(String manufacturerProductId) {
		this.manufacturerProductId = manufacturerProductId;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Manufacturer getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public List<ProductInstance> getProductinstances() {
		return this.productInstances;
	}

	public void setProductinstances(List<ProductInstance> productInstances) {
		this.productInstances = productInstances;
	}

}