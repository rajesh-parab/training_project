/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   Product.java
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

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the products database table.
 * 
 */
@Entity
@Table(name = "products")

public class Product  extends BaseModel{
   
    private String description;

    private Double glp;

    @Temporal(TemporalType.TIMESTAMP)
    private Date glpUpdateDate;

    private Byte isCompromised;

    private Byte isProtected;

    private String name;

    private String productIdLabelLocation;

    private String productImageKey;

    private String serialNumberLabelLocation;

    // bi-directional many-to-one association to Manufacturerproduct
    @OneToMany(mappedBy = "product",fetch=FetchType.LAZY)
    private List<ManufacturerProduct> manufacturerPoducts;

    // bi-directional many-to-one association to MonthlyRevenue
    @OneToMany(mappedBy = "product",fetch=FetchType.LAZY)
    private List<MonthlyRevenue> monthlyRevenues;

    // bi-directional many-to-one association to Level
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "levelId")
    private Level level;

    // bi-directional many-to-one association to Productfamily
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "productFamilyId")
    private Productfamily productfamily;

    // bi-directional many-to-one association to Scan
    @OneToMany(mappedBy = "product",fetch=FetchType.LAZY)
    private List<Scan> scans;
    

    public Product() {
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getGlp() {
        return this.glp;
    }

    public void setGlp(Double glp) {
        this.glp = glp;
    }

    public Date getGlpUpdateDate() {
        return this.glpUpdateDate;
    }

    public void setGlpUpdateDate(Date glpUpdateDate) {
        this.glpUpdateDate = glpUpdateDate;
    }

    public Byte getIsCompromised() {
        return this.isCompromised;
    }

    public void setIsCompromised(Byte isCompromised) {
        this.isCompromised = isCompromised;
    }

    public Byte getIsProtected() {
        return this.isProtected;
    }

    public void setIsProtected(Byte isProtected) {
        this.isProtected = isProtected;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductIdLabelLocation() {
        return this.productIdLabelLocation;
    }

    public void setProductIdLabelLocation(String productIdLabelLocation) {
        this.productIdLabelLocation = productIdLabelLocation;
    }

    public String getProductImageKey() {
        return this.productImageKey;
    }

    public void setProductImageKey(String productImageKey) {
        this.productImageKey = productImageKey;
    }

    public String getSerialNumberLabelLocation() {
        return this.serialNumberLabelLocation;
    }

    public void setSerialNumberLabelLocation(String serialNumberLabelLocation) {
        this.serialNumberLabelLocation = serialNumberLabelLocation;
    }
    
    public Level getLevel() {
        return this.level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Productfamily getProductfamily() {
        return this.productfamily;
    }

    public void setProductfamily(Productfamily productfamily) {
        this.productfamily = productfamily;
    }

    public List<Scan> getScans() {
        return this.scans;
    }

    public void setScans(List<Scan> scans) {
        this.scans = scans;
    }

	public List<ManufacturerProduct> getManufacturerPoducts() {
		return manufacturerPoducts;
	}

	public void setManufacturerPoducts(List<ManufacturerProduct> manufacturerPoducts) {
		this.manufacturerPoducts = manufacturerPoducts;
	}

	public List<MonthlyRevenue> getMonthlyRevenues() {
		return monthlyRevenues;
	}

	public void setMonthlyRevenues(List<MonthlyRevenue> monthlyRevenues) {
		this.monthlyRevenues = monthlyRevenues;
	}

	

}