package com.vpa.core.bo;

public class CounterfeitEntityBO {
	private long tenantId; 
	private long brandId; 
	private int numberOfRecords; 
	private long regionId; 
	private long fromTime;
	private long toTime;
	private long entityTypeId; 
	private long layerId;
	private long countryId;
	private int pageNumber;
	private String orderBy;
	public long getTenantId() {
		return tenantId;
	}
	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}
	public long getBrandId() {
		return brandId;
	}
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}
	public int getNumberOfRecords() {
		return numberOfRecords;
	}
	public void setNumberOfRecords(int numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}
	public long getRegionId() {
		return regionId;
	}
	public void setRegionId(long regionId) {
		this.regionId = regionId;
	}
	public long getFromTime() {
		return fromTime;
	}
	public void setFromTime(long fromTime) {
		this.fromTime = fromTime;
	}
	public long getToTime() {
		return toTime;
	}
	public void setToTime(long toTime) {
		this.toTime = toTime;
	}
	public long getEntityTypeId() {
		return entityTypeId;
	}
	public void setEntityTypeId(long entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
	public long getLayerId() {
		return layerId;
	}
	public void setLayerId(long layerId) {
		this.layerId = layerId;
	}
	public long getCountryId() {
		return countryId;
	}
	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	@Override
	public String toString() {
		return "CounterfeitEntityBO [tenantId=" + tenantId + ", brandId="
				+ brandId + ", numberOfRecords=" + numberOfRecords
				+ ", regionId=" + regionId + ", fromTime=" + fromTime
				+ ", toTime=" + toTime + ", entityTypeId=" + entityTypeId
				+ ", layerId=" + layerId + ", countryId=" + countryId
				+ ", pageNumber=" + pageNumber + ", orderBy=" + orderBy + "]";
	}
	
}
