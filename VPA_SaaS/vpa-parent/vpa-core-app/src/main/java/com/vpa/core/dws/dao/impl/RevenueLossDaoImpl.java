/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   RevenueLossDaoImpl.java
 ** Version     :   1.0
 ** Description :   
 **
 ** Author      :   Narayan Singh,Partha Bhowmick
 ** Created Date :  Thursday, 1 Oct 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date 		Author     			Version 	Description:
 ** -------- 	--------   			-------- 	-------------
 **  27-Oct		Narayan Singh		1.0			review comments incorporate.review comments given by hunter
 **************************************************************************************************************/
package com.vpa.core.dws.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.vpa.core.bo.AuthenticationDashboardFilterBO;
import com.vpa.core.bo.RevenueLossBO;
import com.vpa.core.bo.RevenueLossByCountryBO;
import com.vpa.core.bo.RevenueLossByEntityTypeBO;
import com.vpa.core.bo.RevenueLossByRegionBO;
import com.vpa.core.bo.RevenueLossDashboardAdditionalFilterBO;
import com.vpa.core.bo.RevenueLossOverTimeResponseBO;
import com.vpa.core.dws.dao.RevenueLossDao;
import com.vpa.core.exceptions.ResourceNotFoundException;

/**
 * Implementation class for Dao Interface for Revenue Loss.
 * 
 * @author NS60097
 *
 */
@Repository
public class RevenueLossDaoImpl implements RevenueLossDao {

	private static final int PROTECTED_PRODUCT = 1;

	private static final int COMPROMISED_PRODUCT = 1;

	private static final int ONLY_CHASISS = 3;

	@PersistenceContext(unitName = "vpaDWPU")
	private EntityManager em;

	private static final String REVENUE_LOSS_DETAILS = "select max(rp.glp_update_date) as glp_update_date, "
			+ "sum(rp.projected_revenue_loss) as projected_revenue_loss, fact.no_product*sum(rp.revenue_loss) as revenue_loss "
			+ " from  "
			+ "(select v.glp_update_date as glp_update_date, product_id,round((sum(r.revenue)*0.07), 2) as projected_revenue_loss , "
			+ " (v.glp*sum(r.revenue)) as revenue_loss from fact_revenue_monthly r  "
			+ " inner join dim_product v  on r.product_id=v.id where  month_end_date >=LAST_DAY(DATE_SUB(CURDATE(),INTERVAL 1 YEAR)) "
			+ " and v.protected=:protected and v.compromised=:compromised and v.tenant_id=:tenantId and v.level_id=:LevelId group by r.product_id  ) rp "
			+ " inner join "
			+ "(select  product_id,sum(product_units) as no_product from fact_authentication f "
			+ " where f.authentication_id<>2 and f.brand_id=:brandId and f.tenant_id=:tenantId  ";

	private static final String REVENUE_LOSS_BY_REGION = "select c.id,c.region_name,sum(f.revenue_loss) as revenue_loss ,"
			+ "count(distinct f.company_id) as no_entities from (select a.region_id, a.product_id, a.company_id,"
			+ "a.country_id,a.time_id,a.entity_type_id,(b.glp*sum(a.product_units)) as revenue_Loss "
			+ "from fact_authentication a,dim_product b "
			+ "where a.product_id=b.id and  b.protected=:protected and b.compromised=:compromised "
			+ "and b.level_id=:LevelId and a.authentication_id <> 2 and a.tenant_id=:tenantId  and a.brand_id=:brandId "
			+ "group by region_id,product_id,a.company_id,a.country_id,a.entity_type_id,a.time_id)  f,dim_region c  "
			+ "where f.region_id=c.id ";

	private static final String REVENUE_LOSS_BY_ENTITY_TYPE = "select e.entity_type_name,sum(f.revenue_loss) as revenue_loss  "
			+ "from "
			+ "(select a.region_id, a.product_id, a.country_id,a.time_id,a.entity_type_id ,(b.glp*sum(a.product_units)) as revenue_Loss "
			+ "from fact_authentication a,dim_product b "
			+ "where a.product_id=b.id and  b.protected=:protected and b.compromised=:compromised "
			+ "and b.level_id=:LevelId and a.authentication_id <> 2 and a.tenant_id=:tenantId  and a.brand_id=:brandId "
			+ "group by region_id,product_id,a.country_id,a.entity_type_id,a.time_id)  f,dim_entity_type e "
			+ "where f.entity_type_id=e.id";

