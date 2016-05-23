
/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   LabelValueBean.java
 ** Version     :   1.0
 ** Description :   Bean which represent select box on UI contains id and label. Idea is based on struts framework.
 **
 ** Author      :   Rajesh Parab 
 ** Created Date :  Monday, 07 Sep 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.core.models;

public class LabelValueBean {

	private Long id;

	private String name;
	
	public LabelValueBean(){
		
	}

	public LabelValueBean(int id, String name) {
		Long l = new Long(id);
		this.id = l;
		this.name = name;
	} 
	
	public LabelValueBean(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setLabel(String name) {
		this.name = name;
	}

}

