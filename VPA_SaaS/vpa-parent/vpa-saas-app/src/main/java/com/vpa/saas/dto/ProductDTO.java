package com.vpa.saas.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class ProductDTO {

	private Long id;

	private Date createdDate;

	private String description;

	private byte enable;

	private Double glp;

	private Date glpUpdateDate;

	private Byte isCompromised = 0;

	private Byte isProtected;

	private String name;

	private String productImageKey;

	private Long levelId;

	private String productIdLabelLocation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte getEnable() {
		return enable;
	}

	public void setEnable(byte enable) {
		this.enable = enable;
	}

	public Double getGlp() {
		return glp;
	}

	public void setGlp(Double glp) {
		this.glp = glp;
	}

	public Date getGlpUpdateDate() {
		return glpUpdateDate;
	}

	public void setGlpUpdateDate(Date glpUpdateDate) {
		this.glpUpdateDate = glpUpdateDate;
	}

	public Byte getIsCompromised() {
		return isCompromised;
	}

	public void setIsCompromised(Byte isCompromised) {
		this.isCompromised = isCompromised;
	}

	public Byte getIsProtected() {
		return isProtected;
	}

	public void setIsProtected(Byte isProtected) {
		this.isProtected = isProtected;
	}

	public String getName() {
		return (name == null) ? "" : name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductImageKey() {
		return productImageKey;
	}

	public void setProductImageKey(String productImageKey) {
		this.productImageKey = productImageKey;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	public String getProductIdLabelLocation() {
		return (productIdLabelLocation == null) ? "" : productIdLabelLocation
				.trim();
	}

	public void setProductIdLabelLocation(String productIdLabelLocation) {
		this.productIdLabelLocation = productIdLabelLocation;
	}

	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", createdDate=" + createdDate
				+ ", description=" + description + ", enable=" + enable
				+ ", glp=" + glp + ", glpUpdateDate=" + glpUpdateDate
				+ ", isCompromised=" + isCompromised + ", isProtected="
				+ isProtected + ", name=" + name + ", productImageKey="
				+ productImageKey + ", productIdLabelLocation="
				+ productIdLabelLocation + "]";
	}

}
