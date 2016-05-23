/**
 * 
 */
package com.vpa.core.dws.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.vpa.core.bo.CounterfeitEntityBO;
import com.vpa.core.dws.dao.CounterfeitEntityDao;
import com.vpa.core.dws.models.DimCompany;
import com.vpa.core.enums.Sorting;

/**
 * @author PD42694
 *
 */
public class DimCompanyDaoImpl implements CounterfeitEntityDao {

	private static final String COUNTERFEIT_BUYING_SELECT_SQL = "select dc.id,dc.company_name,sum(dp.glp*fa.product_units) as totalProductGlp,sum(product_units) as suspectAuthentication ";

	private static final String COUNTERFEIT_BUYING_FROM_SQL = " from fact_authentication fa, dim_brand db, "
			+ "dim_tenant dt,  dim_company dc,dim_authentication da, dim_product dp, dim_entity_type det where fa.tenant_id=dt.id "
			+ "and fa.brand_id=db.id and fa.company_id=dc.id and fa.authentication_id=da.id  and fa.product_id=dp.id "
			+ "and dc.entity_type_id=det.id and da.final_result<>'Genuine' "
			+ "and fa.tenant_id= ? and fa.brand_id= ?  group by  dc.company_name "
			+ "order by  totalProductGlp desc";

	private static final String COUNTERFEIT_BUYING_ENTITIES_SQL = COUNTERFEIT_BUYING_SELECT_SQL
			+ ",det.entity_type_name as entityType  "
			+ COUNTERFEIT_BUYING_FROM_SQL;

	private static final String TOP_COUNTERFEIT_BUYING_ENTITIES_SQL = COUNTERFEIT_BUYING_SELECT_SQL
			+ COUNTERFEIT_BUYING_FROM_SQL;

	private static final String COUNTERFEIT_SELLING_ENTITIES_SELECT_SQL = "select ds.id,ds.seller_name as sellingEntity, "
			+ " sum(dp.glp*fa.product_units) as revenueLoss, sum(product_units) as suspectAuthentication ";

	private static final String COUNTERFEIT_SELLING_ENTITIES_FROM_SQL = " from fact_authentication fa, dim_brand db, dim_tenant dt, dim_authentication da, "
			+ "dim_product dp, dim_seller ds where fa.tenant_id=dt.id and fa.brand_id=db.id and fa.authentication_id=da.id  and fa.product_id=dp.id "
			+ "and fa.seller_id=ds.id   and da.final_result<>'Genuine' and fa.tenant_id= ? "
			+ "and fa.brand_id= ?  group by "
			+ "ds.seller_name  order by  revenueLoss desc";

	private static final String COUNTERFEIT_SELLING_ENTITIES_SQL = COUNTERFEIT_SELLING_ENTITIES_SELECT_SQL
			+ ", ds.seller_city as sellerCity, ds.seller_state as sellerState,ds.seller_country as sellerCountry, "
			+ "ds.seller_type as sellerType, ds.seller_web_address as sellerWebAddress "
			+ COUNTERFEIT_SELLING_ENTITIES_FROM_SQL;

	private static final String TOP_COUNTERFEIT_SELLING_ENTITIES_SQL = COUNTERFEIT_SELLING_ENTITIES_SELECT_SQL
			+ COUNTERFEIT_SELLING_ENTITIES_FROM_SQL;

	private static final String TOTAL_AUTHENTICATION_FOR_BUYING_ENTITY_SQL = "select sum(fa.product_units) as totalNumberOfAuthentications "
			+ "from fact_authentication fa, dim_brand db, dim_tenant dt, dim_company dc where fa.tenant_id=dt.id and fa.brand_id=db.id "
			+ "and fa.company_id=dc.id and fa.tenant_id=? and fa.brand_id= ? "
			+ "and dc.company_name=?";

	private static final String TOTAL_AUTHENTICATION_FOR_SELLING_ENTITY_SQL = "select sum(fa.product_units) as suspectAuthentication "
			+ "from fact_authentication fa, dim_brand db, dim_tenant dt, dim_seller ds where fa.tenant_id=dt.id and fa.brand_id=db.id "
			+ "and fa.seller_id=ds.id and fa.tenant_id=? and fa.brand_id=? and fa.seller_id=? ";

