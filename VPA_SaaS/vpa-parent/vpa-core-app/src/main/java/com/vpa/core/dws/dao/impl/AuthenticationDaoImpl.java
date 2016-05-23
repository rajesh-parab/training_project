/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   AuthenticationServiceImpl.java
 ** Version     :   1.0
 ** Description :    
 **
 ** Author      :   Paratha Bhowmick
 ** Created Date :  Monday, 3 Aug 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/

package com.vpa.core.dws.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.vpa.core.bo.AuthenticationByEntityResponseBO;
import com.vpa.core.bo.AuthenticationByLayerResponseBO;
import com.vpa.core.bo.AuthenticationByMapModuleBO;
import com.vpa.core.bo.AuthenticationDashboardAdditionalFilterBO;
import com.vpa.core.bo.AuthenticationLast24HrsResponseBO;
import com.vpa.core.bo.AuthenticationOverTimeResponseBO;
import com.vpa.core.bo.AuthenticationTop3CountriesResponseBO;
import com.vpa.core.mes.dao.AuthenticationDao;

@Repository
public class AuthenticationDaoImpl implements AuthenticationDao {

	@PersistenceContext(unitName = "vpaDWPU")
	private EntityManager em;
	
	
	
	private static final String TOP_3_COUNTRIES_ON_AUTHENTICATION="select b.country_name, count(distinct f.company_id) as no_entities, sum(f.product_units ) as suspectAuthentication from fact_authentication f "
			+ "inner join dim_country b on f.country_id=b.id "
			+" inner join dim_authentication c on f.authentication_id=c.id "
			+ "where f.tenant_id =:tenantId and f.brand_id = :brandId "; 
	
	
	private static final String AUTHENTICATION_BY_LAYER="select e.level_name, sum(f.product_units ) as suspectAuthentication from fact_authentication f "
			+ "inner join dim_level e on f.level_id=e.id "
			+ "inner join dim_country b on f.country_id=b.id "
			+ "where f.tenant_id =:tenantId and f.brand_id = :brandId";
	
	private static final String AUTHENTICATION_BY_ENTITY_TYPE="select e.id, e.entity_type_name,sum(f.product_units ) as Authentication from fact_authentication f "
			+ "inner join dim_entity_type e on f.entity_type_id=e.id "
			+ "inner join dim_country b on f.country_id=b.id "
			+ "inner join dim_authentication c on f.authentication_id=c.id "
			+ "where f.tenant_id =:tenantId and f.brand_id = :brandId ";
	
	private static final String AUTHENTICATION_BY_MAP_MODULE="select d.region_name, count(distinct f.company_id) as no_entities, sum(f.product_units ) as Authentication from fact_authentication f "
			+ "inner join dim_entity_type e on f.level_id=e.id "
			+ "inner join dim_country b on f.country_id=b.id "
			+ "inner join dim_authentication c on f.authentication_id=c.id "
			+ "inner join dim_region d on f.region_id=d.id "
			+ "where f.tenant_id =:tenantId and f.brand_id = :brandId ";
	
	private static final String AUTHENTICATION_IN_LAST_24_HRS="Select v.final_result, sum(v.authentication) as authentication from "
			+ "(select CASE when f.authentication_id=2 then 'Genuine' else 'Suspected' end as final_result, sum(f.product_units) as Authentication from fact_authentication f "
			+ "inner join dim_entity_type e on f.level_id=e.id "
			+ "inner join dim_country b on f.country_id=b.id "
			+ "inner join dim_authentication c on f.authentication_id=c.id "
			+ "inner join dim_region d on f.region_id=d.id where (f.created_date>=DATE_SUB(now(),INTERVAL 24 HOUR) or f.updated_date>=DATE_SUB(now(),INTERVAL 24 HOUR) ) and "
			+ "f.tenant_id =:tenantId and f.brand_id = :brandId";
	
	private static final String AUTHENTICATION_BY_MONTH_PART1="select a.year_number, upper(a.month_name) as month_name, a.month_number, IFNULL(sum(f.product_units),0) as no_authentication from "
			+ "(select year_number,month_name,month_number,date_sk from dim_time   where ";
	private static final String AUTHENTICATION_BY_MONTH_PART2= " a left join fact_authentication f on a.date_sk=f.time_id and  "
					+ "f.tenant_id =:tenantId and f.brand_id = :brandId"; 
	
