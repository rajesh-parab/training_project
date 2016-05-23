/*************************************************************************************************************
 **    Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 ** Module : VPA-SaaS
 ** File :      BusinessUserDTO.java
 ** Version : 1.0
 ** Description :  
 ** Author :        Rajesh Parab
 ** Created Date : Tuesday, 05 May 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.saas.dto;

import java.util.Collections;
import java.util.Map;

import com.vpa.core.utils.VPASaaSConstant;

public class BusinessUserDTO extends BaseDTO {

	private CompanyDTO company = new CompanyDTO();
	
	private Map<String, BrandDTO> brandProductMapping = Collections.emptyMap();
	

	private byte enable = VPASaaSConstant.TRUE;;
	
	public byte getEnable() {
		return enable;
	}

	public void setEnable(byte enable) {
		this.enable = enable;
	}

	
	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}
	

	public Map<String, BrandDTO> getBrandProductMapping() {
		return brandProductMapping;
	}

	public void setBrandProductMapping(Map<String, BrandDTO> brandProductMapping) {
		this.brandProductMapping = brandProductMapping;
	}
  

}
