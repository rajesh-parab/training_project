/**
 * 
 */
package com.vpa.saas.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author PD42694
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class TenantDTO {

	private Long id;
	
	private String tenantName;
	
	private String logoImageName;
	
	private List<BrandDTO> brands = new ArrayList<>();

	/**
	 * Default constructor
	 */
	public TenantDTO() {
		 
	}

	/**
	 * Parameterized constructor
	 * 
	 * @param tenantId
	 * @param tenantName
	 * @param brands
	 */
	public TenantDTO(Long tenantId, String tenantName, List<BrandDTO> brands) {
		super();
		this.id = tenantId;
		this.tenantName = tenantName;
		this.brands = brands;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long tenantId) {
		this.id = tenantId;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	
	public String getLogoImageName() {
		return logoImageName;
	}

	public void setLogoImageName(String logoImageName) {
		this.logoImageName = logoImageName;
	}

	public List<BrandDTO> getBrands() {
		return brands;
	}

	public void setBrands(List<BrandDTO> brands) {
		this.brands = brands;
	}
	
	public void addBrand(BrandDTO brandDto){
		brands.add(brandDto);
	}

}
