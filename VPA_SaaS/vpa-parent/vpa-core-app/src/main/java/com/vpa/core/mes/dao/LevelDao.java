/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module : VPA-Core
 ** File :   LevelDao.java
 ** Version : 1.0
 ** Description :  Dao to return data from level table based on various criteria.
 ** Author :        Rajesh Parab
 ** Created Date : Tuesday, 05 May 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.core.mes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vpa.core.models.LabelValueBean;
import com.vpa.core.models.Level;

@Repository
public interface LevelDao extends JpaRepository<Level, Long>{
    /**
     * Return all the id and names from level master table.
     * 
     * @return list of lebelvaluebean class wrapping id's and name pair of all the data in level
     */
	@Query("select new com.vpa.core.models.LabelValueBean(l.id,l.name) from Level l where l.enable = 1")
	public List<LabelValueBean> findAllLavelsIdAndNames();
}
