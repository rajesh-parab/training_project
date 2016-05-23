/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   Manufacturer.java
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the manufacturer database table.
 * 
 */
@Entity
@Table(name = "manufacturer")
public class Manufacturer extends BaseModel {

	private String description;

	private String name;

	// bi-directional many-to-one association to ManufacturerProduct
	@OneToMany(mappedBy = "manufacturer")
	private List<ManufacturerProduct> manufacturerProducts;

	// bi-directional many-to-one association to Manufacturersite
	@OneToMany(mappedBy = "manufacturer")
	private List<Manufacturersite> manufacturersites;

	public Manufacturer() {
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

	public List<ManufacturerProduct> getManufacturerproducts() {
		return this.manufacturerProducts;
	}

	public void setManufacturerproducts(
			List<ManufacturerProduct> manufacturerProducts) {
		this.manufacturerProducts = manufacturerProducts;
	}

	public ManufacturerProduct addManufacturerproduct(
			ManufacturerProduct manufacturerProduct) {
		getManufacturerproducts().add(manufacturerProduct);
		manufacturerProduct.setManufacturer(this);

		return manufacturerProduct;
	}

	public ManufacturerProduct removeManufacturerproduct(
			ManufacturerProduct manufacturerProduct) {
		getManufacturerproducts().remove(manufacturerProduct);
		manufacturerProduct.setManufacturer(null);

		return manufacturerProduct;
	}

	public List<Manufacturersite> getManufacturersites() {
		return this.manufacturersites;
	}

	public void setManufacturersites(List<Manufacturersite> manufacturersites) {
		this.manufacturersites = manufacturersites;
	}

	public Manufacturersite addManufacturersite(
			Manufacturersite manufacturersite) {
		getManufacturersites().add(manufacturersite);
		manufacturersite.setManufacturer(this);

		return manufacturersite;
	}

	public Manufacturersite removeManufacturersite(
			Manufacturersite manufacturersite) {
		getManufacturersites().remove(manufacturersite);
		manufacturersite.setManufacturer(null);

		return manufacturersite;
	}

}