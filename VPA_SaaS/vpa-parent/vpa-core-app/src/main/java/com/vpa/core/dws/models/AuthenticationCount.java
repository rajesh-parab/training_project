package com.vpa.core.dws.models;

public class AuthenticationCount {

	private long totalCount;

	private long genuineCount;

	private long suspectedCount;

	private long totalEntitiesCount;

	private long totalGenuineEntitiesCount;

	private long totalSuspectedEntitiesCount;

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public void sumUpTotalCount(long totalCount) {
		this.totalCount += totalCount;
	}

	public long getGenuineCount() {
		return genuineCount;
	}

	public void setGenuineCount(long genuineCount) {
		this.genuineCount = genuineCount;
	}

	public void sumUpGenuineCount(long genuineCount) {
		this.genuineCount += genuineCount;
	}

	public long getSuspectedCount() {
		return suspectedCount;
	}

	public void setSuspectedCount(long suspectedCount) {
		this.suspectedCount = suspectedCount;
	}

	public void sumUpSuspectCount(long suspectedCount) {
		this.suspectedCount += suspectedCount;

	}

	public long getTotalEntitiesCount() {
		return totalEntitiesCount;
	}

	public void setTotalEntitiesCount(long totalEntitiesCount) {
		this.totalEntitiesCount = totalEntitiesCount;
	}

	public long getTotalGenuineEntitiesCount() {
		return totalGenuineEntitiesCount;
	}

	public void setTotalGenuineEntitiesCount(long totalGenuineEntitiesCount) {
		this.totalGenuineEntitiesCount = totalGenuineEntitiesCount;
	}

	public long getTotalSuspectedEntitiesCount() {
		return totalSuspectedEntitiesCount;
	}

	public void setTotalSuspectedEntitiesCount(long totalSuspectedEntitiesCount) {
		this.totalSuspectedEntitiesCount = totalSuspectedEntitiesCount;
	}

	@Override
	public String toString() {
		return "AuthenticationCount [totalCount=" + totalCount
				+ ", genuineCount=" + genuineCount + ", suspectedCount="
				+ suspectedCount + ", totalEntitiesCount=" + totalEntitiesCount
				+ ", totalGenuineEntitiesCount=" + totalGenuineEntitiesCount
				+ ", totalSuspectedEntitiesCount="
				+ totalSuspectedEntitiesCount+"]";
	}

	public void incrementTotalEntitiesCount(boolean flag) {
		if (flag) {
			totalEntitiesCount++;
		}
	}

	public void incrementGenuineEntitiesCount(boolean flag) {
		if (flag) {
			totalGenuineEntitiesCount++;
		}
	}

	public void incrementSuspectedEntitiesCount(boolean flag) {
		if (flag) {
			totalSuspectedEntitiesCount++;
		}
	}

}
