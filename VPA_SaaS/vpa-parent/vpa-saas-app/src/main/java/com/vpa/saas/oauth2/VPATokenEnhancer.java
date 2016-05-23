package com.vpa.saas.oauth2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Service;

import com.vpa.core.mes.dao.BrandOwnerUserDao;
import com.vpa.core.mes.dao.BusinessUserDao;
import com.vpa.core.mes.dao.UserDao;
import com.vpa.core.models.BrandOwnerUser;
import com.vpa.core.models.BusinessUser;
import com.vpa.core.models.Company;
import com.vpa.core.models.Tenant;
import com.vpa.core.models.User;
import com.vpa.core.models.UserType;
import com.vpa.saas.exception.VPASaaSAuthenticationException;

/**
 * @author PD42694
 *
 */
@Service
public class VPATokenEnhancer implements TokenEnhancer {

	private static final Logger LOGGER = Logger
			.getLogger(VPATokenEnhancer.class);

	private static final String BUSINESS_USER = "Business User";
	private static final String BRAND_OWNER_USER = "Brand Owner User";

	@Autowired
	private UserDao userDao;

	@Autowired
	private BrandOwnerUserDao brandOwnerUserDao;

	@Autowired
	private BusinessUserDao businessUserDao;

	private List<String> additionInformationForAuthorities = Collections
			.emptyList();

	public void setAdditionInformationForAuthorities(
			List<String> additionInformationForAuthorities) {
		this.additionInformationForAuthorities = additionInformationForAuthorities;
	}

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
			OAuth2Authentication authentication) {
		LOGGER.info("Calling enhance method...");
		DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;

		final Map<String, Object> additionalInformation = new HashMap<String, Object>();
		final String email = authentication.getName();
		final Collection<GrantedAuthority> authorities =  authentication
				.getAuthorities();

		final List<String> roles = new ArrayList<String>();
		for (GrantedAuthority authority : authorities) {
			if (additionInformationForAuthorities.contains(authority
					.getAuthority())) {
				roles.add(authority.getAuthority());
			}
		}
		if (!roles.isEmpty()) {
			additionalInformation.put("roles", roles);
			additionalInformation.put("userName", email);
			addAadditionalInformation(email, additionalInformation);
		}
		defaultOAuth2AccessToken.setAdditionalInformation(additionalInformation);


		return defaultOAuth2AccessToken;
	}

	/**
	 * This method is used to add addition information with token like tenant
	 * Name in case of brandowner user, Company name in case of business user
	 * 
	 * @param email
	 * @param additionalInformation
	 */
	private void addAadditionalInformation(final String email,
			final Map<String, Object> additionalInformation) {
		final User user = userDao.findActiveUser(email);
		final UserType userType = user.getUserType();

		if (BUSINESS_USER.equalsIgnoreCase(userType.getName())) {
			addAadditionalInformationForBusinessUser(user, additionalInformation, email);
		} else if (BRAND_OWNER_USER.equalsIgnoreCase(userType.getName())) {
			addAadditionalInformationForBrandOwnerUser(user, additionalInformation, email);
		} else {
			LOGGER.info("No addition information is added for: " + email
					+ " as it is " + userType.getName());
		}
	}
	
	
	/**
	 * Adding additional information into the token for business user 
	 * @param user
	 * @param additionalInformation
	 * @param email
	 */
	private void addAadditionalInformationForBusinessUser(final User user, final Map<String, Object> additionalInformation, final String email){

		final BusinessUser businessUser = businessUserDao
				.findActiveUser(user.getId());
		if (null != businessUser) {
			final Company company = businessUser.getCompany();
			if (null != company) {
				additionalInformation.put("company", businessUser
						.getCompany().getName());
			} else {
				final String exceptionMessage = "Company is not found for email:  "
						+ email;
				LOGGER.info(exceptionMessage);
				throw new VPASaaSAuthenticationException(exceptionMessage);
			}
		} else {
			final String exceptionMessage = "User with email: " + email
					+ " is not associated with any Company";
			LOGGER.info(exceptionMessage);
			throw new VPASaaSAuthenticationException(exceptionMessage);
		}
	
	}
	
	/**
	 * Adding additional information to the token for brand owner user
	 * @param user
	 * @param additionalInformation
	 * @param email
	 */
	private void addAadditionalInformationForBrandOwnerUser(final User user, final Map<String, Object> additionalInformation, final String email){
		final BrandOwnerUser brandOwnerUser = brandOwnerUserDao
				.findActiveUser(user.getId());
		if (null != brandOwnerUser) {
			final Tenant tenant = brandOwnerUser.getTenant();
			if (null != tenant) {
				additionalInformation.put("tenant", tenant.getName());
			} else {
				final String exceptionMessage = "Tenant is not found for email:  "
						+ email;
				LOGGER.info(exceptionMessage);
				throw new VPASaaSAuthenticationException(exceptionMessage);
			}
		} else {
			final String exceptionMessage = "User with email: " + email
					+ " is not associated with any Tenant";
			LOGGER.info(exceptionMessage);
			throw new VPASaaSAuthenticationException(exceptionMessage);
		}
	}

}