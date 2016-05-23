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

import static com.vpa.core.utils.VPASaaSConstant.PASSWORD_FIELD;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vpa.core.enums.UserTypeEnum;
import com.vpa.core.exceptions.ResourceAlreadyExistException;
import com.vpa.core.exceptions.ResourceNotFoundException;
import com.vpa.core.exceptions.ResourcePreconditionException;
import com.vpa.core.exceptions.UnauthorizedResourceException;
import com.vpa.core.exceptions.VPASaaSSystemException;
import com.vpa.core.mes.dao.EntityTypeDao;
import com.vpa.core.mes.dao.TenantDao;
import com.vpa.core.mes.dao.UserDao;
import com.vpa.core.mes.dao.UserTypeDao;
import com.vpa.core.models.Token;
import com.vpa.core.models.User;
import com.vpa.core.services.EmailSender;
import com.vpa.core.utils.VPASaaSConstant;
import com.vpa.core.utils.VPASaaSUtil;
import com.vpa.saas.dto.BrandDTO;
import com.vpa.saas.dto.TenantDTO;
import com.vpa.saas.dto.UserDTO;
import com.vpa.saas.services.TokenService;
import com.vpa.saas.services.UserService;

@Service
@PropertySources({
		@PropertySource("classpath:vpa_saas_email_${vpa.saas.profile:dev}.properties"),
		@PropertySource("classpath:message.properties") })
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
public abstract class UserServiceImpl implements UserService {