	private static final String REVENUE_LOSS_BY_COUNTRY = "select c.id,c.country_name,sum(f.revenue_loss) as revenue_loss ,"
			+ "count(distinct f.company_id) as no_entities"
			+ " from "
			+ "(select a.region_id, a.product_id, a.company_id,a.country_id,a.time_id,a.entity_type_id,(b.glp*sum(a.product_units)) as revenue_Loss "
			+ "from fact_authentication a,dim_product b "
			+ "where a.product_id=b.id and  b.protected=:protected and b.compromised=:compromised "
			+ "and b.level_id=:LevelId and a.authentication_id <> 2 and a.tenant_id=:tenantId  and a.brand_id=:brandId "
			+ "group by region_id,product_id,a.company_id,a.country_id,a.entity_type_id,a.time_id)  f,dim_country c  "
			+ "where f.country_id=c.id";


	
	
	private static final String REVENUE_LOSS_BY_DATE_PART1="select a.year_number, concat(upper(substring(a.month_name,1,1)),lower(substring(a.month_name,2)) ) as month_name, a.month_number, a.day_desc, IFNULL(sum(f.revenue_loss),0) as revenue_loss from (select month_name, month_number, day_desc, date_sk, year_number from dim_time where ";
	private static final String REVENUE_LOSS_BY_DATE_PART2=" a left join (select r.tenant_id, r.brand_id, r.region_id,r.country_id,r.time_id,r.entity_type_id, sum(r.revenue_Loss) as revenue_loss from (select a.tenant_id, a.brand_id, a.region_id,  a.product_id, a.country_id, a.time_id, a.entity_type_id, (b.glp*sum(a.product_units)) as revenue_Loss from fact_authentication a, dim_product b where a.product_id=b.id and  b.protected=1 and b.compromised=1 and a.authentication_id <> 2 and a.tenant_id=:tenantId and a.brand_id = :brandId ";
	private static final String REVENUE_LOSS_BY_DATE_PART3=" group by a.region_id, a.product_id, a.country_id, a.time_id, a.entity_type_id, a.tenant_id, a.brand_id) r group by  r.region_id, r.country_id,r.time_id,r.entity_type_id,r.tenant_id,r.brand_id) f on a.date_sk=f.time_id group by a.month_name, a.day_desc, a.month_number, a.year_number order by a.year_number asc, a.month_number asc, a.day_desc asc ";
	
	private static final String REVENUE_LOSS_BY_WEEK_PART1=" select a.year_number, concat(upper(substring(a.month_name,1,1)),lower(substring(a.month_name,2)) ) as month_name, a.month_number, a.number_of_week_in_month, a.week_date_range, IFNULL(sum(f.revenue_loss),0) as revenue_loss from (select month_name, month_number, number_of_week_in_month, week_date_range, date_sk, year_number  from dim_time  where ";
	
	private static final String REVENUE_LOSS_BY_WEEK_PART2=" a  left join (select  r.tenant_id,r.brand_id,r.region_id ,r.country_id ,r.time_id ,r.entity_type_id ,sum(r.revenue_Loss) as revenue_loss from (select a.tenant_id, a.brand_id, a.region_id, a.product_id, a.country_id, a.time_id, a.entity_type_id, (b.glp*sum(a.product_units)) as revenue_Loss  from fact_authentication a, dim_product b where a.product_id=b.id and  b.protected=1 and b.compromised=1 and a.authentication_id <> 2 and a.tenant_id=:tenantId and a.brand_id = :brandId ";
	
	private static final String REVENUE_LOSS_BY_WEEK_PART3=" group by a.region_id, a.product_id, a.country_id, a.time_id, a.entity_type_id, a.tenant_id, a.brand_id) r group by  r.region_id ,r.country_id ,r.time_id ,r.entity_type_id ,r.tenant_id ,r.brand_id ) f on a.date_sk=f.time_id group by a.month_name, a.number_of_week_in_month, a.month_number, a.year_number, a.week_date_range order by year_number asc, month_number asc, number_of_week_in_month asc ";
	
	private static final String REVENUE_LOSS_BY_MONTH_PART1=" select a.year_number,concat(upper(substring(a.month_name,1,1)),lower(substring(a.month_name,2)) ) as month_name, a.month_number, IFNULL(sum(f.revenue_loss),0) as revenue_loss from (select month_name, month_number, date_sk, year_number  from dim_time  where ";
	
