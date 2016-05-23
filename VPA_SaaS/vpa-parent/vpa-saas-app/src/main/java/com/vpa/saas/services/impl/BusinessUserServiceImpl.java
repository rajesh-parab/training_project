package com.vpa.saas.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.vpa.core.enums.UserTypeEnum;
import com.vpa.core.exceptions.ResourceNotFoundException;
import com.vpa.core.exceptions.UnauthorizedResourceException;
import com.vpa.core.mes.dao.BusinessUserDao;
import com.vpa.core.mes.dao.CompanyDao;
import com.vpa.core.models.BusinessUser;
import com.vpa.core.models.Company;
import com.vpa.core.models.User;
import com.vpa.core.utils.VPASaaSConstant;
import com.vpa.saas.dto.CompanyDTO;
import com.vpa.saas.dto.EntityType;
import com.vpa.saas.dto.UserDTO;

@Service("businessUserService")
@Primary
public class BusinessUserServiceImpl extends UserServiceImpl {

	@Autowired
	private BusinessUserDao businessUserDao;
	@Autowired
	private CompanyDao companyDao;

	public void setBusinessUserDao(BusinessUserDao businessUserDao) {
		this.businessUserDao = businessUserDao;
	}

	private BusinessUser createBusinessUser(UserDTO user) {
		User newUser = createUserDetails(user);
		newUser.setUserType(getUserTypeDao().findOne(
				UserTypeEnum.BUSINESS_USER.getId()));

		Company company = createCompany(user);
		BusinessUser newBusinessUser = new BusinessUser();
		newBusinessUser.setCompany(company);
		newBusinessUser.setEnable(newUser.getEnable());
		newBusinessUser.setStatus(newUser.getEnable());
		newBusinessUser.setCreatedDate(newUser.getCreatedDate());
		newBusinessUser.setUpdatedDate(newUser.getUpdatedDate());
		newBusinessUser.setCompany(company);
		newBusinessUser.setUser(newUser);
		user.setBrandOwnerUser(null);
		return newBusinessUser;
	}

	private Company createCompany(UserDTO user) {

		CompanyDTO companyToSave = user.getBusinessUser().getCompany();
		Company company = null;
		if (companyToSave.getId() == null) {
			company = companyDao
					.findCompany(companyToSave.getName(),companyToSave.getCountry(),companyToSave.getState(),companyToSave.getCity(),companyToSave.getStreet());
		} else {
			company = companyDao.findOne(companyToSave.getId());
		}

		if (company == null) {
			company = new Company();
			BeanUtils.copyProperties(user.getBusinessUser().getCompany(),
					company);
			company.setEntityType(getEntityTypeDao().findOne(
					companyToSave.getType().getId()));
		}

		return company;
	}

	@Override
	public User createNewUser(UserDTO user) {

		BusinessUser newBusinessUser = createBusinessUser(user);
		newBusinessUser = businessUserDao.saveAndFlush(newBusinessUser);
		return newBusinessUser.getUser();

	}

	@Override
	protected void activateUser(long userId) {
		BusinessUser businessUser = businessUserDao.findInactiveUser(userId);
		businessUser.setEnable(VPASaaSConstant.TRUE);

	}

	@Override
	protected void validateUser(UserDTO validUser, String errorMessage) {
		// allow all brandonwer user to use mobile apps
		if (validUser.getUserType()
				.equals(UserTypeEnum.BRANDOWNER_USER.getId())) {
			return;
		}

		BusinessUser businessUser = businessUserDao.findActiveUser(validUser
				.getId());
		if (businessUser == null) {
			throw new UnauthorizedResourceException(errorMessage);
		}
		BeanUtils.copyProperties(businessUser, validUser.getBusinessUser());
		BeanUtils.copyProperties(businessUser.getCompany(), validUser
				.getBusinessUser().getCompany());
		validUser
				.getBusinessUser()
				.getCompany()
				.setType(
						EntityType.getType(businessUser.getCompany()
								.getEntityType().getId()));
		validUser.setBrandOwnerUser(null);
	}

	/**
	 * @see
	 */
	@Override
	public UserDTO updateUserSettings(UserDTO user) {
		UserDTO userDTO = super.changeUserDetails(user);
		BusinessUser savedUser = businessUserDao.findActiveUser(user.getId());
		if (savedUser == null) {
			throw new ResourceNotFoundException("Not a brandowner user");
		}

		BeanUtils.copyProperties(userDTO.getBusinessUser().getCompany(),
				savedUser.getCompany());
		BeanUtils.copyProperties(userDTO.getBusinessUser(), savedUser);
		 
		businessUserDao.saveAndFlush(savedUser);

		userDTO.setBrandOwnerUser(null);
		return userDTO;

	}
}
