package com.vpa.saas.dto;

public class Last24HrsAuthenticationDTO {

	private String regionName;

	private String entityTypeName;

	private String levelName;

	private String authenticationType;

	private String totalAuthentication;

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

}