	private static final String REVENUE_LOSS_BY_MONTH_PART2=" a  left join (select  r.tenant_id ,r.brand_id ,r.region_id ,r.country_id ,r.time_id ,r.entity_type_id ,sum(r.revenue_Loss) as revenue_loss from (select a.tenant_id, a.brand_id, a.region_id,  a.product_id, a.country_id, a.time_id, a.entity_type_id, (b.glp*sum(a.product_units)) as revenue_Loss  from fact_authentication a, dim_product b where a.product_id=b.id  and  b.protected=1  and b.compromised=1  and a.authentication_id <> 2  and a.tenant_id=:tenantId and a.brand_id = :brandId  ";
	
	private static final String REVENUE_LOSS_BY_MONTH_PART3=" group by a.region_id,  a.product_id, a.country_id, a.time_id, a.entity_type_id, a.tenant_id, a.brand_id) r group by   r.region_id ,r.country_id ,r.time_id ,r.entity_type_id ,r.tenant_id ,r.brand_id ) f on a.date_sk=f.time_id group by a.month_name, a.month_number, a.year_number order by a.year_number asc, a.month_number asc  ";

	/**
	 * @see RevenueLossDao#getRevenueDetails(AuthenticationDashboardFilterBO)
	 */
	@Override
	public RevenueLossBO getRevenueDetails(
			AuthenticationDashboardFilterBO filter) {
		StringBuilder queryStr = new StringBuilder(REVENUE_LOSS_DETAILS);
		buildQuery(filter, queryStr);
		queryStr.append(" group by f.product_id ) fact on fact.product_id= rp.product_id ");
		Query query = queryParameter(filter, queryStr);
		Object[] obj = (Object[]) query.getSingleResult();
		try {
			RevenueLossBO.Builder revenueBuilder = RevenueLossBO.Builder
					.revenueLossBO().withglpDate(((Date) obj[0]))
					.withProjectedRevenueLoss(((Double) obj[1]).longValue())
					.withrevenueLoss(((Double) obj[2]).longValue());
			return revenueBuilder.build();
		} catch (Exception e) {
			throw new ResourceNotFoundException("no record found for tenant "
					+ filter.getTenantId() + " brand " + filter.getBrandId()
					+ " in VPADWS DB ");
		}

	}

	/**
	 * @see RevenueLossDao#revenueLossByRegion(AuthenticationDashboardFilterBO)
	 */

	@Override
	public List<RevenueLossByRegionBO> revenueLossByRegion(
			AuthenticationDashboardFilterBO filter) {
		StringBuilder queryStr = new StringBuilder(REVENUE_LOSS_BY_REGION);
		buildQuery(filter, queryStr);
		queryStr.append(" group by c.id ");
		Query query = queryParameter(filter, queryStr);
		@SuppressWarnings("unchecked")
		List<Object[]> result = (List<Object[]>) query.getResultList();

		validateQueryResult(result,filter);
		List<RevenueLossByRegionBO> revenueLossList = new ArrayList<>();

		for (Object[] obj : result) {
			RevenueLossByRegionBO.Builder revenueBuilder = RevenueLossByRegionBO.Builder
					.revenueLossByRegionBO()
					.withRegionId(((Integer) obj[0]).intValue())
					.withRegionName(((String) obj[1]).toString())
					.withRevenueLoss(((Double) obj[2]).longValue())
					.withEntityCount(((BigInteger) obj[3]).longValue());
			revenueLossList.add(revenueBuilder.build());
		}

		return revenueLossList;
	}

