package com.vpa.saas.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author PD42694
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class CounterfeitBuyingSellingEntityDTO {

	private Integer entityId;
	private String entityName;
	private Double revenueLoss;
	private String formattedRevenueLoss;
	private BigDecimal numberOfSuspectAuthentications;
	private BigDecimal totalAuthentications;
	private String entityType;
	private String sellerCity;
	private String sellerState;
	private String sellerCountry;
	private String sellerType;
	private String sellerWebAddress;
	private String region;	
	private String timePeriod;	
	private String level;
	private BigInteger flaggedProducts;
	private BigInteger activeUsers;


	/**
	 * Default Constructor
	 */
	public CounterfeitBuyingSellingEntityDTO() {
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Double getRevenueLoss() {
		return revenueLoss;
	}

	public void setRevenueLoss(Double revenueLoss) {
		this.revenueLoss = revenueLoss;
	}

	public String getFormattedRevenueLoss() {
		return formattedRevenueLoss;
	}

	public void setFormattedRevenueLoss(String formattedRevenueLoss) {
		this.formattedRevenueLoss = formattedRevenueLoss;
	}

	public BigDecimal getNumberOfSuspectAuthentications() {
		return numberOfSuspectAuthentications;
	}

	public void setNumberOfSuspectAuthentications(
			BigDecimal numberOfSuspectAuthentications) {
		this.numberOfSuspectAuthentications = numberOfSuspectAuthentications;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getSellerCity() {
		return sellerCity;
	}

	public void setSellerCity(String sellerCity) {
		this.sellerCity = sellerCity;
	}

	public String getSellerState() {
		return sellerState;
	}

	public void setSellerState(String sellerState) {
		this.sellerState = sellerState;
	}

	public String getSellerCountry() {
		return sellerCountry;
	}

	public void setSellerCountry(String sellerCountry) {
		this.sellerCountry = sellerCountry;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public String getSellerWebAddress() {
		return sellerWebAddress;
	}

	public void setSellerWebAddress(String sellerWebAddress) {
		this.sellerWebAddress = sellerWebAddress;
	}

	public BigDecimal getTotalAuthentications() {
		return totalAuthentications;
	}

	public void setTotalAuthentications(BigDecimal totalAuthentications) {
		this.totalAuthentications = totalAuthentications;
	}	

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}	

	public BigInteger getFlaggedProducts() {
		return flaggedProducts;
	}

	public void setFlaggedProducts(BigInteger flaggedProducts) {
		this.flaggedProducts = flaggedProducts;
	}	

	public BigInteger getActiveUsers() {
		return activeUsers;
	}

	public void setActiveUsers(BigInteger activeUsers) {
		this.activeUsers = activeUsers;
	}

	@Override
	public String toString() {
		return "CounterfeitBuyingSellingEntityDTO [entityId=" + entityId
				+ ", entityName=" + entityName + ", revenueLoss=" + revenueLoss
				+ ", formattedRevenueLoss=" + formattedRevenueLoss
				+ ", numberOfSuspectAuthentications="
				+ numberOfSuspectAuthentications + ", totalAuthentications="
				+ totalAuthentications + ", entityType=" + entityType
				+ ", sellerCity=" + sellerCity + ", sellerState=" + sellerState
				+ ", sellerCountry=" + sellerCountry + ", sellerType="
				+ sellerType + ", sellerWebAddress=" + sellerWebAddress
				+ ", region=" + region + ", timePeriod=" + timePeriod
				+ ", level=" + level + ", flaggedProducts=" + flaggedProducts
				+ ", activeUsers=" + activeUsers + "]";
	}
	
}
