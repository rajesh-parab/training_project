package com.vpa.core.dws.models;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The persistent class for the dim_product database table.
 * 
 */
@Entity
@Table(name = "vpadws.dim_product")
public class DimProduct{

	@Id
	private Long id;

	private byte active;

	private byte compromised;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "end_date")
	private Date endDate;

	private double glp;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "glp_update_date")
	private Date glpUpdateDate;

	@Column(name = "level_id")
	private String levelId;

	@Column(name = "product_name")
	private String product;

	@Column(name = "product_description")
	private String productDescription;

	@Column(name = "protected")
	private byte protectedProduct;

	@Temporal(TemporalType.DATE)
	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "tenant_id")
	private int tenantId;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_date")
	private Date updatedDate;

	public DimProduct() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte getActive() {
		return this.active;
	}

	public void setActive(byte active) {
		this.active = active;
	}

	public byte getCompromised() {
		return this.compromised;
	}

	public void setCompromised(byte compromised) {
		this.compromised = compromised;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public double getGlp() {
		return this.glp;
	}

	public void setGlp(double glp) {
		this.glp = glp;
	}

	public Date getGlpUpdateDate() {
		return this.glpUpdateDate;
	}

	public void setGlpUpdateDate(Date glpUpdateDate) {
		this.glpUpdateDate = glpUpdateDate;
	}

	public String getLevelId() {
		return this.levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getProduct() {
		return this.product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getProductDescription() {
		return this.productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public byte getProtectedProduct() {
		return protectedProduct;
	}

	public void setProtectedProduct(byte protectedProduct) {
		this.protectedProduct = protectedProduct;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "DimProduct [id=" + id + ", active=" + active + ", compromised="
				+ compromised + ", createdDate=" + createdDate + ", endDate="
				+ endDate + ", glp=" + glp + ", product=" + product
				+ ", productDescription=" + productDescription
				+ ", protectedProduct="
				+ protectedProduct+ ", tenantId=" + tenantId + "]";
	}

}