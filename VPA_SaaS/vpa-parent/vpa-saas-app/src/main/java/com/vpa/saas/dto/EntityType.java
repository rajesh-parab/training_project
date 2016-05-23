package com.vpa.saas.dto;

import java.util.ArrayList;
import java.util.List;


public enum EntityType {
	// Note if there is a change in table design or records in entityType table
	// This enum may need to change
	ALL(0L),CHANNEL_PARTNER(1L), LAW_ENFORCEMENT_AGENCY(2L), END_CUSTOMER(3L), MANUFACTURER(
			4L), BRANDOWNER(5L), DISTRIBUTOR(6L),DEMO_CUSTOMER(7L) ;

	private Long id;

	private EntityType(Long id) {
		this.id = id;
	}

	public static EntityType getType(Long id) {

		if (id == null) {
			return null;
		}

		for (EntityType entityType : EntityType.values()) {
			if (id.equals(entityType.getId())) {
				return entityType;
			}
		}
		throw new IllegalArgumentException("No matching type for id " + id);
	}

	public static Long getEntityTypeId(String entityName) {

		if (entityName.isEmpty()) {
			throw new IllegalArgumentException("entity Name is null in JSON ");
		}
		if (entityName.equalsIgnoreCase("ALL")) {
			return 0L;
		}
		String entityTypeName = entityName.replace(' ', '_').toUpperCase();
		for (EntityType entityType : EntityType.values()) {
			if (entityTypeName.equals(entityType.name())) {
				return entityType.getId();
			}
		}
		throw new IllegalArgumentException("No matching type for entity Name "
				+ entityName);
	}
	
	public static List<Long> getEntityIdList() {
		List<Long> list = new ArrayList<>();

		for (EntityType entityType : EntityType.values()) {
			if (entityType.getId() != 0) {
				list.add(entityType.getId());
			}
		}
		return list;
	}

	public Long getId() {
		return id;
	}

}
