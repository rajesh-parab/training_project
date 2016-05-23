package com.vpa.core.enums;

import java.util.ArrayList;
import java.util.List;

public enum ProductLevel {
	// CHASSIS == PRODUCTS
	// BOX == PACKAGING
	BARE_PCB(1), PCBA(2), CHASIS(3), BOX(4), PALLET(5);

	private long id;

	ProductLevel(int id) {
		this.id = id;
	}

	public boolean isPCBA(long id) {
		return id == PCBA.getId();
	}

	public static long getLevelId(String level) {

		if (level.isEmpty()) {
			throw new IllegalArgumentException("level is Null in JSON ");
		}

		String levelName = level.replace(' ', '_').toUpperCase();
		for (ProductLevel procudtLevel : ProductLevel.values()) {
			if (levelName.equals(procudtLevel.name())) {
				return procudtLevel.getId();
			}
		}
		throw new IllegalArgumentException("No matching type for level "
				+ level);
	}

	public static List<Long> getLevelIdList() {
		List<Long> list = new ArrayList<>();

		for (ProductLevel procudtLevel : ProductLevel.values()) {
			if (procudtLevel.getId() != 0) {
				list.add(procudtLevel.getId());
			}
		}
		return list;
	}

	public long getId() {
		return id;
	}

}
