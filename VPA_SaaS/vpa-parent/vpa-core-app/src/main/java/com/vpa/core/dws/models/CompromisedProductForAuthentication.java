package com.vpa.core.dws.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

@Entity
@Table(name = "vpadws.fact_revenue_monthly")
@SqlResultSetMapping(name = "CompromisedProductForAuthentication", entities = { @EntityResult(entityClass = CompromisedProductForAuthentication.class, fields = {}) })
public class CompromisedProductForAuthentication {

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "product_name")
	private String productName;

	private int suspectAuthentication;

	private String region;

	private String entityType;

	private String timePeriod;

	private String level;

	@Column(name = "revenue_loss")
	private int revenueLoss;

	@Column(name = "projected_revenue_loss")
	private int projectedRevenueLoss;

	@Column(name = "product_family")
	private String productFamily;

	@Column(name = "business_unit")
	private String businessUnit;

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

	public int getSuspectAuthentication() {
		return suspectAuthentication;
	}

	public void setSuspectAuthentication(int suspectAuthentication) {
		this.suspectAuthentication = suspectAuthentication;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
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

}
