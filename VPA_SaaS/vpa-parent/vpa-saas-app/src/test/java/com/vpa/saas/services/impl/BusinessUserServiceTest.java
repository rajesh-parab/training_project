package com.vpa.saas.services.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpa.core.enums.UserTypeEnum;
import com.vpa.core.exceptions.UnauthorizedResourceException;
import com.vpa.core.mes.dao.BrandOwnerUserDao;
import com.vpa.core.mes.dao.BusinessUserDao;
import com.vpa.core.mes.dao.UserDao;
import com.vpa.core.models.BrandOwnerUser;
import com.vpa.core.models.BusinessUser;
import com.vpa.core.models.User;
import com.vpa.saas.dto.UserDTO;
import com.vpa.saas.services.TokenService;

public class BusinessUserServiceTest extends AbstractMockUnitTest {

	@Autowired
	private BusinessUserDao businessUserDao;
	
	@Autowired
	private BrandOwnerUserDao brandOwnerUserDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private TokenService tokenService;

	@Test
	public void completeRegistrationBusinessUser() {
		UserDTO userDto = new UserDTO();
		userDto.setId(1L);
		userDto.setUserType(UserTypeEnum.BUSINESS_USER.getId());
		BusinessUserServiceImpl businessUserService = new BusinessUserServiceImpl();
		businessUserService.setBusinessUserDao(businessUserDao);
		businessUserService.setUserDao(userDao);
		businessUserService.setTokenService(tokenService);

		BusinessUser businessUser = new BusinessUser();
		User user = new User();
		when(userDao.findInactiveUser(userDto.getId())).thenReturn(user);
		tokenService.validateToken(userDto, user);
		when(businessUserDao.findInactiveUser(userDto.getId())).thenReturn(
				businessUser);

		userDto = businessUserService.completeRegistration(userDto);
		assertTrue(userDto.isTokenExpired() == false);

	}

	@Test
	public void completeRegistrationBrandOwnerUser() {
		UserDTO userDto = new UserDTO();
		userDto.setId(1L);
		userDto.setUserType(UserTypeEnum.BRANDOWNER_USER.getId());
		BrandOwnerUserServiceImpl brandOwnerUserService = new BrandOwnerUserServiceImpl();
		brandOwnerUserService.setBrandOwnerUserDao(brandOwnerUserDao);
		brandOwnerUserService.setUserDao(userDao);
		brandOwnerUserService.setTokenService(tokenService);

		BrandOwnerUser brandOwnerUser = new BrandOwnerUser();
		User user = new User();
		when(userDao.findInactiveUser(userDto.getId())).thenReturn(user);
		tokenService.validateToken(userDto, user);
		when(brandOwnerUserDao.findInactiveUser(userDto.getId())).thenReturn(
				brandOwnerUser);

		userDto = brandOwnerUserService.completeRegistration(userDto);
		assertTrue(userDto.isTokenExpired() == false);

	}

	@Test(expected=UnauthorizedResourceException.class)
	public void vaidateUserTest() {
		UserDTO validUser = new UserDTO();
		validUser.setId(1L);
		validUser.setUserType(UserTypeEnum.BUSINESS_USER.getId());
		BusinessUserServiceImpl businessUserService = new BusinessUserServiceImpl();
		businessUserService.setBusinessUserDao(businessUserDao);
		businessUserService.setUserDao(userDao);
		businessUserService.setTokenService(tokenService);
 
	 
		 		when(businessUserDao.findInactiveUser(validUser.getId()))
				.thenReturn(null);

		businessUserService.validateUser(validUser, "");
	}

}

