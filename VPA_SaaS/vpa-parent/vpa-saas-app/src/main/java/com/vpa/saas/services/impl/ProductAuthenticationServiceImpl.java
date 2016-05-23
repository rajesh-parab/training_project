/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   ProductAuthenticationServiceImpl.java
 ** Version     :   1.0
 ** Description :   This implementation class for Mobile Apps  .
 **
 ** Author      :   Rajesh Parab
 ** Created Date :  Tuesday, 19 May 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  Friday, 16 Oct 2015 DATE TIME should taken from DB not table. Null timing handled in respective methods In 
 *                        VPASaaSUtil
 *
 **************************************************************************************************************/
package com.vpa.saas.services.impl;

import static com.vpa.core.utils.VPASaaSConstant.FALSE;
import static com.vpa.core.utils.VPASaaSConstant.GENUINE_TRUTH_TABLE_VALUE;
import static com.vpa.core.utils.VPASaaSConstant.TRUE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.vpa.core.enums.ProductLevel;
import com.vpa.core.enums.UserEnteredKeyType;
import com.vpa.core.exceptions.ResourceNotFoundException;
import com.vpa.core.exceptions.UnprocessableResourceException;
import com.vpa.core.exceptions.VPASaaSSystemException;
import com.vpa.core.mes.dao.BrandDao;
import com.vpa.core.mes.dao.ProductDao;
import com.vpa.core.mes.dao.ProductInstanceDao;
import com.vpa.core.mes.dao.ScanDao;
import com.vpa.core.mes.dao.ScanDetailsDao;
import com.vpa.core.mes.dao.ScanResultDao;
import com.vpa.core.mes.dao.SecurityLabelDao;
import com.vpa.core.mes.dao.UserDao;
import com.vpa.core.mes.dao.UserEnteredKeyDao;
import com.vpa.core.models.Brand;
import com.vpa.core.models.City;
import com.vpa.core.models.CountryMaster;
import com.vpa.core.models.Level;
import com.vpa.core.models.Product;
import com.vpa.core.models.ProductInstance;
import com.vpa.core.models.Scan;
import com.vpa.core.models.ScanDetail;
import com.vpa.core.models.ScanResult;
import com.vpa.core.models.SecurityLabel;
import com.vpa.core.models.User;
import com.vpa.core.models.UserEnteredKey;
import com.vpa.core.utils.VPASaaSConstant;
import com.vpa.core.utils.VPASaaSUtil;
import com.vpa.saas.controllers.ProductAuthenticationController;
import com.vpa.saas.dto.ProductScanDTO;
import com.vpa.saas.dto.ScanResultType;
import com.vpa.saas.services.CityService;
import com.vpa.saas.services.CountryMasterService;
import com.vpa.saas.services.ProductAuthenticationService;
import com.vpa.saas.services.SellerService;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
public class ProductAuthenticationServiceImpl implements
		ProductAuthenticationService {

	@Autowired
	private ScanDao scanDao;

	@Autowired
	private ScanResultDao scanResultDao;

	@Autowired
	private ScanDetailsDao scanDetailsDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ProductDao productDao;
 
	@Autowired
	CountryMasterService countryMasterService;

	@Autowired
	CityService cityService;
	
	@Autowired
	SellerService sellerService;
	 
	@Autowired
	private SecurityLabelDao securityLabelDao;

	@Autowired
	private BrandDao brandDao;

	@Autowired
	private UserEnteredKeyDao userEnteredKeyDao;

	@Autowired
	private ProductInstanceDao productInstanceDao;

	@Autowired
	VPASaaSUtil vpaSaaSUtil;

	private static final Logger LOG = Logger
			.getLogger(ProductAuthenticationController.class);

	// WEB-SOCKET For live Authentication

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	public ProductAuthenticationServiceImpl() {

	}

	public void setSecurityLabelDao(SecurityLabelDao securityLabelDao) {
		this.securityLabelDao = securityLabelDao;
	}

	/**
	 * @see ProductAuthenticationService#getGenuineProductInformation(ProductScanDTO)
	 */
	@Override
	public ProductScanDTO getGenuineProductInformation(
			ProductScanDTO scannedProduct) {

		try {
			SecurityLabel securityLabel = securityLabelDao
					.getActiveSecurityLabel(scannedProduct.getSerialNumber());

			if (securityLabel == null) {

				throw new ResourceNotFoundException(
						"security lable does not exist in DB");
			}

			BeanUtils.copyProperties(securityLabel, scannedProduct,
					new String[] { "createdDate", "updatedDate" });
			BeanUtils.copyProperties(securityLabel.getBrand(),
					scannedProduct.getBrand());
			fetchProductInfo(scannedProduct, securityLabel);

		} catch (IncorrectResultSizeDataAccessException ex) {
			LOG.error(
					" Problem with MES data while verifying genuine product scan label -- might have duplicate entries",
					ex);
			throw new UnprocessableResourceException(
					" problem with MES data while verifying genuine product scan label-- might have duplicate entries");
		} catch (JpaSystemException ex) {

			throw new VPASaaSSystemException(ex.getMessage(), ex);
		}

		return scannedProduct;

	}

	/**
	 * Fetch the product information of scanned product.
	 * 
	 * @param scannedProduct
	 * @param securityLabel
	 */
	public void fetchProductInfo(ProductScanDTO scannedProduct,
			SecurityLabel securityLabel) {

		ProductInstance productInstance = null;

		if (securityLabel != null) {
			productInstance = securityLabel.getProductInstance();

		
		} else {

			productInstance = productInstanceDao
					.findActiveProductInstanceByProductSerialNumber(scannedProduct
							.getProductInstance().getProductSerialNumber());
		}

		// **********
		if (productInstance == null || productInstance.getEnable() != 1) {
			//TODO change error message 
			throw new UnprocessableResourceException(
					"Product serial number entered is not in MES data or There is no product instance associate with this security lable check MES data");
		}
		
		Product product = productInstance.getManufacturerProduct().getProduct();
		String originalProductName = null;

		Level level = product.getLevel();

		if (ProductLevel.PCBA.isPCBA(level.getId())) {
			ProductInstance parent = productInstanceDao.findOne(Long
					.valueOf(productInstance.getParentId()));
			if (parent == null) {
				throw new UnprocessableResourceException(
						"There is no chasis for the PCBA in MES data");
			}
			originalProductName = product.getName();
			product = parent.getManufacturerProduct().getProduct();
		} else if (level.isBox()) {
			ProductInstance child = productInstanceDao.getChild(productInstance
					.getId());
			if (child == null) {
				throw new UnprocessableResourceException(
						"There is no chasis for the box in MES data");
			}

			product = child.getManufacturerProduct().getProduct();
		}

		// **********

		BeanUtils.copyProperties(productInstance,
				scannedProduct.getProductInstance());

		BeanUtils.copyProperties(product, scannedProduct.getProduct());
		scannedProduct.getProduct().setLevelId(product.getLevel().getId());
		scannedProduct.setUserEnteredProductId(product.getName());
		if (StringUtils.hasText(originalProductName)) {
			scannedProduct.getProduct().setName(originalProductName);
		}
	}

	/**
	 * @see ProductAuthenticationService#updateScannedProduct(ProductScanDTO)
	 */
	@Override
	public void updateScannedProduct(ProductScanDTO updatedScannedProduct) {

		Scan savedScanedProduct = scanDao.findOne(updatedScannedProduct
				.getScanId());

		if (savedScanedProduct == null) {
			throw new ResourceNotFoundException(
					"This product has not been scanned please scanned the product");
		}

		if (updatedScannedProduct.getScanDetails().isEmpty()) {
			updatedScannedProduct.setScanResultType(ScanResultType.Genuine
					.getId());
			savedScanedProduct
					.setScanVerificationFlag(GENUINE_TRUTH_TABLE_VALUE);
		} else {
			BeanUtils.copyProperties(savedScanedProduct, updatedScannedProduct);
			BeanUtils.copyProperties(savedScanedProduct.getBrand(),
					updatedScannedProduct.getBrand());
			Product product = savedScanedProduct.getProduct();
			BeanUtils.copyProperties(product,
					updatedScannedProduct.getProduct());
			updatedScannedProduct.getProduct().setProductIdLabelLocation(
					product.getLevel().getName());
			updatedScannedProduct.setUserId(savedScanedProduct.getUser()
					.getId());
			savedScanedProduct.setScanVerificationFlag(updatedScannedProduct
					.calculateVerificationFlag());

			updateCompromiseProductDate(updatedScannedProduct,
					savedScanedProduct, product);
		}
		ScanResult scanResult = scanResultDao.findOne(updatedScannedProduct
				.getScanResultType());
		savedScanedProduct.setScanResult(scanResult);

		scanDao.saveAndFlush(savedScanedProduct);
		afterScanExecuted(savedScanedProduct);

	}

	/**
	 * @see ProductAuthenticationService#saveScanResult(ProductScanDTO)
	 */
	@Override
	public void saveScanResult(ProductScanDTO scannedProduct) {

		Scan newScannedProduct = new Scan();
		BeanUtils.copyProperties(scannedProduct, newScannedProduct);

		CountryMaster countryMaster = countryMasterService
				.addNewCountryInDatabase(scannedProduct.getCountryName(),
						scannedProduct.getCountryCode());
		newScannedProduct.setCountryMaster(countryMaster);

		City city = cityService.addNewCityInDatabase(countryMaster,
				scannedProduct.getCity());

		newScannedProduct.setCity(city);

		Product product = productDao.findEnabledGenuineProduct(scannedProduct
				.getProduct().getId());
		updateCompromiseProductDate(scannedProduct, newScannedProduct, product);

		newScannedProduct.setBrand(new Brand());

		BeanUtils.copyProperties(scannedProduct.getBrand(),
				newScannedProduct.getBrand());

		saveScannedProduct(scannedProduct, newScannedProduct);

	}

	/**
	 * Update the compromised product update date. Date at which any product
	 * found as compromised.
	 * 
	 * @param scannedProduct
	 * @param newScannedProduct
	 * @param product
	 */
	private void updateCompromiseProductDate(ProductScanDTO scannedProduct,
			Scan newScannedProduct, Product product) {
		Byte compromisedProduct = scannedProduct.getProduct()
				.getIsCompromised();

		if (product != null) {
			if (TRUE == compromisedProduct
					&& product.getIsCompromised() == FALSE) {
				// this will automatically update product table
				product.setIsCompromised(compromisedProduct);
				product.setUpdatedDate(scannedProduct.getUpdatedDate());

			}

			newScannedProduct.setProduct(product);
		}
	}

	/**
	 * Save the scanned product in scan table.
	 * 
	 * @param scannedProduct
	 * @param newScannedProduct
	 */
	private void saveScannedProduct(ProductScanDTO scannedProduct,
			Scan newScannedProduct) {
		ScanResult scanResult = scanResultDao.getOne(scannedProduct
				.getScanResultType().intValue());
		newScannedProduct.setScanResult(scanResult);
		Long usrId = scannedProduct.getUserId();
		User user = userDao.findAtiveUser(usrId);
		if (null == user) {
			throw new ResourceNotFoundException("User is not registered "
					+ usrId);
		}
		newScannedProduct.setUser(user);
		newScannedProduct.setEnable(VPASaaSConstant.TRUE);
		newScannedProduct.setCreatedDate(vpaSaaSUtil
				.convertStringToTimeStamp(scanDao.getCurrentTimeStamp()));
		newScannedProduct = scanDao.saveAndFlush(newScannedProduct);
		scannedProduct.setScanId(newScannedProduct.getId());

		afterScanExecuted(newScannedProduct);

	}

	/**
	 * @see ProductAuthenticationService#saveScanDetails(ProductScanDTO)
	 */
	@Override
	public void saveScanDetails(ProductScanDTO scannedProduct) {

		Map<UserEnteredKeyType, String> scanDetailMap = scannedProduct
				.getScanDetails();
		// if null ignore silently
		if (null == scanDetailMap) {
			LOG.info("#### scan details are null");
			return;
		}

		// *************************
		// following three methods are temporary fix to prevent dashboard crash
		// Once mobile UI ready to pass id's then following methods are not
		// required
		CountryMaster country = saveSellerCountryDetails(scanDetailMap);
		City city = saveSellerCityDetails(scanDetailMap, country);
		saveSellerDetails(scannedProduct, scanDetailMap, country, city);
		// *********************

		List<ScanDetail> scanDetails = new ArrayList<>();

		for (Entry<UserEnteredKeyType, String> entry : scanDetailMap.entrySet()) {

			ScanDetail scanDetail = new ScanDetail();

			UserEnteredKey userEnteredKey = userEnteredKeyDao.findOne(entry
					.getKey().getId());

			Scan scan = new Scan(scannedProduct.getScanId());
			scanDetail.setScan(scan);
			scanDetail.setCreatedDate(scannedProduct.getCreatedDate());
			scanDetail.setUserenteredkey(userEnteredKey);
			scanDetail.setValue(entry.getValue());
			scanDetail.setEnable(VPASaaSConstant.TRUE);
			scanDetails.add(scanDetail);

		}

		scanDetailsDao.save(scanDetails);
		scanDetailsDao.flush();
	}

	/**
	 * save sellers in db if seller is not found in seller master table.
	 * 
	 * @param scannedProduct
	 * @param scanDetailMap
	 */
	private void saveSellerDetails(ProductScanDTO scannedProduct,
			Map<UserEnteredKeyType, String> scanDetailMap,
			CountryMaster country, City city) {
		Long sellerId = 0L;
		// SELLER_ID is seller name when come from mobile client while storing
		// in db becomes seller id primary key
		String sellerName = scanDetailMap.get(UserEnteredKeyType.SELLER_ID);
		sellerService.addNewSellerInDB(scannedProduct.getSeller(),sellerName,country,city);
		// since mobile is giving seller name and not seller id. Need to put
		// back seller id TOBE Decided on mobile UI
		scanDetailMap.put(UserEnteredKeyType.SELLER_ID, sellerId.toString());

	}

	
	/**
	 * save seller city details. If city not found in city table new city will
	 * be inserted.
	 * 
	 * @param scanDetailMap
	 * @param country
	 */
	private City saveSellerCityDetails(
			Map<UserEnteredKeyType, String> scanDetailMap, CountryMaster country) {
		Long cityId = 0L;
		// SELLER_CITY_ID is seller name when come from mobile client while
		// storing in db becomes seller id primary key
		String cityName = scanDetailMap.get(UserEnteredKeyType.SELLER_CITY_ID);
		City city = null;
		if (StringUtils.hasText(cityName)) {
			city = cityService.addNewCityInDatabase(country, cityName);
			cityId = city.getId();
		}
		// since mobile is giving seller name and not seller id. Need to put
		// back seller id TOBE Decided on mobile UI
		scanDetailMap.put(UserEnteredKeyType.SELLER_CITY_ID, cityId.toString());
		return city;

	}

	

	/**
	 * Save the sellers country details if country name not found in db new
	 * record inserted in db.
	 * 
	 * @param scanDetailMap
	 * @return
	 */
	private CountryMaster saveSellerCountryDetails(
			Map<UserEnteredKeyType, String> scanDetailMap) {
		Long countryId = 0L;
		CountryMaster countryMaster = null;
		// SELLER_COUNTRY_ID is seller name when come from mobile client while
		// storing in db becomes seller id primary key
		String countryName = scanDetailMap
				.get(UserEnteredKeyType.SELLER_COUNTRY_ID);
		if (StringUtils.hasText(countryName)) {
			countryMaster = countryMasterService.addNewCountryInDatabase(
					countryName, null);
			countryId = countryMaster.getId();
		}

		// since mobile is giving seller name and not seller id. Need to put
		// back seller id TOBE Decided on mobile UI
		scanDetailMap.put(UserEnteredKeyType.SELLER_COUNTRY_ID,
				countryId.toString());
		return countryMaster;
	}

	/**
	 * @see ProductAuthenticationService#getScanDetails(Long)
	 */
	@Override
	public ProductScanDTO getScanDetails(Long scanId) {
		Scan scan = scanDao.findOne(scanId);
		if (null == scan) {
			throw new ResourceNotFoundException("Scan Id not exist in system");
		}
		ProductScanDTO scanDTO = new ProductScanDTO();
		BeanUtils.copyProperties(scan, scanDTO);

		return scanDTO;
	}

	/**
	 * This method get called when scan table updated. This method send push
	 * notification using websocket to client code. Tenant id send along with
	 * the notification. Tenant id for which scan occured.
	 * 
	 * @param scan
	 */
	private void afterScanExecuted(Scan scan) {
		/*
		 * this.messagingTemplate.convertAndSendToUser(
		 * scan.getUser().getEmailAddress(), "/queue/live-scan-count", true);
		 */
		if (ScanResultType.Incomplete.IsScanComplete(scan.getScanResult()
				.getId())) {

			Long tenantId = brandDao.getTenantId(scan.getBrand().getId());
			if (tenantId == null) {
				throw new UnprocessableResourceException(
						"There is no tenant agains brand "
								+ scan.getBrand().getId());
			}

			this.messagingTemplate.convertAndSend("/queue/live-scan-count",
					tenantId);
		}
	}

}
