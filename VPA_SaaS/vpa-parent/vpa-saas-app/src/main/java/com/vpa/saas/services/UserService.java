/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   UserService.java
 ** Version     :   1.0
 ** Description :   Service interface for user related use cases.
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
package com.vpa.saas.services;

import com.vpa.saas.dto.TenantDTO;
import com.vpa.saas.dto.UserDTO;

public interface UserService {

	/**
	 * Register the user. Save the user in database.
	 * 
	 * @param user
	 *            DTO object
	 * @return user DTO object with success message.
	 */
	UserDTO registerUser(UserDTO user);

	/**
	 * Authenticate the user using database.
	 * 
	 * @param email
	 *            email as an userid
	 * @param password
	 *            password
	 * @return user DTO object
	 */
	UserDTO login(String email, String password);

	/**
	 * Activate the unregistered user upon clicking email link.
	 * 
	 * @param user
	 *            DTO object
	 * @return user DTO object
	 */
	UserDTO completeRegistration(UserDTO user);

	/**
	 * Send an email the registered user.
	 * 
	 * @param user
	 *            DTO object with email address
	 * @return user DTO object
	 */
	UserDTO forgotPassword(UserDTO user);

	/**
	 * Change the existing user password.
	 * 
	 * @param user
	 *            DTO object with email address and new password
	 * @return user DTO object
	 */
	UserDTO resetPassword(UserDTO user);
	
	/**
	 * This will simply update the password directly in database without going through the process of registratio.
	 * 
	 * @param user
	 * @return 
	 */
	public UserDTO updatePassword(UserDTO user);
	
	/**
	 * Update the user details in user table
	 * 
	 * @param UserDTO
	 */
	UserDTO changeUserDetails(UserDTO userDTO);

	/**
	 * Update the user settings changed by User.
	 * 
	 * @param user
	 * @return
	 */
	UserDTO updateUserSettings(UserDTO user);
	
	

	void sendImage(UserDTO user);

	TenantDTO getTenant();

}