	@PersistenceContext(unitName = "vpaDWPU")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<DimCompany> getCounterfeitBuyingEntities(final long tenantId,
			final long brandId) {

		final Query query = em
				.createNativeQuery(COUNTERFEIT_BUYING_ENTITIES_SQL);
		query.setParameter(1, tenantId);
		query.setParameter(2, brandId);
		final List<Object[]> queryResult = query.getResultList();
		final List<DimCompany> result = new ArrayList<>();
		for (final Object[] arr : queryResult) {
			final DimCompany dimCompany = new DimCompany();
			final Integer entityId = (Integer) ((null == arr[0]) ? 0 : arr[0]);
			dimCompany.setId(entityId);
			dimCompany.setEntityName((String) arr[1]);
			dimCompany.setRevenueLoss((null == arr[2]) ? 0.0 : (Double) arr[2]);
			dimCompany
					.setNumberOfSuspectAuthentications((null == arr[3]) ? BigDecimal.ZERO
							: (BigDecimal) arr[3]);
			dimCompany.setEntityType((String) arr[4]);
			dimCompany
					.setTotalNumberOfAuthentications(getTotalAuthenticationByComapny(
							tenantId, brandId, dimCompany.getEntityName()));
			result.add(dimCompany);
		}

		return result;
	}

	@Override
	public List<DimCompany> getCounterfeitBuyingEntities(final long tenantId,
			final long brandId, final int totalNumberOfRecords) {
		final Query query = em
				.createNativeQuery(TOP_COUNTERFEIT_BUYING_ENTITIES_SQL);
		query.setParameter(1, tenantId);
		query.setParameter(2, brandId);
		query.setMaxResults(totalNumberOfRecords);
		@SuppressWarnings("unchecked")
		final List<Object[]> queryResult = query.getResultList();
		final List<DimCompany> result = new ArrayList<>();
		for (final Object[] arr : queryResult) {
			final DimCompany dimCompany = new DimCompany();
			final Integer entityId = (Integer) ((null == arr[0]) ? 0 : arr[0]);
			dimCompany.setId(entityId);
			dimCompany.setEntityName((String) arr[1]);
			dimCompany.setRevenueLoss((null == arr[2]) ? 0.0 : (Double) arr[2]);
			dimCompany
					.setNumberOfSuspectAuthentications((null == arr[3]) ? BigDecimal.ZERO
							: (BigDecimal) arr[3]);
			result.add(dimCompany);
		}
		return result;
	}

	@Override
	public List<DimCompany> getCounterfeitSellingEntities(final long tenantId,
			final long brandId) {
		final Query query = em
				.createNativeQuery(COUNTERFEIT_SELLING_ENTITIES_SQL);
		query.setParameter(1, tenantId);
		query.setParameter(2, brandId);
		@SuppressWarnings("unchecked")
		final List<Object[]> queryResult = query.getResultList();
		final List<DimCompany> result = new ArrayList<>();
		for (final Object[] arr : queryResult) {
			final DimCompany dimCompany = new DimCompany();
			Integer entityId = (Integer) ((null == arr[0]) ? 0 : arr[0]);
			dimCompany.setId(entityId);
			dimCompany.setEntityName((String) arr[1]);
			dimCompany.setRevenueLoss((arr[2] == null) ? 0.0 : (Double) arr[2]);
			dimCompany
					.setNumberOfSuspectAuthentications((arr[3] == null) ? BigDecimal.ZERO
							: (BigDecimal) arr[3]);
			dimCompany.setSellerCity((String) arr[4]);
			dimCompany.setSellerState((String) arr[5]);
			dimCompany.setSellerCountry((String) arr[6]);
			dimCompany.setSellerType((String) arr[7]);
			dimCompany.setSellerWebAddress((String) arr[8]);
			dimCompany
					.setTotalNumberOfAuthentications(getTotalAuthenticationBySeller(
							tenantId, brandId, entityId));
			result.add(dimCompany);
		}
		return result;
	}

	@Override
	public List<DimCompany> getCounterfeitSellingEntities(final long tenantId,
			final long brandId, final int numberOfRecords) {
		final Query query = em
				.createNativeQuery(TOP_COUNTERFEIT_SELLING_ENTITIES_SQL);
		query.setParameter(1, tenantId);
		query.setParameter(2, brandId);
		query.setMaxResults(numberOfRecords);
		@SuppressWarnings("unchecked")
		final List<Object[]> queryResult = query.getResultList();
		final List<DimCompany> result = new ArrayList<>();
		for (final Object[] arr : queryResult) {
			final DimCompany dimCompany = new DimCompany();
			Integer entityId = (Integer) ((null == arr[0]) ? 0 : arr[0]);
			dimCompany.setId(entityId);
			dimCompany.setEntityName((String) arr[1]);
			dimCompany.setRevenueLoss((arr[2] == null) ? 0.0 : (Double) arr[2]);
			dimCompany
					.setNumberOfSuspectAuthentications((arr[3] == null) ? BigDecimal.ZERO
							: (BigDecimal) arr[3]);
			result.add(dimCompany);
		}

		return result;
	}

