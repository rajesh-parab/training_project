package com.vpa.core.bo;

public class RevenueLossByRegionBO {

	private final int regionId;
	private final String regionName;
	private final Long revenueLoss;
	private final Long entityCount;

	public RevenueLossByRegionBO(int regionId, String regionName,
			Long revenueLoss, Long entityCount) {
		this.regionId = regionId;
		this.regionName = regionName;
		this.revenueLoss = revenueLoss;
		this.entityCount = entityCount;
	}

	public int getRegionId() {
		return regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public Long getRevenueLoss() {
		return revenueLoss;
	}

	public Long getEntityCount() {
		return entityCount;
	}

	public static class Builder {
		private int regionId;
		private String regionName;
		private Long revenueLoss;
		private Long entityCount;

		private Builder() {

		}

		public static Builder revenueLossByRegionBO() {
			return new Builder();
		}

		public Builder withRegionId(int regionId){
			this.regionId= regionId;
			return this;
		}
		public Builder withRegionName(String regionName) {
			this.regionName = regionName;
			return this;
		}

		public Builder withRevenueLoss(Long revenueLoss) {
			this.revenueLoss = revenueLoss;
			return this;
		}

		public Builder withEntityCount(Long entityCount) {
			this.entityCount = entityCount;
			return this;
		}

		public RevenueLossByRegionBO build() {
			return new RevenueLossByRegionBO(regionId,regionName, revenueLoss,
					entityCount);
		}
	}
}
