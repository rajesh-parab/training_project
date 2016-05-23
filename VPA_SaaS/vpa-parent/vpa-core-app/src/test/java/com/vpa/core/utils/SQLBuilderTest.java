package com.vpa.core.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.vpa.core.bo.CounterfeitEntityBO;
import com.vpa.core.dws.models.DimCompany;
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:vpa-core-app-test-data-source.xml")
@TransactionConfiguration(transactionManager = "transactionManagerDWS")
public class SQLBuilderTest {

    @Autowired
	private DriverManagerDataSource vpaDWSDS;

	private JdbcTemplate vpaDWJdbcTemplate = null;

	private JdbcTemplate getVpaDWJdbcTemplate() {
	 
			
		if (vpaDWJdbcTemplate == null) {
			vpaDWJdbcTemplate = new JdbcTemplate(vpaDWSDS);
		}
		return vpaDWJdbcTemplate;
	}
	@Test
	public void whereClause() {
		SQLQueryBuilder sqlBuilder = new SQLQueryBuilder();
		sqlBuilder.column("*").from("users").where("name = ?");
		//case sensitive test you can use assertTrue with equalIgnoreCase of string method.
		assertEquals(sqlBuilder.toString(),"select * from users where name = ?");
		
		
	}

	@Test
	public void getCounterfeitBuyingEntitiesForAuthentication(){
	 		
		CounterfeitEntityBO counterfeitEntityBO = new CounterfeitEntityBO();
		counterfeitEntityBO.setBrandId(22L);
		counterfeitEntityBO.setTenantId(22L);
		counterfeitEntityBO.setEntityTypeId(4);
    	String expectedSQL=	buildCounterfeitBuyingEntitySQLForAuthentication(counterfeitEntityBO );
		SQLQueryBuilder sqlBuilder = new SQLQueryBuilder();
		sqlBuilder=sqlBuilder.column("dc.id as entityId, dc.company_name as buyerName, sum(fa.product_units) as numberOfSuspectAuthentications, sum(dp.glp*fa.product_units) as totalProductGlp ");
		sqlBuilder=sqlBuilder.from(" fact_authentication fa, dim_tenant dt, dim_brand db, dim_authentication da, dim_product dp ");
		sqlBuilder=sqlBuilder.from("dim_entity_type det",counterfeitEntityBO.getEntityTypeId()+"");
		
		if (counterfeitEntityBO.getRegionId()>0 ) {
			sqlBuilder=sqlBuilder.from("dim_region dr");
		}
		if(counterfeitEntityBO.getCountryId()>0){
			sqlBuilder = sqlBuilder.from("dim_country dcon");
		}
		sqlBuilder=sqlBuilder.from("dim_level dl",counterfeitEntityBO.getLayerId()+"");
		if(counterfeitEntityBO.getFromTime()>0 || counterfeitEntityBO.getToTime()>0){
			sqlBuilder=sqlBuilder.from("dim_time t");
		}
		sqlBuilder=sqlBuilder.from("dim_company dc ");
		
		
		sqlBuilder=sqlBuilder.where("fa.tenant_id=dt.id AND fa.brand_id=db.id ").where("fa.company_id=dc.id ").where("fa.authentication_id=da.id "); 
		sqlBuilder=sqlBuilder.where("fa.product_id=dp.id ").where("da.final_result<>'Genuine' ");
		sqlBuilder=sqlBuilder.where("fa.tenant_id= ",counterfeitEntityBO.getTenantId());
		sqlBuilder=sqlBuilder.where("fa.brand_id= ",counterfeitEntityBO.getBrandId());
		sqlBuilder=sqlBuilder.where("fa.entity_type_id=det.id and det.entity_type_name=",counterfeitEntityBO.getEntityTypeId());
	
		if (counterfeitEntityBO.getRegionId()>0) {
			sqlBuilder=sqlBuilder.where("fa.region_id=dr.id ");
		}
		if( counterfeitEntityBO.getCountryId()>0){
			sqlBuilder=sqlBuilder.where("fa.country_id=dcon.id ");
		}

		sqlBuilder=sqlBuilder.where("dr.region_name=",counterfeitEntityBO.getRegionId());		 
		sqlBuilder=sqlBuilder.where("dr.country_id=",counterfeitEntityBO.getCountryId());		 
		sqlBuilder=sqlBuilder.where("fa.level_id=dl.id AND dl.level_name=",counterfeitEntityBO.getLayerId());		 
		 		
		if (counterfeitEntityBO.getFromTime()>0) {			
			sqlBuilder=sqlBuilder.where("fa.time_id=t.date_sk and fa.time_id>=",counterfeitEntityBO.getFromTime());
		}
		if (counterfeitEntityBO.getToTime()>0) {			
			sqlBuilder=sqlBuilder.where("fa.time_id=t.date_sk and fa.time_id<=",counterfeitEntityBO.getToTime());
		}
  		
		sqlBuilder=sqlBuilder.groupBy(" dc.company_name ").orderBy("numberOfSuspectAuthentications desc ");
	 
		List<DimCompany> list1  =this.getVpaDWJdbcTemplate().queryForList(expectedSQL, DimCompany.class);
		List<DimCompany> list2  =this.getVpaDWJdbcTemplate().queryForList(sqlBuilder.toString(), DimCompany.class);
		assertTrue(list1.size()==list2.size());
		int ctr=0;
		for(DimCompany dim1 : list1){
			DimCompany dim2 = list2.get(ctr);
			assertEquals(dim1.toString(),dim2.toString());
		}
		
	}

