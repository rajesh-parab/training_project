/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module       :   VPA-SaaS
 ** File         :   EnumsTest.java
 ** Version      :   1.0
 ** Description  :  Test cases for all the enums used in this project. This test will check if they match with values and id.
 **
 ** Author       :   Rajesh Parab
 ** Created Date :  Tuesday, 18 Aug 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.saas.dto;

 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpa.core.enums.UserEnteredKeyType;
import com.vpa.core.mes.dao.UserEnteredKeyDao;
import com.vpa.core.models.UserEnteredKey;
import com.vpa.saas.services.AbstractVPASaaSTest;

/*
 * This test case test all the enums against database. Enums are mapped with master tables.
 * for example if master table userenteredkey  has columns called id and key and one row with key  SECURITY_SERIAL_NUMBER with id 1 
 * and 2nd row with  key PRODUCT_SERIAL_NUMBER with id 2 
 * then in enum it is declared SECURITY_SERIAL_NUMBER(1), PRODUCT_SERIAL_NUMBER(2)
 * numbers should be matched with primary key most of the time.
 * name should be match with any varchar table usually a columns like name , key etc.
 * If some one change/delete/insert database values and developer forgot to add / chang enum then this test case will detect.
 * 
 */

public class EnumsTest extends AbstractVPASaaSTest {
    @Autowired
	private UserEnteredKeyDao userEnteredKeyDao;

	@Test
	public void userEnteredKeyType() {
		List<UserEnteredKey> userEnteredKeys = userEnteredKeyDao.findAll();
	
		for(UserEnteredKey userEnteredKey : userEnteredKeys){
			UserEnteredKeyType type=UserEnteredKeyType.getType(userEnteredKey.getId());
			assertTrue(userEnteredKey.getId()==type.getId());

			  assertEquals(userEnteredKey.getKey(),type.name());

		}
		
		
	}

}