	private static final String AUTHENTICATION_BY_DATE_PART1="select a.year_number, upper(a.month_name) as month_name, a.month_number,f.a.day_desc, IFNULL(sum(f.product_units),0) as no_authentication ";
	private static final String AUTHENTICATION_BY_DATE_PART2="from (select year_number,month_name,month_number,day_desc,date_sk from dim_time where ";
	private static final String AUTHENTICATION_BY_DATE_PART3= " a left join fact_authentication f on a.date_sk=f.time_id and  "
	+ "f.tenant_id =:tenantId and f.brand_id = :brandId";
	private static final String AUTHENTICATION_BY_DATE_PART4=" group by a.year_number, a.month_name, a.day_desc, a.month_number order by 3,1 asc ";
	
 
	
	private static final String AUTHENTICATION_BY_WEEK_PART1="select  a.year_number, concat(upper(substring(a.month_name,1,1)),lower(substring(a.month_name,2)) ) as month_name, a.month_number, a.number_of_week_in_month, a.week_date_range,  IFNULL(sum(f.product_units),0) as total_authentication from (select month_name,month_number,number_of_week_in_month,week_date_range,date_sk,year_number from dim_time  where ";
	private static final String AUTHENTICATION_BY_WEEK_PART2=" a left join fact_authentication f on a.date_sk=f.time_id and "
			+ "f.tenant_id =:tenantId and f.brand_id = :brandId";
	private static final String AUTHENTICATION_BY_WEEK_PART3=" group by a.month_name, a.number_of_week_in_month, a.month_number, a.year_number,a.week_date_range order by year_number asc,month_number asc,number_of_week_in_month asc  "; 
 
	 
	private static final String GENUINE="Genuine";
	private static final String SUSPECT="Suspect";
	private static final String PRIMARY="Primary";
	private static final String SECONDARY="Secondary";
	
	@Override
	public List<AuthenticationTop3CountriesResponseBO> top3CountriesOnAuthentication(AuthenticationDashboardAdditionalFilterBO authenticationDashboardAdditionalFilterBO ) {
		
		StringBuilder queryStr = new StringBuilder(TOP_3_COUNTRIES_ON_AUTHENTICATION);
		buildQuery(authenticationDashboardAdditionalFilterBO,queryStr);
			
		if(( null!=authenticationDashboardAdditionalFilterBO.getSuspectAuthType() )&& (authenticationDashboardAdditionalFilterBO.getSuspectAuthType().equals(PRIMARY) ||
				authenticationDashboardAdditionalFilterBO.getSuspectAuthType().equals(SECONDARY))){
			queryStr.append(" and c.level_authentication= '" + authenticationDashboardAdditionalFilterBO.getSuspectAuthType() +"'");
		}
			
		
			
		queryStr.append(" group by  b.country_name order by suspectAuthentication desc  limit 3");
		
		
		List<Object[]> result = getResultList(
				authenticationDashboardAdditionalFilterBO, queryStr);
		
		List<AuthenticationTop3CountriesResponseBO> top3CountriesList = new ArrayList<AuthenticationTop3CountriesResponseBO>();
		
		for (Object[] obj : result) {
			AuthenticationTop3CountriesResponseBO top3CountriesResponseBO = new AuthenticationTop3CountriesResponseBO();
			top3CountriesResponseBO.setCountryName((String) obj[0]);
			top3CountriesResponseBO.setNoOfEntities(Integer.parseInt(obj[1].toString()));
			top3CountriesResponseBO.setAuthenticationNumber(Integer.parseInt(obj[2].toString()));
			top3CountriesList.add(top3CountriesResponseBO);
		}
		return top3CountriesList;
		
	}


	
	@Override
	public List<AuthenticationByLayerResponseBO> authenticationByLayer(AuthenticationDashboardAdditionalFilterBO authenticationDashboardAdditionalFilterBO) {
		
		StringBuilder queryStr = new StringBuilder(AUTHENTICATION_BY_LAYER);
		buildQuery(authenticationDashboardAdditionalFilterBO,queryStr);
			
		queryStr.append(" group by e.level_name ");
		
		
		List<Object[]> result = getResultList(
				authenticationDashboardAdditionalFilterBO, queryStr);
		
		List<AuthenticationByLayerResponseBO> authenticationByLayerResponseBOList = new ArrayList<AuthenticationByLayerResponseBO>();
		
		for (Object[] obj : result) {
			AuthenticationByLayerResponseBO authenticationByLayerResponseBO = new AuthenticationByLayerResponseBO();
			authenticationByLayerResponseBO.setLayerName((String) obj[0]);
			authenticationByLayerResponseBO.setLayercount(Integer.parseInt(obj[1].toString()));
			authenticationByLayerResponseBOList.add(authenticationByLayerResponseBO);
		}
		return authenticationByLayerResponseBOList;
		
	}
	
