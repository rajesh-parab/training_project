/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   UserDao.java
 ** Version     :   1.0
 ** Description :   
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
package com.vpa.core.mes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vpa.core.models.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE emailAddress = ? AND password = ? AND enable = 1 ")
	User findActiveUser(String email, String password);

	@Query("SELECT u FROM User u WHERE emailAddress = ?   AND enable = 1 ")
	User findActiveUser(String email);

	User findByEmailAddress(String email);

	@Query("SELECT u FROM User u WHERE id = ? and  enable = 0")
	User findInactiveUser(long userId);

	@Query("SELECT u FROM User u WHERE id = ? and  enable = 1")
	User findAtiveUser(long userId);

	@Query("SELECT u FROM User u WHERE emailAddress = ?   AND enable = 0 ")
	User findInactiveUserByMail(String email);

}
