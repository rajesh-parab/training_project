package com.vpa.saas.dto;

public class BrandOwnerUserDTO extends BaseDTO{
	
	private TenantDTO tenant = new TenantDTO();
	
	private String designation;
	
	public TenantDTO getTenant() {
		return tenant;
	}

	public void setTenant(TenantDTO tenant) {
		this.tenant = tenant;
	}
	
	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}


}
