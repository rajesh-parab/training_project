/*************************************************************************************************************
 ** Vantage Point Analytics .  Copyright 2015 - 16  All rights reserved.
 **
 ** No Part of this file should be copied or distributed without the permission of Vantage Point Analytics.
 ** Application : Vantage Point analytics SaaS Authentication Platform
 **
 ** Module      :   VPA-SaaS
 ** File        :   DashBoardServiceImpl.java
 ** Version     :   1.0
 ** Description :   service  class for interface compromised Product  .
 **
 ** Author      :   Narayan Singh
 ** Created Date :  Friday, 19 June 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/

package com.vpa.saas.services.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vpa.core.bo.ProductRequestBO;
import com.vpa.saas.dto.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vpa.core.bo.AuthenticationDashboardFilterBO;
import com.vpa.core.bo.Top3ProductBO;
import com.vpa.core.dws.dao.CompromisedProductDao;
import com.vpa.core.dws.dao.UnprotectedProductDao;
import com.vpa.core.dws.models.CompromisedProductViewAll;
import com.vpa.core.dws.models.UnprotectedProductViewAll;
import com.vpa.saas.services.DashBoardService;

/**
 * 
 * This service class is used to display all the details of unprotected product
 * as well as flagged product on dashboard
 *
 */
@Service
@Transactional(value = "transactionManagerDWS", propagation = Propagation.SUPPORTS)
public class DashBoardServiceImpl implements DashBoardService {

	private static final Logger logger = Logger
			.getLogger(DashBoardServiceImpl.class);

	@Autowired
	private CompromisedProductDao compromisedProductDao;

	@Autowired
	private UnprotectedProductDao unprotectedProductDao;

	/**
	 * @see DashBoardService#unProtectedProductDashboard(String)
	 */

