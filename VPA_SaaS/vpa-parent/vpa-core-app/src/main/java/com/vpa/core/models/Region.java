/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   Region.java
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
 * The persistent class for the region database table.
 * 
 */
@Entity
@Table(name = "region")
public class Region extends BaseModel {

	private String description;

	private String name;

	// bi-directional many-to-one association to Subregion
	@OneToMany(mappedBy = "region")
	private List<Subregion> subregions;

	public Region() {
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

	public List<Subregion> getSubregions() {
		return this.subregions;
	}

	public void setSubregions(List<Subregion> subregions) {
		this.subregions = subregions;
	}

	public Subregion addSubregion(Subregion subregion) {
		getSubregions().add(subregion);
		subregion.setRegion(this);

		return subregion;
	}

	public Subregion removeSubregion(Subregion subregion) {
		getSubregions().remove(subregion);
		subregion.setRegion(null);

		return subregion;
	}

}