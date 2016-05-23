package com.vpa.saas.dto;

import java.util.Date;
import java.util.Map;
import java.util.Set;


/**
 * @author PD42694
 *
 */
public class VPAOAuth2AccessToken{

	Map additionalInformation;

	Set scope;

	OAuth2RefreshToken refreshToken;

	String tokenType;

	boolean expired;

	Date expiration;
	
	int expiresIn;

	String value;

	public Map getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(Map additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public Set getScope() {
		return scope;
	}

	public void setScope(Set scope) {
		this.scope = scope;
	}

	public OAuth2RefreshToken getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(OAuth2RefreshToken refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
		
		
}
