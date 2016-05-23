  
package com.vpa.core.exceptions;

import org.springframework.core.NestedRuntimeException;

@SuppressWarnings("serial")
public class DuplicateRecordException  extends NestedRuntimeException{


	public DuplicateRecordException(String msg, Throwable cause) {
		super(msg, cause);		
	}

	public DuplicateRecordException(String msg) {
		super(msg);
	}
	

}
 
 
