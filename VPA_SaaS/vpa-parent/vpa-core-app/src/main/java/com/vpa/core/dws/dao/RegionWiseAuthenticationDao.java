package com.vpa.core.dws.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.vpa.core.dws.models.RegionWiseAuthentication;

@Repository
public interface RegionWiseAuthenticationDao {

	/**
	 * This method will return world wide authentication on KPI indicator tab on
	 * dashboard.
	 * 
	 * @param tenantId
	 * @param brandId
	 * @return
	 */
	List<RegionWiseAuthentication> getWorldWideAuthenticationCount(
			Long tenantId, Long brandId);

	/**
	 * This method will return world wide authentication for last 24 hours
	 * scanned happened on KPI indicator tab on dashboard.
	 * 
	 * @param tenantId
	 * @param brandId
	 * @return
	 */
	List<RegionWiseAuthentication> getWorldWideAuthenticationCountForLast24Hrs(
			Long tenantId, Long brandId);

	/**
	 * This method will return data on world map wide authentication.
	 * 
	 * @param tenantId
	 * @param brandId
	 * @return
	 */
	List<RegionWiseAuthentication> getRegionWiseAuthenticationCount(
			Long tenantId, Long brandId);

}
