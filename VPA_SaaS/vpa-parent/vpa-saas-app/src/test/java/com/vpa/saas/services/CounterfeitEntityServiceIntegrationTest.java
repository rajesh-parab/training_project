package com.vpa.saas.services;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.vpa.core.enums.Sorting;
import com.vpa.saas.dto.BrandDTO;
import com.vpa.saas.dto.CounterfeitBuyingSellingEntityDTO;
import com.vpa.saas.dto.TenantDTO;
import com.vpa.saas.forms.CounterfeitEntityForm;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:vpa-core-app-test-config.xml")
@TransactionConfiguration(transactionManager = "transactionManager")
public class CounterfeitEntityServiceIntegrationTest {
	
	/**
	 * Filter parameters
	 */
	final static long[] REGIONS = { 0, 3, 4, 5 };
	final static long[] COUNTRY_IDS = { 13, 15, 17, 35, 50, 83, 135 };
	final static long[] PARTNERS = { 0,	1, 6, 3, 2, 4 };
	final static long[] TIME_PERIOD = { 20150727, 20150728, 20150730, 20150821, 20150901 };
	final static long[] LAYERS = { 0, 2, 3, 4 };
	final static String[] SORTING = { "REVENUE", "SUSPECTAUTHENTICATION", "TOTALAUTHENTICATION", "SUSPECT_PRODUCTS", "COUNTRY", "ENTITY_TYPE", "ENTITY_NAME" };
	
	private static final int NUMBER_OF_RECORDS = 3;

	private List<TenantDTO> tenantList;

	@Autowired
	private CounterfeitEntityService counterfeitEntityService;

	@Resource
	private TenantBrandService tenantBrandService;

	@Before
	public void loadInitials() {
		tenantList = getAllTenant();
	}

	@Test
	public void counterfeitBuyingEntitiesTest() {
		for (TenantDTO tenantDTO : tenantList) {
			for (BrandDTO brandDTO : tenantDTO.getBrands()) {
				List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
						.getCounterfeitBuyingEntities(tenantDTO.getId(),
								brandDTO.getId());
				assertTrue("No record found", result.isEmpty());
				assertTrue("Record found", result.size() > 0);
				for (final CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
					assertTrue(StringUtils
							.isNotBlank(counterfeitBuyingSellingEntityDTO
									.getEntityName()));
					assertTrue(counterfeitBuyingSellingEntityDTO
							.getNumberOfSuspectAuthentications()
							.longValue() > 0);
					assertTrue(counterfeitBuyingSellingEntityDTO
							.getRevenueLoss() > 0);
				}
			}
		}

	}

	@Test
	public void counterfeitBuyingEntitiesEmptyResultTest() {
		List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
				.getCounterfeitBuyingEntities(0, 0);
		assertTrue("No record found", result.isEmpty());
	}

	@Test
	public void counterfeitSellingEntitiesTest() {
		for (TenantDTO tenantDTO : tenantList) {
			for (BrandDTO brandDTO : tenantDTO.getBrands()) {
				List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
						.getCounterfeitSellingEntities(tenantDTO.getId(),
								brandDTO.getId());
				assertTrue("No record found", result.isEmpty());
				assertTrue("Record found", result.size() > 0);
				for (final CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
					assertTrue(StringUtils
							.isNotBlank(counterfeitBuyingSellingEntityDTO
									.getEntityName()));
					assertTrue(counterfeitBuyingSellingEntityDTO
							.getNumberOfSuspectAuthentications()
							.longValue() > 0);
					assertTrue(counterfeitBuyingSellingEntityDTO
							.getRevenueLoss() > 0);
				}
			}
		}
	}

	@Test
	public void counterfeitSellingEntitiesEmptyResultTest() {
		List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
				.getCounterfeitSellingEntities(0, 0);
		assertTrue("No record found", result.isEmpty());
	}