	@Override
	public List<AuthenticationByEntityResponseBO> authenticationEntityType(AuthenticationDashboardAdditionalFilterBO authenticationDashboardAdditionalFilterBO) {
		StringBuilder queryStr = new StringBuilder(AUTHENTICATION_BY_ENTITY_TYPE);
		buildQuery(authenticationDashboardAdditionalFilterBO,queryStr);
				
		queryStr.append(" group by e.entity_type_name ");
		
		
		List<Object[]> result = getResultList(
				authenticationDashboardAdditionalFilterBO, queryStr);
		
		List<AuthenticationByEntityResponseBO> authenticationByEntityResponseBOList = new ArrayList<AuthenticationByEntityResponseBO>();
		
		for (Object[] obj : result) {
			AuthenticationByEntityResponseBO authenticationByEntityResponseBO = new AuthenticationByEntityResponseBO();
			authenticationByEntityResponseBO.setEntityId(Integer.parseInt(obj[0].toString()));
			authenticationByEntityResponseBO.setEntityName(((String) obj[1]));
			authenticationByEntityResponseBO.setEntityCount(Integer.parseInt(obj[2].toString()));
			authenticationByEntityResponseBOList.add(authenticationByEntityResponseBO);
		}
		return authenticationByEntityResponseBOList;
	}
	
