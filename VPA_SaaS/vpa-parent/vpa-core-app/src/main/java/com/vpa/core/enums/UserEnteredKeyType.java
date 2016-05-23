/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   UserEnteredKeyType.java
 ** Version     :   1.0
 ** Description :   Enummberation for userenteredkey table.
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
package com.vpa.core.enums;

public enum UserEnteredKeyType {
	//Note if there is a change in table design or records in userenteredkey table
	// This enum may need to change
	SECURITY_SERIAL_NUMBER(1L), PRODUCT_SERIAL_NUMBER(2L) ,ENTITY_TYPE(3L),LEVEL_ID(4L),SCANNED_IMAGE_KEY(5L),DECODED_IMAGE_KEY(6L),
	SELLER_ID(7L),SELLER_CITY_ID(8L),SELLER_COUNTRY_ID(9L),BRAND_ID(10L);

	private Long id;

	UserEnteredKeyType(Long userEnteredKey) {
		id = userEnteredKey;
	}

	public static UserEnteredKeyType getType(Long id) {

		if (id == null) {
			return null;
		}

		for (UserEnteredKeyType userEnteredKey : UserEnteredKeyType.values()) {
			if (id.equals(userEnteredKey.getId())) {
				return userEnteredKey;
			}
		}
		throw new IllegalArgumentException("No matching type for id " + id);
	}

	public Long getId() {
		return id;
	}

}
