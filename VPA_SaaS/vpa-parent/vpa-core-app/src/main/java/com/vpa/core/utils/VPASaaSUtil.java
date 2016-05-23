/*************************************************************************************************************
 **    Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 ** Module : VPA-SaaS
 ** File :   VPASaaSUtil.java
 ** Version : 1.0
 ** Description : REST Controller for ............
 ** Author :        Rajesh Parab
 ** Created Date : Tuesday, 05 May 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  Friday, 16 Oct 2015 Null check added to convertStringToTimeStamp method
 **************************************************************************************************************/
package com.vpa.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

@Component
public class VPASaaSUtil {

	private static final Logger logger = Logger.getLogger(VPASaaSUtil.class);

	@Autowired
	private ShaPasswordEncoder passwordEncoder;

	@Autowired
	private SaltSource saltSource;

	public String encodePassword(String emailAddress, String password) {
		Collection<? extends GrantedAuthority> authorityList = Collections
				.emptyList();
		User userDetail = new User(emailAddress, "", authorityList);

		return passwordEncoder.encodePassword(password,
				saltSource.getSalt(userDetail));

	}

	public boolean isPasswordValid(String emailAddress, String encodedPassword,
			String password) {
		Collection<? extends GrantedAuthority> authorityList = Collections
				.emptyList();
		User userDetail = new User(emailAddress, "", authorityList);

		return passwordEncoder.isPasswordValid(encodedPassword, password,
				saltSource.getSalt(userDetail));

	}

	// encode logic using base64Utils spring framework
	public String encodeString(String token) {
		byte[] encodeByte = Base64Utils.encode(token.getBytes());
		return new String(encodeByte);
	}

	// decode logic using base64Utils spring framework
	public String decodeString(String token) {
		byte[] decodeByte = Base64Utils.decode(token.getBytes());
		return new String(decodeByte);

	}

	public Date convertStringToTimeStamp(String timeStamp) {
		Date date = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		try {
			date = dateFormat.parse(timeStamp);
		} catch (Exception e) {
			logger.error(
					"### error while parsing date in converStringToTimeStamp.",
					e);

			date = Calendar.getInstance().getTime();
		}
		return date;
	}

	public static long sumAll(List<? extends Number> list) {
		long sum = 0L;
		for (Number item : list) {
			sum = sum + item.longValue();
		}
		return sum;
	}

	public static boolean isBetween(int x, int lower, int upper) {
		return lower <= x && x <= upper;
	}

	/**
	 * reverse if BeanUtils.copyProperties.
	 * 
	 * @param source
	 *            - source object
	 * @param target
	 *            - target object
	 * @param propertiesToInclude
	 *            - property to includes. (In spring only properties to excludes
	 *            are allowed)
	 */
	public static void copyProperties(final Object source, final Object target,
			final String... propertiesToInclude) {

		final BeanWrapper src = new BeanWrapperImpl(source);
		final BeanWrapper trg = new BeanWrapperImpl(target);

		for (final String propertyName : propertiesToInclude) {
			trg.setPropertyValue(propertyName,
					src.getPropertyValue(propertyName));
		}

	}
}
