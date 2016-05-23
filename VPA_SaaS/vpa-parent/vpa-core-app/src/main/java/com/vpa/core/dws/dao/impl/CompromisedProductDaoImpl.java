/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   CompromisedProductDaoImpl.java
 ** Version     :   1.0
 ** Description :   DAO  class for compromised Product interface .
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
package com.vpa.core.dws.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.vpa.core.bo.AuthenticationDashboardFilterBO;
import com.vpa.core.bo.Top3ProductBO;
import com.vpa.core.dws.dao.CompromisedProductViewAllDao;
import com.vpa.core.dws.models.CompromisedProductViewAll;
import com.vpa.core.exceptions.ResourceNotFoundException;
import com.vpa.core.bo.ProductRequestBO;

public class CompromisedProductDaoImpl implements CompromisedProductViewAllDao {

	private static final int PROTECTED_PRODUCT = 1;

	private static final int COMPROMISED_PRODUCT = 1;

	private static final int ONLY_CHASISS = 3;

	// where r.month_end_date>=LAST_DAY(DATE_SUB(CURDATE(),INTERVAL 1 YEAR))  and 
//	+ "where month_end_date between v.updated_date and LAST_DAY(now()) and "
	private static final String COMPROMISED_PRODUCT_DETAILS = " select  v.glp, v.total_product, v.compromised_product,"
			+ " round ((v.compromised_product/v.total_product)*100,2) as percentage "
			+ " from (select max(glp_update_date) as glp, (select count(*) from dim_product where protected=:protected and tenant_id=:tenantId and level_id=:LevelId) as total_product , "
			+ " (select count(*) from dim_product where protected=:protected and compromised=:compromised and tenant_id=:tenantId and level_id=:LevelId ) as compromised_product "
			+ " FROM dim_product where tenant_id =:tenantId and level_id=:LevelId ) v";

	private static final String COMPROMISED_PRODUCT_FOR_AUTHENTICATION_PERFORMANCE = "select v.id , v.product_name as product_name, "
			+ " (v.glp*sum(f.product_units)) as revenue_loss,sum(f.product_units ) as suspectAuthentication from fact_authentication f "
			+ " inner join dim_product v on f.product_id=v.id  where v.protected=:protected and v.compromised=:compromised and f.authentication_id <>2 "
			+ " and v.tenant_id=:tenantId and f.brand_id=:brandId and v.level_id=:LevelId ";

	@PersistenceContext(unitName = "vpaDWPU")
	private EntityManager em;


	private boolean isLegitFilter(String filter){
		return (filter != null && filter.length() != 0 && !filter.equals("0"));
	}

	private Query makeNativeCompromisedSQL(ProductRequestBO requestBO) {
		String authTableJoin = ""
				+ "SELECT "
				+ "    product_id, "
				+ "        SUM((CASE "
				+ "            WHEN f.final_result = 'Genuine' THEN 0 "
				+ "            ELSE 1 "
				+ "        END) * product_units) AS suspect_auth, "
				+ "        SUM(product_units) AS total_auth "
				+ "FROM "
				+ "    fact_authentication fa "
				+ "    INNER JOIN dim_authentication f ON fa.authentication_id = f.id ";

		String authTableWhere = "WHERE 1";

		String fromTime = requestBO.getFromTime();
		String toTime = requestBO.getToTime();
		String region = requestBO.getRegion();
		String entity = requestBO.getEntity();

		if (isLegitFilter(fromTime)) {
			authTableWhere += " AND fa.time_id>=:fromTime";
		}
		if (isLegitFilter(toTime)) {
			authTableWhere += " AND fa.time_id<=:toTime";
		}
		if (isLegitFilter(region)) {
			authTableWhere += " AND fa.region_id=:region";
		}
		if (isLegitFilter(entity)) {
			authTableWhere += " AND fa.entity_type_id=:entity";
		}

		authTableJoin += authTableWhere;
		authTableJoin += "    GROUP BY product_id";

		String productTableWhere = ""
				+ "    WHERE a.protected = " + PROTECTED_PRODUCT + " "
				+ "          AND a.compromised = " + COMPROMISED_PRODUCT + " "
				+ "          AND a.tenant_id = :tenantId";

		String level = requestBO.getLevel();
		if (isLegitFilter(level)) {
			productTableWhere += " AND a.level_id = :level";
		}

		String finalCompromisedSQL = ""
				+ "SELECT "
				+ "    a.id, "
				+ "    a.product_name, "
				+ "    a.glp, "
				+ "    rp.revenue AS projected_revenue_loss, "
				+ "    e.suspect_auth, "
				+ "    e.total_auth, "
				+ "    (a.glp*e.suspect_auth) as revenue_loss, "
				+ "    d.business_unit_name AS business_unit, "
				+ "    c.product_family_name AS product_family "
				+ "FROM "
				+ "    dim_product a "
				+ "        INNER JOIN "
				+ "    (SELECT "
				+ "        product_id, ROUND((SUM(revenue) * 0.07), 2) AS revenue "
				+ "    FROM "
				+ "        fact_revenue_monthly r "
				+ "    WHERE month_end_date >= LAST_DAY(DATE_SUB(CURDATE(), INTERVAL 1 YEAR))"
				+ "    GROUP BY r.product_id) rp ON a.id = rp.product_id "
				+ "        INNER JOIN "
				+ "    dim_product_family c ON a.product_family_id = c.id "
				+ "        INNER JOIN "
				+ "    dim_business_unit d ON c.business_unit_id = d.id "
				+ "        INNER JOIN "
				+ "    (" + authTableJoin + ")e ON rp.product_id = e.product_id "
				+ productTableWhere
				+ " ORDER BY rp.revenue DESC";

		Query nativeQuery = em.createNativeQuery(finalCompromisedSQL,
				CompromisedProductViewAll.class);
		nativeQuery.setParameter("tenantId", requestBO.getTenantId());

		if (isLegitFilter(fromTime)) {
			nativeQuery.setParameter("fromTime", fromTime);
		}
		if (isLegitFilter(toTime)) {
			nativeQuery.setParameter("toTime", toTime);
		}
		if (isLegitFilter(region)) {
			nativeQuery.setParameter("region", region);
		}
		if (isLegitFilter(entity)) {
			nativeQuery.setParameter("entity", entity);
		}
		if (isLegitFilter(level)) {
			nativeQuery.setParameter("level", level);
		}

		return nativeQuery;

	}

