package com.vpa.saas.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.vpa.core.enums.UserTypeEnum;
import com.vpa.core.mes.dao.BrandOwnerUserDao;
import com.vpa.core.mes.dao.BusinessUserDao;
import com.vpa.core.mes.dao.UserDao;
import com.vpa.core.models.BrandOwnerUser;
import com.vpa.core.models.BusinessUser;
import com.vpa.core.models.Company;
import com.vpa.core.models.User;
import com.vpa.core.utils.VPASaaSConstant;
import com.vpa.saas.dto.CompanyDTO;
import com.vpa.saas.dto.UserDTO;

public abstract class UserTest extends AbstractVPASaaSTest {

	public static final String DESIGNATION = "Production officer";

	@Resource(name = "brandOwnerUserService")
	protected UserService brandOwnerUserService;

	@Autowired
	protected UserDao userDao;

	@Autowired
	protected BusinessUserDao businessUserDao;

	@Autowired
	protected BrandOwnerUserDao brandOwnerUserDao;


	public void activateBusinessUser(BusinessUser businessUser) {
		businessUser.setEnable(VPASaaSConstant.TRUE);
		businessUserDao.saveAndFlush(businessUser);
	}

	public void activateBrandOwnerUser(BrandOwnerUser brandOwnerUser) {
		brandOwnerUser.setEnable(VPASaaSConstant.TRUE);
		brandOwnerUserDao.saveAndFlush(brandOwnerUser);
	}

	public void activateUser(User savedUser) {
		savedUser.setEnable(VPASaaSConstant.TRUE);
		userDao.saveAndFlush(savedUser);
	}
	
	public void assertCompany(CompanyDTO company, Company businessUserCompany) {
		assertEquals(businessUserCompany.getName(), company.getName());
		assertEquals(businessUserCompany.getCity(), company.getCity());
		assertEquals(businessUserCompany.getState(), company.getState());
		assertEquals(businessUserCompany.getCountry(), company.getCountry());
		assertEquals(businessUserCompany.getEntityType().getId(), company
				.getType().getId());
	}

	public void assertActiveBusinessUser(UserDTO user, User savedUser,
			BusinessUser savedBusinessUser) {
		assertActiveUser(user, savedUser);
		assertBusinessUser(user, savedUser, savedBusinessUser);
		assertEquals(savedBusinessUser.getEnable(), VPASaaSConstant.TRUE);
	}

	public void assertInactiveBusinessUser(UserDTO user, User savedUser,
			BusinessUser savedBusinessUser) {
		assertInactiveUser(user, savedUser);
		assertBusinessUser(user, savedUser, savedBusinessUser);
		assertEquals(savedBusinessUser.getEnable(), VPASaaSConstant.FALSE);
	}

	private void assertBusinessUser(UserDTO user, User savedUser,
			BusinessUser savedBusinessUser) {
		assertUser(user, savedUser);

		assertEquals(savedUser.getUserType().getId(),
				UserTypeEnum.BUSINESS_USER.getId());
		assertEquals(savedBusinessUser.getCreatedDate(), user.getCreatedDate());
		assertEquals(savedBusinessUser.getCreatedDate(),
				savedUser.getCreatedDate());
		assertEquals(savedBusinessUser.getUpdatedDate(), user.getUpdatedDate());
		assertEquals(savedBusinessUser.getUpdatedDate(),
				savedUser.getUpdatedDate());
		assertEquals(savedBusinessUser.getEnable(), user.getEnable());
		assertEquals(savedBusinessUser.getEnable(), savedUser.getEnable());
		assertNull(user.getBrandOwnerUser());
		assertCompany(user.getBusinessUser().getCompany(), savedBusinessUser.getCompany());
	}

	public void assertActiveUser(UserDTO user, User savedUser) {
		assertUser(user, savedUser);
		assertEquals(savedUser.getEnable(), VPASaaSConstant.TRUE);
	}

	private void assertInactiveUser(UserDTO user, User savedUser) {
		assertUser(user, savedUser);
		assertEquals(savedUser.getEnable(), VPASaaSConstant.FALSE);

	}

	private void assertUser(UserDTO user, User savedUser) {

		assertEquals(savedUser.getFirstName(), user.getFirstName());
		assertEquals(savedUser.getLastName(), user.getLastName());
		assertEquals(savedUser.getEmailAddress(), user.getEmailAddress());
		assertEquals(savedUser.getUserType().getId(), user.getUserType());
		assertEquals(savedUser.getEnable(), user.getEnable());
		assertEquals(savedUser.getCreatedDate(), user.getCreatedDate());
		assertEquals(savedUser.getUpdatedDate(), user.getUpdatedDate());

	}

	public UserDTO registerBrandOwnerUser() {
		UserDTO user = getUser();
		user.setUserType(UserTypeEnum.BRANDOWNER_USER.getId());
		user.getBrandOwnerUser().setDesignation(DESIGNATION);
		user.getBrandOwnerUser().getTenant().setId(super.getTenant());
		user = brandOwnerUserService.registerUser(user);
		return user;
	}

	public void assertActiveBrandOwnerUser(UserDTO user, User savedUser,
			BrandOwnerUser savedBrandOwnerUse) {
		assertActiveUser(user, savedUser);
		assertBrandOwnerUser(user, savedUser, savedBrandOwnerUse);
		assertEquals(savedBrandOwnerUse.getEnable(), VPASaaSConstant.TRUE);
	}

	public void assertInactiveBrandOwnerUser(UserDTO user, User savedUser,
			BrandOwnerUser savedBrandOwnerUse) {
		assertInactiveUser(user, savedUser);
		assertBrandOwnerUser(user, savedUser, savedBrandOwnerUse);
		assertEquals(savedBrandOwnerUse.getEnable(), VPASaaSConstant.FALSE);
	}

	private void assertBrandOwnerUser(UserDTO user, User savedUser,
			BrandOwnerUser savedBrandOwnerUser) {

		assertEquals(savedUser.getUserType().getId(),
				UserTypeEnum.BRANDOWNER_USER.getId());
		assertEquals(savedBrandOwnerUser.getCreatedDate(),
				user.getCreatedDate());
		assertEquals(savedBrandOwnerUser.getCreatedDate(),
				savedUser.getCreatedDate());
		assertEquals(savedBrandOwnerUser.getUpdatedDate(),
				user.getUpdatedDate());
		assertEquals(savedBrandOwnerUser.getUpdatedDate(),
				savedUser.getUpdatedDate());
		assertEquals(savedBrandOwnerUser.getEnable(), user.getEnable());
		assertEquals(savedBrandOwnerUser.getEnable(), savedUser.getEnable());

		assertEquals(savedBrandOwnerUser.getDesignation(), DESIGNATION);
		assertEquals(savedBrandOwnerUser.getTenant().getId(), user
				.getBrandOwnerUser().getTenant().getId());
		assertNull(user.getBusinessUser());

	}

}
