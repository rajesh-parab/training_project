/*************************************************************************************************************
 **    Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 ** Module : VPA-SaaS
 ** File :      UserController.java
 ** Version : 1.0
 ** Description : REST Controller for ............
 ** Author :        Rajesh Parab
 ** Created Date : Tuesday, 05 May 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.core.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:vpa-core-app-test-util-config.xml")
public class VPAUtilTest {

	@Autowired 
	VPASaaSUtil vpaSaaSUtil;
 
	 
	@Test
	public void validPassword() throws Exception {

		String emailAddress = "r.parab@zensar.com";
		String password = "dummypass123";
		String encodedPassword = vpaSaaSUtil.encodePassword(emailAddress, password);
		boolean result = vpaSaaSUtil.isPasswordValid(emailAddress, encodedPassword,
				password);
		assertTrue(result);

	}

	 
	@Test
	public void validUserNameAndValidPassword() throws Exception {

		String emailAddress = "r.parab@zensar.com";
		String password = "dummypass123";
		String encodedPassword = vpaSaaSUtil.encodePassword(emailAddress, password);
		boolean result = vpaSaaSUtil.isPasswordValid(emailAddress, encodedPassword,
				password);
		assertTrue(result);
		result = vpaSaaSUtil.isPasswordValid("r.parab@tcs.com", encodedPassword,
				password);
		assertFalse(result);

	}
 
	@Test
	public void validUserNameAndInValidPassword() throws Exception {

		String emailAddress = "r.parab@zensar.com";
		String password = "dummypass123";
		String encodedPassword = vpaSaaSUtil.encodePassword(emailAddress, password);
		boolean result = vpaSaaSUtil.isPasswordValid(emailAddress, encodedPassword,
				password);
		assertTrue(result);
		result = vpaSaaSUtil.isPasswordValid(emailAddress, encodedPassword,
				"somethignelse123");
		assertFalse(result);

	}

 
	@Test
	public void sumAll() {
		LinkedMultiValueMap<String, Integer> a = new LinkedMultiValueMap<>();
		a.add("america", 12);
		a.add("america", 18);
		a.add("america", 10);
		a.add("apa", 22);
		a.add("apa", 38);
 
		Set<Entry<String, List<Integer>>> s = a.entrySet();
		for (Entry<String, List<Integer>> e : s) {
			if(e.getKey().equals("america"))
			assertTrue(vpaSaaSUtil.sumAll(e.getValue()) == 40L);
			if(e.getKey().equals("apa"))
				assertTrue(vpaSaaSUtil.sumAll(e.getValue()) == 60L);
		}

	}

}
