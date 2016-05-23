/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module : VPA-Core
 ** File :   SellerDao.java
 ** Version : 1.0
 ** Description :  Dao to return data from seller table based on various criteria.
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

import com.vpa.core.models.LabelValueBean;
import com.vpa.core.models.Seller;

@Repository
public interface SellerDao extends JpaRepository<Seller, Long> {

	/**
	 * Return seller object based on given seller name irrespective of case.
	 * 
	 * @param sellerName
	 *            Complete name of the seller
	 * @return Seller entity class for given seller name mapped to seller table.
	 */
	Seller findByNameIgnoringCase(String sellerName);

	/**
	 * Return id and names from seller database using like operator. Caller to
	 * suffix % while passing parameter. example.
	 * findAutoSelectSellerName("In%");
	 * 
	 * @param alphabets  starting alphabets of seller name to find with.
	 * @return id and names of the seller starts with alphabets character  passed.
	 */
	@Query("select new com.vpa.core.models.LabelValueBean(s.id,s.name) from Seller s where s.enable = 1 and s.sellerType = 'PHYSICAL STORE' and  s.name like :alphabets")
	// @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true")
	// , @QueryHint(name = "org.hibernate.cacheMode", value ="NORMAL") })
	// @Cacheable(value="vpaSaasCache")
	List<LabelValueBean> findAutoSelectSellerName(@Param("alphabets") String alphabets);

	/**
	 * Return id and names from seller database using like operator. Caller to
	 * suffix % while passing parameter. example.
	 * findAutoSelectSellerName("In%");
	 * 
	 * @param alphabets  starting alphabets of seller name to find with.
	 * @return id and names of the online seller starts with alphabets character  passed.
	 */
	@Query("select new com.vpa.core.models.LabelValueBean(s.id,s.name) from Seller s where s.enable = 1 and s.sellerType = 'ONLINE' and  s.name like :alphabets")
	List<LabelValueBean> findAutoSelectOnlineSellerName(@Param("alphabets") String alphabets);

}
