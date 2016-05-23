package com.vpa.saas.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.vpa.saas.dto.ScanVerificationFlagTest;
import com.vpa.saas.services.impl.BusinessUserServiceTest;
import com.vpa.saas.services.impl.ProductAuthenticationServiceTest;
import com.vpa.saas.services.impl.WebSocketUnitTest;

@RunWith(Suite.class)
@SuiteClasses({ProductAuthenticationServiceTest.class,BusinessUserServiceTest.class,WebSocketUnitTest.class,ScanVerificationFlagTest.class})
public class AllUnitTest {

 

}
