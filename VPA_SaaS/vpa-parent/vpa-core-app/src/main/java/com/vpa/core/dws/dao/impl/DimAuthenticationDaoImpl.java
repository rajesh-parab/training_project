/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   DimAuthenticationDaoImpl.java
 ** Version     :   1.0
 ** Description :   This implementation class for Mobile Apps  .
 **
 ** Author      :   Rajesh Parab
 ** Created Date :  Tuesday, 19 May 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  Friday, 16 Oct 2015  Rajesh Parab      Code refactored by eliminating Hibernate mapping due to some problem mapping 
 *                                          Query result to Java Object.
 *
 **************************************************************************************************************/
package com.vpa.core.dws.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.vpa.core.dws.dao.RegionWiseAuthenticationDao;
import com.vpa.core.dws.models.RegionWiseAuthentication;

// Spring will merge JPARepository implementation in this class.
public class DimAuthenticationDaoImpl implements RegionWiseAuthenticationDao {

	@PersistenceContext(unitName = "vpaDWPU")
	private EntityManager em;

	public static final String WORLDWIDE_AUTHENTICATIONS_TOTAL_SQL = "select b.id, b.final_result,"
			+ " sum(a.product_units) as totalAuthentications  from fact_authentication a"
			+ " left join dim_authentication b on a.authentication_id=b.id where a.tenant_id=:tenantId and  a.brand_id=:brandId "
			+ " group by authentication_id,b.final_result";

	private static final String WORLDWIDE_AUTHENTICATIONS_LAST_24HRS_TOTAL_SQL = "select  a.id,a.authentication_id,a.tenant_id,b.final_result,sum(a.product_units) as totalAuthentications"
			+ " FROM fact_authentication a left join dim_authentication b on a.authentication_id=b.id left join dim_time c on a.time_id=c.date_sk "
			+ " where a.tenant_id = :tenantId  and a.brand_id = :brandId and (a.created_date>=DATE_SUB(now(),INTERVAL 24 HOUR) 	or a.updated_date>=DATE_SUB(now(),INTERVAL 24 HOUR) ) group by authentication_id, b.final_result, a.tenant_id";

	private static final String REGIONWISE_AUTHENTICATIONS_TOTAL_GENUINE_SQL = "select   b.id,    b.region_name as regionName,   IFNULL(sum(a.product_units),0) as genuine,  IFNULL(count(distinct c.company_name),0) as no_of_gen_entities"
			+ " from  fact_authentication a  inner join  dim_region b   on a.region_id=b.id inner join  dim_company c  on a.company_id=c.id where authentication_id=2 "
			+ " and a.tenant_id = :tenantId    and a.brand_id = :brandId group by   b.region_name";

	private static final String REGIONWISE_AUTHENTICATIONS_TOTAL_SUSPECT_SQL = "select  b.id,   b.region_name as regionName, IFNULL(sum(a.product_units), 0) as suspect, IFNULL(count(distinct c.company_name), 0) as no_of_susp_entities"
			+ " from  fact_authentication a inner join  dim_region b  on a.region_id=b.id inner join  dim_company c  on a.company_id=c.id where  authentication_id<>2 "
			+ " and a.tenant_id = :tenantId    and a.brand_id = :brandId group by   b.region_name";

	private static final String REGIONWISE_AUTHENTICATIONS_TOTAL_ENTITIES_SQL = "select d.region_name, count(distinct f.company_id) as no_entities  from fact_authentication f "
			+ " inner join dim_region d on f.region_id=d.id where f.tenant_id =:tenantId and f.brand_id = :brandId group by d.region_name";

