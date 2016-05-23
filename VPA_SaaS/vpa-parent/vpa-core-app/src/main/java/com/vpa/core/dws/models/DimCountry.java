package com.vpa.core.dws.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the dim_country database table.
 * 
 */
@Entity
@Table(name="vpadws.dim_country")
public class DimCountry  {

	@Id
	private Long id;

	private byte active;

	@Column(name="country_description")
	private String countryDescription;

	@Column(name="country_name")
	private String countryName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="current_flag")
	private byte currentFlag;

	@Temporal(TemporalType.DATE)
	@Column(name="end_date")
	private Date endDate;

	@Column(name="region_id")
	private int regionId;

	@Temporal(TemporalType.DATE)
	@Column(name="start_date")
	private Date startDate;

	@Column(name="sub_region_id")
	private int subRegionId;

	@Column(name="sub_region_name")
	private String subRegionName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_date")
	private Date updatedDate;

	public DimCountry() {
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

	public String getCountryDescription() {
		return this.countryDescription;
	}

	public void setCountryDescription(String countryDescription) {
		this.countryDescription = countryDescription;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public byte getCurrentFlag() {
		return this.currentFlag;
	}

	public void setCurrentFlag(byte currentFlag) {
		this.currentFlag = currentFlag;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getRegionId() {
		return this.regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getSubRegionId() {
		return this.subRegionId;
	}

	public void setSubRegionId(int subRegionId) {
		this.subRegionId = subRegionId;
	}

	public String getSubRegionName() {
		return this.subRegionName;
	}

	public void setSubRegionName(String subRegionName) {
		this.subRegionName = subRegionName;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}