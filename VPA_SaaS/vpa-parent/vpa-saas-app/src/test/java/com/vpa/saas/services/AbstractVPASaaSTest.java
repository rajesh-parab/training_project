package com.vpa.saas.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.vpa.core.enums.UserEnteredKeyType;
import com.vpa.core.enums.UserTypeEnum;
import com.vpa.core.mes.dao.BusinessUserDao;
import com.vpa.core.models.Scan;
import com.vpa.core.models.ScanDetail;
import com.vpa.core.utils.VPASaaSUtil;
import com.vpa.saas.dto.CompanyDTO;
import com.vpa.saas.dto.EntityType;
import com.vpa.saas.dto.ProductScanDTO;
import com.vpa.saas.dto.ScanResultType;
import com.vpa.saas.dto.UserDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:vpa-core-app-test-config.xml")
@TransactionConfiguration(transactionManager = "transactionManager")
public abstract class AbstractVPASaaSTest {

	@Autowired
	private DataSource vpaCoreDS;

	private JdbcTemplate vpaCoreJdbcTemplate = null;

	@Resource(name = "businessUserService")
	protected UserService businessUserService;

	@Autowired
	private VPASaaSUtil vpaUtil;

	public static final String EMAILD_ID = "r.parab@z2exxn4sar15.com";

	public static final String PASSWORD = "temp123";

	public VPASaaSUtil getVpaUtil() {
		return vpaUtil;
	}

	@Autowired
	private BusinessUserDao businessUserDao;

	public UserDTO getUser() {
		UserDTO user = new UserDTO();
		user.setFirstName("rajesh");
		user.setLastName("parab");
		user.setEmailAddress(EMAILD_ID);
		user.setPassword(PASSWORD);
		user.getCreatedDate();

		return user;
	}

	public CompanyDTO getCompany() {
		CompanyDTO company = new CompanyDTO();
		company.setStreet("52nd street");
		company.setCity("pune");
		company.setCountry(getCountryNameFromDB());
		company.setName("rajesh_parab"); // this company name should not exist
											// in db.

		company.setType(EntityType.MANUFACTURER);
		return company;
	}

	public CompanyDTO getCompanyFromDB() {

		CompanyDTO company = getVpaCoreJdbcTemplate().queryForObject(
				"select * from company where enable=1 limit 1",
				new BeanPropertyRowMapper<CompanyDTO>(CompanyDTO.class));
		Long entityTypeId = getVpaCoreJdbcTemplate().queryForObject(
				"select entityTypeId from company where enable=1 limit 1",
				Long.class);

		company.setType(EntityType.getType(entityTypeId));
		return company;
	}

	public String getCountryNameFromDB() {

		String countryName = getVpaCoreJdbcTemplate().queryForObject(
				"select isoCode from countrymaster where enable=1 limit 1",
				String.class);
		return countryName;
	}

 
	public String getCityNameFromDB() {

		String cityName = getVpaCoreJdbcTemplate().queryForObject(
				"select name from city where enable=1 limit 1", String.class);
		return cityName;
	}

	public String getSellerNameFromDB() {

		String cityName = getVpaCoreJdbcTemplate().queryForObject(
				"select name from seller where enable=1 limit 1", String.class);
		return cityName;
	}

	public UserDTO registerBusinessUser(CompanyDTO company) {
		UserDTO user = getUser();
		user.setUserType(UserTypeEnum.BUSINESS_USER.getId());

		user.getBusinessUser().setCompany(company);
		user = businessUserService.registerUser(user);
		return user;
	}

	public UserDTO completeRegistration(UserDTO user) {
		String tokeIdSQL = "select t.tokenValue from token t,users u where  u.id = t.userId and u.emailAddress=? order by t.createdDate desc limit 1";
		String tokenId = getVpaCoreJdbcTemplate().queryForObject(tokeIdSQL,
				new String[] { user.getEmailAddress() }, String.class);
		user.setTokenId(vpaUtil.encodeString(tokenId));

		user = businessUserService.completeRegistration(user);
		return user;
	}

	public JdbcTemplate getVpaCoreJdbcTemplate() {
		if (vpaCoreJdbcTemplate == null) {
			vpaCoreJdbcTemplate = new JdbcTemplate(vpaCoreDS);
		}
		return vpaCoreJdbcTemplate;
	}

	public String getSecurityLabel(String sql) {
		return getVpaCoreJdbcTemplate().queryForObject(sql, String.class);

	}

	public UserDTO getUnregisteredUser() {
		// id 999999999 should be present in insert-inactive-user.sql
		String unRegisteredUserSql = "select * from users where enable =0 and id=999999999 limit 1";
		executeSQLScript("insert-inactive-user.sql");

		return getInactiveUser(unRegisteredUserSql);

	}

	public void intiDuplicateSecurityLabel() {
		executeSQLScript("insert-duplicate-security-label.sql");
	}

	private UserDTO getInactiveUser(String unRegisteredUserSql) {
		return getVpaCoreJdbcTemplate().queryForObject(unRegisteredUserSql,
				new BeanPropertyRowMapper<UserDTO>(UserDTO.class));
	}

	public Long getTenant() {
		Long tenantId = 0L;

		tenantId = getVpaCoreJdbcTemplate().queryForObject(
				"select id from tenants where enable=1 limit 1", Long.class);

		return tenantId;
	}

	public String getLatestScanTime() {

		String latestScanTime;

		latestScanTime = getVpaCoreJdbcTemplate()
				.queryForObject(
						"select createdDate from scan where enable=1 order by createdDate desc limit 1",
						String.class);

		return latestScanTime;
	}

	public String getActiveSecurityLabel() {
		String securityLabel = null;

		securityLabel = getSecurityLabel("select serialNumber from securitylabel where enable =1 limit 1");

		return securityLabel;
	}

