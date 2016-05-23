package com.vpa.core.models;



 


public class ProductAgainstBrand {

	private Long brandId;
	
	private String brandName;
	
	private Long productId;
	
	private String productName;
	
	public ProductAgainstBrand() {
	 
	}

	 
	public ProductAgainstBrand(Long brandId, String brandName, Long productId,
			String productName) {
	 
		this.brandId = brandId;
		this.brandName = brandName;
		this.productId = productId;
		this.productName = productName;
	}


	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
	
	
	
	
}
