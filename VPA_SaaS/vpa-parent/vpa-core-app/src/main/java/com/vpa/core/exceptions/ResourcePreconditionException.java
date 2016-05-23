/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-Core
 ** File        :   ResourcePreconditionException.java
 ** Version     :   1.0
 ** Description :   common exception class for 412 status code precondition not met etc. 
 **
 ** Author      :   Rajesh Parab
 ** Created Date :  Sunday, 14 June 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.core.exceptions;

import org.springframework.core.NestedRuntimeException;

@SuppressWarnings("serial")
public class ResourcePreconditionException extends NestedRuntimeException {

    public ResourcePreconditionException(String msg) {
        super(msg);

    }

}