	public List<RevenueLossOverTimeResponseBO> revenueLossByTime(RevenueLossDashboardAdditionalFilterBO revenueLossDashboardAdditionalFilterBO){
		List<RevenueLossOverTimeResponseBO> revenueLossOverTimeResponseBOList = new ArrayList<RevenueLossOverTimeResponseBO>();
		StringBuilder queryStr =null;		
		// By DATE
		if(revenueLossDashboardAdditionalFilterBO.getTimeGraphIndicator()==1){
			queryStr=new StringBuilder(REVENUE_LOSS_BY_DATE_PART1);
			
			setDateRange(revenueLossDashboardAdditionalFilterBO, queryStr);
			
			queryStr.append(REVENUE_LOSS_BY_DATE_PART2);
			
			buildQueryOverTime(revenueLossDashboardAdditionalFilterBO,queryStr);
			
			queryStr.append(REVENUE_LOSS_BY_DATE_PART3);
			
			List<Object[]> result = getResultList(revenueLossDashboardAdditionalFilterBO, queryStr);
			
			handleBlankResult(revenueLossDashboardAdditionalFilterBO, result);
			
			for (Object[] obj : result) {
				RevenueLossOverTimeResponseBO revenueLossOverTimeResponseBO = new RevenueLossOverTimeResponseBO();
				revenueLossOverTimeResponseBO.setYear(Integer.parseInt(obj[0].toString()));
				revenueLossOverTimeResponseBO.setMonthName((String) obj[1]);
				revenueLossOverTimeResponseBO.setMonthNo(Integer.parseInt(obj[2].toString()));
				revenueLossOverTimeResponseBO.setDate((String) obj[3]);
				revenueLossOverTimeResponseBO.setRevenueLoss(Float.parseFloat(obj[4].toString()));
				revenueLossOverTimeResponseBOList.add(revenueLossOverTimeResponseBO);
			}
		}
			// by week
			if(revenueLossDashboardAdditionalFilterBO.getTimeGraphIndicator()==2 
					|| revenueLossDashboardAdditionalFilterBO.getTimeGraphIndicator()==3){
				queryStr=new StringBuilder(REVENUE_LOSS_BY_WEEK_PART1);
				
				setDateRange(revenueLossDashboardAdditionalFilterBO, queryStr);
				
				queryStr.append(REVENUE_LOSS_BY_WEEK_PART2);
				
				buildQueryOverTime(revenueLossDashboardAdditionalFilterBO,queryStr);
				
				queryStr.append(REVENUE_LOSS_BY_WEEK_PART3);
				
				List<Object[]> result = getResultList(revenueLossDashboardAdditionalFilterBO, queryStr);
				

				handleBlankResult(revenueLossDashboardAdditionalFilterBO,
						result);
				for (Object[] obj : result) {
					RevenueLossOverTimeResponseBO revenueLossOverTimeResponseBO = new RevenueLossOverTimeResponseBO();
					revenueLossOverTimeResponseBO.setYear(Integer.parseInt(obj[0].toString()));
					revenueLossOverTimeResponseBO.setMonthName((String) obj[1]);
					revenueLossOverTimeResponseBO.setMonthNo(Integer.parseInt(obj[2].toString()));
					revenueLossOverTimeResponseBO.setWeekOfMonth(Integer.parseInt(obj[3].toString()));
					revenueLossOverTimeResponseBO.setWeekDateRange((String) obj[4]);
					revenueLossOverTimeResponseBO.setRevenueLoss(Float.parseFloat(obj[5].toString()));
					revenueLossOverTimeResponseBOList.add(revenueLossOverTimeResponseBO);
				}
				
			}
			
			// By Month
			else{

				queryStr=new StringBuilder(REVENUE_LOSS_BY_MONTH_PART1);
				
				setDateRange(revenueLossDashboardAdditionalFilterBO, queryStr);
				
				queryStr.append(REVENUE_LOSS_BY_MONTH_PART2);
				
				buildQueryOverTime(revenueLossDashboardAdditionalFilterBO,queryStr);
				
				queryStr.append(REVENUE_LOSS_BY_MONTH_PART3);
				
				List<Object[]> result = getResultList(revenueLossDashboardAdditionalFilterBO, queryStr);
				

				handleBlankResult(revenueLossDashboardAdditionalFilterBO,
						result);
				for (Object[] obj : result) {
					RevenueLossOverTimeResponseBO revenueLossOverTimeResponseBO = new RevenueLossOverTimeResponseBO();
					revenueLossOverTimeResponseBO.setYear(Integer.parseInt(obj[0].toString()));
					revenueLossOverTimeResponseBO.setMonthName((String) obj[1]);
					revenueLossOverTimeResponseBO.setMonthNo(Integer.parseInt(obj[2].toString()));
					revenueLossOverTimeResponseBO.setRevenueLoss(Float.parseFloat(obj[3].toString()));
					revenueLossOverTimeResponseBOList.add(revenueLossOverTimeResponseBO);
				}
			}
			
			
			
		
		
		return revenueLossOverTimeResponseBOList;
	}



	
	