	public String getInactiveSecurityLabel() {
		String securityLabel = null;
		String inactiveSecurityLabelSql = "select serialNumber from securitylabel where enable =0 limit 1";

		try {
			securityLabel = getSecurityLabel(inactiveSecurityLabelSql);
		} catch (IncorrectResultSizeDataAccessException ex) {
			executeSQLScript("insert-inactive-security-label.sql");
			securityLabel = getSecurityLabel(inactiveSecurityLabelSql);
		}
		return securityLabel;
	}

	public String getInactiveProductInstance() {
		String activeSecurityLabelSql = "select serialNumber from securitylabel where id = 999999999";
		executeSQLScript("insert-inactive-product-instance.sql");
		return getSecurityLabel(activeSecurityLabelSql);

	}

	public void deleteInactiveProductInstance() {
		executeSQLScript("delete-inactive-product-instance.sql");
	}

	public void deleteInactiveSecurityLabel() {
		executeSQLScript("delete-inactive-security-label.sql");
	}

	public void deleteInactiveUser() {
		executeSQLScript("delete-inactive-user.sql");
	}

	public Long getActiveBusinessUserId() {
		return getVpaCoreJdbcTemplate().queryForObject(
				"select id from users where enable=1 limit 1", Long.class);
	}

	public Long getProductId() {
		return getVpaCoreJdbcTemplate().queryForObject(
				"select id from products where enable=1 limit 1", Long.class);
	}

	public Long getBrandId() {
		return getVpaCoreJdbcTemplate().queryForObject(
				"select id from brand where enable=1 limit 1", Long.class);
	}

	public Long getInActiveBusinessUserId() {
		Long userId = 0L;
		try {
			userId = getVpaCoreJdbcTemplate().queryForObject(
					"select id from users where enable=0 limit 1", Long.class);

		} catch (EmptyResultDataAccessException e) {
			UserDTO user = registerBusinessUser(getCompany());
			userId = user.getId();
		}
		return userId;

	}

	private void executeSQLScript(String... sqlScripts) {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		for (String sqlScript : sqlScripts) {
			populator.addScripts(new ClassPathResource(sqlScript));
		}

		populator.execute(vpaCoreDS);
	}

	public void assertIncompleteScan(ProductScanDTO scannedProduct,
			Scan expectedScan) {
		assertEquals(scannedProduct.getScanResultType(),
				ScanResultType.Incomplete.getId());
		assertEquals(scannedProduct.getScanResultType(), expectedScan
				.getScanResult().getId());
		assertEquals(expectedScan.getScanResult().getId(),
				ScanResultType.Incomplete.getId());
		assertTrue(expectedScan.getScanVerificationFlag() == 0);
		assertTrue(scannedProduct.getScanVerificationFlag() == 0);
		assertScan(scannedProduct, expectedScan);

	}

	public void assertGenuineScan(ProductScanDTO scannedProduct,
			Scan expectedScan) {
		assertEquals(scannedProduct.getScanResultType(),
				ScanResultType.Genuine.getId());
		assertEquals(scannedProduct.getScanResultType(), expectedScan
				.getScanResult().getId());
		assertEquals(expectedScan.getScanResult().getId(),
				ScanResultType.Genuine.getId());
		assertTrue(expectedScan.getScanVerificationFlag() == 31);
		assertTrue(scannedProduct.getScanVerificationFlag() == 31);

	}

	public void assertCounterfeitScan(ProductScanDTO scannedProduct,
			Scan expectedScan) {
		assertEquals(scannedProduct.getScanResultType(),
				ScanResultType.Counterfeit.getId());
		assertTrue(expectedScan.getScanVerificationFlag() == 0);
		assertTrue(scannedProduct.getScanVerificationFlag() == 0);
		assertScan(scannedProduct, expectedScan);

	}

	public void assertTemperedScan(ProductScanDTO scannedProduct,
			Scan expectedScan, Integer scanResultTypeId,
			int scanVerificationFlag) {
		assertEquals(scannedProduct.getScanResultType(), scanResultTypeId);
		assertTrue(expectedScan.getScanVerificationFlag() == scanVerificationFlag);
		assertTrue(scannedProduct.getScanVerificationFlag() == scanVerificationFlag);
		assertScan(scannedProduct, expectedScan);

	}

	private void assertScan(ProductScanDTO scannedProduct, Scan expectedScan) {

		assertEquals(expectedScan.getScanResult().getId(),
				scannedProduct.getScanResultType());
		assertEquals(expectedScan.getCountryMaster().getIsoCode(),
				scannedProduct.getCountryCode());
 
		assertEquals(expectedScan.getCity().getName(), scannedProduct.getCity());
	/*	assertEquals(expectedScan.getCreatedDate(),
				scannedProduct.getCreatedDate());*/
		assertEquals(expectedScan.getLatitude(), scannedProduct.getLatitude());
		assertEquals(expectedScan.getLongitude(), scannedProduct.getLongitude());
		assertEquals(expectedScan.getScanTimeZone(),
				scannedProduct.getScanTimeZone());

	}

	public void assertScanDetails(
			Map<UserEnteredKeyType, String> scanDetailMap,
			List<ScanDetail> expectedScanDetails) {

		assertEquals(scanDetailMap.size(), expectedScanDetails.size());
		for (ScanDetail expectedScanDetail : expectedScanDetails) {
			UserEnteredKeyType key = UserEnteredKeyType
					.getType(expectedScanDetail.getUserenteredkey().getId());
			assertEquals(expectedScanDetail.getValue(), scanDetailMap.get(key));
		}

	}

	public List<String> getAllCountries() {
		return getVpaCoreJdbcTemplate().queryForList("select name from CountryMaster", String.class);
	}

}
