/*************************************************************************************************************
** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module : VPA-Core
 ** File :   CompanyDao.java
 ** Version : 1.0
 ** Description :  Dao to return data from company table based on various criteria.
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

import com.vpa.core.models.Company;
import com.vpa.core.models.LabelValueBean;

@Repository
public interface CompanyDao extends JpaRepository<Company, Long> {
    /**
     * Return company object based on given company name irrespective of case..
     * 
     * @param companyName  Complete name of the company
     * @return company entity class for given company name mapped to company table.
     */
	Company findByNameIgnoringCase(String companyName);
	 
	/**
	 * Search company based on country,sate,city and street
	 * 
	 * @param companyName
	 * @param country
	 * @param state
	 * @param city
	 * @param street
	 * @return
	 */
	@Query("select c from Company c where upper(c.name)=upper(?) and upper(c.country)=upper(?)  and upper(c.state)=upper(?)  and upper(c.city)=upper(?)  and upper(c.street)=upper(?)")
	Company findCompany(String companyName,String country,String state,String city,String street);
    /**
     * Return id and names from company database using like operator. Caller to suffix % while passing parameter.
     * example.  findAutoSelectCompanyNames("Ame%");
     * 
     * @param  starting alphabets of company name to find with.
     * @return id and names of the company starts with alphabets character passed.
     */
	@Query("select new com.vpa.core.models.LabelValueBean(c.id,c.name) from Company c where c.enable = 1 and  c.name like :alphabets")
	List<LabelValueBean> findAutoSelectCompanyNames(@Param("alphabets") String alphabets);

}
