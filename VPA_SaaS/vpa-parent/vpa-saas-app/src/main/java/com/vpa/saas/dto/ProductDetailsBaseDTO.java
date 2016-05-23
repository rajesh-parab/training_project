package com.vpa.saas.dto;

public class ProductDetailsBaseDTO {

	private String region;

	private String country;

	private String timePeriod;

	private String entityType;

	private int totalAuthentication;

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public int getTotalAuthentication() {
		return totalAuthentication;
	}

	public void setTotalAuthentication(int totalAuthentication) {
		this.totalAuthentication = totalAuthentication;
	}

}
