/**
 * 
 */
package com.vpa.core.exceptions;

import org.springframework.core.NestedRuntimeException;

/**
 * @author PD42694
 *
 */
@SuppressWarnings("serial")
public class CustomIllegalArgumentException extends NestedRuntimeException{

	/**
	 * @param msg
	 */
	public CustomIllegalArgumentException(String msg) {
		super(msg);
	}
	
	
}
