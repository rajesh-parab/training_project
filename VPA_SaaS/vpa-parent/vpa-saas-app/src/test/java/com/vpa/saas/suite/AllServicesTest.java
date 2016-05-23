
package com.vpa.saas.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.vpa.saas.dto.EnumsTest;

@RunWith(Suite.class)
@SuiteClasses({AllMobileIntegrationTestSuite.class,AllDashBoardIntegrationTestSuite.class,EnumsTest.class})
 
public class AllServicesTest {

}
