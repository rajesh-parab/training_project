package com.vpa.saas.dto;

public class TotalAuthCountDTO {

	private String monthYr;
	private String regionName;
	private String entityTypeName;
	private String levelName;
	private String authenticationType;
	private String totalAuthentication;
	private int uniqueEntities;

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

	public String getEntityTypeName() {
		return entityTypeName;
	}

	public void setEntityTypeName(String entityTypeName) {
		this.entityTypeName = entityTypeName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getAuthenticationType() {
		return authenticationType;
	}

	public void setAuthenticationType(String authenticationType) {
		this.authenticationType = authenticationType;
	}

	public String getTotalAuthentication() {
		return totalAuthentication;
	}

	public void setTotalAuthentication(String totalAuthentication) {
		this.totalAuthentication = totalAuthentication;
	}
	

	public int getUniqueEntities() {
		return uniqueEntities;
	}

	public void setUniqueEntities(int uniqueEntities) {
		this.uniqueEntities = uniqueEntities;
	}

}