	private String buildCounterfeitBuyingEntitySQLForAuthentication(final CounterfeitEntityBO counterfeitEntityBO) {
		final long tenantId = counterfeitEntityBO.getTenantId();
		final long brandId = counterfeitEntityBO.getBrandId();
		final long entityTypeId = counterfeitEntityBO.getEntityTypeId();
		final long regionId = counterfeitEntityBO.getRegionId();
		final long layerId = counterfeitEntityBO.getLayerId();
		final long fromTime = counterfeitEntityBO.getFromTime();
		final long toTime = counterfeitEntityBO.getToTime();
		final long countryId = counterfeitEntityBO.getCountryId();
		
		final StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select ")
        	.append("dc.id as entityId, ")
        	.append("dc.company_name as buyerName, ")
        	.append("sum(fa.product_units) as numberOfSuspectAuthentications, ")
			.append("sum(dp.glp*fa.product_units) as totalProductGlp ")
		.append("from ")
			.append(" fact_authentication fa, ")
			.append("dim_tenant dt, ")
			.append("dim_brand db, ")
	        .append("dim_authentication da,  ")	        
	        .append("dim_product dp, ");
		
		String fromEntityType="";
		String fromLayer="";
		String whereEntityType="";
		String whereLayer="";
		
		
		
		if (entityTypeId>0) {
			fromEntityType = "dim_entity_type det, ";
			whereEntityType = "AND fa.entity_type_id=det.id and det.entity_type_name="+ entityTypeId + " ";
		}
		String fromRegion = StringUtils.EMPTY;
		StringBuilder whereRegion = new StringBuilder();		
		if (regionId>0) {
			fromRegion = "dim_region dr,";
			whereRegion.append("AND fa.region_id=dr.id AND dr.id=").append(regionId).append(" ");
		}
		String whereCountryId = StringUtils.EMPTY;
		if(countryId>0){
			whereCountryId = "AND dr.country_id="+countryId+" ";
		}
		
		if (layerId>0) {
			fromLayer = "dim_level dl,";
			whereLayer = "AND fa.level_id=dl.id AND dl.level_name=" + layerId + " ";
		}
		String fromTimePeriod = StringUtils.EMPTY;
		StringBuilder whereTimePeriod = new StringBuilder();
		
		if(fromTime>0 || toTime>0){
			fromTimePeriod = "dim_time t,";
		}
		
		if (fromTime>0) {			
			whereTimePeriod.append("and fa.time_id=t.date_sk and fa.time_id>=").append(fromTime).append(" ");
		}
		if(toTime>0){
			whereTimePeriod.append("and fa.time_id=t.date_sk and fa.time_id<=").append(toTime).append(" ");
		}			        
		queryBuilder.append(fromEntityType).append(fromRegion)
			.append(fromLayer).append(fromTimePeriod).append("dim_company dc ")
			.append("where ").append("fa.tenant_id=dt.id ").append("AND fa.brand_id=db.id ") 
			.append("AND fa.company_id=dc.id ").append("AND fa.authentication_id=da.id ") 
			.append("AND fa.product_id=dp.id ").append("AND da.final_result<>'Genuine' ") 
			.append("AND fa.tenant_id= ").append(tenantId).append(" ")
			.append("AND fa.brand_id= ").append(brandId).append(" ")
			.append(whereEntityType).append(whereRegion).append(whereLayer)
			.append(whereCountryId).append(whereTimePeriod)
			.append("group by ")
			.append("dc.company_name ")       
			.append("order by ")
			.append("numberOfSuspectAuthentications desc ");

		
		return queryBuilder.toString();
	}
}
