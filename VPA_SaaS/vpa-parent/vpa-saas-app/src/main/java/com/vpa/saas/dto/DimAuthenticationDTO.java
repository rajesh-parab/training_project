package com.vpa.saas.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vpa.core.dws.models.AuthenticationCount;

public class DimAuthenticationDTO {

	private int id;

	private List<AuthenticationKpiDTO> authenticationKpis = new ArrayList<>();

	private Map<String, AuthenticationCount> regionWiseEntitiesMap = new HashMap<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<AuthenticationKpiDTO> getAuthenticationKpis() {
		return authenticationKpis;
	}

	public void setAuthenticationKpis(
			List<AuthenticationKpiDTO> authenticationKpis) {
		this.authenticationKpis = authenticationKpis;
	}

	public Map<String, AuthenticationCount> getRegionWiseEntitiesMap() {
		return regionWiseEntitiesMap;
	}

	public void setRegionWiseEntitiesMap(
			Map<String, AuthenticationCount> regionWiseEntitiesMap) {

		this.regionWiseEntitiesMap = regionWiseEntitiesMap;
	}

	public void addAuthenticationKpi(String authenticationType,
			Long totalGenuineAuthentication, Long totalInLast24Hrs) {
		AuthenticationKpiDTO authenticationKpi = new AuthenticationKpiDTO();
		authenticationKpi.setAuthenticationType(authenticationType);
		authenticationKpi
				.setTotalAuthetncationCount(totalGenuineAuthentication);
		authenticationKpi.setTotalAuthenticationInLast24Hrs(totalInLast24Hrs);
		authenticationKpis.add(authenticationKpi);
	}

	public void addRegionWiseAuthenticationCount(String region,
			Long totalCount, Long genuineCount, Long suspectedCount,
			Long totalGenuineEntitiesCount, Long totalSuspectedEntitiesCount, Long totalEntitiesCount) {

		AuthenticationCount authenticationCount = new AuthenticationCount();
		authenticationCount.setTotalCount(totalCount);
		authenticationCount.setGenuineCount(genuineCount);
		authenticationCount.setSuspectedCount(suspectedCount);
		authenticationCount
				.setTotalGenuineEntitiesCount(totalGenuineEntitiesCount);
		authenticationCount
				.setTotalSuspectedEntitiesCount(totalSuspectedEntitiesCount);
		authenticationCount.setTotalEntitiesCount(totalEntitiesCount);
		regionWiseEntitiesMap.put(region, authenticationCount);
	}

}
