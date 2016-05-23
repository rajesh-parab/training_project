package com.vpa.saas.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.vpa.core.exceptions.ResourceAlreadyExistException;
import com.vpa.core.models.BrandOwnerUser;
import com.vpa.core.models.BusinessUser;
import com.vpa.core.models.User;
import com.vpa.saas.dto.CompanyDTO;
import com.vpa.saas.dto.UserDTO;

public class UserRegistrationServiceIntegrationTest extends UserTest {

	
	@Test
	public void registerBusinessUserHappyPath() throws Exception {

		CompanyDTO company = getCompany();
		UserDTO user = registerBusinessUser(company);

		User savedUser = userDao.findByEmailAddress(EMAILD_ID);

		BusinessUser savedBusinessUser = businessUserDao
				.findInactiveUser(savedUser.getId());
		assertInactiveBusinessUser(user, savedUser, savedBusinessUser);

	}
	@Test
	public void registerBusinessUserHappyPathWithExistingCompanyInDB() throws Exception {

		CompanyDTO company = getCompanyFromDB();
		UserDTO user = registerBusinessUser(company);

		User savedUser = userDao.findByEmailAddress(EMAILD_ID);

		BusinessUser savedBusinessUser = businessUserDao
				.findInactiveUser(savedUser.getId());
		assertInactiveBusinessUser(user, savedUser, savedBusinessUser);

	}
	@Test
	public void completeRegisterationHappyPath() throws Exception {
 
		try{
 
		System.setProperty("vpa.core.token.valid.duration", "99999");
 
		UserDTO savedUser=  super.getUnregisteredUser();
		
		savedUser=super.completeRegistration(savedUser);
		assertTrue(savedUser.isTokenExpired());
		assertTrue(!savedUser.isUserNotFound());
		}finally{
		deleteInactiveUser();
		}
	
		

	}
	 
	@Test(expected = ResourceAlreadyExistException.class)
	public void alreadyRegisterBusinessUser() throws Exception {

		CompanyDTO company = getCompany();
		UserDTO user = registerBusinessUser(company);
		try {
			user = businessUserService.registerUser(user);
		} catch (ResourceAlreadyExistException ex) {
			// TODO message to be come from
			assertEquals(ex.getMessage(),
					"User already register with this email id");
			throw ex;
		}

	}

	@Test
	public void registerBrandOwnerUserHappyPath() throws Exception {

		UserDTO user = registerBrandOwnerUser();

		User savedUser = userDao.findByEmailAddress(EMAILD_ID);

		BrandOwnerUser savedBrandOwnerUser = brandOwnerUserDao
				.findInactiveUser(savedUser.getId());
		assertInactiveBrandOwnerUser(user, savedUser, savedBrandOwnerUser);

	}
 
	@Test(expected = ResourceAlreadyExistException.class)
	public void alreadyRegisterBrandOwnerUser() throws Exception {

		UserDTO user = registerBrandOwnerUser();
		try {
			user =  brandOwnerUserService.registerUser(user);
		} catch (ResourceAlreadyExistException ex) {
			// TODO message to be come from
			assertEquals(ex.getMessage(),
					"User already register with this email id");
			throw ex;
		}

	}


}