	@Override
	public BigDecimal getTotalAuthenticationByComapny(final long tenantId,
			final long brandId, final String companyName) {
		final Query query = em
				.createNativeQuery(TOTAL_AUTHENTICATION_FOR_BUYING_ENTITY_SQL);
		query.setParameter(1, tenantId);
		query.setParameter(2, brandId);
		query.setParameter(3, companyName);
		@SuppressWarnings("unchecked")
		final List<Object[]> queryResult = query.getResultList();
		BigDecimal totalAuthentications = BigDecimal.ZERO;
		for (@SuppressWarnings("rawtypes")
		final Iterator iterator = queryResult.iterator(); iterator.hasNext();) {
			totalAuthentications = (BigDecimal) iterator.next();
		}
		return totalAuthentications;
	}

	@Override
	public BigDecimal getTotalAuthenticationBySeller(final long tenantId,
			final long brandId, final Integer sellerId) {

		final Query query = em
				.createNativeQuery(TOTAL_AUTHENTICATION_FOR_SELLING_ENTITY_SQL);
		query.setParameter(1, tenantId);
		query.setParameter(2, brandId);
		query.setParameter(3, sellerId);
		@SuppressWarnings("unchecked")
		final List<Object[]> queryResult = query.getResultList();
		BigDecimal totalAuthentications = BigDecimal.ZERO;
		for (@SuppressWarnings("rawtypes")
		final Iterator iterator = queryResult.iterator(); iterator.hasNext();) {
			totalAuthentications = (BigDecimal) iterator.next();
		}
		return totalAuthentications;
	}

