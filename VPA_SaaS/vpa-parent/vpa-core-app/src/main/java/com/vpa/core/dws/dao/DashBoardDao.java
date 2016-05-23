/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   DashBoardDao.java
 ** Version     :   1.0
 ** Description :   DAO interface for compromised Product  .
 **
 ** Author      :   Narayan Singh
 ** Created Date :  Friday, 19 June 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.core.dws.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vpa.core.dws.models.DimProduct;

@Repository
public interface DashBoardDao extends JpaRepository<DimProduct, Long> {

	   

}