	@Test
	public void counterfeitSellingEntitiesForAuthenticationPageTest() {

		for (TenantDTO tenantDTO : tenantList) {
			for (BrandDTO brandDTO : tenantDTO.getBrands()) {
				final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				counterfeitEntityForm.setOrderBy(Sorting.SUSPECTAUTHENTICATION.toString());
				List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
						.getCounterfeitSellingEntitiesForAuthentication(counterfeitEntityForm );
				assertTrue("No record found", result.isEmpty());
				assertTrue("Total " + result.size() + " record found",
						result.size() > 0);
				for (final CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
					assertTrue(StringUtils
							.isNotBlank(counterfeitBuyingSellingEntityDTO
									.getEntityName()));
					assertTrue(counterfeitBuyingSellingEntityDTO
							.getNumberOfSuspectAuthentications()
							.longValue() > 0);
					assertTrue(counterfeitBuyingSellingEntityDTO
							.getRevenueLoss() > 0);
				}
			}
		}
	}
	
	@Test
	public void counterfeitSellingEntitiesPaginatedResultForAuthenticationPageTest() {
		for (TenantDTO tenantDTO : tenantList) {
			for (BrandDTO brandDTO : tenantDTO.getBrands()) {
				final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				counterfeitEntityForm.setOrderBy(Sorting.SUSPECTAUTHENTICATION.toString());
				for (int i = 1; i <= 3; i++) {
					counterfeitEntityForm.setPageNumber(i);
				List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
						.getCounterfeitSellingEntitiesForAuthentication(counterfeitEntityForm );
				assertTrue("No record found", result.isEmpty());
				assertTrue("Total " + result.size() + " record found",
						result.size() > 0);
					for (final CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
						assertTrue(StringUtils
								.isNotBlank(counterfeitBuyingSellingEntityDTO
										.getEntityName()));
						assertTrue(counterfeitBuyingSellingEntityDTO
								.getNumberOfSuspectAuthentications()
								.longValue() > 0);
						assertTrue(counterfeitBuyingSellingEntityDTO
								.getRevenueLoss() > 0);
					}
				}
			}
		}
	}
	
