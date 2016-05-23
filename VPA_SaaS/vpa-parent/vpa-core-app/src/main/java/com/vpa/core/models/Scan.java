/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   Scan.java
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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the scan database table.
 * 
 */
@Entity
@Table(name = "scan")
public class Scan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "countryMasterId")
	private CountryMaster countryMaster;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cityId")
	private City city;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	private byte enable;

	private String latitude;

	private String longitude;

	private String scanTimeZone;

	private int scanVerificationFlag;

	private String securitySerialNumber;

	private String userEnteredProductId;

	// bi-directional many-to-one association to Brand
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "brandId")
	private Brand brand;

	// bi-directional many-to-one association to Product
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "productId")
	private Product product;

	// bi-directional many-to-one association to Scanresult
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "scanresultId")
	private ScanResult scanResult;

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;

	// bi-directional many-to-one association to Scandetail
	@OneToMany(mappedBy = "scan", fetch = FetchType.LAZY)
	private List<ScanDetail> scanDetails;

	public Scan() {
	}

	public Scan(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CountryMaster getCountryMaster() {
		return countryMaster;
	}

	public void setCountryMaster(CountryMaster countryMaster) {
		this.countryMaster = countryMaster;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setScanDetails(List<ScanDetail> scanDetails) {
		this.scanDetails = scanDetails;
	}

	public byte getEnable() {
		return this.enable;
	}

	public void setEnable(byte enable) {
		this.enable = enable;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getScanTimeZone() {
		return this.scanTimeZone;
	}

	public void setScanTimeZone(String scanTimeZone) {
		this.scanTimeZone = scanTimeZone;
	}

	public int getScanVerificationFlag() {
		return this.scanVerificationFlag;
	}

	public void setScanVerificationFlag(int scanVerificationFlag) {
		this.scanVerificationFlag = scanVerificationFlag;
	}

	public String getSecuritySerialNumber() {
		return this.securitySerialNumber;
	}

	public void setSecuritySerialNumber(String securitySerialNumber) {
		this.securitySerialNumber = securitySerialNumber;
	}

	public String getUserEnteredProductId() {
		return this.userEnteredProductId;
	}

	public void setUserEnteredProductId(String userEnteredProductId) {
		this.userEnteredProductId = userEnteredProductId;
	}

	public Brand getBrand() {
		return this.brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ScanResult getScanResult() {
		return this.scanResult;
	}

	public void setScanResult(ScanResult scanResult) {
		this.scanResult = scanResult;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ScanDetail> getScanDetails() {
		return this.scanDetails;
	}

	public void setScandetails(List<ScanDetail> scanDetails) {
		this.scanDetails = scanDetails;
	}

	public String fetchCountryName() {
		String country = "";
		CountryMaster countryMaster = this.getCountryMaster();
		if (countryMaster != null) {
			country = countryMaster.getName();
		}
		return country;
	}

	public String fetchCityName() {
		String cityName = "";
		City city = this.getCity();
		if (city != null) {
			cityName = city.getName();
		}
		return cityName;
	}

}