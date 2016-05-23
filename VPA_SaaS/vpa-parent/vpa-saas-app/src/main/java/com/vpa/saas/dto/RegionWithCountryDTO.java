package com.vpa.saas.dto;

import java.util.ArrayList;
import java.util.List;

import com.vpa.core.models.LabelValueBean;

public class RegionWithCountryDTO {

	private Long id;

	private String name;

	List<LabelValueBean> countryList = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LabelValueBean> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<LabelValueBean> countryList) {
		this.countryList = countryList;
	}
}
