package com.vpa.saas.services;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpa.core.models.LabelValueBean;

public class SelectBoxServiceIntegrationTest extends AbstractVPASaaSTest {

	private static final String SELLER_NAMES_LIKE_SQL_1 = "select count(*) from seller where name like '";
	private static final String CITY_NAMES_LIKE_SQL_1 = "select count(*) from city where name like '";
	private static final String COUNTRY_NAMES_LIKE_SQL_1 = "select count(*) from countryMaster where name like '";
	private static final String COMPANY_NAMES_LIKE_SQL_1 = "select count(*) from company where name like '";
	private static final String LIKE_SQL_2 = "%' and enable=1";
	@Autowired
	private SelectBoxService selectBoxService;

	@Test
	public void autoSelectSellers() {

		for (char a = 'A'; a <= 'Z'; a++) {
			String character = String.valueOf(a);
			List<LabelValueBean> sellers = selectBoxService
					.findAutoSelectSellerNamesStartWith(character);
			processAutoSelectSQL(character, SELLER_NAMES_LIKE_SQL_1, sellers);

		}
	}

	@Test
	public void autoSelectCountry() {

		for (char a = 'A'; a <= 'Z'; a++) {
			String character = String.valueOf(a);
			List<LabelValueBean> countries = selectBoxService
					.findAutoSelectCountryNamesStartWith(character);
			processAutoSelectSQL(character, COUNTRY_NAMES_LIKE_SQL_1, countries);

		}

	}

	@Test
	public void autoSelectCity() {

		for (char a = 'A'; a <= 'H'; a++) {

			String character = String.valueOf(a);
			List<LabelValueBean> countries = selectBoxService
					.findAutoSelectCityNamesStartWith(character);
			processAutoSelectSQL(character, CITY_NAMES_LIKE_SQL_1, countries);

		}

	}

	@Test
	public void autoSelectCompany() {

		for (char a = 'A'; a <= 'Z'; a++) {

			String character = String.valueOf(a);
			List<LabelValueBean> companies = selectBoxService
					.findAutoSelectCompanyNamesStartWith(character);
			processAutoSelectSQL(character, COMPANY_NAMES_LIKE_SQL_1, companies);

		}

	}

	private void processAutoSelectSQL(String character, String sql1,
			List<LabelValueBean> records) {
		 
		StringBuilder sql = new StringBuilder();
		sql.append(sql1);
		sql.append(character);
		sql.append(LIKE_SQL_2);

		Integer count = super.getVpaCoreJdbcTemplate().queryForObject(
				sql.toString(), Integer.class);

		assertTrue(records.size() == count);
		for (LabelValueBean record : records) {
System.out.println(record.getName());
			assertTrue(record.getName().startsWith(character.toLowerCase())
					|| record.getName().startsWith(character));
		}
	}
	
	public void leveIdAndNames(){
		
	List<LabelValueBean> expectedList=	super.getVpaCoreJdbcTemplate().queryForList("select id,name from level",LabelValueBean.class);
	List<LabelValueBean> actualList=selectBoxService.findAllLevelsIdAndNames(); 		
		assertTrue(expectedList.size()==actualList.size());
		actualList.retainAll(expectedList);
		assertTrue(expectedList.size()==actualList.size());
		expectedList.removeAll(actualList);
		assertTrue(expectedList.isEmpty());
		
	}
	

}