	@Override
	public List<AuthenticationByMapModuleBO> authenticationByMapModule(AuthenticationDashboardAdditionalFilterBO authenticationDashboardAdditionalFilterBO){
		
		StringBuilder queryStr = new StringBuilder(AUTHENTICATION_BY_MAP_MODULE);
		
		buildQuery(authenticationDashboardAdditionalFilterBO,queryStr);
		queryStr.append(" group by d.region_name ");
		
		
		List<Object[]> result = getResultList(
				authenticationDashboardAdditionalFilterBO, queryStr);
		
		List<AuthenticationByMapModuleBO> authenticationByMapModuleBOList = new ArrayList<AuthenticationByMapModuleBO>();
		
		for (Object[] obj : result) {
			AuthenticationByMapModuleBO authenticationByMapModuleBO = new AuthenticationByMapModuleBO();
			authenticationByMapModuleBO.setRegionName((String) obj[0]);
			authenticationByMapModuleBO.setEntityCount(Integer.parseInt(obj[1].toString()));
			authenticationByMapModuleBO.setNoOfAuthentication(Integer.parseInt(obj[2].toString()));
			authenticationByMapModuleBOList.add(authenticationByMapModuleBO);
		}
		return authenticationByMapModuleBOList;
	}
	
	
	@Override		
	public List<AuthenticationLast24HrsResponseBO> authenticationLast24hrs(AuthenticationDashboardAdditionalFilterBO authenticationDashboardAdditionalFilterBO){
		
		StringBuilder queryStr = new StringBuilder(AUTHENTICATION_IN_LAST_24_HRS);		
		buildQuery(authenticationDashboardAdditionalFilterBO,queryStr);
		queryStr.append(" group by final_result) v group by v.final_result ");
		
		
		List<Object[]> result = getResultList(
				authenticationDashboardAdditionalFilterBO, queryStr);
		
		List<AuthenticationLast24HrsResponseBO> authenticationLast24HrsResponseBOList = new ArrayList<AuthenticationLast24HrsResponseBO>();
		
		for (Object[] obj : result) {
			AuthenticationLast24HrsResponseBO authenticationLast24HrsResponseBO = new AuthenticationLast24HrsResponseBO();
			authenticationLast24HrsResponseBO.setAuthtype((String) obj[0]);
			authenticationLast24HrsResponseBO.setNoOfAuthentication(Integer.parseInt(obj[1].toString()));
			authenticationLast24HrsResponseBOList.add(authenticationLast24HrsResponseBO);
		}
		return authenticationLast24HrsResponseBOList;
	}
	
	
	public List<AuthenticationOverTimeResponseBO> authenticationByTime(AuthenticationDashboardAdditionalFilterBO authenticationDashboardAdditionalFilterBO){
		List<AuthenticationOverTimeResponseBO> authenticationOverTimeResponseBOList = new ArrayList<AuthenticationOverTimeResponseBO>();
		StringBuilder queryStr =null;
		
		if(authenticationDashboardAdditionalFilterBO.getTimeGraphIndicator()==1){
			queryStr=new StringBuilder(AUTHENTICATION_BY_DATE_PART1);
			queryStr.append(AUTHENTICATION_BY_DATE_PART2);
			
			if(authenticationDashboardAdditionalFilterBO.getStartDate()!=0 
					&& authenticationDashboardAdditionalFilterBO.getEndDate()!=0)
				{
			queryStr.append( "date_sk between '"
					+ authenticationDashboardAdditionalFilterBO.getStartDate()
					+ "' and '"
					+ authenticationDashboardAdditionalFilterBO.getEndDate()
					+ "') ");
				} else{
				queryStr.append("date_sk between "
						+ "(select min(time_id)from fact_authentication) and date_format(CURDATE(),'%Y%m%d'))"
						 );
						}
			
			queryStr.append(AUTHENTICATION_BY_DATE_PART3);
			
			buildQuery(authenticationDashboardAdditionalFilterBO,queryStr);
			
			queryStr.append(AUTHENTICATION_BY_DATE_PART4);
			
			List<Object[]> result = getResultList(authenticationDashboardAdditionalFilterBO, queryStr);
			
			for (Object[] obj : result) {
				AuthenticationOverTimeResponseBO authenticationOverTimeResponseBO = new AuthenticationOverTimeResponseBO();
				authenticationOverTimeResponseBO.setYear(Integer.parseInt(obj[0].toString()));
				authenticationOverTimeResponseBO.setMonthName((String) obj[1]);
				authenticationOverTimeResponseBO.setMonthNo(Integer.parseInt(obj[2].toString()));
				authenticationOverTimeResponseBO.setDate((String) obj[3]);
				authenticationOverTimeResponseBO.setNoOfAuthentication(Integer.parseInt(obj[4].toString()));
				authenticationOverTimeResponseBOList.add(authenticationOverTimeResponseBO);
			}
		}
		
		if(authenticationDashboardAdditionalFilterBO.getTimeGraphIndicator()==2 
				|| authenticationDashboardAdditionalFilterBO.getTimeGraphIndicator()==3){

			queryStr=new StringBuilder(AUTHENTICATION_BY_WEEK_PART1);
			
			if(authenticationDashboardAdditionalFilterBO.getStartDate()!=0 
					&& authenticationDashboardAdditionalFilterBO.getEndDate()!=0)
				{
			queryStr.append( "date_sk between '"
					+ authenticationDashboardAdditionalFilterBO.getStartDate()
					+ "' and '"
					+ authenticationDashboardAdditionalFilterBO.getEndDate()
					+ "') ");
				} else{
				queryStr.append("date_sk between "
						+ "(select min(time_id)from fact_authentication) and date_format(CURDATE(),'%Y%m%d'))"
						 );
						}
			
			queryStr.append(AUTHENTICATION_BY_WEEK_PART2);
			
			buildQuery(authenticationDashboardAdditionalFilterBO,queryStr);
			
			queryStr.append(AUTHENTICATION_BY_WEEK_PART3);
			
			List<Object[]> result = getResultList(authenticationDashboardAdditionalFilterBO, queryStr);
			
			for (Object[] obj : result) {
				AuthenticationOverTimeResponseBO authenticationOverTimeResponseBO = new AuthenticationOverTimeResponseBO();
				authenticationOverTimeResponseBO.setYear(Integer.parseInt(obj[0].toString()));
				authenticationOverTimeResponseBO.setMonthName((String) obj[1]);
				authenticationOverTimeResponseBO.setMonthNo(Integer.parseInt(obj[2].toString()));
				authenticationOverTimeResponseBO.setWeekOfMonth(Integer.parseInt(obj[3].toString()));
 				authenticationOverTimeResponseBO.setWeekDateRange((String) obj[4]);
				authenticationOverTimeResponseBO.setNoOfAuthentication(Integer.parseInt(obj[5].toString()));
 				authenticationOverTimeResponseBOList.add(authenticationOverTimeResponseBO);
			}
			
		}
		
		else{
			queryStr=new StringBuilder(AUTHENTICATION_BY_MONTH_PART1);
			if(authenticationDashboardAdditionalFilterBO.getStartDate()!=0 
					&& authenticationDashboardAdditionalFilterBO.getEndDate()!=0)
				{
			queryStr.append( "date_sk between '"
					+ authenticationDashboardAdditionalFilterBO.getStartDate()
					+ "' and '"
					+ authenticationDashboardAdditionalFilterBO.getEndDate()
					+ "') ");
				} else{
				queryStr.append("date_sk between "
						+ "(select min(time_id)from fact_authentication) and date_format(CURDATE(),'%Y%m%d'))"
						 );
						}
			queryStr.append(AUTHENTICATION_BY_MONTH_PART2);
			
			buildQuery(authenticationDashboardAdditionalFilterBO,queryStr);
			queryStr.append(" group by a.year_number, a.month_name, a.month_number order by 1 asc,3 asc ");
			
			List<Object[]> result = getResultList(authenticationDashboardAdditionalFilterBO, queryStr);
			
			for (Object[] obj : result) {
				AuthenticationOverTimeResponseBO authenticationOverTimeResponseBO = new AuthenticationOverTimeResponseBO();
				authenticationOverTimeResponseBO.setYear(Integer.parseInt(obj[0].toString()));
				authenticationOverTimeResponseBO.setMonthName((String) obj[1]);
				authenticationOverTimeResponseBO.setMonthNo(Integer.parseInt(obj[2].toString()));
				authenticationOverTimeResponseBO.setNoOfAuthentication(Integer.parseInt(obj[3].toString()));
				authenticationOverTimeResponseBOList.add(authenticationOverTimeResponseBO);
			}
		}
		
		
		return authenticationOverTimeResponseBOList;
		
		
	}



