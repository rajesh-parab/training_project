/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   Prouductinstance.java
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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the productinstance database table.
 * 
 */
@Entity
@Table(name = "productinstance")
public class ProductInstance extends BaseModel{
    
    private Integer parentId;

    private String productSerialNumber;

    //bi-directional many-to-one association to Manufacturerproduct
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="manufacturerProductId")
    private ManufacturerProduct manufacturerProduct;

    //bi-directional many-to-one association to Manufacturersite
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="manufacturerSiteId")
    private Manufacturersite manufacturersite;

    //bi-directional many-to-one association to Securitylabel
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="securityLabelId")
    private SecurityLabel securitylabel;

    
    public ProductInstance() {
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getProductSerialNumber() {
        return this.productSerialNumber;
    }

    public void setProductSerialNumber(String productSerialNumber) {
        this.productSerialNumber = productSerialNumber;
    }
 
    public Manufacturersite getManufacturersite() {
        return this.manufacturersite;
    }

    public void setManufacturersite(Manufacturersite manufacturersite) {
        this.manufacturersite = manufacturersite;
    }

    public ManufacturerProduct getManufacturerProduct() {
        return manufacturerProduct;
    }

    public void setManufacturerProduct(ManufacturerProduct manufacturerProduct) {
        this.manufacturerProduct = manufacturerProduct;
    }

    public SecurityLabel getSecuritylabel() {
        return securitylabel;
    }

    public void setSecuritylabel(SecurityLabel securitylabel) {
        this.securitylabel = securitylabel;
    }
    
    

}

 