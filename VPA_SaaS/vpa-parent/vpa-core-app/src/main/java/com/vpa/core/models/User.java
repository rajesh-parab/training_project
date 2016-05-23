/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   User.java
 ** Version     :   1.0
 ** Description :   Model class  
 **
 ** Author      :   Rajesh Parab 
 ** Created Date :  Tuesday, 05 May 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.core.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.vpa.core.enums.UserTypeEnum;

/**
 * The persistent class for the users database table.
 * 
 */

@Entity
@Table(name = "users")
public class User extends BaseModel {

	private String emailAddress;

	private String firstName;

	private String lastName;

	private String password;

	private Byte accountNonExpired;

	private Byte accountNonLocked;

	private Byte credentialsNonExpired;

	// bi-directional many-to-one association to Scan
	@OneToMany(mappedBy = "user")
	private List<Scan> scans;

	// bi-directional many-to-one association to Tocken
	@OneToMany(mappedBy = "user")
	private List<Token> tokens;

	// bi-directional many-to-one association to UserType
	@ManyToOne
	@JoinColumn(name = "userTypeId")
	private UserType userType;

	public User() {
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Scan> getScans() {
		return this.scans;
	}

	public void setScans(List<Scan> scans) {
		this.scans = scans;
	}

	public Scan addScan(Scan scan) {
		getScans().add(scan);
		scan.setUser(this);

		return scan;
	}

	public Scan removeScan(Scan scan) {
		getScans().remove(scan);
		scan.setUser(null);

		return scan;
	}

	public List<Token> getTokens() {
		return this.tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	public Token addToken(Token token) {
		getTokens().add(token);
		token.setUser(this);

		return token;
	}

	public Token removeToken(Token token) {
		getTokens().remove(token);
		token.setUser(null);
		return token;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Byte getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Byte accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Byte getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Byte accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Byte getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Byte credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isBusinessUser() {
		return (this.userType.getId()==UserTypeEnum.BUSINESS_USER.getId());
		 
	}

}