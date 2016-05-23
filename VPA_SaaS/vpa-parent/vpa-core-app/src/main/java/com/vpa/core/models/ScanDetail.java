/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   ScanDetail.java
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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the scandetail database table.
 * 
 */
@Entity
@Table(name="scandetail")
public class ScanDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private byte enable;

    private String value;

    // bi-directional many-to-one association to Scan
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "scanId")
    private Scan scan;

    // bi-directional many-to-one association to Userenteredkey
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "userEnteredKey")
    private UserEnteredKey userEnteredKey;

    public ScanDetail() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   
    public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public byte getEnable() {
        return this.enable;
    }

    public void setEnable(byte enable) {
        this.enable = enable;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Scan getScan() {
        return this.scan;
    }

    public void setScan(Scan scan) {
        this.scan = scan;
    }

    public UserEnteredKey getUserenteredkey() {
        return this.userEnteredKey;
    }

    public void setUserenteredkey(UserEnteredKey userEnteredKey) {
        this.userEnteredKey = userEnteredKey;
    }

}