package com.vpa.core.bo;

public class RevenueLossByCountryBO {

	private final int countryId;
	private final String countryName;
	private final int entities;
	private final Long revenueLoss;

	public RevenueLossByCountryBO(int countryId, String countryName,
			int entities, Long revenueLoss) {
		this.countryId = countryId;
		this.countryName = countryName;
		this.entities = entities;
		this.revenueLoss = revenueLoss;
	}

	public int getCountryId() {
		return countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public int getEntities() {
		return entities;
	}

	public Long getRevenueLoss() {
		return revenueLoss;
	}

	public static class Builder {
		private int countryId;
		private String countryName;
		private int entities;
		private Long revenueLoss;

		private Builder() {

		}

		public static Builder revenueLossByCountry() {
			return new Builder();
		}

		public Builder withCountryId(int countryId) {
			this.countryId = countryId;
			return this;
		}

		public Builder withCountryName(String countryName) {
			this.countryName = countryName;
			return this;
		}

		public Builder withEntities(int entities) {
			this.entities = entities;
			return this;
		}

		public Builder withRevenueLoss(Long revenueLoss) {
			this.revenueLoss = revenueLoss;
			return this;
		}

		public RevenueLossByCountryBO builder() {
			return new RevenueLossByCountryBO(countryId, countryName, entities,
					revenueLoss);
		}
	}
}
