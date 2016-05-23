/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   UserServiceImpl.java
 ** Version     :   1.0
 ** Description :   Service class implementation of UserService Interface to server the user controller and user related use cases.
 **
 ** Author      :   Rajesh Parab,Narayan Singh,Paratha Bhowmick
 ** Created Date :  Tuesday, 05 May 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.saas.services.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vpa.core.mes.dao.TokenDao;
import com.vpa.core.mes.dao.UserDao;
import com.vpa.core.models.Token;
import com.vpa.core.models.Token.VerificationTokenType;
import com.vpa.core.models.User;
import com.vpa.core.services.EmailSender;
import com.vpa.core.utils.VPASaaSConstant;
import com.vpa.core.utils.VPASaaSUtil;
import com.vpa.saas.dto.UserDTO;
import com.vpa.saas.services.TokenService;

@Service
@PropertySources({
		@PropertySource("classpath:vpa_saas_email_${vpa.saas.profile:dev}.properties"),
		@PropertySource("classpath:message.properties") })
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
public class TokenServiceImpl implements TokenService {

	private static final Logger LOG = Logger.getLogger(TokenServiceImpl.class);

	@Autowired
	private Environment env;

	@Autowired
	private UserDao userDao;

	@Autowired
	private TokenDao tokenDao;

	@Autowired
	EmailSender emailSender;

	@Autowired
	private VPASaaSUtil vpaUtil;

	//@Value("${vpa.core.token.valid.duration}")
	//temporary work around
	private Integer thresholdTime=4320;

	@Override
	public UserDTO regenerateTokenToActivate(String emailAddress) {
		String userRegistrationPending = env
				.getProperty("vpa.saas.user.regeneration.activate");
		UserDTO userDTO = new UserDTO();
		User user = userDao.findInactiveUserByMail(emailAddress);

		if (user != null) {
			Token token = new Token(user,
					Token.VerificationTokenType.emailRegistration);
			token = tokenDao.saveAndFlush(token);

			String encodeToken = vpaUtil.encodeString(token.getTokenValue());

			emailSender.sendmailWithToken(user, encodeToken,
					userRegistrationPending, "activate");
			LOG.info(" encoded Token generated for user registration Retry ::  "
					+ encodeToken);
			userDTO.setSuccessNotification("SUCCESS");
			userDTO.setSuccessMessage("Email verification link has been sent to your email address.");
		} else {
			userDTO.setSuccessNotification("FAILED");
			userDTO.setSuccessMessage("This email is not registered. Please provide the email address used to register the account");
		}
		return userDTO;

	}

	/**
	 * Check if token is valid or not expired.
	 * 
	 * @param user
	 * @param inactiveUser
	 */
	@Override
	public void validateToken(UserDTO user, User inactiveUser) {
		String decodeToken = vpaUtil.decodeString(user.getTokenId());
		LOG.info(" decoded Token generated ::  " + decodeToken);
		Token token = tokenDao.findTokenForUser(user.getId(), decodeToken);
		if (token != null) {
			if (isTokenValid(token.getCreatedDate())) {
				token.setEnable(VPASaaSConstant.FALSE);
				inactiveUser.setEnable(VPASaaSConstant.TRUE);
				tokenDao.saveAndFlush(token);
				user.setTokenExpired(false);
			} else {
				user.setTokenExpired(true);
			}
		} else {
			user.setTokenExpired(true);

		}
	}

	/**
	 * This method is used to validate is the token send in email link is valid
	 * or not expiry date should be greater than today dates
	 * 
	 * @param createdDate
	 * @return
	 */
	private boolean isTokenValid(Date createdDate) {
		LOG.info(" Threshhold time as given in properties file::  "
				+ thresholdTime);
		DateTime registerDate = new DateTime(createdDate);
		DateTime expiryDate = registerDate.plusMinutes(thresholdTime);
		int isValidToken = DateTimeComparator.getDateOnlyInstance().compare(
				null, expiryDate);
		if (isValidToken >= 0) {
			return false;
		}

		return true;

	}

	@Override
	public Token prepareTokenForRegistration(User user) {
		return prepareToken(user,
				Token.VerificationTokenType.emailRegistration);
	}

	@Override
	public Token prepareTokenForForgetPassword(User user) {
			return prepareToken(user,
				Token.VerificationTokenType.forgetPassword);
	}

	private Token prepareToken(User user, VerificationTokenType tokenType) {
		Token token = new Token(user, tokenType);
		token = tokenDao.saveAndFlush(token);
		return token;
	}

}
