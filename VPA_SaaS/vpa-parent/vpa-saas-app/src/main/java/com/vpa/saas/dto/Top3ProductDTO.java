package com.vpa.saas.dto;

public class Top3ProductDTO {

	private int id;

	private String productName;

	private int revenue;

	private int suspectAuthentication;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getRevenue() {
		return revenue;
	}

	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}

	public int getSuspectAuthentication() {
		return suspectAuthentication;
	}

	public void setSuspectAuthentication(int suspectAuthentication) {
		this.suspectAuthentication = suspectAuthentication;
	}

}
