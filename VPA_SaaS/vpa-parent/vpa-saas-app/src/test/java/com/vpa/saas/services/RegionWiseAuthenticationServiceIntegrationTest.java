package com.vpa.saas.services;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.vpa.core.dws.dao.impl.DimAuthenticationDaoImpl;
import com.vpa.core.dws.models.AuthenticationCount;
import com.vpa.core.dws.models.DimAuthentication;
import com.vpa.saas.dto.AuthenticationKpiDTO;
import com.vpa.saas.dto.DimAuthenticationDTO;

public class RegionWiseAuthenticationServiceIntegrationTest extends
		AbstractVPADashBoardTest {

	@Autowired
	private DimAuthenticationService dimAuthenticationService;
	
	private Long tenantId=0L;
	private Long brandId=0L;
	
	@Before
	public void initTest(){
		  tenantId=super.getTenantId();
		  brandId= super.getBrandId();
	}

	@Test
	public void getWorldWideAuthenticationCount() {
		 
	
		 
		List<DimAuthentication> expectedResult = getVpaDWJdbcTemplate().query(
				DimAuthenticationDaoImpl.WORLDWIDE_AUTHENTICATIONS_TOTAL_SQL,new DimAuthenticationRowMapper(),tenantId);
		 
		 
		long expectedCouterFietTotal = 0L;
		long expectedGenuineTotal = 0L;
		for (DimAuthentication d : expectedResult) {
			if (d.getFinalResult().equalsIgnoreCase("Genuine")) {
				expectedGenuineTotal = expectedGenuineTotal + d.getTotalAuthentications();
			} else {
				expectedCouterFietTotal = expectedCouterFietTotal + d.getTotalAuthentications();
			}

		}

		DimAuthenticationDTO result = dimAuthenticationService
				.getWorldWideAuthenticationKPI(tenantId,brandId);
		List<AuthenticationKpiDTO> kpis = result.getAuthenticationKpis();
		for (AuthenticationKpiDTO kpi : kpis) {
			if (kpi.getAuthenticationType().equalsIgnoreCase("Genuine")) {
				 assertTrue(expectedGenuineTotal==kpi.getTotalAuthetncationCount());
			} else if (kpi.getAuthenticationType().equalsIgnoreCase("Suspect")) {
				 assertTrue(expectedCouterFietTotal==kpi.getTotalAuthetncationCount());
			}else{
				assertTrue((expectedGenuineTotal+expectedCouterFietTotal)==kpi.getTotalAuthetncationCount());
			}
		}
 
	}

	@Test
	public void regionWiseMap() {
		DimAuthenticationDTO result = dimAuthenticationService
				.getRegionWideAuthenticationTotals(tenantId,brandId);

		for (Entry<String, AuthenticationCount> region : result
				.getRegionWiseEntitiesMap().entrySet()) {
			System.out.println(region.getKey());
			System.out.println(region.getValue());
		}

	}

	private static final class DimAuthenticationRowMapper implements
			RowMapper<DimAuthentication> {
		public DimAuthentication mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			DimAuthentication d = new DimAuthentication();
			d.setId(rs.getInt("b.id"));
			d.setFinalResult(rs.getString("b.final_result"));
			d.setTotalAuthentications(rs.getLong("totalAuthentications"));
			return d;

		}
	}
}
