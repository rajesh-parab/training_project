/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   Tenant.java
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * The persistent class for the tenants database table.
 * 
 */
@Entity
@Table(name = "tenants")
public class Tenant extends BaseModel {

	private String description;
	
	private String logoImageName;

	private String name;
 
	// bi-directional many-to-one association to Brand
	@OneToMany(mappedBy = "tenant",fetch=FetchType.EAGER)
	@JsonBackReference
	private List<Brand> brands;

	// bi-directional many-to-one association to Brandowneruser
	@OneToMany(mappedBy = "tenant",fetch=FetchType.LAZY)
	@JsonBackReference
	private List<BrandOwnerUser> brandownerusers;

	public Tenant() {
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
	
	public List<Brand> getBrands() {
		return this.brands;
	}

	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}

	
	public String getLogoImageName() {
		return logoImageName;
	}

	public void setLogoImageName(String logoImageName) {
		this.logoImageName = logoImageName;
	}

	public Brand addBrand(Brand brand) {
		getBrands().add(brand);
		brand.setTenant(this);

		return brand;
	}

	public Brand removeBrand(Brand brand) {
		getBrands().remove(brand);
		brand.setTenant(null);

		return brand;
	}

	public List<BrandOwnerUser> getBrandownerusers() {
		return this.brandownerusers;
	}

	public void setBrandownerusers(List<BrandOwnerUser> brandownerusers) {
		this.brandownerusers = brandownerusers;
	}

	public BrandOwnerUser addBrandowneruser(BrandOwnerUser brandowneruser) {
		getBrandownerusers().add(brandowneruser);
		brandowneruser.setTenant(this);

		return brandowneruser;
	}

	public BrandOwnerUser removeBrandowneruser(BrandOwnerUser brandowneruser) {
		getBrandownerusers().remove(brandowneruser);
		brandowneruser.setTenant(null);

		return brandowneruser;
	}

}