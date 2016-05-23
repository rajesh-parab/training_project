 
package com.vpa.core.bo;

public class AuthenticationTop3CountriesResponseBO {
	private String countryName;
	private int noOfEntities;
	private int authenticationNumber;

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public int getAuthenticationNumber() {
		return authenticationNumber;
	}

	public void setAuthenticationNumber(int authenticationNumber) {
		this.authenticationNumber = authenticationNumber;
	}

	public int getNoOfEntities() {
		return noOfEntities;
	}

	public void setNoOfEntities(int noOfEntities) {
		this.noOfEntities = noOfEntities;
	}

}
 