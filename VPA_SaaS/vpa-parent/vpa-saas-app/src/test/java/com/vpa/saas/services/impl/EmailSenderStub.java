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
package com.vpa.saas.services.impl;

import com.vpa.core.models.User;
import com.vpa.core.services.EmailSender;

public class EmailSenderStub implements EmailSender {

	@Override
	public void sendmail(String emailId, String subject) {
		// stub no  implementation use forjunit testing
	}

	@Override
	public void sendmailWithToken(User user, String token, String subject,
			String caller) {
		// no implementation use for junit testing only
	}

	@Override
	public void sendmailWithAttachment(String email, String base64Val) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendErrorMail(String subject, String content) {
		// TODO Auto-generated method stub
		
	}

}