	/*
	 * @Deprecated private String generateSQLFromSQLBuilder(final
	 * CounterfeitEntityBO counterfeitEntityBO) {
	 * 
	 * SQLQueryBuilder sqlBuilder = new SQLQueryBuilder();
	 * sqlBuilder=sqlBuilder.column(
	 * "dc.id as entityId, dc.company_name as buyerName, sum(fa.product_units) as numberOfSuspectAuthentications, sum(dp.glp*fa.product_units) as totalProductGlp "
	 * ); sqlBuilder=sqlBuilder.from(
	 * " fact_authentication fa, dim_tenant dt, dim_brand db, dim_authentication da, dim_product dp "
	 * ); sqlBuilder=sqlBuilder.from("dim_entity_type det",counterfeitEntityBO.
	 * getEntityType());
	 * 
	 * if (StringUtils.isNotBlank(counterfeitEntityBO.getRegion()) ||
	 * counterfeitEntityBO.getCountryId()>0) {
	 * sqlBuilder=sqlBuilder.from("dim_region dr"); }
	 * sqlBuilder=sqlBuilder.from(
	 * "dim_level dl",counterfeitEntityBO.getLayer());
	 * if(counterfeitEntityBO.getFromTime()>0 ||
	 * counterfeitEntityBO.getToTime()>0){
	 * sqlBuilder=sqlBuilder.from("dim_time t"); }
	 * sqlBuilder=sqlBuilder.from("dim_company dc ");
	 * 
	 * 
	 * sqlBuilder=sqlBuilder.where("fa.tenant_id=dt.id AND fa.brand_id=db.id ").
	 * where("fa.company_id=dc.id ").where("fa.authentication_id=da.id ");
	 * sqlBuilder
	 * =sqlBuilder.where("fa.product_id=dp.id ").where("da.final_result<>'Genuine' "
	 * );
	 * sqlBuilder=sqlBuilder.where("fa.tenant_id= ",counterfeitEntityBO.getTenantId
	 * ());
	 * sqlBuilder=sqlBuilder.where("fa.brand_id= ",counterfeitEntityBO.getBrandId
	 * ()); sqlBuilder=sqlBuilder.where(
	 * "fa.entity_type_id=det.id and det.entity_type_name="
	 * ,counterfeitEntityBO.getEntityType());
	 * 
	 * if (StringUtils.isNotBlank(counterfeitEntityBO.getRegion()) ||
	 * counterfeitEntityBO.getCountryId()>0) {
	 * sqlBuilder=sqlBuilder.where("fa.region_id=dr.id "); }
	 * 
	 * sqlBuilder=sqlBuilder.where("dr.region_name=",counterfeitEntityBO.getRegion
	 * ());
	 * sqlBuilder=sqlBuilder.where("dr.country_id=",counterfeitEntityBO.getCountryId
	 * ()); sqlBuilder=sqlBuilder.where("fa.level_id=dl.id AND dl.level_name=",
	 * counterfeitEntityBO.getLayer());
	 * 
	 * if (counterfeitEntityBO.getFromTime()>0) {
	 * sqlBuilder=sqlBuilder.where("fa.time_id=t.date_sk and fa.time_id>="
	 * ,counterfeitEntityBO.getFromTime()); } if
	 * (counterfeitEntityBO.getToTime()>0) {
	 * sqlBuilder=sqlBuilder.where("fa.time_id=t.date_sk and fa.time_id<="
	 * ,counterfeitEntityBO.getToTime()); }
	 * 
	 * sqlBuilder=sqlBuilder.groupBy(" dc.company_name ").orderBy(
	 * "numberOfSuspectAuthentications desc ");
	 * 
	 * 
	 * return sqlBuilder.toString(); }
	 */
	@Override
	public List<DimCompany> getCounterfeitBuyingEntitiesForAuthentication(
			final CounterfeitEntityBO counterfeitEntityBO) {

		final long tenantId = counterfeitEntityBO.getTenantId();
		final long brandId = counterfeitEntityBO.getBrandId();

		final long entityTypeId = counterfeitEntityBO.getEntityTypeId();
		final long regionId = counterfeitEntityBO.getRegionId();
		final long layerId = counterfeitEntityBO.getLayerId();
		final long fromTime = counterfeitEntityBO.getFromTime();
		final long toTime = counterfeitEntityBO.getToTime();

		final long countryId = counterfeitEntityBO.getCountryId();
		final String orderBy = counterfeitEntityBO.getOrderBy();

		final StringBuilder queryBuilder = new StringBuilder();
		queryBuilder
				.append("select ")
				.append("dc.id as entityId, ")
				.append("dc.company_name as buyerName, ")
				.append("sum(fa.product_units) as numberOfSuspectAuthentications, ")
				.append("sum(dp.glp*fa.product_units) as totalProductGlp ")
				.append("from ").append(" fact_authentication fa, ")
				.append("dim_tenant dt, ").append("dim_brand db, ")
				.append("dim_authentication da,  ").append("dim_product dp, ");

		String fromEntityType = StringUtils.EMPTY;
		String whereEntityType = StringUtils.EMPTY;

		if (entityTypeId > 0) {
			fromEntityType = "dim_entity_type det, ";
			whereEntityType = "AND fa.entity_type_id=det.id and det.id="
					+ entityTypeId + " ";
		}
		String fromRegion = StringUtils.EMPTY;
		StringBuilder whereRegion = new StringBuilder();
		if (regionId > 0) {
			fromRegion = "dim_region dr, ";
			whereRegion.append("AND fa.region_id=dr.id AND dr.id=")
					.append(regionId).append(" ");
		}

		String fromCountry = StringUtils.EMPTY;
		String whereCountryId = StringUtils.EMPTY;
		if (countryId > 0) {
			fromCountry = "dim_country dcon, ";
			whereCountryId = "AND fa.country_id=dcon.id AND dcon.id="
					+ countryId + " ";
		}

		String fromLayer = StringUtils.EMPTY;
		String whereLayer = StringUtils.EMPTY;

		if (layerId > 0) {
			fromLayer = "dim_level dl, ";
			whereLayer = "AND fa.level_id=dl.id AND dl.id=" + layerId + " ";
		}
		String fromTimePeriod = StringUtils.EMPTY;
		StringBuilder whereTimePeriod = new StringBuilder();

		if (fromTime > 0 || toTime > 0) {
			fromTimePeriod = "dim_time t, ";
		}

		if (fromTime > 0) {
			whereTimePeriod.append("and fa.time_id=t.date_sk and fa.time_id>=")
					.append(fromTime).append(" ");
		}
		if (toTime > 0) {
			whereTimePeriod.append("and fa.time_id=t.date_sk and fa.time_id<=")
					.append(toTime).append(" ");
		}
		queryBuilder.append(fromEntityType).append(fromRegion)
				.append(fromLayer).append(fromTimePeriod).append(fromCountry)
				.append("dim_company dc ").append("where ")
				.append("fa.tenant_id=dt.id ").append("AND fa.brand_id=db.id ")
				.append("AND fa.company_id=dc.id ")
				.append("AND fa.authentication_id=da.id ")
				.append("AND fa.product_id=dp.id ")
				.append("AND da.final_result<>'Genuine' ")
				.append("AND fa.tenant_id= ").append(tenantId).append(" ")
				.append("AND fa.brand_id= ").append(brandId).append(" ")
				.append(whereEntityType).append(whereRegion).append(whereLayer)
				.append(whereCountryId).append(whereTimePeriod)
				.append("group by ").append("dc.company_name ")
				.append("order by ");
		if (StringUtils.isNotBlank(orderBy)) {
			if (StringUtils.equalsIgnoreCase(orderBy,
					Sorting.SUSPECTAUTHENTICATION.toString())) {
				queryBuilder.append("numberOfSuspectAuthentications desc ");
			} else if (StringUtils.equalsIgnoreCase(orderBy,
					Sorting.REVENUE.toString())) {
				queryBuilder.append("totalProductGlp desc ");
			}
		}
		final int numberOfRecords = counterfeitEntityBO.getNumberOfRecords();
		final int pageNumber = counterfeitEntityBO.getPageNumber();

		return fetchCounterfeitEntities(queryBuilder.toString(),
				numberOfRecords, pageNumber);
	}

