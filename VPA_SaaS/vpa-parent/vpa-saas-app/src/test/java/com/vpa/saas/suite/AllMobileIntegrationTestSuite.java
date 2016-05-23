
package com.vpa.saas.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.vpa.saas.services.ProductAuthenticationIntegrationTest;
import com.vpa.saas.services.UserLoginIntegrationTest;
import com.vpa.saas.services.UserRegistrationServiceIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ProductAuthenticationIntegrationTest.class,UserRegistrationServiceIntegrationTest.class,UserLoginIntegrationTest.class})
 
public class AllMobileIntegrationTestSuite {

}