	@Override
	public UnProtectedProductDTO unProtectedProductDashboard(String tenantId) {

		Object[] obj = unprotectedProductDao
				.unprotectedProductDetails(tenantId);
		List<UnprotectedProductViewAll> viewAll = unprotectedProductDao
				.viewAll(tenantId);
		UnProtectedProductDTO unProtectedProductDTO = new UnProtectedProductDTO();
		try {
			//Validate.noNullElements(obj);
			Date glpDate = (Date) obj[0];
			int totalProductForTenant = ((BigInteger) obj[1]).intValue();
			int totalUnprotectedProduct = ((BigInteger) obj[2]).intValue();
			int unProtectedProductPercentage = ((BigDecimal) obj[3]).intValue();
			unProtectedProductDTO.setTotalProduct(totalProductForTenant);
			unProtectedProductDTO.setTotalUnprotectedProduct(totalUnprotectedProduct);
			unProtectedProductDTO.setUnProtectedProductPercentage(unProtectedProductPercentage);
			unProtectedProductDTO.setGlpDate(glpDate);
			if (!CollectionUtils.isEmpty(viewAll)) {
				List<Top3ProductDTO> top3Product = getTop3UnprotectedProduct(viewAll);
				Long totalRevenueAtRisk = calculateRevenueRisk(viewAll);
				unProtectedProductDTO.setTotalRevenueAtRisk(totalRevenueAtRisk);
				unProtectedProductDTO.setTop3UnProtectedProduct(top3Product);
				unProtectedProductDTO.setViewAll(viewAll);
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}

		return unProtectedProductDTO;
	}

	/**
	 * 
	 * @see DashBoardService#compromisedProductDashboard(ProductRequestBO)
	 */
	
	@Override
	public CompromisedProductDTO compromisedProductDashboard(ProductRequestBO requestBO) {
		String tenantId = requestBO.getTenantId();
		Object[] obj = compromisedProductDao
				.compromisedProductDetails(tenantId);
		List<CompromisedProductViewAll> viewAll = compromisedProductDao
				.viewAll(requestBO);
		CompromisedProductDTO compromisedProductDTO = new CompromisedProductDTO();
		try {
			//Validate.noNullElements(obj);
			Date glpDate = (Date) obj[0];
			int totalProtectedProduct = ((BigInteger) obj[1]).intValue();
			int totalCompromisedProduct = ((BigInteger) obj[2]).intValue();
			int compromisedProductPercentage = ((BigDecimal) obj[3]).intValue();
			compromisedProductDTO.setGlpDate(glpDate);
			compromisedProductDTO.setTotalProduct(totalProtectedProduct);
			compromisedProductDTO.setTotalCompromisedProduct(totalCompromisedProduct);
			compromisedProductDTO.setCompromisedProductPercentage(compromisedProductPercentage);
			if (!CollectionUtils.isEmpty(viewAll)) {
				List<Top3ProductDTO> top3Product = getTop3CompromisedProduct(viewAll);
				Long projectedRevenueLoss = calculateProjectedRevenueLoss(viewAll);
				compromisedProductDTO.setProjectedRevenueLoss(projectedRevenueLoss);
				compromisedProductDTO.setTop3CompromisedProduct(top3Product);
				compromisedProductDTO.setViewAll(viewAll);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return compromisedProductDTO;
	}

	/**
	 * this method calculate top 3 unprotected product based on revenue at risk
	 * from the viewAll functionality for Unprotected products.
	 *
	 */
	private List<Top3ProductDTO> getTop3UnprotectedProduct(
			List<UnprotectedProductViewAll> viewAll) {
		int viewAllSize = viewAll.size();
		int loopTill = viewAllSize < 3 ? viewAllSize : 3;
		List<Top3ProductDTO> top3Product = new ArrayList<>(2);
		for (int i = 0; i < loopTill; i++) {
			UnprotectedProductViewAll unProtectedProduct = viewAll.get(i);
			Top3ProductDTO top3Record = new Top3ProductDTO();
			top3Record.setProductName(unProtectedProduct.getProductName());
			top3Record.setRevenue(unProtectedProduct.getRevenueRisk());
			top3Product.add(top3Record);
		}
		return top3Product;
	}

	/**
	 * this method calculate total revenue risk from the viewAll functionality
	 * for Unprotected products.
	 *
	 */
	private Long calculateRevenueRisk(List<UnprotectedProductViewAll> viewAll) {
		Long total = 0L;
		for (UnprotectedProductViewAll unprotectedProductViewAll : viewAll) {
			total = total + unprotectedProductViewAll.getRevenueRisk();
		}
		return total;
	}

	/**
	 * this method calculate top 3 flagged product based on projected revenue
	 * loss from the viewAll functionality for flagged products.
	 *
	 */
	private List<Top3ProductDTO> getTop3CompromisedProduct(
			List<CompromisedProductViewAll> viewAll) {
		int viewAllSize = viewAll.size();
		int loopTill = viewAllSize < 3 ? viewAllSize : 3;
		List<Top3ProductDTO> top3Product = new ArrayList<>(2);
		for (int i = 0; i < loopTill; i++) {
			CompromisedProductViewAll compromisedProduct = viewAll.get(i);
			Top3ProductDTO top3Record = new Top3ProductDTO();
			top3Record.setProductName(compromisedProduct.getProductName());
			top3Record.setRevenue(compromisedProduct.getProjectedRevenueLoss());
			top3Product.add(top3Record);
		}
		return top3Product;
	}

	/**
	 * this method calculate total projected revenue loss from the viewAll
	 * functionality for flagged products.
	 *
	 */
	private Long calculateProjectedRevenueLoss(
			List<CompromisedProductViewAll> viewAll) {
		Long total = 0L;
		for (CompromisedProductViewAll compromisedProductViewAll : viewAll) {
			total = total + compromisedProductViewAll.getProjectedRevenueLoss();
		}
		return total;
	}

	/**
	 * @see DashBoardService#compromisedProductForAuthentication(AuthenticationDashboardFilterForm)
	 */

	@Override
	public List<Top3ProductBO> compromisedProduct(AuthenticationDashboardFilterForm filter) {
		AuthenticationDashboardFilterBO filterBO = new AuthenticationDashboardFilterBO();
		BeanUtils.copyProperties(filter, filterBO);
		return compromisedProductDao
				.topCompromisedProduct(filterBO);
	}

}
