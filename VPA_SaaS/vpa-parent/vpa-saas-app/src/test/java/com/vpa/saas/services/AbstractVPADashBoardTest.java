package com.vpa.saas.services;

import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:vpa-core-app-test-config.xml")
@TransactionConfiguration(transactionManager = "transactionManagerDWS")
public abstract class AbstractVPADashBoardTest {

	@Autowired
	private DataSource vpaDWSDS;

	private JdbcTemplate vpaDWJdbcTemplate = null;

	public JdbcTemplate getVpaDWJdbcTemplate() {
		if (vpaDWJdbcTemplate == null) {
			vpaDWJdbcTemplate = new JdbcTemplate(vpaDWSDS);
		}
		return vpaDWJdbcTemplate;
	}

	public Long getTenantId() {
		return getVpaDWJdbcTemplate().queryForObject(
				"select tenant_id from fact_authentication limit 1",
				Long.class);
	}
	
	public Long getBrandId() {
		return getVpaDWJdbcTemplate().queryForObject(
				"select brand_id from fact_authentication limit 1",
				Long.class);
	}

}
