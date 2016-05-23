/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   ProductAuthenticationIntegrationTest.java
 ** Version     :   1.0
 ** Description :   Integration test for mobile apps services.
 **
 ** Author      :   Rajesh Parab
 ** Created Date :  Tuesday, 05 May 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.saas.services;

import static com.vpa.core.utils.VPASaaSConstant.GENUINE_TRUTH_TABLE_VALUE;
import static com.vpa.core.utils.VPASaaSConstant.INCOMPLETE_SCAN_TRUTH_TABLE_VALUE;
import static com.vpa.core.utils.VPASaaSConstant.TRUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.vpa.core.enums.UserEnteredKeyType;
import com.vpa.core.exceptions.ResourceNotFoundException;
import com.vpa.core.exceptions.UnprocessableResourceException;
import com.vpa.core.mes.dao.ScanDao;
import com.vpa.core.mes.dao.ScanDetailsDao;
import com.vpa.core.mes.dao.ScanResultDao;
import com.vpa.core.mes.dao.SecurityLabelDao;
import com.vpa.core.mes.dao.SellerDao;
import com.vpa.core.mes.dao.UserDao;
import com.vpa.core.models.Scan;
import com.vpa.core.models.ScanDetail;
import com.vpa.core.models.SecurityLabel;
import com.vpa.saas.dto.ProductScanDTO;
import com.vpa.saas.dto.ScanResultType;
import com.vpa.saas.services.geocoding.GeoLocation;
import com.vpa.saas.services.geocoding.ReverseGeoCoding;
import com.vpa.saas.test.utils.SaaSDTOFacotry;

//@SqlGroup({
//		@Sql(scripts = "classpath:insert-product-authentication-test-data.sql", config = @SqlConfig(transactionMode = ISOLATED, dataSource = "vpaCoreDS", transactionManager = "transactionManager")
//
//		) })
public class ProductAuthenticationIntegrationTest extends AbstractVPASaaSTest {

	@Autowired
	private ProductAuthenticationService productAuthenticationService;

	@Autowired
	private ScanDao scanDao;

	@Autowired
	private ScanDetailsDao scanDetailDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private SecurityLabelDao securityLabelDao;

	@Autowired
	private ScanResultDao scanResultDao;

	@Autowired
	private SellerDao sellerDao;
	
	@Autowired
	private ReverseGeoCoding reverseGeoCoding;

