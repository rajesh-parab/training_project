/*************************************************************************************************************
 * * Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 * *
 * * No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 * * Application : Vantage Point analytics SaaS Authentication Platform
 * *
 * * Module      :   VPA-SaaS
 * * File        :   CompromisedProductViewAll.java
 * * Version     :   1.0
 * * Description :   entity  class for compromised Product having view All functionality .
 * *
 * * Author      :   Narayan Singh
 * * Created Date :  Friday, 19 June 2015
 * *************************************************************************************************************
 * * Change History Header:
 * *************************************************************************************************************
 * * Date Author     Version Description:
 * * -------- --------   -------- -------------
 * *
 **************************************************************************************************************/
package com.vpa.core.dws.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CompromisedProductViewAll {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "revenue_loss")
    private int revenueLoss;

    @Column(name = "projected_revenue_loss")
    private int projectedRevenueLoss;

    @Column(name = "product_family")
    private String productFamily;

    @Column(name = "business_unit")
    private String businessUnit;

    @Column(name = "suspect_auth")
    private int suspectAuth;

    @Column(name = "total_auth")
    private int totalAuth;


    private String glp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getRevenueLoss() {
        return revenueLoss;
    }

    public void setRevenueLoss(int revenueLoss) {
        this.revenueLoss = revenueLoss;
    }

    public int getProjectedRevenueLoss() {
        return projectedRevenueLoss;
    }

    public void setProjectedRevenueLoss(int projectedRevenueLoss) {
        this.projectedRevenueLoss = projectedRevenueLoss;
    }

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getGlp() {
        return glp;
    }

    public void setGlp(String glp) {
        this.glp = glp;
    }

    public int getSuspectAuth() {
        return suspectAuth;
    }

    public void setSuspectAuth(int suspectAuth) {
        this.suspectAuth = suspectAuth;
    }

    public int getTotalAuth() {
        return totalAuth;
    }

    public void setTotalAuth(int totalAuth) {
        this.totalAuth = totalAuth;
    }


}
