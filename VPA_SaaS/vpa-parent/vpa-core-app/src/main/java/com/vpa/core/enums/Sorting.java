package com.vpa.core.enums;

/**
 * @author NS60097
 *
 */
public enum Sorting {

	// REVENUE, SUSPECTAUTHENTICATION, TOTALAUTHENTICATION, SUSPECT_PRODUCTS,
	// COUNTRY, ENTITY_TYPE, ENTITY_NAME;

	REVENUE("revenue_loss"), SUSPECTAUTHENTICATION("suspect_authentication"), TOTALAUTHENTICATION(
			"total_authentication"), COUNTRYNAME("country_name"), SUSPECTPRODUCTS(
			"suspect_products"), SUSPECT_PRODUCTS("suspect_products"), COUNTRY(
			"country"), ENTITY_TYPE("entity_type"), ENTITY_NAME("entity_name");

	public String name;

	Sorting(String name) {
		this.name = name;
	}

	public static String sortedBy(String name) {

		if (name.isEmpty()) {
			throw new IllegalArgumentException("name is Null in JSON ");
		}
		String sortedName = name.toUpperCase();

		for (Sorting sortByname : Sorting.values()) {
			if (sortedName.equals(sortByname.name())) {
				return sortByname.getName();
			}
		}
		throw new IllegalArgumentException("No matching type for level "
				+ sortedName);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
