/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   Comany.java
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
 * The persistent class for the company database table.
 * 
 */
@Entity
@Table(name = "company")
public class Company extends BaseModel {

	private String city;

	private String country;

	private String description;

	private String name;

	private String state;

	private String street;

	private String zipCode;

	// bi-directional many-to-one association to Businessuser
	@OneToMany(mappedBy = "company")
	private List<BusinessUser> businessusers;

	// bi-directional many-to-one association to Entitytype
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entityTypeId")
	private EntityType entityType;

	public Company() {
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public List<BusinessUser> getBusinessusers() {
		return this.businessusers;
	}

	public void setBusinessusers(List<BusinessUser> businessusers) {
		this.businessusers = businessusers;
	}

	public BusinessUser addBusinessuser(BusinessUser businessuser) {
		getBusinessusers().add(businessuser);
		businessuser.setCompany(this);

		return businessuser;
	}

	public BusinessUser removeBusinessuser(BusinessUser businessuser) {
		getBusinessusers().remove(businessuser);
		businessuser.setCompany(null);

		return businessuser;
	}

	public EntityType getEntityType() {
		return this.entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

}