	@Test
	public void activeSecurityLabelSearch() {

		ProductScanDTO scannedProduct = new ProductScanDTO();

		String securitySerialNumber = super.getActiveSecurityLabel();
		SecurityLabel securityLabel = securityLabelDao
				.getActiveSecurityLabel(securitySerialNumber);
		scannedProduct.setSerialNumber(securitySerialNumber);
		ProductScanDTO productScanDTO = productAuthenticationService
				.getGenuineProductInformation(scannedProduct);
		assertEquals(productScanDTO.getSerialNumber(), securitySerialNumber);
		assertEquals(securityLabel.getBrand().getName(), productScanDTO
				.getBrand().getName());
		assertEquals(securityLabel.getProductInstance()
				.getManufacturerProduct().getProduct().getName(),
				productScanDTO.getProduct().getName());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void inactiveSecurityLabelSearchOrLabelNotFound() {

		ProductScanDTO scannedProduct = new ProductScanDTO();
		try {
			String securityLabel = super.getInactiveSecurityLabel();
			if (StringUtils.hasText(securityLabel)) {
				scannedProduct.setSerialNumber(securityLabel);
				productAuthenticationService.getGenuineProductInformation(scannedProduct);

			}
		} finally {
			deleteInactiveSecurityLabel();
		}

	}

	@Test(expected = ResourceNotFoundException.class)
	public void inactiveProductInstanceSearch() {

		ProductScanDTO scannedProduct = new ProductScanDTO();
		try {
			String securityLabel = super.getInactiveProductInstance();
			if (StringUtils.hasText(securityLabel)) {
				scannedProduct.setSerialNumber(securityLabel);
				productAuthenticationService.getGenuineProductInformation(scannedProduct);
			}
		} finally {
			deleteInactiveProductInstance();
		}

	}

	@Test(expected = UnprocessableResourceException.class)
	public void duplicateSecurityLabelNotFound() {
		try {
			intiDuplicateSecurityLabel();
			ProductScanDTO scannedProduct = new ProductScanDTO();

			scannedProduct.setSerialNumber("RAP999999ZEN");
			productAuthenticationService.getGenuineProductInformation(scannedProduct);
		} finally {
			super.deleteInactiveSecurityLabel();
		}

	}

	@Test
	public void saveScanVrifyProduct() {

		String securitySerialNumber = super.getActiveSecurityLabel();

		ProductScanDTO scannedProduct = processIncompletScan(securitySerialNumber);

		Scan expectedScan = scanDao.findOne(scannedProduct.getScanId());

		assertIncompleteScan(scannedProduct, expectedScan);

	}
	
	@Test
	public void checkCountryAndCity() {

		String securitySerialNumber = super.getActiveSecurityLabel();

		ProductScanDTO scannedProduct = processIncompletScan(securitySerialNumber);

		Scan expectedScan = scanDao.findOne(scannedProduct.getScanId());

		assertIncompleteScan(scannedProduct, expectedScan);
		 TestTransaction.end();
		GeoLocation location=	reverseGeoCoding.getLocation(scannedProduct.getLatitude(), scannedProduct.getLongitude());
		assertEquals(location.getCityName(),scannedProduct.getCity());

		assertEquals(location.getIsoCountryCode(),scannedProduct.getCountryCode());
 
	}
	
	private ProductScanDTO processIncompletScan(String securitySerialNumber) {
		ProductScanDTO scannedProduct = SaaSDTOFacotry.createScannedProductDTO(
				securitySerialNumber, getActiveBusinessUserId());

		scannedProduct = productAuthenticationService
				.getGenuineProductInformation(scannedProduct);
		scannedProduct.setScanResultType(ScanResultType.Incomplete.getId());
		productAuthenticationService.saveScanResult(scannedProduct);
		return scannedProduct;
	}

	// this when user check all the return result after scan and he clicks on
	// YES but
	@Test
	public void saveScanGenuineProduct() {

		String securitySerialNumber = super.getActiveSecurityLabel();
		ProductScanDTO scannedProduct = processIncompletScan(securitySerialNumber);

		// User press YES buttonProductScanDTO
		ProductScanDTO genuineScannedProduct = new ProductScanDTO();
		genuineScannedProduct.setScanId(scannedProduct.getScanId());
		genuineScannedProduct.setScanVerificationFlag(GENUINE_TRUTH_TABLE_VALUE);
	    productAuthenticationService
				.updateScannedProduct(genuineScannedProduct);

		Scan expectedScan = scanDao.findOne(scannedProduct.getScanId());

		assertGenuineScan(genuineScannedProduct, expectedScan);

	}

	@Test(expected = ResourceNotFoundException.class)
	public void saveGenuineProductWithInactiveUser() {

		String securitySerialNumber = super.getActiveSecurityLabel();

		ProductScanDTO scannedProduct = SaaSDTOFacotry
				.createGenuineScannedProductDTO(securitySerialNumber,
						getInActiveBusinessUserId());

		scannedProduct = productAuthenticationService
				.getGenuineProductInformation(scannedProduct);
		scannedProduct.setScanResultType(ScanResultType.Genuine.getId());
		productAuthenticationService.saveScanResult(scannedProduct);

		fail("Should have thrown user not exist ");

	}
	 
	@Test
	public void genuineProductFoundInDBSave() {

		String securitySerialNumber = super.getActiveSecurityLabel();
		ProductScanDTO scannedProduct = processIncompletScan(securitySerialNumber);
		assertEquals(scannedProduct.getSerialNumber(), securitySerialNumber);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void genuineProductNotFoundInDBSave() {

		String securityLabel = "VPASL00001_Rajesh_Parab";
		ProductScanDTO scannedProduct = SaaSDTOFacotry.createScannedProductDTO(
				securityLabel, getActiveBusinessUserId());
		scannedProduct = productAuthenticationService
				.getGenuineProductInformation(scannedProduct);
		scannedProduct.setScanResultType(ScanResultType.Incomplete.getId());

		ProductScanDTO scannedProductInDB = productAuthenticationService
				.getGenuineProductInformation(scannedProduct);

		scannedProduct.setBrand(scannedProductInDB.getBrand());
		scannedProduct.setProduct(scannedProductInDB.getProduct());

		productAuthenticationService.saveScanResult(scannedProduct);
		assertEquals(scannedProduct.getSerialNumber(), securityLabel);
	}

	@Test
	public void genuineProductScanDetailsWithOptionalSellerInfo()
			throws JsonGenerationException, JsonMappingException, IOException {

		String securitySerialNumber = super.getActiveSecurityLabel();
		ProductScanDTO scannedProduct = processIncompletScan(securitySerialNumber);

		// User press YES buttonProductScanDTO
		ProductScanDTO genuineScannedProduct = new ProductScanDTO();
		genuineScannedProduct.setScanId(scannedProduct.getScanId());
		genuineScannedProduct.setScanResultType(ScanResultType.Genuine.getId());
		genuineScannedProduct.setScanVerificationFlag(31);
		productAuthenticationService
				.updateScannedProduct(genuineScannedProduct);

		// user send additional seller info when he click yes button
		SaaSDTOFacotry.createGenuineProductWithNewSellerDetails(scannedProduct);
		productAuthenticationService.saveScanDetails(scannedProduct);

		Scan expectedScan = scanDao.findOne(scannedProduct.getScanId());

		assertGenuineScan(genuineScannedProduct, expectedScan);
		Map<UserEnteredKeyType, String> scanDetailMap = scannedProduct
				.getScanDetails();

		List<ScanDetail> expectedScanDetails = scanDetailDao
				.findAllByScanId(expectedScan.getId());
		assertScanDetails(scanDetailMap, expectedScanDetails);

	}

	@Test
	public void counterfietProduct() throws JsonGenerationException,
			JsonMappingException, IOException {
		ProductScanDTO scannedProduct = SaaSDTOFacotry
				.createSuspectedScannedProduct(getBrandId(),
						getActiveBusinessUserId());
		scannedProduct.setScanResultType(ScanResultType.Counterfeit.getId());
		productAuthenticationService.saveScanResult(scannedProduct);
		Scan expectedScan = scanDao.findOne(scannedProduct.getScanId());
		assertCounterfeitScan(scannedProduct, expectedScan);
		Map<UserEnteredKeyType, String> scanDetailMap = scannedProduct
				.getScanDetails();

		List<ScanDetail> expectedScanDetails = scanDetailDao
				.findAllByScanId(expectedScan.getId());
		assertScanDetails(scanDetailMap, expectedScanDetails);
	}

	@Test
	public void counterfietProductScanDetailsWithNewSellerInfo()
			throws JsonGenerationException, JsonMappingException, IOException {
		ProductScanDTO scannedProduct = SaaSDTOFacotry
				.createSuspectedScannedProductWithNewSellerDetails(
						getBrandId(), getActiveBusinessUserId());
		scannedProduct.setScanResultType(ScanResultType.Counterfeit.getId());
		productAuthenticationService.saveScanResult(scannedProduct);
		productAuthenticationService.saveScanDetails(scannedProduct);
		Scan expectedScan = scanDao.findOne(scannedProduct.getScanId());
		assertCounterfeitScan(scannedProduct, expectedScan);
		Map<UserEnteredKeyType, String> scanDetailMap = scannedProduct
				.getScanDetails();
		List<ScanDetail> expectedScanDetails = scanDetailDao
				.findAllByScanId(expectedScan.getId());

		assertScanDetails(scanDetailMap, expectedScanDetails);

	}

	@Test
	public void counterfietProductScanDetailsWithExistingSellerInfo()
			throws JsonGenerationException, JsonMappingException, IOException {
		String sellerName = super.getSellerNameFromDB();
		ProductScanDTO scannedProduct = SaaSDTOFacotry
				.createSuspectedScannedProductWithExistingSellerDetails(
						getBrandId(), getActiveBusinessUserId(), sellerName);
		scannedProduct.setScanResultType(ScanResultType.Counterfeit.getId());
		productAuthenticationService.saveScanResult(scannedProduct);
		productAuthenticationService.saveScanDetails(scannedProduct);
		Scan expectedScan = scanDao.findOne(scannedProduct.getScanId());
		assertCounterfeitScan(scannedProduct, expectedScan);
		Map<UserEnteredKeyType, String> scanDetailMap = scannedProduct
				.getScanDetails();

		List<ScanDetail> expectedScanDetails = scanDetailDao
				.findAllByScanId(expectedScan.getId());
		assertScanDetails(scanDetailMap, expectedScanDetails);

	}
 

	@Test
	public void tamperedProductScanDetailsWithExistingSellerInfo()
			throws JsonGenerationException, JsonMappingException, IOException {
		String sellerName = super.getSellerNameFromDB();
		ProductScanDTO scannedProduct = SaaSDTOFacotry
				.createTamperedProductScanDto(getActiveBusinessUserId(),
						getBrandId(),"DUMMY",
						"1125416",
						"Box",super.getProductId());
		EnumMap<UserEnteredKeyType, String> scanDetails = SaaSDTOFacotry.getScanDetailsForSerialNumberTampered(
				scannedProduct.getBrand().getId(), scannedProduct.getProduct().getName(), "Box" );
		scannedProduct.setScanDetails(scanDetails);
		scannedProduct.getProduct().setIsCompromised(TRUE);
		scannedProduct.getSeller().setName(sellerName);
		scannedProduct.setScanResultType(ScanResultType.Incomplete.getId());
		scannedProduct.setScanVerificationFlag(INCOMPLETE_SCAN_TRUTH_TABLE_VALUE);
		productAuthenticationService.saveScanResult(scannedProduct);
	 	productAuthenticationService.updateScannedProduct(scannedProduct);
	 	scannedProduct.setScanVerificationFlag(24);
		productAuthenticationService.saveScanDetails(scannedProduct);
		Scan expectedScan = scanDao.findOne(scannedProduct.getScanId());
		assertTemperedScan(scannedProduct, expectedScan,ScanResultType.Counterfeit_Upgrade
				.getId(),24);
		Map<UserEnteredKeyType, String> scanDetailMap = scannedProduct
				.getScanDetails();

		List<ScanDetail> expectedScanDetails = scanDetailDao
				.findAllByScanId(expectedScan.getId());
		assertScanDetails(scanDetailMap, expectedScanDetails);

	}

	// TODO this test still not detect if seller entered twiced

}
