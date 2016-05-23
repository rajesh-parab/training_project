package com.vpa.saas.controller;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.vpa.core.enums.Sorting;
import com.vpa.saas.dto.BrandDTO;
import com.vpa.saas.dto.CounterfeitBuyingSellingEntityDTO;
import com.vpa.saas.dto.TenantDTO;
import com.vpa.saas.forms.CounterfeitEntityForm;

public class ConterfeitEnityControllerIntegrationTest {
	
	final static long[] REGIONS = { 0, 3, 4, 5 };
	final static long[] COUNTRY_IDS = { 13, 15, 17, 35, 50, 83, 135 };
	final static long[] PARTNERS = { 0,	1, 6, 3, 2, 4 };
	final static long[] TIME_PERIOD = { 20150727, 20150728, 20150730, 20150821, 20150901 };
	final static long[] LAYERS = { 0, 2, 3, 4 };
	final static String[] SORTING = { "REVENUE", "SUSPECTAUTHENTICATION", "TOTALAUTHENTICATION", "SUSPECT_PRODUCTS", "COUNTRY", "ENTITY_TYPE", "ENTITY_NAME" };

	private static final String HOST_ENDPOINT = "http://localhost:8080/vpa-saas-app";
	private static final String AUTHENTICATION_COUNTERFEIT_BUYING_ENTITIES_URL = HOST_ENDPOINT
			+ "/entity/counterfeit/buying/entities";

	private static final String AUTHENTICATION_COUNTERFEIT_SELLING_ENTITIES_URL = HOST_ENDPOINT
			+ "/entity/counterfeit/selling/entities";

	private static final String AUTHENTICATION_COUNTERFEIT_BUYING_ENTITIES_AUTHENTICATION_PAGE_URL = HOST_ENDPOINT
			+ "/entity/counterfeit/buying/authentication";

	private static final String AUTHENTICATION_COUNTERFEIT_SELLING_ENTITIES_AUTHENTICATION_PAGE_URL = HOST_ENDPOINT
			+ "/entity/counterfeit/selling/authentication";
	
	private static final String AUTHENTICATION_COUNTERFEIT_BUYING_ENTITIES_REVENUE_PAGE_URL = HOST_ENDPOINT
			+ "/entity/counterfeit/buying/revenue";

	private static final String AUTHENTICATION_COUNTERFEIT_SELLING_ENTITIES_REVENUE_PAGE_URL = HOST_ENDPOINT
			+ "/entity/counterfeit/selling/revenue";

	private static final String TENANT_URL = HOST_ENDPOINT + "/tenant/all";
	
	private static final String BUYING_AND_SELLING_ENTITIES_ENTITY_PAGE_URL = HOST_ENDPOINT
			+ "/entity/all";
	
	private static final String BUYING_ENTITIES_ENTITY_PAGE_URL = HOST_ENDPOINT
			+ "/entity/buying";
	
	private static final String SELLING_ENTITIES_ENTITY_PAGE_URL = HOST_ENDPOINT
			+ "/entity/selling";

	private static RestTemplate restTemplate;

	private static List<TenantDTO> tenantList;

	private final static int NUMBER_OF_RECORDS = 3;

	@BeforeClass
	public static void loadAllTenantsAndBrands() {
		restTemplate = new RestTemplate();
		tenantList = getTenantsAndBrands();
	}