	@Override
	public List<DimCompany> getCounterfeitSellingEntitiesForAuthentication(
			final CounterfeitEntityBO counterfeitEntityBO) {

		final long tenantId = counterfeitEntityBO.getTenantId();
		final long brandId = counterfeitEntityBO.getBrandId();
		final int numberOfRecords = counterfeitEntityBO.getNumberOfRecords();
		final long entityTypeId = counterfeitEntityBO.getEntityTypeId();
		final long regionId = counterfeitEntityBO.getRegionId();
		final long layerId = counterfeitEntityBO.getLayerId();
		final long fromTime = counterfeitEntityBO.getFromTime();
		final long toTime = counterfeitEntityBO.getToTime();
		final int pageNumber = counterfeitEntityBO.getPageNumber();
		final long countryId = counterfeitEntityBO.getCountryId();
		final StringBuilder queryBuilder = new StringBuilder();
		final String orderBy = counterfeitEntityBO.getOrderBy();
		queryBuilder
				.append("select ")
				.append("ds.id as entityId, ")
				.append("ds.seller_name as sellerName, ")
				.append("sum(fa.product_units) as numberOfSuspectAuthentications, ")
				.append("sum(dp.glp*fa.product_units) as totalProductGlp ")
				.append("from ").append("fact_authentication fa, ")
				.append("dim_brand db, ").append("dim_tenant dt, ")
				.append("dim_authentication da, ").append("dim_product dp, ");

		String fromEntityType = StringUtils.EMPTY;
		String whereEntityType = StringUtils.EMPTY;

		if (entityTypeId > 0) {
			fromEntityType = "dim_entity_type det, ";
			whereEntityType = "AND fa.entity_type_id=det.id AND det.id="
					+ entityTypeId + " ";
		}
		String fromRegion = StringUtils.EMPTY;
		StringBuilder whereRegion = new StringBuilder();
		if (regionId > 0) {
			fromRegion = "dim_region dr, ";
			whereRegion.append("AND fa.region_id=dr.id AND dr.id=")
					.append(regionId).append(" ");
		}
		String fromCountry = StringUtils.EMPTY;
		String whereCountryId = StringUtils.EMPTY;
		if (countryId > 0) {
			fromCountry = "dim_country dcon, ";
			whereCountryId = "AND fa.country_id=dcon.id AND dcon.id="
					+ countryId + " ";
		}

		String fromLayer = StringUtils.EMPTY;
		String whereLayer = StringUtils.EMPTY;

		if (layerId > 0) {
			fromLayer = "dim_level dl, ";
			whereLayer = "and fa.level_id=dl.id and dl.id=" + layerId + " ";
		}
		String fromTimePeriod = StringUtils.EMPTY;
		StringBuilder whereTimePeriod = new StringBuilder();

		if (fromTime > 0 || toTime > 0) {
			fromTimePeriod = "dim_time t, ";
		}

		if (fromTime > 0) {
			whereTimePeriod.append("and fa.time_id=t.date_sk and fa.time_id>=")
					.append(fromTime).append(" ");
		}
		if (toTime > 0) {
			whereTimePeriod.append("and fa.time_id=t.date_sk and fa.time_id<=")
					.append(toTime).append(" ");
		}

		queryBuilder.append(fromEntityType).append(fromRegion)
				.append(fromLayer).append(fromTimePeriod).append(fromCountry)
				.append("dim_seller ds ");
		queryBuilder.append("where ").append("fa.tenant_id=dt.id ")
				.append("and fa.brand_id=db.id ")
				.append("and fa.authentication_id=da.id ")
				.append("and fa.seller_id=ds.id ")
				.append("and fa.product_id=dp.id ")
				.append("and da.final_result<>'Genuine' ")
				.append("and fa.tenant_id= ").append(tenantId).append(" ")
				.append("and fa.brand_id= ").append(brandId).append(" ")
				.append(whereEntityType).append(whereRegion).append(whereLayer)
				.append(whereCountryId).append(whereTimePeriod);
		queryBuilder.append("group by ").append("ds.seller_name ")
				.append("order by ");

		if (StringUtils.isNotBlank(orderBy)) {
			if (StringUtils.equalsIgnoreCase(orderBy,
					Sorting.SUSPECTAUTHENTICATION.toString())) {
				queryBuilder.append("numberOfSuspectAuthentications desc ");
			} else if (StringUtils.equalsIgnoreCase(orderBy,
					Sorting.REVENUE.toString())) {
				queryBuilder.append("totalProductGlp desc ");
			}
		}

		return fetchCounterfeitEntities(queryBuilder.toString(),
				numberOfRecords, pageNumber);
	}

