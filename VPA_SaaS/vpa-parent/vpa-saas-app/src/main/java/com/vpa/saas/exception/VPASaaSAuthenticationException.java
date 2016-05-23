/**
 * 
 */
package com.vpa.saas.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author PD42694
 *
 */
public class VPASaaSAuthenticationException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2101597324702329855L;

	/**
	 * @param msg
	 * @param t
	 */
	public VPASaaSAuthenticationException(String msg, Throwable t) {
		super(msg, t);
	}

	/**
	 * @param msg
	 */
	public VPASaaSAuthenticationException(String msg) {
		super(msg);
	}

}
