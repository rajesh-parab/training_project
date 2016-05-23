 
package com.vpa.core.bo;

public class MESBO {
	private long tenantId;
	private long brandId;
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
	@Override
	public String toString() {
		return "MESBO [tenantId=" + tenantId + ", brandId=" + brandId + "]";
	}
	
	
}
 
 
