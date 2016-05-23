/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   CountryListDaoImpl.java
 ** Version     :   1.0
 ** Description :   
 **
 ** Author      :   Narayan Singh
 ** Created Date :  Wednesday, 7 Oct 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.core.dws.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.vpa.core.bo.AuthenticationDashboardFilterBO;
import com.vpa.core.bo.CountryListBO;
import com.vpa.core.dws.dao.CountryListDao;
import com.vpa.core.exceptions.ResourceNotFoundException;

@Repository
public class CountryListDaoImpl implements CountryListDao {

	@PersistenceContext(unitName = "vpaDWPU")
	private EntityManager em;

	private static final int PROTECTED_PRODUCT = 1;

	private static final int COMPROMISED_PRODUCT = 1;

	private static final int ONLY_CHASISS = 3;

	private static final String COUNTRY_LIST = "select v.country_id,v.country_name,v.Active_entities,v.active_user, "
			+ "sum(v.total_authentication) as total_authentication, sum(v.revenue_loss) as revenue_loss,"
			+ "v.suspect_authentication as suspect_authentication, sum(v.suspect_products) as suspect_products from "
			+ " (SELECT count(distinct fa.company_id) AS Active_entities,count(distinct fa.user_id) as active_user,"
			+ " fa.product_units as total_authentication, dp.glp AS glp,dp.id AS productId,"
			+ " dcon.country_name AS country_name, dcon.id AS country_id,"
			+ " (CASE WHEN fa.authentication_id <> 2 THEN (1 * fa.product_units) ELSE 0 END) AS suspect_authentication,"
			+ " (CASE WHEN fa.authentication_id <> 2 THEN (1 * fa.product_units * dp.glp) ELSE 0 END) AS revenue_loss,"
			+ " (CASE WHEN fa.authentication_id <> 2 THEN 1 ELSE 0 END) AS suspect_products FROM fact_authentication fa, "
			+ " dim_product dp,   dim_country dcon WHERE fa.product_id = dp.id AND fa.country_id=dcon.id "
			+ "  AND fa.tenant_id =:tenantId AND fa.brand_id =:brandId  AND dp.compromised=:compromised "
			+ "  and dp.protected=:protected and dp.level_id=:LevelId ";

	@SuppressWarnings("unchecked")
	@Override
	public List<CountryListBO> getCountryList(
			AuthenticationDashboardFilterBO filter) {
		StringBuilder queryStr = new StringBuilder(COUNTRY_LIST);
		if (filter.getRegion() != 0) {
			queryStr.append(" and fa.region_id= " + filter.getRegion());
		}
		if (filter.getProductId() != 0) {
			queryStr.append(" and dp.id= " + filter.getProductId());
		}
		if (filter.getStartDate() != 0 && filter.getEndDate() != 0) {
			queryStr.append(" and fa.time_id between " + filter.getStartDate()
					+ " and " + filter.getEndDate());
		}
		queryStr.append(" group by  fa.product_units, dp.glp, dp.id, dcon.country_name) v");
		queryStr.append(" where v.suspect_authentication <> 0  group by  v.country_name ");
		String orderBy=filter.getOrderBy();
		if(orderBy.equalsIgnoreCase("total_authentication") ||orderBy.equalsIgnoreCase("revenue_loss")
				|| orderBy.equalsIgnoreCase("suspect_products")){
			queryStr.append(" order by sum(v." + orderBy + ") desc");
		}else{
			queryStr.append(" order by v." + orderBy + " desc");
		}
		
		Query query = em.createNativeQuery(queryStr.toString());
		query.setParameter("tenantId", filter.getTenantId());
		query.setParameter("brandId", filter.getBrandId());
		query.setParameter("LevelId", ONLY_CHASISS);
		query.setParameter("protected", PROTECTED_PRODUCT);
		query.setParameter("compromised", COMPROMISED_PRODUCT);

		if (filter.getNoOfRecord() > 0) {
			query.setMaxResults(filter.getNoOfRecord());
		}
		if (filter.getPageNumber() > 0) {
			query.setFirstResult((filter.getPageNumber() - 1)
					* filter.getNoOfRecord());
		}
		List<Object[]> result = (List<Object[]>) query.getResultList();
		List<CountryListBO> CountryList = new ArrayList<>();

		if (CollectionUtils.isEmpty(result)) {
			throw new ResourceNotFoundException("no record found for tenant "
					+ filter.getTenantId() + " brand " + filter.getBrandId()
					+ " in VPADWS DB ");
		}


		for (Object[] obj : result) {
			CountryListBO.Builder boBuilder = CountryListBO.Builder.countryListBO()
					.withCountryId(((Integer) obj[0]).intValue())
					.withCountryName((String) obj[1])
					.withEntities(((BigInteger) obj[2]).longValue())
					.withUsers(((BigInteger) obj[3]).longValue())
					.withTotalAuthentication(((BigDecimal) obj[4]).longValue())
					.withRevenueLoss(((Double) obj[5]).longValue())
					.withSuspectAuthentication(((BigInteger) obj[6]).longValue())
					.withSuspectProducts(((BigDecimal) obj[7]).longValue());
			CountryList.add(boBuilder.build());
		}
		return CountryList;
	}
}