	private List<DimCompany> fetchCounterfeitEntities(final String queryString,
			final int numberOfRecords, final int pageNumber) {
		final Query query = em.createNativeQuery(queryString);
		if (numberOfRecords > 0) {
			query.setMaxResults(numberOfRecords);
		}
		if (pageNumber > 0) {
			query.setFirstResult((pageNumber - 1) * numberOfRecords);
		}
		@SuppressWarnings("unchecked")
		final List<Object[]> queryResult = query.getResultList();
		final List<DimCompany> result = new ArrayList<>();
		for (final Object[] arr : queryResult) {
			final DimCompany dimCompany = new DimCompany();
			dimCompany.setId((Integer) arr[0]);
			dimCompany.setEntityName((String) arr[1]);
			dimCompany
					.setNumberOfSuspectAuthentications((arr[2] == null) ? BigDecimal.ZERO
							: (BigDecimal) arr[2]);
			dimCompany.setRevenueLoss((null == arr[3]) ? 0.0 : (Double) arr[3]);
			result.add(dimCompany);
		}
		return result;
	}

	@Override
	public List<DimCompany> getSellingEntities(
			CounterfeitEntityBO counterfeitEntityBO) {
		final int numberOfRecords = counterfeitEntityBO.getNumberOfRecords();
		final int pageNumber = counterfeitEntityBO.getPageNumber();

		final long tenantId = counterfeitEntityBO.getTenantId();
		final long brandId = counterfeitEntityBO.getBrandId();

		final long regionId = counterfeitEntityBO.getRegionId();
		final long fromTime = counterfeitEntityBO.getFromTime();
		final long toTime = counterfeitEntityBO.getToTime();

		final long countryId = counterfeitEntityBO.getCountryId();
		final String orderBy = counterfeitEntityBO.getOrderBy();

		final StringBuilder queryBuilder = new StringBuilder();
		queryBuilder
				.append("SELECT ").append("ge.entityId AS entityId, ")
				.append("ge.entityName AS entityName, ").append("ge.entityType AS entityType, ")
				.append("ge.countryName AS countryName, ").append("COUNT(DISTINCT ge.suspectProducts) AS suspectProduct, ")
				.append("SUM(ge.counterfeitCount) AS suspectAuthentication, ").append("SUM(ge.productUnit) AS totalAuthentication, ")
				.append("SUM(ge.glpLoss) AS revenueLoss ").append("FROM ")
				.append("(SELECT ").append("ds.id AS entityId, ")
				.append("ds.seller_name AS entityName, ").append("fa.product_units AS productUnit, ")
				.append("da.final_result AS result, ").append("dp.glp AS glp, ")
				.append("dp.id AS productId, ").append("ds.seller_type AS entityType, ")
				.append("dcon.country_name AS countryName, ").append("(CASE ")
				.append("WHEN da.final_result = 'Genuine' THEN (1 * fa.product_units) ")
				.append("ELSE 0 ").append("END) AS genuineCount, ")
				.append("(CASE ").append("WHEN da.final_result <> 'Genuine' THEN (1 * fa.product_units) ")
				.append("ELSE 0 ").append("END) AS counterfeitCount, ")
				.append("(CASE ").append("WHEN da.final_result <> 'Genuine' THEN (1 * fa.product_units * dp.glp) ")
				.append("ELSE 0 ").append("END) AS glpLoss, ").append("(CASE ")
				.append("WHEN da.final_result <> 'Genuine' THEN dp.id ")
				.append("END) AS suspectProducts ").append("FROM ")
				.append("fact_authentication fa, ").append("dim_tenant dt, ")
				.append("dim_brand db, ").append("dim_authentication da, ")
				.append("dim_product dp, ").append("dim_country dcon, ");
		String fromRegion = StringUtils.EMPTY;
		String whereRegion = StringUtils.EMPTY;
		if (regionId > 0) {
			fromRegion = "dim_region dr, ";
			whereRegion = "AND fa.region_id = dr.id AND dcon.region_id =  " + regionId
					+ " ";
		}
		String whereCountryId = StringUtils.EMPTY;
		if (countryId > 0) {
			whereCountryId = "AND dcon.id = " + countryId + " ";
		}
		String fromTimePeriod = StringUtils.EMPTY;
		StringBuilder whereTimePeriod = new StringBuilder();

		if (fromTime > 0 || toTime > 0) {
			fromTimePeriod = "dim_time t, ";
			whereTimePeriod.append("AND fa.time_id = t.date_sk ");
			if (fromTime > 0) {
				whereTimePeriod.append("AND t.date_sk >= ").append(fromTime)
						.append("  ");
			}
			if (toTime > 0) {
				whereTimePeriod.append("AND t.date_sk <= ").append(toTime)
						.append("  ");
			}
		}
		queryBuilder.append(fromRegion).append(fromTimePeriod)
				.append("dim_seller ds ").append("WHERE ")
				.append("fa.tenant_id = dt.id ")
				.append("AND fa.brand_id = db.id ")
				.append("AND fa.seller_id=ds.id ")
				.append("AND fa.authentication_id = da.id ")
				.append("AND fa.product_id = dp.id ")
				.append("AND ds.seller_country = dcon.country_name ")
				.append(whereRegion).append(whereCountryId)
				.append(whereTimePeriod).append("AND fa.tenant_id = ")
				.append(tenantId).append(" ").append("AND fa.brand_id = ")
				.append(brandId).append(" ").append(") AS ge ")
				.append("GROUP BY entityName ").append("ORDER BY ");

		// Shorting Order

		if (StringUtils.isNotBlank(orderBy)) {
			buildOrderByQuery(queryBuilder, orderBy);
		}
		final Query query = em.createNativeQuery(queryBuilder.toString());
		if (numberOfRecords > 0) {
			query.setMaxResults(numberOfRecords);
		}
		if (pageNumber > 0) {
			query.setFirstResult((pageNumber - 1) * numberOfRecords);
		}
		@SuppressWarnings("unchecked")
		final List<Object[]> queryResult = query.getResultList();
		final List<DimCompany> result = new ArrayList<>();
		for (Object[] entity : queryResult) {
			DimCompany dimCompany = new DimCompany();
			dimCompany.setId((Integer) entity[0]);
			dimCompany.setEntityName((String) entity[1]);
			dimCompany.setEntityType((String) entity[2]);
			dimCompany.setSellerCountry((String) entity[3]);
			dimCompany.setFlaggedProducts((BigInteger) entity[4]);
			dimCompany
					.setNumberOfSuspectAuthentications((BigDecimal) entity[5]);
			dimCompany.setTotalNumberOfAuthentications((BigDecimal) entity[6]);
			dimCompany.setRevenueLoss((Double) entity[7]);
			result.add(dimCompany);
		}

		return result;
	}

