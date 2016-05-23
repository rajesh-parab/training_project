 
package com.vpa.saas.forms;

public class MESDataFrom {
	
	private long tenantId;
	private long brandId;
	
	private long partNumber;
	private long siteName;
	private long parentSerialNumber;
	private String productSerialNumber; 
	private String securitySerialNumber;
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
	public long getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(long partNumber) {
		this.partNumber = partNumber;
	}
	public long getSiteName() {
		return siteName;
	}
	public void setSiteName(long siteName) {
		this.siteName = siteName;
	}
	public long getParentSerialNumber() {
		return parentSerialNumber;
	}
	public void setParentSerialNumber(long parentSerialNumber) {
		this.parentSerialNumber = parentSerialNumber;
	}
	public String getProductSerialNumber() {
		return productSerialNumber;
	}
	public void setProductSerialNumber(String productSerialNumber) {
		this.productSerialNumber = productSerialNumber;
	}
	public String getSecuritySerialNumber() {
		return securitySerialNumber;
	}
	public void setSecuritySerialNumber(String securitySerialNumber) {
		this.securitySerialNumber = securitySerialNumber;
	}
	@Override
	public String toString() {
		return "MESDataFrom [tenantId=" + tenantId + ", brandId=" + brandId
				+ ", partNumber=" + partNumber + ", siteName=" + siteName
				+ ", parentSerialNumber=" + parentSerialNumber
				+ ", productSerialNumber=" + productSerialNumber
				+ ", securitySerialNumber=" + securitySerialNumber + "]";
	}
	
}
 
 