	/**
	 * @see RevenueLossDao#revenueLossByEntityType(AuthenticationDashboardFilterBO)
	 */
	@Override
	public List<RevenueLossByEntityTypeBO> revenueLossByEntityType(
			AuthenticationDashboardFilterBO filter) {
		StringBuilder queryStr = new StringBuilder(REVENUE_LOSS_BY_ENTITY_TYPE);
		buildQuery(filter, queryStr);
		queryStr.append(" group by e.id ");
		Query query = queryParameter(filter, queryStr);
		@SuppressWarnings("unchecked")
		List<Object[]> result = (List<Object[]>) query.getResultList();

		validateQueryResult(result,filter);

		List<RevenueLossByEntityTypeBO> revenueLossByEntityList = new ArrayList<>();
		for (Object[] obj : result) {
			RevenueLossByEntityTypeBO.Builder revenueBuilder = RevenueLossByEntityTypeBO.Builder
					.revenueLossByEntityType()
					.withEntityType(((String) obj[0]).toString())
					.withRevenueLoss(((Double) obj[1]).longValue());
			revenueLossByEntityList.add(revenueBuilder.build());
		}
		return revenueLossByEntityList;
	}

	/**
	 * @see RevenueLossDao#revenueLossByCountry(AuthenticationDashboardFilterBO)
	 */
	@Override
	public List<RevenueLossByCountryBO> revenueLossByCountry(
			AuthenticationDashboardFilterBO filter) {
		StringBuilder queryStr = new StringBuilder(REVENUE_LOSS_BY_COUNTRY);
		buildQuery(filter, queryStr);
		queryStr.append(" group by c.id order by sum(f.revenue_loss) desc ");
		Query query = queryParameter(filter, queryStr);
		if (filter.getNoOfRecord() > 0) {
			query.setMaxResults(filter.getNoOfRecord());
		}
		if (filter.getPageNumber() > 0) {
			query.setFirstResult((filter.getPageNumber() - 1)
					* filter.getNoOfRecord());
		}
		@SuppressWarnings("unchecked")
		List<Object[]> result = (List<Object[]>) query.getResultList();

		validateQueryResult(result,filter);
		List<RevenueLossByCountryBO> revenueLossByCountryList = new ArrayList<RevenueLossByCountryBO>();
		for (Object[] obj : result) {
			RevenueLossByCountryBO.Builder revenueBuilder = RevenueLossByCountryBO.Builder
					.revenueLossByCountry()
					.withCountryId(((Integer) obj[0]).intValue())
					.withCountryName(((String) obj[1]).toString())
					.withRevenueLoss(((Double) obj[2]).longValue())
					.withEntities(((BigInteger) obj[3]).intValue());
			revenueLossByCountryList.add(revenueBuilder.builder());
		}
		return revenueLossByCountryList;
	}

	/**
	 * this method build the sql query based on filter parameter passed from UI.
	 * 
	 * @param filter
	 * @param query
	 * @return
	 */
	private StringBuilder buildQuery(AuthenticationDashboardFilterBO filter,
			StringBuilder query) {
		if (filter.getRegion() != 0) {
			query.append(" and f.region_id= :region");
		}
		if (filter.getCountryId() != 0) {
			query.append(" and f.country_id=:country ");
		}
		if (filter.getEntityType() != 0) {
			query.append(" and f.entity_type_id=:entity ");
		}
		if (filter.getStartDate() != 0 && filter.getEndDate() != 0) {
			query.append(" and f.time_id between :startDate and :endDate ");
		}
		return query;
	}

