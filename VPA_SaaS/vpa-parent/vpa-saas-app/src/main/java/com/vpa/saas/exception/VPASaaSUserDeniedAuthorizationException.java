/**
 * 
 */
package com.vpa.saas.exception;

import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;

/**
 * @author PD42694
 *
 */
public class VPASaaSUserDeniedAuthorizationException extends
		UserDeniedAuthorizationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7459380520897854612L;

	/**
	 * @param msg
	 * @param t
	 */
	public VPASaaSUserDeniedAuthorizationException(String msg, Throwable t) {
		super(msg, t);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param msg
	 */
	public VPASaaSUserDeniedAuthorizationException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