	/**
	 * 
	 * @see CompromisedProductViewAllDao#viewAll(ProductRequestBO)
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<CompromisedProductViewAll> viewAll(ProductRequestBO requestBO) {
		Query query = makeNativeCompromisedSQL(requestBO);
		return (List<CompromisedProductViewAll>) query.getResultList();
	}

	/**
	 * 
	 * @see CompromisedProductViewAllDao#compromisedProductDetails(String)
	 */

	@Override
	public Object[] compromisedProductDetails(String tenantId) {
		Object[] result = (Object[]) em
				.createNativeQuery(COMPROMISED_PRODUCT_DETAILS)
				.setParameter("tenantId", tenantId)
				.setParameter("protected", PROTECTED_PRODUCT)
				.setParameter("LevelId", ONLY_CHASISS)
				.setParameter("compromised", COMPROMISED_PRODUCT)
				.getSingleResult();
		return result;
	}

	/**
	 * 
	 * @see CompromisedProductViewAllDao#topCompromisedProduct(AuthenticationDashboardFilterBO)
	 */

	@Override
	public List<Top3ProductBO> topCompromisedProduct(AuthenticationDashboardFilterBO
			filter) {
		StringBuilder queryStr = new StringBuilder(
				COMPROMISED_PRODUCT_FOR_AUTHENTICATION_PERFORMANCE);
		if (filter.getRegion() != 0) {
			queryStr.append(" and f.region_id= " + filter.getRegion());
		}
		if (filter.getCountryId() != 0) {
			queryStr.append(" and f.country_id= " + filter.getCountryId());
		}

		if (filter.getEntityType() != 0) {
			queryStr.append(" and f.entity_type_id= " + filter.getEntityType());
		}
		if (filter.getStartDate() != 0 && filter.getEndDate() != 0) {
			queryStr.append(" and f.time_id between " + filter.getStartDate()
					+ " and " + filter.getEndDate());
		}
		String dashboardPage = filter.getDashboardPage().toLowerCase();
		if (dashboardPage.equalsIgnoreCase("authentications")
				&& (filter.getLevel() != 0)) {
			queryStr.append(" and f.level_id= " + filter.getLevel());
			queryStr.append(" group by  v.id,v.product_name order by sum(f.product_units ) desc ");
		}else if (dashboardPage.equalsIgnoreCase("revenueloss")){
			queryStr.append(" group by  v.id,v.product_name order by (v.glp*sum(f.product_units)) desc ");
		}
		//queryStr.append(" group by  v.id,v.product_name order by suspectAuthentication desc ");
		Query query = em.createNativeQuery(queryStr.toString());
		query.setParameter("tenantId", filter.getTenantId());
		query.setParameter("brandId", filter.getBrandId());
		query.setParameter("protected", PROTECTED_PRODUCT);
		query.setParameter("compromised", COMPROMISED_PRODUCT);
		query.setParameter("LevelId", ONLY_CHASISS);
		if (filter.getNoOfRecord() > 0) {
			query.setMaxResults(filter.getNoOfRecord());
		}
		if (filter.getPageNumber() > 0) {
			query.setFirstResult((filter.getPageNumber() - 1)
					* filter.getNoOfRecord());
		}
		@SuppressWarnings("unchecked")
		List<Object[]> result = (List<Object[]>) query.getResultList();
		if (CollectionUtils.isEmpty(result)) {
			throw new ResourceNotFoundException("no record found for tenant "
					+ filter.getTenantId() + " brand" + filter.getBrandId()
					+ " in VPADWS DB ");
		}
		List<Top3ProductBO> compromisedList = new ArrayList<>();

		for (Object[] obj : result) {
			Top3ProductBO productDTO = new Top3ProductBO();
			productDTO.setId(Integer.parseInt(obj[0].toString()));
			productDTO.setProductName((String) obj[1]);
			productDTO.setRevenue(((Double) obj[2]).intValue());
			productDTO.setSuspectAuthentication(((BigDecimal) obj[3])
					.intValue());
			compromisedList.add(productDTO);
		}
		return compromisedList;

	}

}
