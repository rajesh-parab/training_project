package com.vpa.saas.services;

import java.util.List;
import java.util.Map;

import com.vpa.saas.dto.BrandDTO;
import com.vpa.saas.dto.LiveScanDetailDTO;

public interface UtilityService {

	Map<String, BrandDTO> getBrandProductMappings();

	/**
	 * Live details for scan happens for particular tenant. Showed on dashboard.
	 * 
	 * @param tenantId - tenant id
	 * @param loginTime - time when user logged in
	 * @return Details of the scan happened after login.
	 */
	////Start of Pilot enhancement
	List<LiveScanDetailDTO> getLiveAuthenticationDetails(long tenantId,long brandId,String loginTime);
	////End of Pilot enhancement
    /**
     * Live count of the product scan since last login.
     * 
     * @param tenantId
     * @param timeStamp
     * @return number of scan happened after login.
     */
	long getLiveFlagCount(long tenantId,long brandId,String timeStamp);

	String getCurrentMonthYear();

	String getCurrentTimeStamp();

}
