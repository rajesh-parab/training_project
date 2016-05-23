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
package com.vpa.saas.services;

import com.vpa.core.models.Token;
import com.vpa.core.models.User;
import com.vpa.saas.dto.UserDTO;

public interface TokenService {
	
	UserDTO regenerateTokenToActivate(String emailAddress);
	 
	void validateToken(UserDTO user, User inactiveUser);
 
	Token prepareTokenForRegistration(User user);

	Token prepareTokenForForgetPassword(User user);
	
}
