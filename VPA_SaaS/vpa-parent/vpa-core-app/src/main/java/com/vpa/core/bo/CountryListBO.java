package com.vpa.core.bo;


/**
 * @author NS60097
 *
 */
public class CountryListBO {

	private final String countryName;

	private final int countryId;

	private final Long suspectProducts;

	private final Long suspectAuthentication;

	private final Long totalAuthentication;

	private final Long revenueLoss;

	private final Long entities;

	private final Long users;

	public CountryListBO(
			String countryName,
			int countryId,
			Long suspectProducts,
			Long suspectAuthentication,
			Long totalAuthentication,
			Long revenueLoss,
			Long entities,
			Long users) {
		this.countryName = countryName;
		this.countryId = countryId;
		this.suspectProducts = suspectProducts;
		this.suspectAuthentication = suspectAuthentication;
		this.totalAuthentication = totalAuthentication;
		this.revenueLoss = revenueLoss;
		this.entities = entities;
		this.users = users;
	}

	public String getCountryName() {
		return countryName;
	}

	public int getCountryId() {
		return countryId;
	}

	public Long getSuspectProducts() {
		return suspectProducts;
	}

	public Long getSuspectAuthentication() {
		return suspectAuthentication;
	}

	public Long getTotalAuthentication() {
		return totalAuthentication;
	}

	public Long getRevenueLoss() {
		return revenueLoss;
	}

	public Long getEntities() {
		return entities;
	}

	public Long getUsers() {
		return users;
	}


	public static class Builder {
		private String countryName;
		private int countryId;
		private Long suspectProducts;
		private Long suspectAuthentication;
		private Long totalAuthentication;
		private Long revenueLoss;
		private Long entities;
		private Long users;

		private Builder() {
		}

		public static Builder countryListBO() {
			return new Builder();
		}

		public Builder withCountryName(String countryName) {
			this.countryName = countryName;
			return this;
		}

		public Builder withCountryId(int countryId) {
			this.countryId = countryId;
			return this;
		}

		public Builder withSuspectProducts(Long suspectProducts) {
			this.suspectProducts = suspectProducts;
			return this;
		}

		public Builder withSuspectAuthentication(Long suspectAuthentication) {
			this.suspectAuthentication = suspectAuthentication;
			return this;
		}

		public Builder withTotalAuthentication(Long totalAuthentication) {
			this.totalAuthentication = totalAuthentication;
			return this;
		}

		public Builder withRevenueLoss(Long revenueLoss) {
			this.revenueLoss = revenueLoss;
			return this;
		}

		public Builder withEntities(Long entities) {
			this.entities = entities;
			return this;
		}

		public Builder withUsers(Long users) {
			this.users = users;
			return this;
		}

		public CountryListBO build() {
			return new CountryListBO(
				countryName,
				countryId,
				suspectProducts,
				suspectAuthentication,
				totalAuthentication,
				revenueLoss,
				entities,
				users);
		}
	}
}
