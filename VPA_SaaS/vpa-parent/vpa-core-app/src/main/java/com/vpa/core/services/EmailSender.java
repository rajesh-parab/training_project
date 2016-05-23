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
package com.vpa.core.services;

import com.vpa.core.models.User;

public interface EmailSender {

	void sendmail(String emailId, String subject);

	void sendmailWithToken(User user, String token, String subject,
			String caller);

	void sendmailWithAttachment(String email, String base64Val);

	void sendErrorMail(String subject,String content);

}
