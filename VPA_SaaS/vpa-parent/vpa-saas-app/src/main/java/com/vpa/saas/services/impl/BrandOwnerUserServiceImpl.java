package com.vpa.saas.services.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpa.core.enums.UserTypeEnum;
import com.vpa.core.exceptions.ResourceNotFoundException;
import com.vpa.core.exceptions.UnauthorizedResourceException;
import com.vpa.core.mes.dao.BrandOwnerUserDao;
import com.vpa.core.models.Brand;
import com.vpa.core.models.BrandOwnerUser;
import com.vpa.core.models.Tenant;
import com.vpa.core.models.User;
import com.vpa.core.utils.VPASaaSConstant;
import com.vpa.saas.dto.BrandDTO;
import com.vpa.saas.dto.UserDTO;

@Service("brandOwnerUserService")
public class BrandOwnerUserServiceImpl extends UserServiceImpl {

	@Autowired
	private BrandOwnerUserDao brandOwnerUserDao;
	
	

	public void setBrandOwnerUserDao(BrandOwnerUserDao brandOwnerUserDao) {
		this.brandOwnerUserDao = brandOwnerUserDao;
	}

	private BrandOwnerUser createBrandOwnerUser(UserDTO user) {
		User newUser = createUserDetails(user);
		newUser.setUserType(getUserTypeDao().findOne(
				UserTypeEnum.BRANDOWNER_USER.getId()));

		BrandOwnerUser newBrandOwnerUser = new BrandOwnerUser();

		newBrandOwnerUser.setEnable(newUser.getEnable());
		newBrandOwnerUser.setStatus(newUser.getEnable());
		newBrandOwnerUser.setCreatedDate(newUser.getCreatedDate());
		newBrandOwnerUser.setUpdatedDate(newUser.getUpdatedDate());
		newBrandOwnerUser.setDesignation(user.getBrandOwnerUser()
				.getDesignation());
		Tenant tenant = getTenantDao().findOne(user.getBrandOwnerUser().getTenant().getId());
		newBrandOwnerUser.setTenant(tenant);
		user.setBusinessUser(null);
		newBrandOwnerUser.setUser(newUser);
		return newBrandOwnerUser;
	}

	@Override
	public User createNewUser(UserDTO user) {

		BrandOwnerUser newBrandOwnerUser = createBrandOwnerUser(user);
		newBrandOwnerUser = brandOwnerUserDao.saveAndFlush(newBrandOwnerUser);
		return newBrandOwnerUser.getUser();

	}

	@Override
	protected void activateUser(long userId) {
		BrandOwnerUser brandOwnerUser = brandOwnerUserDao
				.findInactiveUser(userId);
		brandOwnerUser.setEnable(VPASaaSConstant.TRUE);

	}

	@Override
	protected void validateUser(UserDTO validUser, String errorMessage) {

		BrandOwnerUser brandOwnerUser = brandOwnerUserDao
				.findActiveUser(validUser.getId());
		if (brandOwnerUser == null) {
			throw new UnauthorizedResourceException(errorMessage);
		}
		BeanUtils.copyProperties(brandOwnerUser, validUser.getBrandOwnerUser());
		BeanUtils.copyProperties(brandOwnerUser.getTenant(), validUser
				.getBrandOwnerUser().getTenant(), "brands", "brandownerusers");
		List<Brand> brands = brandOwnerUser.getTenant().getBrands();
		for (Brand brand : brands) {
			BrandDTO brandDto = new BrandDTO();
			BeanUtils.copyProperties(brand, brandDto);
			validUser.getBrandOwnerUser().getTenant().addBrand(brandDto);
		}

		validUser.setBusinessUser(null);
	}
	
	/**
	 * @see UserServiceImpl#updateUserSettings(UserDTO)
	 */
	@Override
	public UserDTO updateUserSettings(UserDTO user) {
		UserDTO userDTO = super.changeUserDetails(user);
		BrandOwnerUser savedUser = brandOwnerUserDao.findActiveUser(user.getId());
		if (savedUser == null) {
			throw new ResourceNotFoundException("Not a brandowner user");
		}
		savedUser.setDesignation(userDTO.getBrandOwnerUser().getDesignation());
		brandOwnerUserDao.saveAndFlush(savedUser);
		BeanUtils.copyProperties(savedUser, userDTO.getBrandOwnerUser());
		userDTO.setBusinessUser(null);
		return userDTO;
	}
}