	@Override
	public List<DimCompany> getBuyingEntities(
			CounterfeitEntityBO counterfeitEntityBO) {
		
		final int numberOfRecords = counterfeitEntityBO.getNumberOfRecords();
		final int pageNumber = counterfeitEntityBO.getPageNumber();
		final long tenantId = counterfeitEntityBO.getTenantId();
		final long brandId = counterfeitEntityBO.getBrandId();
		final long entityTypeId = counterfeitEntityBO.getEntityTypeId();
		final long regionId = counterfeitEntityBO.getRegionId();
		final long fromTime = counterfeitEntityBO.getFromTime();
		final long toTime = counterfeitEntityBO.getToTime();
		final long countryId = counterfeitEntityBO.getCountryId();
		final String orderBy = counterfeitEntityBO.getOrderBy();
		final StringBuilder queryBuilder = new StringBuilder();
		queryBuilder
				.append("SELECT ").append("ge.entityId AS entityId, ").append("ge.entityName AS entityName, ")
				.append("ge.entityType AS entityType, ").append("ge.countryName AS countryName, ")
				.append("COUNT(DISTINCT ge.suspectProducts) AS suspectProduct, ").append("SUM(ge.counterfeitCount) AS suspectAuthentication, ")
				.append("SUM(ge.productUnit) AS totalAuthentication, ")	.append("SUM(ge.glpLoss) AS revenueLoss, ")
				.append("COUNT(DISTINCT ge.userId) AS activeUsers ").append("FROM ")
				.append("(SELECT ")	.append("dc.id AS entityId, ")
				.append("dc.company_name AS entityName, ").append("fa.product_units AS productUnit, ")
				.append("da.final_result AS result, ").append("dp.glp AS glp, ")
				.append("dp.id AS productId, ").append("det.entity_type_name AS entityType, ")
				.append("dcon.country_name AS countryName, ").append("(CASE ")
				.append("WHEN da.final_result = 'Genuine' THEN (1 * fa.product_units) ")
				.append("ELSE 0 ").append("END) AS genuineCount, ")
				.append("(CASE ").append("WHEN da.final_result <> 'Genuine' THEN (1 * fa.product_units) ")
				.append("ELSE 0 ").append("END) AS counterfeitCount, ")
				.append("(CASE ").append("WHEN da.final_result <> 'Genuine' THEN (1 * fa.product_units * dp.glp) ")
				.append("ELSE 0 ").append("END) AS glpLoss, ").append("(CASE ")
				.append("WHEN da.final_result <> 'Genuine' THEN dp.id ")
				.append("END) AS suspectProducts, ").append("du.id as userId ")
				.append("FROM ").append("fact_authentication fa, ")
				.append("dim_tenant dt, ").append("dim_brand db, ")
				.append("dim_authentication da, ").append("dim_product dp, ")
				.append("dim_user du, ").append("dim_country dcon, ")
				.append("dim_entity_type det, ");
		String fromRegion = StringUtils.EMPTY;
		String whereRegion = StringUtils.EMPTY;
		if (regionId > 0) {
			fromRegion = "dim_region dr, ";
			whereRegion = "AND fa.region_id = dr.id AND dr.id =  " + regionId
					+ " ";
		}
		String whereCountryId = StringUtils.EMPTY;
		if (countryId > 0) {
			whereCountryId = "AND dcon.id = " + countryId + " ";
		}
		String fromTimePeriod = StringUtils.EMPTY;
		StringBuilder whereTimePeriod = new StringBuilder();

		if (fromTime > 0 || toTime > 0) {
			fromTimePeriod = "dim_time t, ";
			whereTimePeriod.append("AND fa.time_id = t.date_sk ");
			if (fromTime > 0) {
				whereTimePeriod.append("AND t.date_sk >= ").append(fromTime)
						.append("  ");
			}
			if (toTime > 0) {
				whereTimePeriod.append("AND t.date_sk <= ").append(toTime)
						.append("  ");
			}
		}
		String whereEntityType = StringUtils.EMPTY;
		if (entityTypeId > 0) {
			whereEntityType = "AND det.id = " + entityTypeId + " ";
		}
		queryBuilder.append(fromRegion).append(fromTimePeriod)
				.append("dim_company dc ").append("WHERE ")
				.append("fa.tenant_id = dt.id ")
				.append("AND fa.brand_id = db.id ")
				.append("AND fa.company_id = dc.id ")
				.append("AND fa.authentication_id = da.id ")
				.append("AND fa.product_id = dp.id ")
				.append("AND fa.user_id=du.id ")
				.append("AND fa.country_id = dcon.id ")
				.append("AND fa.entity_type_id = det.id ").append(whereRegion)
				.append(whereCountryId).append(whereTimePeriod)
				.append(whereEntityType).append("AND fa.tenant_id = ")
				.append(tenantId).append(" ").append("AND fa.brand_id = ")
				.append(brandId).append(" ").append(") AS ge ")
				.append("GROUP BY entityName ").append("ORDER BY ");

		// Shorting Order

		if (StringUtils.isNotBlank(orderBy)) {
			buildOrderByQuery(queryBuilder, orderBy);
		}
		final Query query = em.createNativeQuery(queryBuilder.toString());
		if (numberOfRecords > 0) {
			query.setMaxResults(numberOfRecords);
		}
		if (pageNumber > 0) {
			query.setFirstResult((pageNumber - 1) * numberOfRecords);
		}
		@SuppressWarnings("unchecked")
		final List<Object[]> queryResult = query.getResultList();
		final List<DimCompany> result = new ArrayList<>();
		for (Object[] entity : queryResult) {
			DimCompany dimCompany = new DimCompany();
			dimCompany.setId((Integer) entity[0]);
			dimCompany.setEntityName((String) entity[1]);
			dimCompany.setEntityType((String) entity[2]);
			dimCompany.setSellerCountry((String) entity[3]);
			dimCompany.setFlaggedProducts((BigInteger) entity[4]);
			dimCompany
					.setNumberOfSuspectAuthentications((BigDecimal) entity[5]);
			dimCompany.setTotalNumberOfAuthentications((BigDecimal) entity[6]);
			dimCompany.setRevenueLoss((Double) entity[7]);
			dimCompany.setActiveUsers((BigInteger) entity[8]);
			result.add(dimCompany);
		}

		return result;
	}