	@Override
	@SuppressWarnings("unchecked")
	public List<RegionWiseAuthentication> getWorldWideAuthenticationCount(
			Long tenantId, Long brandId) {

		List<Object[]> result = (List<Object[]>) em
				.createNativeQuery(WORLDWIDE_AUTHENTICATIONS_TOTAL_SQL)
				.setParameter("tenantId", tenantId)
				.setParameter("brandId", brandId).getResultList();

		List<RegionWiseAuthentication> worldWideCounts = new ArrayList<>();
		for (Object[] authentications : result) {
			RegionWiseAuthentication regionWiseAuthentication = new RegionWiseAuthentication();
			regionWiseAuthentication
					.mapToRegionWiseWorldWideCount(authentications);
			worldWideCounts.add(regionWiseAuthentication);

		}

		return worldWideCounts;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<RegionWiseAuthentication> getWorldWideAuthenticationCountForLast24Hrs(
			Long tenantId, Long brandId) {

		List<Object[]> result = (List<Object[]>) em
				.createNativeQuery(
						WORLDWIDE_AUTHENTICATIONS_LAST_24HRS_TOTAL_SQL)
				.setParameter("tenantId", tenantId)
				.setParameter("brandId", brandId).getResultList();
		List<RegionWiseAuthentication> last24hrsAuthentication = new ArrayList<>();
		for (Object[] authentications : result) {
			RegionWiseAuthentication regionWiseAuthentication = new RegionWiseAuthentication();
			regionWiseAuthentication
					.mapToLast24HrsAuthenticationCount(authentications);
			last24hrsAuthentication.add(regionWiseAuthentication);

		}
		return last24hrsAuthentication;
	}

	/*
	 * @Override
	 * 
	 * @SuppressWarnings("unchecked") public List<DimAuthentication>
	 * getRegionWiseAuthenticationCount( Long tenantId, Long brandId) {
	 * 
	 * List<DimAuthentication> result = (List<DimAuthentication>) em
	 * .createNativeQuery(REGIONWISE_AUTHENTICATIONS_TOTAL_SQL,
	 * DimAuthentication.class) .setParameter("tenantId", tenantId)
	 * .setParameter("brandId", brandId).getResultList(); return result; }
	 * 
	 * @Override
	 * 
	 * @SuppressWarnings("unchecked") public List<DimAuthentication>
	 * getRegionWiseAuthenticationCountGenuineOnly( Long tenantId, Long brandId)
	 * {
	 * 
	 * List<DimAuthentication> result = (List<DimAuthentication>) em
	 * .createNativeQuery(REGIONWISE_AUTHENTICATIONS_GENUINE_ONLY_SQL,
	 * DimAuthentication.class) .setParameter("tenantId", tenantId)
	 * .setParameter("brandId", brandId).getResultList(); return result; }
	 */

	@Override
	public List<RegionWiseAuthentication> getRegionWiseAuthenticationCount(
			Long tenantId, Long brandId) {

		Map<String, RegionWiseAuthentication> regionWiseGenuineResult = new HashMap<>();

		calculateRegionWiseGenuine(tenantId, brandId, regionWiseGenuineResult);

		calculateRegionWiseSuspectedCount(tenantId, brandId,
				regionWiseGenuineResult);

		List<RegionWiseAuthentication> regionWiseCount = calculateTotalEntitiesInvolveInScan(
				tenantId, brandId, regionWiseGenuineResult);

		regionWiseGenuineResult.clear();

		return regionWiseCount;
	}

	private List<RegionWiseAuthentication> calculateTotalEntitiesInvolveInScan(
			Long tenantId, Long brandId,
			Map<String, RegionWiseAuthentication> regionWiseGenuineResult) {
		@SuppressWarnings("unchecked")
		List<Object[]> totalEntitiesResult = (List<Object[]>) em
				.createNativeQuery(
						REGIONWISE_AUTHENTICATIONS_TOTAL_ENTITIES_SQL)
				.setParameter("tenantId", tenantId)
				.setParameter("brandId", brandId).getResultList();
		List<RegionWiseAuthentication> regionWiseCount = new ArrayList<>();

		for (Object[] authentications : totalEntitiesResult) {

			String regionName = (authentications[0] == null) ? ""
					: (String) authentications[0];
			RegionWiseAuthentication regionWiseAuthentication = regionWiseGenuineResult
					.get(regionName);
			if (regionWiseAuthentication == null) {
				continue;
			}

			regionWiseAuthentication.mapTotalEntitiesCount(authentications);
			regionWiseCount.add(regionWiseAuthentication);

		}

		return regionWiseCount;
	}

	private void calculateRegionWiseSuspectedCount(Long tenantId, Long brandId,
			Map<String, RegionWiseAuthentication> regionWiseGenuineResult) {
		@SuppressWarnings("unchecked")
		List<Object[]> suspectedResult = (List<Object[]>) em
				.createNativeQuery(REGIONWISE_AUTHENTICATIONS_TOTAL_SUSPECT_SQL)
				.setParameter("tenantId", tenantId)
				.setParameter("brandId", brandId).getResultList();

		Iterator<Object[]> suspectResultIterator = suspectedResult.iterator();
		while (suspectResultIterator.hasNext()) {
			Object[] item = suspectResultIterator.next();
			String regionName = (item[1] == null) ? "" : (String) item[1];
			RegionWiseAuthentication regionWiseAuthentication = regionWiseGenuineResult
					.get(regionName);
			if (regionWiseAuthentication == null) {
				continue;
			}
			regionWiseAuthentication
					.mapToRegionWiseSuspectedAuthenticationCount(item);
			suspectResultIterator.remove();

		}

		for (Object[] authentications : suspectedResult) {
			RegionWiseAuthentication regionWiseAuthentication = new RegionWiseAuthentication();
			regionWiseAuthentication
					.mapToRegionWiseSuspectedAuthenticationCount(authentications);

			regionWiseGenuineResult.put(
					regionWiseAuthentication.getRegionName(),
					regionWiseAuthentication);

		}
	}

	private void calculateRegionWiseGenuine(Long tenantId, Long brandId,
			Map<String, RegionWiseAuthentication> regionWiseGenuineResult) {
		@SuppressWarnings("unchecked")
		List<Object[]> genuineResult = (List<Object[]>) em
				.createNativeQuery(REGIONWISE_AUTHENTICATIONS_TOTAL_GENUINE_SQL)
				.setParameter("tenantId", tenantId)
				.setParameter("brandId", brandId).getResultList();

		for (Object[] authentications : genuineResult) {
			RegionWiseAuthentication regionWiseAuthentication = new RegionWiseAuthentication();
			regionWiseAuthentication
					.mapToRegionWiseGenuineAuthenticationCount(authentications);

			regionWiseGenuineResult.put(
					regionWiseAuthentication.getRegionName(),
					regionWiseAuthentication);

		}

	}
}
