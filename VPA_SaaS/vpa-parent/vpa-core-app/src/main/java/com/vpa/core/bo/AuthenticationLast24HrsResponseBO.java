package com.vpa.core.bo;

public class AuthenticationLast24HrsResponseBO {
	
	private String authtype;
	private int noOfAuthentication;
	public String getAuthtype() {
		return authtype;
	}
	public void setAuthtype(String authtype) {
		this.authtype = authtype;
	}
	public int getNoOfAuthentication() {
		return noOfAuthentication;
	}
	public void setNoOfAuthentication(int noOfAuthentication) {
		this.noOfAuthentication = noOfAuthentication;
	}

}