	private void buildOrderByQuery(StringBuilder queryBuilder, String orderBy) {
		if(StringUtils.equalsIgnoreCase(orderBy, Sorting.REVENUE.toString())){
			queryBuilder.append("glpLoss DESC");
		}else if(StringUtils.equalsIgnoreCase(orderBy, Sorting.SUSPECTAUTHENTICATION.toString())){
			queryBuilder.append("suspectAuthentication DESC");
		}else if(StringUtils.equalsIgnoreCase(orderBy, Sorting.TOTALAUTHENTICATION.toString())){
			queryBuilder.append("totalAuthentication DESC");
		}else if(StringUtils.equalsIgnoreCase(orderBy, Sorting.SUSPECT_PRODUCTS.toString())){
			queryBuilder.append("suspectProduct DESC");
		}else if(StringUtils.equalsIgnoreCase(orderBy, Sorting.COUNTRY.toString())){
			queryBuilder.append("countryName ASC");
		}else if(StringUtils.equalsIgnoreCase(orderBy, Sorting.ENTITY_TYPE.toString())){
			queryBuilder.append("entityType ASC");
		}else if(StringUtils.equalsIgnoreCase(orderBy, Sorting.ENTITY_NAME.toString())){
			queryBuilder.append("entityName ASC");
		}
	}

}