	private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);
	@Autowired
	private Environment env;

	@Autowired
	private UserDao userDao;

	@Autowired
	private TenantDao tenantDao;

	@Autowired
	private UserTypeDao userTypeDao;

	@Autowired
	private EntityTypeDao entityTypeDao;

	@Autowired
	EmailSender emailSender;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private VPASaaSUtil vpaUtil;

	// Although autowired setter method required for injecting mock

	public UserTypeDao getUserTypeDao() {
		return userTypeDao;
	}

	public EntityTypeDao getEntityTypeDao() {
		return entityTypeDao;
	}

	public TenantDao getTenantDao() {
		return tenantDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	/**
	 * This method register user in database make user inactive and send
	 * Registration email.
	 * 
	 * @param user
	 *            User DTO object
	 * @return User DTO object
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public final UserDTO registerUser(UserDTO user) {
		String errorMsg = env.getProperty("vpa.saas.user.email.already.exist");
		// JPA does not give direct IntegrityConstraintViolationException also
		// checking for exception type will
		// tightly coupled the package with layer
		User searchedUser = userDao.findByEmailAddress(user.getEmailAddress());

		if (searchedUser != null) {
			throw new ResourceAlreadyExistException(errorMsg);
		}

		User newUser = createNewUser(user);
		sendVerificationEmail(newUser);
		user.setUserType(newUser.getUserType().getId());
		user.setId(newUser.getId());
		return user;
	}

	public abstract User createNewUser(UserDTO user);

	/**
	 * This method add user in user table.
	 * 
	 * @param user
	 * @return User DTO object
	 */
	private void sendVerificationEmail(User user) {

		try {

			String subject = env.getProperty("mail.user.registartion.subject");

			Token token = tokenService.prepareTokenForRegistration(user);

			String encodeToken = vpaUtil.encodeString(token.getTokenValue());
			LOG.info(" encoded Token generated for new user registration::  "
					+ encodeToken);

			emailSender.sendmailWithToken(user, encodeToken, subject,
					"activate");

		} catch (JpaSystemException ex) {

			throw new VPASaaSSystemException(ex.getMessage(), ex);
		}

	}

	public User createUserDetails(UserDTO user) {
		User newUser = new User();
		// TODO this will come from client mobile or web app
		String encodedPassword = vpaUtil.encodePassword(user.getEmailAddress(),
				user.getPassword());
		user.setPassword(encodedPassword);
		BeanUtils.copyProperties(user, newUser, "company");
		newUser.setUserType(userTypeDao.findOne(UserTypeEnum.BUSINESS_USER
				.getId()));
		user.setEnable(VPASaaSConstant.FALSE);

		return newUser;
	}

	/**
	 * Authenticate the user using database. It encode password and check
	 * against Database.
	 * 
	 * @param email
	 *            email as an userid
	 * @param password
	 *            password
	 * @return user DTO object
	 */
	@Override
	@Transactional(readOnly = true)
	public UserDTO login(String email, String password) {
		String userDoesNotExist = env
				.getProperty("vpa.saas.user.does.not.exist");

		String encodedPassword = vpaUtil.encodePassword(email, password);
		User user = userDao.findActiveUser(email, encodedPassword);
		UserDTO validUser = new UserDTO();
		if (user == null) {
			throw new UnauthorizedResourceException(userDoesNotExist);
		}
		BeanUtils.copyProperties(user, validUser, PASSWORD_FIELD);
		validUser.setUserType(user.getUserType().getId());
		validateUser(validUser, userDoesNotExist);

		return validUser;
	}

	abstract void validateUser(UserDTO validUser, String errorMessage);

	/**
	 * Activate the unregistered user upon clicking email link. Update flag
	 * enabled in database to 1.
	 * 
	 * @param user
	 *            DTO object
	 * @return user DTO object
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public UserDTO completeRegistration(UserDTO user) {

		User inactiveUser = userDao.findInactiveUser(user.getId());

		if (inactiveUser == null) {
			user.setUserNotFound(true);
			return user;
		}

		activateUser(user, inactiveUser);
		BeanUtils.copyProperties(inactiveUser, user, PASSWORD_FIELD);
		return user;
	}

	abstract void activateUser(long userId);

	/**
	 * Send an email with encoded token registered user.
	 * 
	 * @param user
	 *            DTO object with email address
	 * @return user DTO object
	 */
	@Override
	public UserDTO forgotPassword(UserDTO user) {
		String userDoesNotExist = env
				.getProperty("vpa.saas.user.does.not.exist");
		User newUser = userDao.findActiveUser(user.getEmailAddress());
		if (newUser != null) {
			String subject = env.getProperty("mail.password.forgot.subject");
			// Token generation

			Token token = tokenService.prepareTokenForForgetPassword(newUser);

			String encodeToken = vpaUtil.encodeString(token.getTokenValue());
			LOG.info(" encoded Token generated for forgot functionality::  "
					+ encodeToken);
			emailSender.sendmailWithToken(newUser, encodeToken, subject,
					"reset");
		} else {
			throw new ResourceNotFoundException(userDoesNotExist);
		}
		BeanUtils.copyProperties(newUser, user, PASSWORD_FIELD);
		return user;

	}

	/**
	 * Change the existing user password. Request send new password along with
	 * token id. This method validate token and update
	 * 
	 * @param user
	 *            DTO object with email address and new password
	 * @return user DTO object
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public UserDTO resetPassword(UserDTO user) {
		User registeredUser = userDao.findOne(user.getId());
		String encodedPassword = vpaUtil.encodePassword(
				registeredUser.getEmailAddress(), user.getPassword());
		registeredUser.setPassword(encodedPassword);

		if (registeredUser.getEnable() == VPASaaSConstant.FALSE)
			activateUser(user, registeredUser);

		BeanUtils.copyProperties(registeredUser, user, PASSWORD_FIELD);
		return user;
	}

	private void activateUser(UserDTO user, User registeredUser) {
		tokenService.validateToken(user, registeredUser);
		if (!user.isTokenExpired()) {
			userDao.saveAndFlush(registeredUser);
			activateUser(user.getId());
		}
	}

	@Override
	public void sendImage(UserDTO user) {

		emailSender.sendmailWithAttachment(user.getEmailAddress(),
				user.getDashBoardBase64());

	}

	@Override
	public TenantDTO getTenant() {
		TenantDTO tenant = new TenantDTO();

		try {
			String tenantId = env.getProperty("vpa.tenantId");
			String brandID = env.getProperty("vpa.brandId");
			tenant.setId(Long.valueOf(tenantId));

			List<BrandDTO> brands = new ArrayList<>();

			BrandDTO brand = new BrandDTO();
			brand.setId(Long.valueOf(brandID));
			brands.add(brand);
			tenant.setBrands(brands);

		} catch (Exception e) {
			LOG.info(" error reading teant id and brand id from property", e);
		}
		return tenant;
	}

	/**
	 * @see UserService#updateUserDetails(Long)
	 */
	@Override
	public final UserDTO changeUserDetails(UserDTO userDTO) {
		String userDoesNotExist = env
				.getProperty("vpa.saas.user.does.not.exist");
		User user = userDao.findAtiveUser(userDTO.getId());
		if (user == null) {
			throw new ResourceNotFoundException(userDoesNotExist);
		}
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());

		processEmailChange(userDTO, user);
		 
		userDao.saveAndFlush(user);
		return userDTO;

	}

	private void processEmailChange(UserDTO userDTO, User user) {
		if (userDTO.isEmailChanged(user.getEmailAddress())) {

			if (userDao.findActiveUser(userDTO.getEmailAddress()) != null) {
				throw new ResourceAlreadyExistException(
						"User with this email id alradly registerd");
			}
			String encodedPassword = vpaUtil.encodePassword(
					userDTO.getEmailAddress(), userDTO.getPassword());

			if (userDTO.isPasswordEmpty()) {
				throw new ResourcePreconditionException(
						"Password is empty incomming request.");
			}
			user.setEmailAddress(userDTO.getEmailAddress());
			user.setPassword(encodedPassword);
		}
	}

	public final UserDTO updatePassword(UserDTO user) {
		User registeredUser = userDao.findOne(user.getId());
		String encodedPassword = vpaUtil.encodePassword(
				registeredUser.getEmailAddress(), user.getPassword());
		registeredUser.setPassword(encodedPassword);
		userDao.saveAndFlush(registeredUser);
		return user;
	}
}
