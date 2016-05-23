package com.vpa.saas.dto;

public class RevenueLossDTO {

	private String monthYr;
	private String regionName;
	private String countryName;
	private String entityTypeName;
	private String authenticationType;
	private String revenueLoss;
	private String totalAuthentication;

	public String getMonthYr() {
		return monthYr;
	}

	public void setMonthYr(String monthYr) {
		this.monthYr = monthYr;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getEntityTypeName() {
		return entityTypeName;
	}

	public void setEntityTypeName(String entityTypeName) {
		this.entityTypeName = entityTypeName;
	}

	public String getAuthenticationType() {
		return authenticationType;
	}

	public void setAuthenticationType(String authenticationType) {
		this.authenticationType = authenticationType;
	}

	public String getRevenueLoss() {
		return revenueLoss;
	}

	public void setRevenueLoss(String revenueLoss) {
		this.revenueLoss = revenueLoss;
	}

	public String getTotalAuthentication() {
		return totalAuthentication;
	}

	public void setTotalAuthentication(String totalAuthentication) {
		this.totalAuthentication = totalAuthentication;
	}

}
