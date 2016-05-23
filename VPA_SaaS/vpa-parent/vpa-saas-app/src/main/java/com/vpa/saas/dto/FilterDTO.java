package com.vpa.saas.dto;

import java.util.List;

import com.vpa.core.models.LabelValueBean;

/**
 * This DTO class have all the details for the filter criteria to be send to UI.
 * this class become response and convert into json object.
 * 
 * @author NS60097
 *
 */
public class FilterDTO {
	private final List<RegionWithCountryDTO> regions;
	private final List<LabelValueBean> entityTypes;
	private final List<LabelValueBean> level;
	private final List<LabelValueBean> products;

	public FilterDTO(
			List<RegionWithCountryDTO> regions,
			List<LabelValueBean> entityTypes,
			List<LabelValueBean> level,
			List<LabelValueBean> products) {
		this.regions = regions;
		this.entityTypes = entityTypes;
		this.level = level;
		this.products = products;
	}

	public List<RegionWithCountryDTO> getRegions() {
		return regions;
	}

	public List<LabelValueBean> getEntityTypes() {
		return entityTypes;
	}

	public List<LabelValueBean> getLevel() {
		return level;
	}

	public List<LabelValueBean> getProducts() {
		return products;
	}


	public static class Builder {
		private List<RegionWithCountryDTO> regionList;
		private List<LabelValueBean> entityType;
		private List<LabelValueBean> level;
		private List<LabelValueBean> productId;

		private Builder() {
		}

		public static Builder filterDTO() {
			return new Builder();
		}

		public Builder withRegions(List<RegionWithCountryDTO> regionList) {
			this.regionList = regionList;
			return this;
		}

		public Builder withEntityTypes(List<LabelValueBean> entityType) {
			this.entityType = entityType;
			return this;
		}

		public Builder withLevels(List<LabelValueBean> level) {
			this.level = level;
			return this;
		}

		public Builder withProducts(List<LabelValueBean> productId) {
			this.productId = productId;
			return this;
		}

		public FilterDTO build() {
			return new FilterDTO(regionList, entityType, level, productId);
		}
	}
}
