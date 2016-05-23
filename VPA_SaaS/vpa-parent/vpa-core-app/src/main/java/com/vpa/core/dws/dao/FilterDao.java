package com.vpa.core.dws.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vpa.core.dws.models.DimRegion;
import com.vpa.core.models.LabelValueBean;

/**
 * This Dao Interface will get the result using JpaRepository spring jpa inbuild functioanlity. 
 * @author NS60097
 *
 */
@Repository
public interface FilterDao extends JpaRepository<DimRegion, Long> {

	/**
	 * This method get all region list available in DB based on the tenant id .
	 * 
	 * @return
	 */

	@Query("select new com.vpa.core.models.LabelValueBean(r.id,r.regionName) FROM DimRegion r where r.tenantId=:tenantId")
	List<LabelValueBean> getRegionList(@Param("tenantId") int tenantId);

	/**
	 * This method get all the associate country for the region.
	 * 
	 * @param region
	 * @return List<LabelValueBean>
	 */
	@Query("select new com.vpa.core.models.LabelValueBean(c.id,c.countryName) FROM DimCountry c where regionId=:region")
	List<LabelValueBean> getCountryByRegiont(
			@Param(value = "region" ) int region);

	/**
	 * This method get the entity type from DB.
	 * 
	 * @return
	 */
	@Query("select new com.vpa.core.models.LabelValueBean(e.id,e.entityTypeName) FROM DimEntityType e")
	List<LabelValueBean> getEntityTypeList();

	/**
	 * This method get the level(layer) from DB.
	 * 
	 * @return
	 */
	@Query("select new com.vpa.core.models.LabelValueBean(l.id,l.levelName) FROM DimLevel l")
	List<LabelValueBean> getLevelList();

	

}
