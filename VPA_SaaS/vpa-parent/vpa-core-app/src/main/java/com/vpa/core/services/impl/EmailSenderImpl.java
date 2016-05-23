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
package com.vpa.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.vpa.core.exceptions.VPASaaSSystemException;
import com.vpa.core.models.User;
import com.vpa.core.services.EmailSender;

public class EmailSenderImpl implements EmailSender, InitializingBean {

	@Autowired
	private Environment env;

	MimeMessage mimeMessage;
	MimeMessageHelper helper;
	private JavaMailSenderImpl mailSender;

	private String host;
	private String port;
	private String username;
	private String password;
	private String transportprotocol;
	private String smtpAuth;
	private String starttlsEnable;
	private String from;
	private String subject;
	private String htmlEmailMessage;
	private String htmlEmailMessageType;
	private String starttlsRequired;
	private String multipleUser;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTransportprotocol() {
		return transportprotocol;
	}

	public void setTransportprotocol(String transportprotocol) {
		this.transportprotocol = transportprotocol;
	}

	public String getSmtpAuth() {
		return smtpAuth;
	}

	public void setSmtpAuth(String smtpAuth) {
		this.smtpAuth = smtpAuth;
	}

	public String getStarttlsEnable() {
		return starttlsEnable;
	}

	public void setStarttlsEnable(String starttlsEnable) {
		this.starttlsEnable = starttlsEnable;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getHtmlEmailMessage() {
		return htmlEmailMessage;
	}

	public void setHtmlEmailMessage(String htmlEmailMessage) {
		this.htmlEmailMessage = htmlEmailMessage;
	}

	public String getHtmlEmailMessageType() {
		return htmlEmailMessageType;
	}

	public void setHtmlEmailMessageType(String htmlEmailMessageType) {
		this.htmlEmailMessageType = htmlEmailMessageType;
	}

	public String getStarttlsRequired() {
		return starttlsRequired;
	}

	public void setStarttlsRequired(String starttlsRequired) {
		this.starttlsRequired = starttlsRequired;
	}

	public String getMultipleUser() {
		return multipleUser;
	}

	public void setMultipleUser(String multipleUser) {
		this.multipleUser = multipleUser;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		mailSender = new JavaMailSenderImpl();
		mailSender.setHost(getHost());
		mailSender.setPort(Integer.parseInt(getPort()));
		mailSender.setUsername(getUsername());
		mailSender.setPassword(getPassword());
		mailSender.setProtocol(getTransportprotocol());
		Properties javaMailProperties = System.getProperties();
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.smtp.port", getPort());
		javaMailProperties.put("mail.smtp.auth", getSmtpAuth());
		javaMailProperties
				.put("mail.smtp.starttls.enable", getStarttlsEnable());
		javaMailProperties.put("mail.smtp.starttls.required", "true");
		mailSender.setJavaMailProperties(javaMailProperties);
		mimeMessage = mailSender.createMimeMessage();
		helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
		helper.setSubject(getSubject());
		helper.setFrom(getFrom());
		mimeMessage
				.setContent(getHtmlEmailMessage(), getHtmlEmailMessageType());

	}

	@Override
	public void sendmail(String emailId, String subject) {
		try {
			helper.setTo(emailId);
			helper.setSubject(subject);
		} catch (MessagingException ex) {

			throw new VPASaaSSystemException(ex.getMessage(), ex);
		}

		mailSender.send(mimeMessage);

	}

	@Override
	public void sendmailWithToken(User user, String token, String subject,
			String caller) {
		// TODO this decision should be polymorphic and not based on the if else
		try {

			StringBuilder message = new StringBuilder();
			if (!"activate".equals(caller)) {
				message.append("<a href='VPAMobile://");
				message.append(caller);
				message.append("%20");
				message.append(user.getId());
				message.append("%20");
				message.append(token);
				message.append("'><button type='button'>Try this for mobile</button></a>");
			}
			message.append("<br>");
			message.append("<br>");
			if ("activate".equals(caller)) {
				message.append(getHtmlEmailMessage());
			} else {
				message.append(env.getProperty("mail.web.forgot.pass.link"));
			}

			message.append("business/");

			message.append(caller);
			message.append("/");
			message.append(user.getId());
			message.append("/");
			message.append(token);
			message.append("'>Try this for web</a>");

			mimeMessage.setContent(message.toString(),
					getHtmlEmailMessageType());

			helper.setTo(user.getEmailAddress());
			helper.setSubject(subject);
		} catch (MessagingException ex) {
			throw new VPASaaSSystemException(ex.getMessage(), ex);
		}
		mailSender.send(mimeMessage);

	}

	@Override
	public void sendmailWithAttachment(String email, String completeImageData) {
		MimeMessage message = mailSender.createMimeMessage();
		String base64Val = completeImageData.substring(completeImageData
				.indexOf(",") + 1);
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(getFrom());
			helper.setTo(email);
			helper.setSubject("VPA DashBoard");
			helper.setText("Dear User");
			ByteArrayResource byteArrayResource = new ByteArrayResource(
					Base64.decodeBase64(base64Val));
			helper.addAttachment("DashBoardScreen.png", byteArrayResource);

		} catch (MessagingException me) {

			throw new VPASaaSSystemException(me.getMessage(), me);
		}

		mailSender.send(message);
	}

	@Override
	public void sendErrorMail(String subject, String content) {
		try {
			StringTokenizer st = new StringTokenizer(getMultipleUser(), ";");
			List<String> mailAddressList = new ArrayList<String>();
			while (st.hasMoreTokens()) {
				mailAddressList.add(st.nextToken());
			}
			String[] stockArr = new String[mailAddressList.size()];
			stockArr = mailAddressList.toArray(stockArr);
			helper.setTo(stockArr);
			helper.setSubject(subject);
			mimeMessage.setContent(content, getHtmlEmailMessageType());
		} catch (MessagingException ex) {

			throw new VPASaaSSystemException(ex.getMessage(), ex);
		}

		mailSender.send(mimeMessage);

	}

}