	@Test
	public void counterfeitSellingEntitiesWithFiltersForAuthenticationPageTest() {

		for (TenantDTO tenantDTO : tenantList) {
			for (BrandDTO brandDTO : tenantDTO.getBrands()) {
				for (int i = 0; i < REGIONS.length; i++) {
					final long regionId = REGIONS[i];
					for (int j = 0; j < PARTNERS.length; j++) {
						final long partnerId = PARTNERS[j];
						for (int k = 0; k < TIME_PERIOD.length; k++) {
							final long fromTime = TIME_PERIOD[k];
							for (int l = 0; l < LAYERS.length; l++) {
								final long layerId = LAYERS[l];
								for (int m = 0; m < COUNTRY_IDS.length; m++) {
									final long countryId = COUNTRY_IDS[m];
									final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
									counterfeitEntityForm.setTenantId(tenantDTO
											.getId());
									counterfeitEntityForm.setBrandId(brandDTO
											.getId());
									counterfeitEntityForm
											.setNumberOfRecords(NUMBER_OF_RECORDS);
									counterfeitEntityForm.setRegionId(regionId);
									counterfeitEntityForm
											.setCountryId(countryId);
									counterfeitEntityForm.setLayerId(layerId);
									counterfeitEntityForm.setFromTime(fromTime);
									counterfeitEntityForm
											.setToTime(TIME_PERIOD[TIME_PERIOD.length - 1]);
									counterfeitEntityForm
											.setEntityTypeId(partnerId);
									counterfeitEntityForm.setOrderBy(Sorting.SUSPECTAUTHENTICATION.toString());
									List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
											.getCounterfeitSellingEntitiesForAuthentication(counterfeitEntityForm );
									assertTrue("No record found", result.isEmpty());
									assertTrue("Total " + result.size() + " record found",
									result.size() > 0);
									for (final CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
										assertTrue(StringUtils
												.isNotBlank(counterfeitBuyingSellingEntityDTO
														.getEntityName()));
										assertTrue(counterfeitBuyingSellingEntityDTO
												.getNumberOfSuspectAuthentications()
												.longValue() > 0);
										assertTrue(counterfeitBuyingSellingEntityDTO
												.getRevenueLoss() > 0);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Test
	public void counterfeitSellingEntitiesForAuthenticationPageEmptyResultTest() {
		final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
		counterfeitEntityForm.setTenantId(0);
		counterfeitEntityForm.setBrandId(0);
		counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
		counterfeitEntityForm.setOrderBy(Sorting.SUSPECTAUTHENTICATION.toString());
		List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
				.getCounterfeitSellingEntitiesForAuthentication(counterfeitEntityForm );
		assertTrue("No record found", result.isEmpty());
	}
	
	@Test
	public void counterfeitBuyingEntitiesForAuthenticationPageTest() {

		for (TenantDTO tenantDTO : tenantList) {
			for (BrandDTO brandDTO : tenantDTO.getBrands()) {
				final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				counterfeitEntityForm.setOrderBy(Sorting.SUSPECTAUTHENTICATION.toString());
				List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
						.getCounterfeitBuyingEntitiesForAuthentication(counterfeitEntityForm );
				assertTrue("No record found", result.isEmpty());
				assertTrue("Total " + result.size() + " record found",
						result.size() > 0);
				for (final CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
					assertTrue(StringUtils
							.isNotBlank(counterfeitBuyingSellingEntityDTO
									.getEntityName()));
					assertTrue(counterfeitBuyingSellingEntityDTO
							.getNumberOfSuspectAuthentications()
							.longValue() > 0);
					assertTrue(counterfeitBuyingSellingEntityDTO
							.getRevenueLoss() > 0);
				}
			}
		}
	}
	
	@Test
	public void counterfeitBuyingEntitiesPaginatedResultForAuthenticationPageTest() {
		for (TenantDTO tenantDTO : tenantList) {
			for (BrandDTO brandDTO : tenantDTO.getBrands()) {
				final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				for (int i = 1; i <= 3; i++) {
					counterfeitEntityForm.setPageNumber(i);
					counterfeitEntityForm.setOrderBy(Sorting.SUSPECTAUTHENTICATION.toString());
				List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
						.getCounterfeitBuyingEntitiesForAuthentication(counterfeitEntityForm );
				assertTrue("No record found", result.isEmpty());
				assertTrue("Total " + result.size() + " record found",
						result.size() > 0);
					for (final CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
						assertTrue(StringUtils
								.isNotBlank(counterfeitBuyingSellingEntityDTO
										.getEntityName()));
						assertTrue(counterfeitBuyingSellingEntityDTO
								.getNumberOfSuspectAuthentications()
								.longValue() > 0);
						assertTrue(counterfeitBuyingSellingEntityDTO
								.getRevenueLoss() > 0);
					}
				}
			}
		}
	}
	
	@Test
	public void counterfeitBuyingEntitiesWithFiltersForAuthenticationPageTest() {

		for (TenantDTO tenantDTO : tenantList) {
			for (BrandDTO brandDTO : tenantDTO.getBrands()) {
				for (int i = 0; i < REGIONS.length; i++) {
					final long regionId = REGIONS[i];
					for (int j = 0; j < PARTNERS.length; j++) {
						final long partnerId = PARTNERS[j];
						for (int k = 0; k < TIME_PERIOD.length; k++) {
							final long fromTime = TIME_PERIOD[k];
							for (int l = 0; l < LAYERS.length; l++) {
								final long layerId = LAYERS[l];
								for (int m = 0; m < COUNTRY_IDS.length; m++) {
									final long countryId = COUNTRY_IDS[m];
									final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
									counterfeitEntityForm.setTenantId(tenantDTO
											.getId());
									counterfeitEntityForm.setBrandId(brandDTO
											.getId());
									counterfeitEntityForm
											.setNumberOfRecords(NUMBER_OF_RECORDS);
									counterfeitEntityForm.setRegionId(regionId);
									counterfeitEntityForm
											.setCountryId(countryId);
									counterfeitEntityForm.setLayerId(layerId);
									counterfeitEntityForm.setFromTime(fromTime);
									counterfeitEntityForm
											.setToTime(TIME_PERIOD[TIME_PERIOD.length - 1]);
									counterfeitEntityForm
											.setEntityTypeId(partnerId);
									counterfeitEntityForm.setOrderBy(Sorting.SUSPECTAUTHENTICATION.toString());
									List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
											.getCounterfeitBuyingEntitiesForAuthentication(counterfeitEntityForm );
									assertTrue("No record found", result.isEmpty());
									assertTrue("Total " + result.size() + " record found",
									result.size() > 0);
									for (final CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
										assertTrue(StringUtils
												.isNotBlank(counterfeitBuyingSellingEntityDTO
														.getEntityName()));
										assertTrue(counterfeitBuyingSellingEntityDTO
												.getNumberOfSuspectAuthentications()
												.longValue() > 0);
										assertTrue(counterfeitBuyingSellingEntityDTO
												.getRevenueLoss() > 0);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Test
	public void counterfeitBuyingEntitiesForAuthenticationPageEmptyResultTest() {
		final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
		counterfeitEntityForm.setTenantId(0);
		counterfeitEntityForm.setBrandId(0);
		counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
		counterfeitEntityForm.setOrderBy(Sorting.SUSPECTAUTHENTICATION.toString());
		List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
				.getCounterfeitBuyingEntitiesForAuthentication(counterfeitEntityForm );
		assertTrue("No record found", result.isEmpty());
	}
	
	@Test
	public void counterfeitSellingEntitiesForRevenuePageTest() {

		for (TenantDTO tenantDTO : tenantList) {
			for (BrandDTO brandDTO : tenantDTO.getBrands()) {
				final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				counterfeitEntityForm.setOrderBy(Sorting.REVENUE.toString());
				List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
						.getCounterfeitSellingEntitiesForAuthentication(counterfeitEntityForm );
				assertTrue("No record found", result.isEmpty());
				assertTrue("Total " + result.size() + " record found",
						result.size() > 0);
				for (final CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
					assertTrue(StringUtils
							.isNotBlank(counterfeitBuyingSellingEntityDTO
									.getEntityName()));
					assertTrue(counterfeitBuyingSellingEntityDTO
							.getNumberOfSuspectAuthentications()
							.longValue() > 0);
					assertTrue(counterfeitBuyingSellingEntityDTO
							.getRevenueLoss() > 0);
				}
			}
		}
	}
	
	@Test
	public void counterfeitSellingEntitiesPaginatedResultForRevenuePageTest() {
		for (TenantDTO tenantDTO : tenantList) {
			for (BrandDTO brandDTO : tenantDTO.getBrands()) {
				final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				counterfeitEntityForm.setOrderBy(Sorting.REVENUE.toString());
				for (int i = 1; i <= 3; i++) {
					counterfeitEntityForm.setPageNumber(i);
				List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
						.getCounterfeitSellingEntitiesForAuthentication(counterfeitEntityForm );
				assertTrue("No record found", result.isEmpty());
				assertTrue("Total " + result.size() + " record found",
						result.size() > 0);
					for (final CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
						assertTrue(StringUtils
								.isNotBlank(counterfeitBuyingSellingEntityDTO
										.getEntityName()));
						assertTrue(counterfeitBuyingSellingEntityDTO
								.getNumberOfSuspectAuthentications()
								.longValue() > 0);
						assertTrue(counterfeitBuyingSellingEntityDTO
								.getRevenueLoss() > 0);
					}
				}
			}
		}
	}
	
	@Test
	public void counterfeitSellingEntitiesWithFiltersForRevenuePageTest() {

		for (TenantDTO tenantDTO : tenantList) {
			for (BrandDTO brandDTO : tenantDTO.getBrands()) {
				for (int i = 0; i < REGIONS.length; i++) {
					final long regionId = REGIONS[i];
					for (int j = 0; j < PARTNERS.length; j++) {
						final long partnerId = PARTNERS[j];
						for (int k = 0; k < TIME_PERIOD.length; k++) {
							final long fromTime = TIME_PERIOD[k];
							for (int l = 0; l < LAYERS.length; l++) {
								final long layerId = LAYERS[l];
								for (int m = 0; m < COUNTRY_IDS.length; m++) {
									final long countryId = COUNTRY_IDS[m];
									final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
									counterfeitEntityForm.setTenantId(tenantDTO
											.getId());
									counterfeitEntityForm.setBrandId(brandDTO
											.getId());
									counterfeitEntityForm
											.setNumberOfRecords(NUMBER_OF_RECORDS);
									counterfeitEntityForm.setRegionId(regionId);
									counterfeitEntityForm
											.setCountryId(countryId);
									counterfeitEntityForm.setLayerId(layerId);
									counterfeitEntityForm.setFromTime(fromTime);
									counterfeitEntityForm
											.setToTime(TIME_PERIOD[TIME_PERIOD.length - 1]);
									counterfeitEntityForm
											.setEntityTypeId(partnerId);
									counterfeitEntityForm.setOrderBy(Sorting.REVENUE.toString());
									List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
											.getCounterfeitSellingEntitiesForAuthentication(counterfeitEntityForm );
									assertTrue("No record found", result.isEmpty());
									assertTrue("Total " + result.size() + " record found",
									result.size() > 0);
									for (final CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
										assertTrue(StringUtils
												.isNotBlank(counterfeitBuyingSellingEntityDTO
														.getEntityName()));
										assertTrue(counterfeitBuyingSellingEntityDTO
												.getNumberOfSuspectAuthentications()
												.longValue() > 0);
										assertTrue(counterfeitBuyingSellingEntityDTO
												.getRevenueLoss() > 0);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Test
	public void counterfeitSellingEntitiesForRevenuePageEmptyResultTest() {
		final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
		counterfeitEntityForm.setTenantId(0);
		counterfeitEntityForm.setBrandId(0);
		counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
		counterfeitEntityForm.setOrderBy(Sorting.REVENUE.toString());
		List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
				.getCounterfeitSellingEntitiesForAuthentication(counterfeitEntityForm );
		assertTrue("No record found", result.isEmpty());
	}
	
	@Test
	public void counterfeitBuyingEntitiesForRevenuePageTest() {

		for (TenantDTO tenantDTO : tenantList) {
			for (BrandDTO brandDTO : tenantDTO.getBrands()) {
				final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				counterfeitEntityForm.setOrderBy(Sorting.REVENUE.toString());
				List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
						.getCounterfeitBuyingEntitiesForAuthentication(counterfeitEntityForm );
				assertTrue("No record found", result.isEmpty());
				assertTrue("Total " + result.size() + " record found",
						result.size() > 0);
				for (final CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
					assertTrue(StringUtils
							.isNotBlank(counterfeitBuyingSellingEntityDTO
									.getEntityName()));
					assertTrue(counterfeitBuyingSellingEntityDTO
							.getNumberOfSuspectAuthentications()
							.longValue() > 0);
					assertTrue(counterfeitBuyingSellingEntityDTO
							.getRevenueLoss() > 0);
				}
			}
		}
	}
	
	@Test
	public void counterfeitBuyingEntitiesPaginatedResultForRevenuePageTest() {
		for (TenantDTO tenantDTO : tenantList) {
			for (BrandDTO brandDTO : tenantDTO.getBrands()) {
				final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				counterfeitEntityForm.setOrderBy(Sorting.REVENUE.toString());
				for (int i = 1; i <= 3; i++) {
					counterfeitEntityForm.setPageNumber(i);
				List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
						.getCounterfeitBuyingEntitiesForAuthentication(counterfeitEntityForm );
				assertTrue("No record found", result.isEmpty());
				assertTrue("Total " + result.size() + " record found",
						result.size() > 0);
					for (final CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
						assertTrue(StringUtils
								.isNotBlank(counterfeitBuyingSellingEntityDTO
										.getEntityName()));
						assertTrue(counterfeitBuyingSellingEntityDTO
								.getNumberOfSuspectAuthentications()
								.longValue() > 0);
						assertTrue(counterfeitBuyingSellingEntityDTO
								.getRevenueLoss() > 0);
					}
				}
			}
		}
	}
	
	@Test
	public void counterfeitBuyingEntitiesWithFiltersForRevenuePageTest() {

		for (TenantDTO tenantDTO : tenantList) {
			for (BrandDTO brandDTO : tenantDTO.getBrands()) {
				for (int i = 0; i < REGIONS.length; i++) {
					final long regionId = REGIONS[i];
					for (int j = 0; j < PARTNERS.length; j++) {
						final long partnerId = PARTNERS[j];
						for (int k = 0; k < TIME_PERIOD.length; k++) {
							final long fromTime = TIME_PERIOD[k];
							for (int l = 0; l < LAYERS.length; l++) {
								final long layerId = LAYERS[l];
								for (int m = 0; m < COUNTRY_IDS.length; m++) {
									final long countryId = COUNTRY_IDS[m];
									final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
									counterfeitEntityForm.setTenantId(tenantDTO
											.getId());
									counterfeitEntityForm.setBrandId(brandDTO
											.getId());
									counterfeitEntityForm
											.setNumberOfRecords(NUMBER_OF_RECORDS);
									counterfeitEntityForm.setRegionId(regionId);
									counterfeitEntityForm
											.setCountryId(countryId);
									counterfeitEntityForm.setLayerId(layerId);
									counterfeitEntityForm.setFromTime(fromTime);
									counterfeitEntityForm
											.setToTime(TIME_PERIOD[TIME_PERIOD.length - 1]);
									counterfeitEntityForm
											.setEntityTypeId(partnerId);
									counterfeitEntityForm.setOrderBy(Sorting.REVENUE.toString());
									List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
											.getCounterfeitBuyingEntitiesForAuthentication(counterfeitEntityForm );
									assertTrue("No record found", result.isEmpty());
									assertTrue("Total " + result.size() + " record found",
									result.size() > 0);
									for (final CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
										assertTrue(StringUtils
												.isNotBlank(counterfeitBuyingSellingEntityDTO
														.getEntityName()));
										assertTrue(counterfeitBuyingSellingEntityDTO
												.getNumberOfSuspectAuthentications()
												.longValue() > 0);
										assertTrue(counterfeitBuyingSellingEntityDTO
												.getRevenueLoss() > 0);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Test
	public void counterfeitBuyingEntitiesForRevenuePageEmptyResultTest() {
		final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
		counterfeitEntityForm.setTenantId(0);
		counterfeitEntityForm.setBrandId(0);
		counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
		counterfeitEntityForm.setOrderBy(Sorting.REVENUE.toString());
		List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
				.getCounterfeitBuyingEntitiesForAuthentication(counterfeitEntityForm );
		assertTrue("No record found", result.isEmpty());
	}
	
	
	
	
	@Test
	public void getEntitiesForEntityPageWithoutFilterTest() {

		for (TenantDTO tenantDTO : tenantList) {
			for (BrandDTO brandDTO : tenantDTO.getBrands()) {
				final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				for (int l = 0; l < SORTING.length; l++) {
					final String orderBy = SORTING[l];
					counterfeitEntityForm.setOrderBy(orderBy);					
					List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
							.getSellingAndBuyingEntities(counterfeitEntityForm );
					assertTrue("No record found", result.isEmpty());
					assertTrue("Total " + result.size() + " record found",
							result.size() > 0);
					for (final CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
						assertTrue(StringUtils
								.isNotBlank(counterfeitBuyingSellingEntityDTO
										.getEntityName()));
						assertTrue(counterfeitBuyingSellingEntityDTO
								.getNumberOfSuspectAuthentications()
								.longValue() > 0);
						assertTrue(counterfeitBuyingSellingEntityDTO
								.getRevenueLoss() > 0);
					}
				}
			}
		}
	}
	
	@Test
	public void getEntitiesForEntityPageWithoutFilterPaginatedResultTest() {
		for (TenantDTO tenantDTO : tenantList) {
			for (BrandDTO brandDTO : tenantDTO.getBrands()) {
				final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				for (int l = 0; l < SORTING.length; l++) {
					final String orderBy = SORTING[l];
					counterfeitEntityForm.setOrderBy(orderBy);	
					for (int i = 1; i <= 3; i++) {
						counterfeitEntityForm.setPageNumber(i);
					List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
							.getSellingAndBuyingEntities(counterfeitEntityForm );
					assertTrue("No record found", result.isEmpty());
					assertTrue("Total " + result.size() + " record found",
							result.size() > 0);
						for (final CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
							assertTrue(StringUtils
									.isNotBlank(counterfeitBuyingSellingEntityDTO
											.getEntityName()));
							assertTrue(counterfeitBuyingSellingEntityDTO
									.getNumberOfSuspectAuthentications()
									.longValue() > 0);
							assertTrue(counterfeitBuyingSellingEntityDTO
									.getRevenueLoss() > 0);
						}
					}
				}
			}
		}
	}
	
	@Test
	public void getEntitiesForEntityPageWithFilterTest() {

		for (TenantDTO tenantDTO : tenantList) {
			for (BrandDTO brandDTO : tenantDTO.getBrands()) {
				for (int i = 0; i < REGIONS.length; i++) {
					final long regionId = REGIONS[i];
					for (int j = 0; j < PARTNERS.length; j++) {
						final long partnerId = PARTNERS[j];
						for (int k = 0; k < TIME_PERIOD.length; k++) {
							final long fromTime = TIME_PERIOD[k];							
								for (int m = 0; m < COUNTRY_IDS.length; m++) {
									final long countryId = COUNTRY_IDS[m];
									final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
									counterfeitEntityForm.setTenantId(tenantDTO
											.getId());
									counterfeitEntityForm.setBrandId(brandDTO
											.getId());
									counterfeitEntityForm
											.setNumberOfRecords(NUMBER_OF_RECORDS);
									counterfeitEntityForm.setRegionId(regionId);
									counterfeitEntityForm
											.setCountryId(countryId);									
									counterfeitEntityForm.setFromTime(fromTime);
									counterfeitEntityForm
											.setToTime(TIME_PERIOD[TIME_PERIOD.length - 1]);
									counterfeitEntityForm
											.setEntityTypeId(partnerId);
									for (int l = 0; l < SORTING.length; l++) {
										final String orderBy = SORTING[l];
										counterfeitEntityForm.setOrderBy(orderBy);	
										List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
												.getSellingAndBuyingEntities(counterfeitEntityForm );
										assertTrue("No record found", result.isEmpty());
										assertTrue("Total " + result.size() + " record found",
										result.size() > 0);
										for (final CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
											assertTrue(StringUtils
													.isNotBlank(counterfeitBuyingSellingEntityDTO
															.getEntityName()));
											assertTrue(counterfeitBuyingSellingEntityDTO
													.getNumberOfSuspectAuthentications()
													.longValue() > 0);
											assertTrue(counterfeitBuyingSellingEntityDTO
													.getRevenueLoss() > 0);									
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Test
	public void getEntitiesForEntityPageEmptyResultTest() {
		final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
		counterfeitEntityForm.setTenantId(0);
		counterfeitEntityForm.setBrandId(0);
		counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
		for (int l = 0; l < SORTING.length; l++) {
			final String orderBy = SORTING[l];
			counterfeitEntityForm.setOrderBy(orderBy);	
			List<CounterfeitBuyingSellingEntityDTO> result = counterfeitEntityService
					.getSellingAndBuyingEntities(counterfeitEntityForm );
			assertTrue("No record found", result.isEmpty());
		}
	}
	
	

	public List<TenantDTO> getAllTenant() {
		return tenantBrandService.getAllTenantAndBrand();
	}

}