	private List<Object[]> getResultList(
			AuthenticationDashboardAdditionalFilterBO authenticationDashboardAdditionalFilterBO,
			StringBuilder queryStr) {
		Query query = em.createNativeQuery(queryStr.toString());
		query.setParameter("tenantId", authenticationDashboardAdditionalFilterBO.getTenantId());
		query.setParameter("brandId", authenticationDashboardAdditionalFilterBO.getBrandId());
				
		List<Object[]> result=query.getResultList();
		return result;
	}
	
	private void buildQuery(AuthenticationDashboardAdditionalFilterBO authenticationDashboardAdditionalFilterBO, StringBuilder queryStr) {
		
		if (null !=authenticationDashboardAdditionalFilterBO.getRegion() && authenticationDashboardAdditionalFilterBO.getRegion() != 0) {
			queryStr.append(" and f.region_id= " + authenticationDashboardAdditionalFilterBO.getRegion());
		}
		if (null !=authenticationDashboardAdditionalFilterBO.getCountryId() && authenticationDashboardAdditionalFilterBO.getCountryId() != 0) {
			queryStr.append(" and f.country_id = " + authenticationDashboardAdditionalFilterBO.getCountryId());
		}
		if (null !=authenticationDashboardAdditionalFilterBO.getLevel() && authenticationDashboardAdditionalFilterBO.getLevel()!=0){
				queryStr.append(" and f.level_id= " + authenticationDashboardAdditionalFilterBO.getLevel());
		}
		if(null !=authenticationDashboardAdditionalFilterBO.getEntityType() && authenticationDashboardAdditionalFilterBO.getEntityType()!=0){
			queryStr.append(" and f.entity_type_id= " + authenticationDashboardAdditionalFilterBO.getEntityType());
		}
		
		if (authenticationDashboardAdditionalFilterBO.getStartDate() != 0 && authenticationDashboardAdditionalFilterBO.getEndDate() != 0) {
			queryStr.append(" and f.time_id between " + authenticationDashboardAdditionalFilterBO.getStartDate() + " and " + authenticationDashboardAdditionalFilterBO.getEndDate());
		}
		
		if(null !=authenticationDashboardAdditionalFilterBO.getCity() && authenticationDashboardAdditionalFilterBO.getCity()!=0){
			queryStr.append(" and f.city_id= " + authenticationDashboardAdditionalFilterBO.getCity());
		}
		
		if(null!=authenticationDashboardAdditionalFilterBO.getAuthType()){
			
		if(authenticationDashboardAdditionalFilterBO.getAuthType().equals(GENUINE)){
			queryStr.append(" and f.authentication_id = 2");
		} else if(authenticationDashboardAdditionalFilterBO.getAuthType().equals(SUSPECT)){
			queryStr.append(" and f.authentication_id <> 2");
		}
		
		
		}
	}
	
	

}
