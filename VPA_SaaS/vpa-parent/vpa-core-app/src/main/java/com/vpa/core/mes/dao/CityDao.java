/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module : VPA-Core
 ** File :   CityDao.java
 ** Version : 1.0
 ** Description :  Dao to return data from city table based on various criteria.
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

import com.vpa.core.models.City;
import com.vpa.core.models.LabelValueBean;

@Repository
public interface CityDao extends JpaRepository<City, Long> {
      
	/**
	 * Return city object based on given city name irrespective of case.
	 * 
	 * @param cityName  Complete name of the city
	 * @return City entity class for given city name mapped to city table.
	 */
	List<City> findByNameIgnoringCase(String cityName);
	 
	/**
	 * Return id and names from city database using like operator. Caller to
	 * suffix % while passing parameter. example.
	 * findAutoSelectCityNames("In%");
	 * 
	 * @param alphabets  starting alphabets of city name to find with.
	 * @return id and names of the city starts with alphabets character  passed.
	 */
	@Query("select new com.vpa.core.models.LabelValueBean(c.id,c.name) from City c where c.enable = 1 and  c.name like :alphabets")
	List<LabelValueBean> findAutoSelectCityNames(@Param("alphabets") String alphabets);

	/**
	 * Fetch cities within a country that have had authentications for the given brand
	 * @param countryId
	 * @param brandId
	 * @return list of ids and names of cities
	 */
	@Query(value = "SELECT c.id, c.name FROM city c WHERE c.countryId = ?1 AND" +
			" EXISTS (SELECT * FROM scan s WHERE s.cityId = c.id AND s.brandId = ?2)",
			nativeQuery = true)
	List<Object[]> getCitiesByCountryAndBrand(int countryId, int brandId);
}
