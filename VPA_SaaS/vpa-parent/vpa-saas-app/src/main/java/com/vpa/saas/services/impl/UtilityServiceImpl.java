/*************************************************************************************************************
 **    Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 ** Module : VPA-SaaS
 ** File :      UtilityServiceImpl.java
 ** Version : 1.0
 ** Description : REST Controller for ............
 ** Author :        Rajesh Parab
 ** Created Date : Tuesday, 05 May 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  Friday, 16 Oct 2015 Null check added to getCurrentTimeStamp method
 **************************************************************************************************************/
package com.vpa.saas.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.vpa.core.enums.UserEnteredKeyType;
import com.vpa.core.exceptions.UnprocessableResourceException;
import com.vpa.core.mes.dao.BusinessUserDao;
import com.vpa.core.mes.dao.ScanDao;
import com.vpa.core.mes.dao.SecurityLabelDao;
import com.vpa.core.mes.dao.UserDao;
import com.vpa.core.mes.dao.UtilityDao;
import com.vpa.core.models.BusinessUser;
import com.vpa.core.models.CountryMaster;
import com.vpa.core.models.Product;
import com.vpa.core.models.ProductAgainstBrand;
import com.vpa.core.models.Scan;
import com.vpa.core.models.ScanDetail;
import com.vpa.core.models.SecurityLabel;
import com.vpa.core.models.User;
import com.vpa.core.utils.VPASaaSConstant;
import com.vpa.core.utils.VPASaaSUtil;
import com.vpa.saas.dto.BrandDTO;
import com.vpa.saas.dto.LiveScanDetailDTO;
import com.vpa.saas.dto.ScanResultType;
import com.vpa.saas.services.UtilityService;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class UtilityServiceImpl implements UtilityService {

	private static final Logger logger = Logger
			.getLogger(UtilityServiceImpl.class);

	@Autowired
	private UtilityDao utilityDao;

	@Autowired
	private ScanDao scanDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private BusinessUserDao businessUserDao;

	@Autowired
	private VPASaaSUtil vpaUtil;

	@Autowired
	private SecurityLabelDao securityLabelDao;

	/**
	 * @see UtilityService#getLiveAuthenticationDetails(long, String)
	 */
	@Override
	public List<LiveScanDetailDTO> getLiveAuthenticationDetails(long tenantId,long brandId,String loginTime) {

		Date loginDateTime = vpaUtil.convertStringToTimeStamp(loginTime);
		//// Start of Pilot enhancement
		List<Scan> scans = scanDao.getLiveAuthDetails(loginDateTime, tenantId, brandId,new PageRequest(0, 10));
		
		if (CollectionUtils.isEmpty(scans)){
			
			scans=scanDao.getLastFiveAuthDetails(tenantId, brandId,new PageRequest(0, 5));
		}
		//// End of Pilot enhancement
		List<LiveScanDetailDTO> targets = new ArrayList<>();

		for (Scan scan : scans) {
			LiveScanDetailDTO target = new LiveScanDetailDTO();
			BeanUtils.copyProperties(scan, target);

			Product product = scan.getProduct();

			try {
				long productsTenantId = 0;
				productsTenantId = scan.getBrand().getTenant().getId();
				if (productsTenantId != tenantId) {
					continue;
				}

				if (null != product) {
					VPASaaSUtil.copyProperties(product, target.getProduct(),
							new String[] { "name", "glp" });
					if (scan.getScanResult().getId()
							.equals(ScanResultType.Genuine.getId())) {

						SecurityLabel securityLabel = securityLabelDao
								.getActiveSecurityLabel(scan
										.getSecuritySerialNumber());
						String levelName = securityLabel.getProductInstance()
								.getManufacturerProduct().getProduct()
								.getLevel().getName();

						target.setLevel(levelName);
					} else {
						target.setLevel(getLevelId(scan));
					}
				}
			} catch (NullPointerException e) {
				logger.error(
						"There is a problem retriving tenant id for product in getLiveAuthenticationDetails",
						e);
				throw new UnprocessableResourceException(
						" request can not be prcessed ");
			}
			target.setScanResultDesc(scan.getScanResult().getDescription());
			Long userId = scan.getUser().getId();
			target.getUser().setId(userId);
			target.setCreatedDateString(scan.getCreatedDate().toString());

			User user = userDao.findAtiveUser(userId);

			if (user.isBusinessUser()) {
				BusinessUser businessUser = businessUserDao
						.findActiveUser(userId);
				target.setCompanyName(businessUser.getCompany().getName());

			}

			VPASaaSUtil.copyProperties(user, target.getUser(), new String[] {
					"firstName", "lastName" });

			String country = scan.fetchCountryName();
			CountryMaster countryMaster = scan.getCountryMaster();
			if (countryMaster != null) {
				country = countryMaster.getName();
			}

			target.setCountry(country);

			target.setCountry(scan.fetchCountryName());
			target.setCity(scan.fetchCityName());

			targets.add(target);
		}

		return targets;
	}

	@Override
	public Map<String, BrandDTO> getBrandProductMappings() {

		List<ProductAgainstBrand> products = utilityDao
				.getProductBrandMappings();

		Map<String, BrandDTO> brandProductMapping = new HashMap<>();

		try {

			for (ProductAgainstBrand product : products) {

				String brandName = product.getBrandName();
				if (brandProductMapping.containsKey(brandName)) {
					BrandDTO brand = brandProductMapping.get(brandName);
					brand.addProduct(product.getProductId(),
							product.getProductName());

				} else {
					BrandDTO brand = new BrandDTO();
					brand.setId(product.getBrandId());
					brand.setName(brandName);
					brand.addProduct(product.getProductId(),
							product.getProductName());
					brandProductMapping.put(brandName, brand);
				}

			}
		} catch (Exception e) {
			// Wired but can't rely on MES data is correct
			// silently ignored the exception to allow login.

			logger.info("MES Data problem ", e);

		}
		return brandProductMapping;
	}

	private String getLevelId(Scan scan) {
		String levelId = VPASaaSConstant.EMPTY_VALUE;
		List<ScanDetail> scanDetails = scan.getScanDetails();
		for (ScanDetail scanDetail : scanDetails) {
			if (scanDetail.getUserenteredkey().getId()
					.equals(UserEnteredKeyType.LEVEL_ID.getId())) {
				levelId = scanDetail.getValue();
				break;
			}

		}
		return levelId;

	}

	@Override
	public long getLiveFlagCount(long tenantId,long brandId, String timeStamp) {
		Date date = vpaUtil.convertStringToTimeStamp(timeStamp);
		return scanDao.getLiveFlagCount(date, tenantId,brandId);
	}

	@Override
	public String getCurrentMonthYear() {
		return scanDao.getCurrentMonthYear();

	}

	@Override
	public String getCurrentTimeStamp() {
		String date = scanDao.getCurrentTimeStamp();
		if (!StringUtils.hasText(date)) {
			date = Calendar.getInstance().getTime().toString();
		}
		return date;
	}

}
