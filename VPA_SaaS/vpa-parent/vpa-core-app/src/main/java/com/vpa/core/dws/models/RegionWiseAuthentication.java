package com.vpa.core.dws.models;

import java.math.BigDecimal;
import java.math.BigInteger;

public class RegionWiseAuthentication {

	private String finalResult;

	private Long totalAuthentications = 0L;

	private String regionName;

	private long regionWiseGenuineCount = 0L;

	private long regionWiseSuspectedCount = 0L;

	private long genuineEntitiesCount = 0L;

	private long suspectedEntitiesCount = 0L;

	private long totalEntitiesCount = 0L;

	public String getFinalResult() {
		return finalResult;
	}

	public void setFinalResult(String finalResult) {
		this.finalResult = finalResult;
	}

	public Long getTotalAuthentications() {
		return (null == totalAuthentications) ? 0L : totalAuthentications;
	}

	public void setTotalAuthentications(Long totalAuthentications) {
		this.totalAuthentications = totalAuthentications;
	}

	public String getRegionName() {
		return (regionName != null) ? regionName.trim() : "";
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public long getRegionWiseGenuineCount() {
		return regionWiseGenuineCount;
	}

	public void setRegionWiseGenuineCount(long regionWiseGenuineCount) {
		this.regionWiseGenuineCount = regionWiseGenuineCount;
	}

	public long getRegionWiseSuspectedCount() {
		return regionWiseSuspectedCount;
	}

	public void setRegionWiseSuspectedCount(long regionWiseSuspectedCount) {
		this.regionWiseSuspectedCount = regionWiseSuspectedCount;
	}

	public long getRegionWiseTotalCount() {
		return regionWiseGenuineCount + regionWiseSuspectedCount;
	}

	public long getGenuineEntitiesCount() {
		return genuineEntitiesCount;
	}

	public void setGenuineEntitiesCount(long genuineEntitiesCount) {
		this.genuineEntitiesCount = genuineEntitiesCount;
	}

	public long getSuspectedEntitiesCount() {
		return suspectedEntitiesCount;
	}

	public void setSuspectedEntitiesCount(long suspectedEntitiesCount) {
		this.suspectedEntitiesCount = suspectedEntitiesCount;
	}

	public long getTotalEntitiesCount() {
		return totalEntitiesCount;
	}

	public void setTotalEntitiesCount(long totalEntitiesCount) {
		this.totalEntitiesCount = totalEntitiesCount;
	}

	public void mapTotalEntitiesCount(Object[] authentications) {

		this.setTotalEntitiesCount((authentications[1] == null) ? 0
				: ((BigInteger) authentications[1]).longValue());

	}

	public void mapToRegionWiseWorldWideCount(Object[] authentications) {
		this.setFinalResult((authentications[1] == null) ? ""
				: (String) authentications[1]);
		this.setTotalAuthentications((authentications[2] == null) ? 0
				: ((BigDecimal) authentications[2]).longValue());

	}

	public void mapToLast24HrsAuthenticationCount(Object[] authentications) {

		this.setFinalResult((authentications[3] == null) ? ""
				: (String) authentications[3]);
		this.setTotalAuthentications((authentications[4] == null) ? 0
				: ((BigDecimal) authentications[4]).longValue());

	}

	public void mapToRegionWiseGenuineAuthenticationCount(
			Object[] authentications) {

		this.setRegionName((authentications[1] == null) ? ""
				: (String) authentications[1]);
		this.setRegionWiseGenuineCount((authentications[2] == null) ? 0L
				: ((BigDecimal) authentications[2]).longValue());
		this.setGenuineEntitiesCount((authentications[3] == null) ? 0L
				: ((BigInteger) authentications[3]).longValue());

	}

	public void mapToRegionWiseSuspectedAuthenticationCount(
			Object[] authentications) {

		this.setRegionName((authentications[1] == null) ? ""
				: (String) authentications[1]);
		this.setRegionWiseSuspectedCount((authentications[2] == null) ? 0L
				: ((BigDecimal) authentications[2]).longValue());
		this.setSuspectedEntitiesCount((authentications[3] == null) ? 0L
				: ((BigInteger) authentications[3]).longValue());
	}

}
