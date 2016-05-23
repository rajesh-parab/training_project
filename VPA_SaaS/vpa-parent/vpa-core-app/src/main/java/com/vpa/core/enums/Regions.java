package com.vpa.core.enums;

public enum Regions {
	WORLD_WIDE(0), AMERICAS(1), EMEA(2), APAC(3);

	private int id;

	Regions(int id) {
		this.id = id;
	}

	public static int getRegionId(String regionStr) {

		if (regionStr.isEmpty()) {
			throw new IllegalArgumentException("Region is Null in JSON ");
		}
		String regionName = regionStr.replace('-', '_').toUpperCase();
		if (regionName.equalsIgnoreCase("WORLD_WIDE")) {
			return 0;
		}
		
		for (Regions region : Regions.values()) {
			if (regionName.equals(region.name())) {
				return region.getId();
			}
		}
		throw new IllegalArgumentException("No matching type for level "
				+ regionName);
	}

	public int getId() {
		return id;
	}

}
