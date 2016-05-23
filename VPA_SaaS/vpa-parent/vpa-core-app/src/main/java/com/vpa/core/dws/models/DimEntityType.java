package com.vpa.core.dws.models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the dim_entity_type database table.
 * 
 */
@Entity
@Table(name = "vpadws.dim_entity_type")
@NamedQuery(name = "DimEntityType.findAll", query = "SELECT d FROM DimEntityType d")
public class DimEntityType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private byte active;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "current_flag")
	private byte currentFlag;

	@Temporal(TemporalType.DATE)
	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "entity_type_description")
	private String entityTypeDescription;

	@Column(name = "entity_type_name")
	private String entityTypeName;

	@Temporal(TemporalType.DATE)
	@Column(name = "start_date")
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_date")
	private Date updatedDate;

	public DimEntityType() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getActive() {
		return this.active;
	}

	public void setActive(byte active) {
		this.active = active;
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

	public String getEntityTypeDescription() {
		return this.entityTypeDescription;
	}

	public void setEntityTypeDescription(String entityTypeDescription) {
		this.entityTypeDescription = entityTypeDescription;
	}

	public String getEntityTypeName() {
		return this.entityTypeName;
	}

	public void setEntityTypeName(String entityTypeName) {
		this.entityTypeName = entityTypeName;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}