package com.vpa.saas.dto;

public class OAuth2RefreshToken {
	private String value;
	private String expiration;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getExpiration() {
		return expiration;
	}
	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}
	
	
}
