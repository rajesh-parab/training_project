package com.vpa.core.dws.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the fact_revenue_monthly database table.
 * 
 */
@Entity
@Table(name = "vpadws.fact_revenue_monthly")
@NamedQuery(name = "RevenueMonthly.findAll", query = "SELECT f FROM RevenueMonthly f")
public class RevenueMonthly {

	@Id
	private int id;

	@Column(name = "brand_id")
	private int brandId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "fiscal_month_num")
	private int fiscalMonthNum;

	@Temporal(TemporalType.DATE)
	@Column(name = "month_end_date")
	private Date monthEndDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "month_start_date")
	private Date monthStartDate;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private DimProduct dimProduct;

	@Column(name = "region_id")
	private int regionId;

	private double revenue;

	@Column(name = "tenant_id")
	private int tenantId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_date")
	private Date updatedDate;

	public RevenueMonthly() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBrandId() {
		return this.brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getFiscalMonthNum() {
		return this.fiscalMonthNum;
	}

	public void setFiscalMonthNum(int fiscalMonthNum) {
		this.fiscalMonthNum = fiscalMonthNum;
	}

	public Date getMonthEndDate() {
		return this.monthEndDate;
	}

	public void setMonthEndDate(Date monthEndDate) {
		this.monthEndDate = monthEndDate;
	}

	public Date getMonthStartDate() {
		return this.monthStartDate;
	}

	public void setMonthStartDate(Date monthStartDate) {
		this.monthStartDate = monthStartDate;
	}

	public DimProduct getDimProduct() {
		return dimProduct;
	}

	public void setDimProduct(DimProduct dimProduct) {
		this.dimProduct = dimProduct;
	}

	public int getRegionId() {
		return this.regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public double getRevenue() {
		return this.revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
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

}