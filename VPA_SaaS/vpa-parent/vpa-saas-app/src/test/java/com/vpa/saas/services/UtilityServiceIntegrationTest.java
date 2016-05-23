/**
 * 
 */
package com.vpa.saas.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.vpa.core.mes.dao.SellerDao;
import com.vpa.saas.dto.BrandDTO;
import com.vpa.saas.dto.LiveScanDetailDTO;
import com.vpa.saas.dto.ProductDTO;

/**
 * @author PD42694
 *
 */
public class UtilityServiceIntegrationTest extends AbstractVPASaaSTest {

	@Autowired
	private UtilityService utilityService;

	@Autowired
	private SellerDao sellerDao;

	private static final String LIVE_AUTH_DETAIL_SQL = "select s.* ,u.firstName,u.lastName ,p.name as productName ,cm.name as country,c.name as city ,p.glp,sr.name as scanResultDesc from scan s,users u ,products p,countrymaster cm,city c,scanresult sr "
			+ " where s.createdDate > ? and scanresultId <> 6 and u.id=s.userId  and p.id = s.productId and cm.id=s.countrymasterId and c.id=s.cityId and sr.id=s.scanresultId"
			+ " order by s.createdDate desc";

	
	@Test
	public void liveAuthenticationDetails() throws JsonGenerationException,
			JsonMappingException, IOException {
        long tenatId = super.getTenant();
        long brandId=super.getBrandId();
		String loginTime = super.getLatestScanTime();
		List<LiveScanDetailDTO> actualList = utilityService
				.getLiveAuthenticationDetails(tenatId,brandId,loginTime);

		List<LiveScanDetailDTO> expectedList = super.getVpaCoreJdbcTemplate()
				.query(LIVE_AUTH_DETAIL_SQL, new String[] { loginTime },
						new ScanRowMapper());
		assertTrue(actualList.size() == expectedList.size());
		int i = 0;
		for (LiveScanDetailDTO expected : expectedList) {
			assertLiveScanDetails(expected, actualList.get(i));
			i++;
		}

	}

	private void assertLiveScanDetails(LiveScanDetailDTO expected,
			LiveScanDetailDTO actual) {

		assertEquals(expected.getUser().getFirstName(), actual.getUser()
				.getFirstName());
		assertEquals(expected.getUser().getLastName(), actual.getUser()
				.getLastName());
		assertEquals(expected.getCity(), actual.getCity());
		assertEquals(expected.getCountry(), actual.getCountry());
		assertEquals(expected.getProduct().getName(), actual.getProduct()
				.getName());
		assertEquals(expected.getScanResultDesc(), actual.getScanResultDesc());
		assertEquals(expected.getProduct().getGlp(), actual.getProduct()
				.getGlp());

	}

	class ScanRowMapper implements RowMapper<LiveScanDetailDTO> {

		@Override
		public LiveScanDetailDTO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			LiveScanDetailDTO scan = new LiveScanDetailDTO();
	  
			scan.getUser().setFirstName(rs.getString("firstName"));
			scan.getUser().setLastName(rs.getString("lastName"));
			scan.setCity(rs.getString("city"));
			scan.setCountry(rs.getString("country"));
			scan.getProduct().setName(rs.getString("productName"));
			scan.getProduct().setGlp(rs.getDouble("glp"));
			scan.setScanResultDesc(rs.getString("scanResultDesc"));

			return scan;
		}

	}

	@Test
	public void getCurrentMonthYear() {

		String currentServerTime = utilityService.getCurrentTimeStamp();
		String formatedString = null;
		if ('0' == currentServerTime.charAt(5)) {
			formatedString = currentServerTime.substring(6, 7) + "/"
					+ currentServerTime.substring(2, 4);
		} else {
			formatedString = currentServerTime.substring(5, 7) + "/"
					+ currentServerTime.substring(2, 4);
		}
		assertEquals(formatedString, utilityService.getCurrentMonthYear());

	}

	@Test
	public void getCurrentTimeStamp() {

		String expectedTime = super.getVpaCoreJdbcTemplate().queryForObject(
				"Select CURRENT_TIMESTAMP from scan limit 1", String.class);
		assertEquals(expectedTime, utilityService.getCurrentTimeStamp());

	}

	@Test
	public void productAgainstBrand() {
		Map<String, BrandDTO> mappings = utilityService
				.getBrandProductMappings();
		for (Entry<String, BrandDTO> entry : mappings.entrySet()) {
			System.out.println(entry.getKey());
			BrandDTO brand = entry.getValue();
			System.out.println(brand);
			for (ProductDTO p : brand.getProducts()) {
				System.out.println(p);
			}
		}

	}
}
