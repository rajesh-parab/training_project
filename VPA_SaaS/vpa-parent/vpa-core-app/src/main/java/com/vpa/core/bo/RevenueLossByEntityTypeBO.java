package com.vpa.core.bo;

public class RevenueLossByEntityTypeBO {

	private final String entityType;
	private final Long revenueLoss;

	public RevenueLossByEntityTypeBO(String entityType, Long revenueLoss) {
		this.entityType = entityType;
		this.revenueLoss = revenueLoss;
	}

	public String getEntityType() {
		return entityType;
	}

	public Long getRevenueLoss() {
		return revenueLoss;
	}

	public static class Builder {
		private String entityType;
		private Long revenueLoss;

		private Builder() {

		}

		public static Builder revenueLossByEntityType() {
			return new Builder();
		}

		public Builder withEntityType(String entityType) {
			this.entityType = entityType;
			return this;
		}

		public Builder withRevenueLoss(Long revenueLoss) {
			this.revenueLoss = revenueLoss;
			return this;
		}

		public RevenueLossByEntityTypeBO build() {
			return new RevenueLossByEntityTypeBO(entityType, revenueLoss);
		}
	}

}
