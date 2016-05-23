package com.vpa.saas.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

import com.vpa.core.models.BusinessUser;
import com.vpa.core.models.User;
import com.vpa.core.utils.VPASaaSConstant;
import com.vpa.saas.dto.CompanyDTO;
import com.vpa.saas.dto.UserDTO;

public class UserSettingIntegrationTest  extends UserTest {
	
	@Test
	public void firstNameLastNameChange(){
		
		CompanyDTO company = getCompany();
		registerBusinessUser(company);
		User savedUser = userDao.findByEmailAddress(EMAILD_ID);

		activateUser(savedUser);
		BusinessUser businessUser = businessUserDao.findInactiveUser(savedUser
				.getId());

		activateBusinessUser(businessUser);
		 
	  
		assertTrue(businessUser.getEnable() == VPASaaSConstant.TRUE);
		assertTrue(savedUser.getEnable() == VPASaaSConstant.TRUE);
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(savedUser, userDTO);
		BeanUtils.copyProperties(businessUser, userDTO.getBusinessUser());
		BeanUtils.copyProperties(businessUser.getCompany(), userDTO.getBusinessUser().getCompany());
		String changedFirstName = savedUser.getFirstName()+"ZZZ";
	
		userDTO.setFirstName(changedFirstName);
		String changedLastName = savedUser.getLastName()+"ZZZ";
		userDTO.setLastName(changedLastName);
		userDTO=businessUserService.updateUserSettings(userDTO);
		User changedBusinessUser = userDao.findAtiveUser(userDTO
				.getId());
		assertEquals(changedBusinessUser.getFirstName(),changedFirstName);
		assertEquals(changedBusinessUser.getLastName(),changedLastName);
		
	}

}