	/**
	 * This method execute the sql query in DB which was build using filter
	 * criteria.
	 * 
	 * @param filter
	 * @param queryStr
	 * @return
	 */
	private Query queryParameter(AuthenticationDashboardFilterBO filter,
			StringBuilder queryStr) {
		Query query = em.createNativeQuery(queryStr.toString());
		query.setParameter("tenantId", filter.getTenantId());
		query.setParameter("brandId", filter.getBrandId());
		query.setParameter("LevelId", ONLY_CHASISS);
		query.setParameter("protected", PROTECTED_PRODUCT);
		query.setParameter("compromised", COMPROMISED_PRODUCT);
		if (filter.getRegion() != 0) {
			query.setParameter("region", filter.getRegion());
		}
		if (filter.getCountryId() != 0) {
			query.setParameter("country", filter.getCountryId());
		}
		if (filter.getEntityType() != 0) {
			query.setParameter("entity", filter.getEntityType());
		}
		if (filter.getStartDate() != 0 && filter.getEndDate() != 0) {
			query.setParameter("startDate", filter.getStartDate());
			query.setParameter("endDate", filter.getEndDate());
		}
		return query;
	}

/**
 * this method do validation of the query return from db.
 * @param result
 * @param filter
 */
	private void validateQueryResult(List<Object[]> result,
			AuthenticationDashboardFilterBO filter) {
		if (CollectionUtils.isEmpty(result)) {
			throw new ResourceNotFoundException("no record found for tenant "
					+ filter.getTenantId() + " brand " + filter.getBrandId()
					+ " in VPADWS DB ");
		}
	}


	
private void buildQueryOverTime(RevenueLossDashboardAdditionalFilterBO revenueLossDashboardAdditionalFilterBO, StringBuilder queryStr) {
		
		if (null !=revenueLossDashboardAdditionalFilterBO.getRegion() && revenueLossDashboardAdditionalFilterBO.getRegion() != 0) {
			queryStr.append(" and a.region_id= " + revenueLossDashboardAdditionalFilterBO.getRegion());
		}
		if (null !=revenueLossDashboardAdditionalFilterBO.getCountryId() && revenueLossDashboardAdditionalFilterBO.getCountryId() != 0) {
			queryStr.append(" and a.country_id = " + revenueLossDashboardAdditionalFilterBO.getCountryId());
		}
		if (null !=revenueLossDashboardAdditionalFilterBO.getLevel() && revenueLossDashboardAdditionalFilterBO.getLevel()!=0){
				queryStr.append(" and a.level_id= " + revenueLossDashboardAdditionalFilterBO.getLevel());
		}
		if(null !=revenueLossDashboardAdditionalFilterBO.getEntityType() && revenueLossDashboardAdditionalFilterBO.getEntityType()!=0){
			queryStr.append(" and a.entity_type_id= " + revenueLossDashboardAdditionalFilterBO.getEntityType());
		}
		
		if (revenueLossDashboardAdditionalFilterBO.getStartDate() != 0 && revenueLossDashboardAdditionalFilterBO.getEndDate() != 0) {
			queryStr.append(" and a.time_id between " + revenueLossDashboardAdditionalFilterBO.getStartDate() + " and " + revenueLossDashboardAdditionalFilterBO.getEndDate());
		}
	}

	private List<Object[]> getResultList(
			RevenueLossDashboardAdditionalFilterBO revenueLossDashboardAdditionalFilterBO,
			StringBuilder queryStr) {
		Query query = em.createNativeQuery(queryStr.toString());
		query.setParameter("tenantId",
				revenueLossDashboardAdditionalFilterBO.getTenantId());
		query.setParameter("brandId",
				revenueLossDashboardAdditionalFilterBO.getBrandId());

		List<Object[]> result = query.getResultList();
		return result;
	}
	
	/**
	 * @param revenueLossDashboardAdditionalFilterBO
	 * @param queryStr
	 */
	private void setDateRange(
			RevenueLossDashboardAdditionalFilterBO revenueLossDashboardAdditionalFilterBO,
			StringBuilder queryStr) {
		if(revenueLossDashboardAdditionalFilterBO.getStartDate()!=0 
				&& revenueLossDashboardAdditionalFilterBO.getEndDate()!=0)
			{
		queryStr.append( "date_sk between '"
				+ revenueLossDashboardAdditionalFilterBO.getStartDate()
				+ "' and '"
				+ revenueLossDashboardAdditionalFilterBO.getEndDate()
				+ "') ");
			} else{
			queryStr.append("date_sk between "
					+ "(select min(time_id)from fact_authentication) and date_format(CURDATE(),'%Y%m%d'))"
					 );
					}
	}
	
	
	/**
	 * @param revenueLossDashboardAdditionalFilterBO
	 * @param result
	 */
	private void handleBlankResult(
			RevenueLossDashboardAdditionalFilterBO revenueLossDashboardAdditionalFilterBO,
			List<Object[]> result) {
		if (CollectionUtils.isEmpty(result)) {
			throw new ResourceNotFoundException("no record found for tenant for this period of time "
					+ revenueLossDashboardAdditionalFilterBO.getTenantId() + " brand" + revenueLossDashboardAdditionalFilterBO.getBrandId()
					+ " in VPADWS DB ");
		}
	}

}
