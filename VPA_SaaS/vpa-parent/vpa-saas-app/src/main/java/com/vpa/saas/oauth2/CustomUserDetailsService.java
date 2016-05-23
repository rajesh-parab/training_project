package com.vpa.saas.oauth2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.vpa.core.mes.dao.UserDao;
import com.vpa.core.models.UserType;
import com.vpa.saas.exception.VPASaaSAuthenticationException;
import com.vpa.saas.exception.VPASaaSUserDeniedAuthorizationException;

/**
 * @author PD42694
 *
 */
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	private static final String ROLE_DELIMETER = ",";

	private static final Logger logger = Logger
			.getLogger(CustomUserDetailsService.class);

	@Override
	public UserDetails loadUserByUsername(final String emailAddress)
			throws UsernameNotFoundException {

		logger.info("Calling loadUserByUsername method for email :"
				+ emailAddress);
		final com.vpa.core.models.User user = userDao
				.findActiveUser(emailAddress);
		if (null == user) {
			final String message = "No such user found for email address: "
					+ emailAddress;
			logger.info(message);
			throw new VPASaaSAuthenticationException(message);
		} else if (user.getCredentialsNonExpired() == 0) {
			final String message = "Credential is expired for email address: "
					+ emailAddress;
			logger.info(message);
			throw new VPASaaSAuthenticationException(message);
		} else if (user.getAccountNonExpired() == 0) {
			final String message = "Account is expired for email address: "
					+ emailAddress;
			logger.info(message);
			throw new VPASaaSAuthenticationException(message);
		} else if (user.getAccountNonLocked() == 0) {
			final String message = "Account is locked for email address: "
					+ emailAddress;
			logger.info(message);
			throw new VPASaaSAuthenticationException(message);
		}
		UserType userType = user.getUserType();
		return new User(
				getGrantedAuthorities(userType.getRole()),
				user.getPassword(),
				user.getEmailAddress(),
				user.getAccountNonExpired() == 1 ? Boolean.TRUE : Boolean.FALSE,
				user.getAccountNonLocked() == 1 ? Boolean.TRUE : Boolean.FALSE,
				user.getCredentialsNonExpired() == 1 ? Boolean.TRUE
						: Boolean.FALSE, user.getEnable() == 1 ? Boolean.TRUE
						: Boolean.FALSE);
	}

	/**
	 * This method provides SimpleGrantedAuthority list
	 * 
	 * @param role
	 * @return Collection<GrantedAuthority>
	 */
	public Collection<GrantedAuthority> getGrantedAuthorities(final String role) {
		logger.info("Calling getGrantedAuthorities method....");
 
		String[] roles = role.split(ROLE_DELIMETER);
		List<GrantedAuthority> authorityList = new ArrayList<>();
 
		if (roles.length == 0) {

			logger.error("No role defined for this user");
			throw new VPASaaSUserDeniedAuthorizationException(
					"No role defined for this user");
		}
		for (String roleStr : roles) {
			authorityList.add(new SimpleGrantedAuthority(roleStr));
		}
		return authorityList;
	}

}