	@Test
	public void counterfeitBuyingEntitiesDashboardTest() {
		StringBuilder request = new StringBuilder(
				AUTHENTICATION_COUNTERFEIT_BUYING_ENTITIES_URL);
		for (TenantDTO tenantDTO : tenantList) {
			List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				request.append("/").append(tenantDTO.getId()).append("/")
						.append(brandDTO.getId()).append("/")
						.append(NUMBER_OF_RECORDS);
				List<CounterfeitBuyingSellingEntityDTO> result = Arrays
						.asList(restTemplate.getForObject(request.toString(),
								CounterfeitBuyingSellingEntityDTO[].class));
				assertTrue(null != result);
				assertTrue(result.size() <= 3);
			}
		}

	}

	@Test
	public void counterfeitSellingEntitiesDashboardTest() {
		StringBuilder request = new StringBuilder(
				AUTHENTICATION_COUNTERFEIT_SELLING_ENTITIES_URL);
		for (TenantDTO tenantDTO : tenantList) {
			List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				request.append("/").append(tenantDTO.getId()).append("/")
						.append(brandDTO.getId()).append("/")
						.append(NUMBER_OF_RECORDS);
				List<CounterfeitBuyingSellingEntityDTO> result = Arrays
						.asList(restTemplate.getForObject(request.toString(),
								CounterfeitBuyingSellingEntityDTO[].class));
				assertTrue(null != result);
				assertTrue(result.size() <= 3);
			}
		}
	}

	@Test
	public void counterfeitSellingEntitiesAuthenticationPageTest() {
		for (TenantDTO tenantDTO : tenantList) {
			List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				counterfeitEntityForm.setOrderBy(Sorting.SUSPECTAUTHENTICATION.toString());
				List<CounterfeitBuyingSellingEntityDTO> result = Arrays
						.asList(restTemplate
								.postForObject(
										AUTHENTICATION_COUNTERFEIT_SELLING_ENTITIES_AUTHENTICATION_PAGE_URL,
										counterfeitEntityForm,
										CounterfeitBuyingSellingEntityDTO[].class));
				assertTrue(null != result);
				assertTrue(result.size() <= 3);
				for (CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
					assertTrue(StringUtils
							.isNotBlank(counterfeitBuyingSellingEntityDTO
									.getEntityName()));
					assertTrue(counterfeitBuyingSellingEntityDTO
							.getNumberOfSuspectAuthentications().longValue() > 0);
					assertTrue(counterfeitBuyingSellingEntityDTO
							.getRevenueLoss() > 0);
				}
			}
		}
	}

	@Test
	public void counterfeitSellingEntitiesAuthenticationPageWithFiltersTest() {
		for (TenantDTO tenantDTO : tenantList) {
			final List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				
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
									final List<CounterfeitBuyingSellingEntityDTO> result = Arrays
											.asList(restTemplate
													.postForObject(
															AUTHENTICATION_COUNTERFEIT_SELLING_ENTITIES_AUTHENTICATION_PAGE_URL,
															counterfeitEntityForm,
															CounterfeitBuyingSellingEntityDTO[].class));
									assertTrue(null != result);
									assertTrue(result.size() <= 3);
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
	public void counterfeitSellingEntitiesAuthenticationPagePaginatedResultTest() {
		for (TenantDTO tenantDTO : tenantList) {
			final List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				counterfeitEntityForm.setOrderBy(Sorting.SUSPECTAUTHENTICATION.toString());
				for (int i = 1; i <= 3; i++) {
					counterfeitEntityForm.setPageNumber(i);
					final List<CounterfeitBuyingSellingEntityDTO> result = Arrays
							.asList(restTemplate
									.postForObject(
											AUTHENTICATION_COUNTERFEIT_SELLING_ENTITIES_AUTHENTICATION_PAGE_URL,
											counterfeitEntityForm,
											CounterfeitBuyingSellingEntityDTO[].class));
					for (CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
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
	public void counterfeitBuyingEntitiesAuthenticationPageTest() {
		for (TenantDTO tenantDTO : tenantList) {
			List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				counterfeitEntityForm.setOrderBy(Sorting.SUSPECTAUTHENTICATION.toString());
				List<CounterfeitBuyingSellingEntityDTO> result = Arrays
						.asList(restTemplate
								.postForObject(
										AUTHENTICATION_COUNTERFEIT_BUYING_ENTITIES_AUTHENTICATION_PAGE_URL,
										counterfeitEntityForm,
										CounterfeitBuyingSellingEntityDTO[].class));
				assertTrue(null != result);
				assertTrue(result.size() <= 3);
				for (CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
					assertTrue(StringUtils
							.isNotBlank(counterfeitBuyingSellingEntityDTO
									.getEntityName()));
					assertTrue(counterfeitBuyingSellingEntityDTO
							.getNumberOfSuspectAuthentications().longValue() > 0);
					assertTrue(counterfeitBuyingSellingEntityDTO
							.getRevenueLoss() > 0);
				}
			}
		}
	}

	@Test
	public void counterfeitBuyingEntitiesAuthenticationPageWithFiltersTest() {
		for (TenantDTO tenantDTO : tenantList) {
			final List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
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
									final List<CounterfeitBuyingSellingEntityDTO> result = Arrays
											.asList(restTemplate
													.postForObject(
															AUTHENTICATION_COUNTERFEIT_BUYING_ENTITIES_AUTHENTICATION_PAGE_URL,
															counterfeitEntityForm,
															CounterfeitBuyingSellingEntityDTO[].class));
									assertTrue(null != result);
									assertTrue(result.size() <= 3);
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
	public void counterfeitBuyingEntitiesAuthenticationPagePaginatedResultTest() {
		for (TenantDTO tenantDTO : tenantList) {
			final List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				counterfeitEntityForm.setOrderBy(Sorting.SUSPECTAUTHENTICATION.toString());
				for (int i = 1; i <= 3; i++) {
					counterfeitEntityForm.setPageNumber(i);
					final List<CounterfeitBuyingSellingEntityDTO> result = Arrays
							.asList(restTemplate
									.postForObject(
											AUTHENTICATION_COUNTERFEIT_BUYING_ENTITIES_AUTHENTICATION_PAGE_URL,
											counterfeitEntityForm,
											CounterfeitBuyingSellingEntityDTO[].class));
					for (CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
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
	public void counterfeitSellingEntitiesRevenuePageTest() {
		for (TenantDTO tenantDTO : tenantList) {
			List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				counterfeitEntityForm.setOrderBy(Sorting.REVENUE.toString());
				List<CounterfeitBuyingSellingEntityDTO> result = Arrays
						.asList(restTemplate
								.postForObject(
										AUTHENTICATION_COUNTERFEIT_SELLING_ENTITIES_REVENUE_PAGE_URL,
										counterfeitEntityForm,
										CounterfeitBuyingSellingEntityDTO[].class));
				assertTrue(null != result);
				assertTrue(result.size() <= 3);
				for (CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
					assertTrue(StringUtils
							.isNotBlank(counterfeitBuyingSellingEntityDTO
									.getEntityName()));
					assertTrue(counterfeitBuyingSellingEntityDTO
							.getNumberOfSuspectAuthentications().longValue() > 0);
					assertTrue(counterfeitBuyingSellingEntityDTO
							.getRevenueLoss() > 0);
				}
			}
		}
	}

	@Test
	public void counterfeitSellingEntitiesRevenuePageWithFiltersTest() {
		for (TenantDTO tenantDTO : tenantList) {
			final List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				
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
									final List<CounterfeitBuyingSellingEntityDTO> result = Arrays
											.asList(restTemplate
													.postForObject(
															AUTHENTICATION_COUNTERFEIT_SELLING_ENTITIES_REVENUE_PAGE_URL,
															counterfeitEntityForm,
															CounterfeitBuyingSellingEntityDTO[].class));
									assertTrue(null != result);
									assertTrue(result.size() <= 3);
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
	public void counterfeitSellingEntitiesRevenuePagePaginatedResultTest() {
		for (TenantDTO tenantDTO : tenantList) {
			final List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				counterfeitEntityForm.setOrderBy(Sorting.REVENUE.toString());
				for (int i = 1; i <= 3; i++) {
					counterfeitEntityForm.setPageNumber(i);
					final List<CounterfeitBuyingSellingEntityDTO> result = Arrays
							.asList(restTemplate
									.postForObject(
											AUTHENTICATION_COUNTERFEIT_SELLING_ENTITIES_REVENUE_PAGE_URL,
											counterfeitEntityForm,
											CounterfeitBuyingSellingEntityDTO[].class));
					for (CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
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
	public void counterfeitBuyingEntitiesRevenuePageTest() {
		for (TenantDTO tenantDTO : tenantList) {
			List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				counterfeitEntityForm.setOrderBy(Sorting.REVENUE.toString());
				List<CounterfeitBuyingSellingEntityDTO> result = Arrays
						.asList(restTemplate
								.postForObject(
										AUTHENTICATION_COUNTERFEIT_BUYING_ENTITIES_REVENUE_PAGE_URL,
										counterfeitEntityForm,
										CounterfeitBuyingSellingEntityDTO[].class));
				assertTrue(null != result);
				assertTrue(result.size() <= 3);
				for (CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
					assertTrue(StringUtils
							.isNotBlank(counterfeitBuyingSellingEntityDTO
									.getEntityName()));
					assertTrue(counterfeitBuyingSellingEntityDTO
							.getNumberOfSuspectAuthentications().longValue() > 0);
					assertTrue(counterfeitBuyingSellingEntityDTO
							.getRevenueLoss() > 0);
				}
			}
		}
	}

	@Test
	public void counterfeitBuyingEntitiesRevenueWithFiltersTest() {
		for (TenantDTO tenantDTO : tenantList) {
			final List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
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
									final List<CounterfeitBuyingSellingEntityDTO> result = Arrays
											.asList(restTemplate
													.postForObject(
															AUTHENTICATION_COUNTERFEIT_BUYING_ENTITIES_REVENUE_PAGE_URL,
															counterfeitEntityForm,
															CounterfeitBuyingSellingEntityDTO[].class));
									assertTrue(null != result);
									assertTrue(result.size() <= 3);
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
	public void counterfeitBuyingEntitiesRevenuePagePaginatedResultTest() {
		for (TenantDTO tenantDTO : tenantList) {
			final List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				counterfeitEntityForm.setOrderBy(Sorting.REVENUE.toString());
				for (int i = 1; i <= 3; i++) {
					counterfeitEntityForm.setPageNumber(i);
					final List<CounterfeitBuyingSellingEntityDTO> result = Arrays
							.asList(restTemplate
									.postForObject(
											AUTHENTICATION_COUNTERFEIT_BUYING_ENTITIES_REVENUE_PAGE_URL,
											counterfeitEntityForm,
											CounterfeitBuyingSellingEntityDTO[].class));
					for (CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
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
	public void getEntitiesWithoutFiltersForEntityPageTest() {
		for (TenantDTO tenantDTO : tenantList) {
			List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				for (int l = 0; l < SORTING.length; l++) {
					final String orderBy = SORTING[l];
					counterfeitEntityForm.setOrderBy(orderBy);
					List<CounterfeitBuyingSellingEntityDTO> result = Arrays
							.asList(restTemplate
									.postForObject(
											BUYING_AND_SELLING_ENTITIES_ENTITY_PAGE_URL,
											counterfeitEntityForm,
											CounterfeitBuyingSellingEntityDTO[].class));
					assertTrue(null != result);
					assertTrue(result.size() <= 3);
					for (CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
						assertTrue(StringUtils
								.isNotBlank(counterfeitBuyingSellingEntityDTO
										.getEntityName()));
						assertTrue(counterfeitBuyingSellingEntityDTO
								.getNumberOfSuspectAuthentications().longValue() > 0);
						assertTrue(counterfeitBuyingSellingEntityDTO
								.getRevenueLoss() > 0);
					}
				}
			}
		}
	}

	@Test
	public void getEntitiesWithFiltersForEntityPageTest() {
		for (TenantDTO tenantDTO : tenantList) {
			final List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				for (int i = 0; i < REGIONS.length; i++) {
					final long regionId = REGIONS[i];
					for (int j = 0; j < PARTNERS.length; j++) {
						final long partnerId = PARTNERS[j];
						for (int k = 0; k < TIME_PERIOD.length; k++) {
							final long fromTime = TIME_PERIOD[k];
							for (int l = 0; l < SORTING.length; l++) {
								final String orderBy = SORTING[l];
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
									counterfeitEntityForm.setOrderBy(orderBy);
									counterfeitEntityForm.setFromTime(fromTime);
									counterfeitEntityForm
											.setToTime(TIME_PERIOD[TIME_PERIOD.length - 1]);
									counterfeitEntityForm
											.setEntityTypeId(partnerId);
									counterfeitEntityForm.setOrderBy(Sorting.REVENUE.toString());
									final List<CounterfeitBuyingSellingEntityDTO> result = Arrays
											.asList(restTemplate
													.postForObject(
															BUYING_AND_SELLING_ENTITIES_ENTITY_PAGE_URL,
															counterfeitEntityForm,
															CounterfeitBuyingSellingEntityDTO[].class));
									assertTrue(null != result);
									assertTrue(result.size() <= 3);
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
	public void getEntitiesPaginatedResultForEntityPageTest() {
		for (TenantDTO tenantDTO : tenantList) {
			final List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				for (int l = 0; l < SORTING.length; l++) {
					final String orderBy = SORTING[l];
					counterfeitEntityForm.setOrderBy(orderBy);
					for (int i = 1; i <= 3; i++) {
						counterfeitEntityForm.setPageNumber(i);
						final List<CounterfeitBuyingSellingEntityDTO> result = Arrays
								.asList(restTemplate
										.postForObject(
												BUYING_AND_SELLING_ENTITIES_ENTITY_PAGE_URL,
												counterfeitEntityForm,
												CounterfeitBuyingSellingEntityDTO[].class));
						for (CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
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
	public void getBuyingEntitiesWithoutFiltersForEntityPageTest() {
		for (TenantDTO tenantDTO : tenantList) {
			List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				for (int l = 0; l < SORTING.length; l++) {
					final String orderBy = SORTING[l];
					counterfeitEntityForm.setOrderBy(orderBy);
					List<CounterfeitBuyingSellingEntityDTO> result = Arrays
							.asList(restTemplate
									.postForObject(
											BUYING_ENTITIES_ENTITY_PAGE_URL,
											counterfeitEntityForm,
											CounterfeitBuyingSellingEntityDTO[].class));
					assertTrue(null != result);
					assertTrue(result.size() <= 3);
					for (CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
						assertTrue(StringUtils
								.isNotBlank(counterfeitBuyingSellingEntityDTO
										.getEntityName()));
						assertTrue(counterfeitBuyingSellingEntityDTO
								.getNumberOfSuspectAuthentications().longValue() > 0);
						assertTrue(counterfeitBuyingSellingEntityDTO
								.getRevenueLoss() > 0);
					}
				}
			}
		}
	}

	@Test
	public void getBuyingEntitiesWithFiltersForEntityPageTest() {
		for (TenantDTO tenantDTO : tenantList) {
			final List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				for (int i = 0; i < REGIONS.length; i++) {
					final long regionId = REGIONS[i];
					for (int j = 0; j < PARTNERS.length; j++) {
						final long partnerId = PARTNERS[j];
						for (int k = 0; k < TIME_PERIOD.length; k++) {
							final long fromTime = TIME_PERIOD[k];
							for (int l = 0; l < SORTING.length; l++) {
								final String orderBy = SORTING[l];
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
									counterfeitEntityForm.setOrderBy(orderBy);
									counterfeitEntityForm.setFromTime(fromTime);
									counterfeitEntityForm
											.setToTime(TIME_PERIOD[TIME_PERIOD.length - 1]);
									counterfeitEntityForm
											.setEntityTypeId(partnerId);
									counterfeitEntityForm.setOrderBy(Sorting.REVENUE.toString());
									final List<CounterfeitBuyingSellingEntityDTO> result = Arrays
											.asList(restTemplate
													.postForObject(
															BUYING_ENTITIES_ENTITY_PAGE_URL,
															counterfeitEntityForm,
															CounterfeitBuyingSellingEntityDTO[].class));
									assertTrue(null != result);
									assertTrue(result.size() <= 3);
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
	public void getBuyingEntitiesPaginatedResultForEntityPageTest() {
		for (TenantDTO tenantDTO : tenantList) {
			final List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				for (int l = 0; l < SORTING.length; l++) {
					final String orderBy = SORTING[l];
					counterfeitEntityForm.setOrderBy(orderBy);
					for (int i = 1; i <= 3; i++) {
						counterfeitEntityForm.setPageNumber(i);
						final List<CounterfeitBuyingSellingEntityDTO> result = Arrays
								.asList(restTemplate
										.postForObject(
												BUYING_ENTITIES_ENTITY_PAGE_URL,
												counterfeitEntityForm,
												CounterfeitBuyingSellingEntityDTO[].class));
						for (CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
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
	public void getSellingEntitiesWithoutFiltersForEntityPageTest() {
		for (TenantDTO tenantDTO : tenantList) {
			List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				for (int l = 0; l < SORTING.length; l++) {
					final String orderBy = SORTING[l];
					counterfeitEntityForm.setOrderBy(orderBy);
					List<CounterfeitBuyingSellingEntityDTO> result = Arrays
							.asList(restTemplate
									.postForObject(
											SELLING_ENTITIES_ENTITY_PAGE_URL,
											counterfeitEntityForm,
											CounterfeitBuyingSellingEntityDTO[].class));
					assertTrue(null != result);
					assertTrue(result.size() <= 3);
					for (CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
						assertTrue(StringUtils
								.isNotBlank(counterfeitBuyingSellingEntityDTO
										.getEntityName()));
						assertTrue(counterfeitBuyingSellingEntityDTO
								.getNumberOfSuspectAuthentications().longValue() > 0);
						assertTrue(counterfeitBuyingSellingEntityDTO
								.getRevenueLoss() > 0);
					}
				}
			}
		}
	}

	@Test
	public void getSellingEntitiesWithFiltersForEntityPageTest() {
		for (TenantDTO tenantDTO : tenantList) {
			final List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				for (int i = 0; i < REGIONS.length; i++) {
					final long regionId = REGIONS[i];
					for (int j = 0; j < PARTNERS.length; j++) {
						final long partnerId = PARTNERS[j];
						for (int k = 0; k < TIME_PERIOD.length; k++) {
							final long fromTime = TIME_PERIOD[k];
							for (int l = 0; l < SORTING.length; l++) {
								final String orderBy = SORTING[l];
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
									counterfeitEntityForm.setOrderBy(orderBy);
									counterfeitEntityForm.setFromTime(fromTime);
									counterfeitEntityForm
											.setToTime(TIME_PERIOD[TIME_PERIOD.length - 1]);
									counterfeitEntityForm
											.setEntityTypeId(partnerId);
									counterfeitEntityForm.setOrderBy(Sorting.REVENUE.toString());
									final List<CounterfeitBuyingSellingEntityDTO> result = Arrays
											.asList(restTemplate
													.postForObject(
															SELLING_ENTITIES_ENTITY_PAGE_URL,
															counterfeitEntityForm,
															CounterfeitBuyingSellingEntityDTO[].class));
									assertTrue(null != result);
									assertTrue(result.size() <= 3);
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
	public void getSellingEntitiesPaginatedResultForEntityPageTest() {
		for (TenantDTO tenantDTO : tenantList) {
			final List<BrandDTO> brandDTOs = tenantDTO.getBrands();
			for (BrandDTO brandDTO : brandDTOs) {
				final CounterfeitEntityForm counterfeitEntityForm = new CounterfeitEntityForm();
				counterfeitEntityForm.setTenantId(tenantDTO.getId());
				counterfeitEntityForm.setBrandId(brandDTO.getId());
				counterfeitEntityForm.setNumberOfRecords(NUMBER_OF_RECORDS);
				for (int l = 0; l < SORTING.length; l++) {
					final String orderBy = SORTING[l];
					counterfeitEntityForm.setOrderBy(orderBy);
					for (int i = 1; i <= 3; i++) {
						counterfeitEntityForm.setPageNumber(i);
						final List<CounterfeitBuyingSellingEntityDTO> result = Arrays
								.asList(restTemplate
										.postForObject(
												SELLING_ENTITIES_ENTITY_PAGE_URL,
												counterfeitEntityForm,
												CounterfeitBuyingSellingEntityDTO[].class));
						for (CounterfeitBuyingSellingEntityDTO counterfeitBuyingSellingEntityDTO : result) {
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
	

	public static List<TenantDTO> getTenantsAndBrands() {
		return Arrays.asList(restTemplate.getForObject(TENANT_URL,
				TenantDTO[].class));
	}
}
