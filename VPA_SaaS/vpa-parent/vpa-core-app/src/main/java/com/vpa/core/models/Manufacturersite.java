/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   Manufacturersite.java
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
 * The persistent class for the manufacturersite database table.
 * 
 */
@Entity
@Table(name = "manufacturersite")
public class Manufacturersite extends BaseModel {

	private String description;

	private String name;

	// bi-directional many-to-one association to Manufacturer
	@ManyToOne
	@JoinColumn(name = "manufacturerId")
	private Manufacturer manufacturer;

	// bi-directional many-to-one association to ProductInstance
	@OneToMany(mappedBy = "manufacturersite")
	private List<ProductInstance> productInstances;

	public Manufacturersite() {
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

	public ProductInstance addProductinstance(ProductInstance productInstance) {
		getProductinstances().add(productInstance);
		productInstance.setManufacturersite(this);

		return productInstance;
	}

	public ProductInstance removeProductinstance(ProductInstance productInstance) {
		getProductinstances().remove(productInstance);
		productInstance.setManufacturersite(null);

		return productInstance;
	}

}