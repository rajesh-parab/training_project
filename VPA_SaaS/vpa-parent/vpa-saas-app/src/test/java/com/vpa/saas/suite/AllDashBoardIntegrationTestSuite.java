
package com.vpa.saas.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.vpa.saas.services.AuthenticationDashboardIntegrationTest;
import com.vpa.saas.services.CounterfeitEntityServiceIntegrationTest;
import com.vpa.saas.services.ProductDashBoardIntegrationTest;
import com.vpa.saas.services.RegionWiseAuthenticationServiceIntegrationTest;
import com.vpa.saas.services.UtilityServiceIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({UtilityServiceIntegrationTest.class,RegionWiseAuthenticationServiceIntegrationTest.class,CounterfeitEntityServiceIntegrationTest.class,ProductDashBoardIntegrationTest.class,AuthenticationDashboardIntegrationTest.class})
 
public class AllDashBoardIntegrationTestSuite {

}
