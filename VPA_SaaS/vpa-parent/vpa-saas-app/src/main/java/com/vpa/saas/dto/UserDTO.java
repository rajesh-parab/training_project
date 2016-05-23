/*************************************************************************************************************
 **    Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 ** Module : VPA-SaaS
 ** File :      UserDTO.java
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

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.vpa.core.utils.VPASaaSConstant;

@JsonInclude(Include.NON_EMPTY)
public class UserDTO extends BaseDTO {

	private Long id;

	private String emailAddress;
 
	private byte enable = VPASaaSConstant.TRUE;;
	 
	private String firstName;

	private String lastName;

	private Long userType;

	// password policy @Pattern(regexp = "{A-Za-z0-9}*")
	private String password;

	private String tokenId;

	private boolean isTokenExpired;

	private boolean isUserNotFound;

	private String currentTimeStamp;

	private String dashBoardBase64;

	private BusinessUserDTO businessUser = new BusinessUserDTO();

	private BrandOwnerUserDTO brandOwnerUser = new BrandOwnerUserDTO();

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {

		this.emailAddress = emailAddress;
	}

	public byte getEnable() {
		return enable;
	}

	public void setEnable(byte enable) {
		this.enable = enable;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public Long getUserType() {
		return userType;
	}

	public void setUserType(Long userType) {
		this.userType = userType;
	}

	public boolean isTokenExpired() {
		return isTokenExpired;
	}

	public void setTokenExpired(boolean isTokenExpired) {
		this.isTokenExpired = isTokenExpired;
	}

	public boolean isUserNotFound() {
		return isUserNotFound;
	}

	public void setUserNotFound(boolean isUserNotFound) {
		this.isUserNotFound = isUserNotFound;
	}

	public String getCurrentTimeStamp() {
		return currentTimeStamp;
	}

	public void setCurrentTimeStamp(String currentTimeStamp) {
		this.currentTimeStamp = currentTimeStamp;
	}

	public String getDashBoardBase64() {
		return dashBoardBase64;
	}

	public void setDashBoardBase64(String dashBoardBase64) {
		this.dashBoardBase64 = dashBoardBase64;
	}

	public BusinessUserDTO getBusinessUser() {
		return businessUser;
	}

	public void setBusinessUser(BusinessUserDTO businessUser) {
		this.businessUser = businessUser;
	}

	public BrandOwnerUserDTO getBrandOwnerUser() {
		return brandOwnerUser;
	}

	public void setBrandOwnerUser(BrandOwnerUserDTO brandOwnerUser) {
		this.brandOwnerUser = brandOwnerUser;
	}

	public boolean isEmailChanged(String emailAddress) {
		if (StringUtils.hasText(emailAddress)) {
			return !this.emailAddress.equals(emailAddress);
		}
		return false;
	}

	public boolean isPasswordEmpty() {
		if (!StringUtils.hasText(password)) {
			return true;
		}
		return false;
	}

	public boolean isPasswordChange(String password) {
		return (password.equals(this.password));

	}

}
