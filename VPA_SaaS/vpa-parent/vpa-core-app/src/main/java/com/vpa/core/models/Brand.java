/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   Brand.java
 ** Version     :   1.0
 ** Description :   Service class implementation of UserService Interface to server the user controller and user related use cases.
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

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * The persistent class for the brand database table.
 * 
 */
@Entity
@Table(name = "brand")
public class Brand extends BaseModel{

    private String description;

    private String name;

    // bi-directional many-to-one association to Tenant
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "tenantId")
    private Tenant tenant;

    // bi-directional many-to-one association to BusinessUnit
    @OneToMany(mappedBy = "brand",fetch=FetchType.LAZY)
    @JsonBackReference
    private List<BusinessUnit> businessUnits;

    // bi-directional many-to-one association to Scan
    @OneToMany(mappedBy = "brand",fetch=FetchType.LAZY)
    @JsonBackReference
    private List<Scan> scans;

    // bi-directional many-to-one association to SecurityLabel
    @OneToMany(mappedBy = "brand",fetch=FetchType.LAZY)
    @JsonBackReference
    private List<SecurityLabel> securityLabels;

    public Brand() {
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

    public Tenant getTenant() {
        return this.tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public List<BusinessUnit> getBusinessunits() {
        return this.businessUnits;
    }

    public void setBusinessunits(List<BusinessUnit> businessUnits) {
        this.businessUnits = businessUnits;
    }

    public BusinessUnit addBusinessunit(BusinessUnit businessUnit) {
        getBusinessunits().add(businessUnit);
        businessUnit.setBrand(this);

        return businessUnit;
    }

    public BusinessUnit removeBusinessunit(BusinessUnit businessUnit) {
        getBusinessunits().remove(businessUnit);
        businessUnit.setBrand(null);

        return businessUnit;
    }

    public List<Scan> getScans() {
        return this.scans;
    }

    public void setScans(List<Scan> scans) {
        this.scans = scans;
    }

    public Scan addScan(Scan scan) {
        getScans().add(scan);
        scan.setBrand(this);

        return scan;
    }

    public Scan removeScan(Scan scan) {
        getScans().remove(scan);
        scan.setBrand(null);

        return scan;
    }

    public List<SecurityLabel> getSecuritylabels() {
        return this.securityLabels;
    }

    public void setSecuritylabels(List<SecurityLabel> securityLabels) {
        this.securityLabels = securityLabels;
    }

    public SecurityLabel addSecuritylabel(SecurityLabel securityLabel) {
        getSecuritylabels().add(securityLabel);
        securityLabel.setBrand(this);

        return securityLabel;
    }

    public SecurityLabel removeSecuritylabel(SecurityLabel securityLabel) {
        getSecuritylabels().remove(securityLabel);
        securityLabel.setBrand(null);

        return securityLabel;
    }

}