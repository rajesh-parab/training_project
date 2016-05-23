/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module : VPA-Core
 ** File :   CountryDao.java
 ** Version : 1.0
 ** Description :  Dao to return data from country table based on various criteria.
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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vpa.core.models.CountryMaster;
import com.vpa.core.models.LabelValueBean;

@Repository
public interface CountryMasterDao extends JpaRepository<CountryMaster, Long> {
	 
    /**
     * Find the country by ISO alpha 2 country code.
     * 
     * @param isoCountryCode
     * @return
     */
	CountryMaster findByIsoCodeIgnoringCase(String isoCountryCode);
	
	/**
	 * Return id and names from country database using like operator. Caller to
	 * suffix % while passing parameter. example.
	 * findAutoSelectCountryNames("In%");
	 * 
	 * @param alphabets  starting alphabets of country name to find with.
	 * @return id and names of the country starts with alphabets character  passed.
	 */
	@Query("select new com.vpa.core.models.LabelValueBean(cm.id,cm.name) from CountryMaster cm where cm.enable = 1 and  cm.name like :alphabets")
	List<LabelValueBean> findAutoSelectCountryNames(@Param("alphabets") String alphabets);

}
