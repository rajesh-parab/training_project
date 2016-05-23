/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-Core
 ** File        :   VPASaaSSystemException.java
 ** Version     :   1.0
 ** Description :    
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
public class VPASaaSSystemException extends NestedRuntimeException {

    public VPASaaSSystemException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
