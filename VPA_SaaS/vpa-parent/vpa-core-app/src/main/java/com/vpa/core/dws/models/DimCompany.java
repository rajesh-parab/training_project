package com.vpa.core.dws.models;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

/**
 * @author PD42694
 *
 */
@Entity
@Table(name = "vpadws.dim_company")
@SqlResultSetMapping(name = "DimCompany", entities = {
		@EntityResult(entityClass = DimCompany.class, fields = {
				@FieldResult(name = "revenueLoss", column = "revenueLoss"),
				@FieldResult(name = "entityName", column = "entityName"),
				@FieldResult(name = "numberOfSuspectAuthentications", column = "numberOfSuspectAuthentications"),
				@FieldResult(name = "totalNumberOfAuthentications", column = "totalNumberOfAuthentications"),
				@FieldResult(name = "sellerCity", column = "sellerCity"),
				@FieldResult(name = "sellerState", column = "sellerState"),
				@FieldResult(name = "sellerCountry", column = "sellerCountry"),
				@FieldResult(name = "sellerType", column = "sellerType"),
				@FieldResult(name = "sellerWebAddress", column = "sellerWebAddress"),
				@FieldResult(name = "entityType", column = "entityType"),
				@FieldResult(name = "level", column = "level"),
				@FieldResult(name = "region", column = "region"),
				@FieldResult(name = "timePeriod", column = "timePeriod"),
				@FieldResult(name = "flaggedProducts", column = "flaggedProducts"),
				@FieldResult(name = "activeUsers", column = "activeUsers"),

		}), @EntityResult(entityClass = DimAuthentication.class)

})
public class DimCompany {

	@Id
	private Integer id;

	@Column(name = "company_name")
	private String companyName;

	/**
	 * It represents total revenueLoss. There is no column is DB table for this
	 * attribute. It is derived from ResultSet
	 */
	private Double revenueLoss;
	/**
	 * It represents entityName(Buyer/Seller). There is no column is DB table
	 * for this attribute. It is derived from ResultSet
	 */
	private String entityName;

	/**
	 * It represents number of suspect scan. There is no column is DB table for
	 * this attribute. It is derived from ResultSet
	 */
	private BigDecimal numberOfSuspectAuthentications;	
	/**
	 *It represents number of scan. There is no column is DB table for
	 * this attribute. It is derived from ResultSet 
	 */
	private BigDecimal totalNumberOfAuthentications;
	
	/**
	 * It represents number of products found suspect. There is no column is DB table for
	 * this attribute. It is derived from ResultSet 
	 */
	private BigInteger flaggedProducts;
	
	private BigInteger activeUsers;
	
	private String sellerCity;
	private String sellerState;
	private String sellerCountry;
	private String sellerType;
	private String sellerWebAddress;
	private String entityType;
	private String region;	
	private String timePeriod;	
	private String level;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Double getRevenueLoss() {
		return revenueLoss;
	}

	public void setRevenueLoss(Double revenueLoss) {
		this.revenueLoss = revenueLoss;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public BigDecimal getNumberOfSuspectAuthentications() {
		return numberOfSuspectAuthentications;
	}

	public void setNumberOfSuspectAuthentications(
			BigDecimal numberOfSuspectAuthentications) {
		this.numberOfSuspectAuthentications = numberOfSuspectAuthentications;
	}
	
	public BigDecimal getTotalNumberOfAuthentications() {
		return totalNumberOfAuthentications;
	}

	public void setTotalNumberOfAuthentications(
			BigDecimal totalNumberOfAuthentications) {
		this.totalNumberOfAuthentications = totalNumberOfAuthentications;
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
		return "DimCompany [id=" + id + ", companyName=" + companyName
				+ ", revenueLoss=" + revenueLoss + ", entityName=" + entityName
				+ ", numberOfSuspectAuthentications="
				+ numberOfSuspectAuthentications
				+ ", totalNumberOfAuthentications="
				+ totalNumberOfAuthentications + ", flaggedProducts="
				+ flaggedProducts + ", activeUsers=" + activeUsers
				+ ", sellerCity=" + sellerCity + ", sellerState=" + sellerState
				+ ", sellerCountry=" + sellerCountry + ", sellerType="
				+ sellerType + ", sellerWebAddress=" + sellerWebAddress
				+ ", entityType=" + entityType + ", region=" + region
				+ ", timePeriod=" + timePeriod + ", level=" + level + "]";
	}

	
	
	
}
