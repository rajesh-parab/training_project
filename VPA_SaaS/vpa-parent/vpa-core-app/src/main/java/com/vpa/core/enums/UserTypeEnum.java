package com.vpa.core.enums;

public enum UserTypeEnum {

	BUSINESS_USER(1L), BRANDOWNER_USER(2L), VPA_ADMIN_USER(3L), API_USER(4L);

	Long id;

	UserTypeEnum(Long userTypeId) {
		id = userTypeId;
	}

	public static boolean isBusinessUser(Long id) {
		return BUSINESS_USER.getId().equals(id);
	}

	public static boolean isBrandOwnerUser(Long id) {
		return BRANDOWNER_USER.getId().equals(id);
	}

	public static UserTypeEnum getType(Long id) {

		if (id == null) {
			return null;
		}

		for (UserTypeEnum userTypeId : UserTypeEnum.values()) {
			if (id.equals(userTypeId.getId())) {
				return userTypeId;
			}
		}
		throw new IllegalArgumentException("No matching type for id " + id);
	}

	public Long getId() {
		return id;
	}

}
