/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   UserServiceImpl.java
 ** Version     :   1.0
 ** Description :   Service class implementation of UserService Interface to server the user controller and user related use cases.
 **
 ** Author      :   Punit
 ** Created Date :  Tuesday, 05 May 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/

package com.vpa.saas.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vpa.core.enums.Sorting;
import com.vpa.core.exceptions.CustomIllegalArgumentException;
import com.vpa.core.exceptions.ResourceNotFoundException;
import com.vpa.saas.dto.CounterfeitBuyingSellingEntityDTO;
import com.vpa.saas.forms.CounterfeitEntityForm;
import com.vpa.saas.services.CounterfeitEntityService;

/**
 * @author PD42694
 *
 */
@RestController
@RequestMapping("/entity")
public class ConterfeitEntityController {
	private static final String NO_RECORD_FOUND_FOR_TENANT_ID = "No record found for tenantId : ";

	private static final String BRAND_ID = " brandId :";

	private static final Logger logger = Logger
			.getLogger(ConterfeitEntityController.class);

	@Autowired
	private CounterfeitEntityService counterfeitEntityService;

	/**
	 * This method is used to fetch all counterfeit buying entities for given
	 * tenantId and brandId. Use below end point to call this web service
	 * http:// localhost:8080/vpa-saas-app/entity/counterfeit/buying/entities/{
	 * tenantId }/{brandId}
	 * 
	 * @param tenantId
	 * @param brandId
	 * @return <code>List<<code>CounterfeitBuyingSellingEntityDTO</code>></code>
	 */
	@RequestMapping(value = "/counterfeit/buying/entities/{tenantId}/{brandId}", method = RequestMethod.GET)
	public List<CounterfeitBuyingSellingEntityDTO> getAllCounterfeitBuyingEntity(
			@PathVariable("tenantId") long tenantId,
			@PathVariable("brandId") long brandId) {

		logger.info("Calling getAllCounterfeitBuyingEntity method with parameter tenantId: "
				+ tenantId + BRAND_ID + brandId);

		List<CounterfeitBuyingSellingEntityDTO> counterFeitBuyerList = new ArrayList<>();
		counterFeitBuyerList = counterfeitEntityService
				.getCounterfeitBuyingEntities(tenantId, brandId);

		if (CollectionUtils.isEmpty(counterFeitBuyerList)) {

			logger.info(NO_RECORD_FOUND_FOR_TENANT_ID + tenantId + BRAND_ID
			+ brandId);
			throw new ResourceNotFoundException(NO_RECORD_FOUND_FOR_TENANT_ID
			+ tenantId + BRAND_ID + brandId);

		}

		return counterFeitBuyerList;
	}

	/**
	 * This method is used to fetch top n counterfeit buying entities for given
	 * tenantId and brandId. Use below end point to call this web service
	 * http:// localhost:8080/vpa-saas-app/entity/counterfeit/buying/entities/{
	 * 
	 * tenantId }/{brandId}/{numberOfrecords}
	 * 
	 * 
	 * 
	 * @param tenantId
	 * @param brandId
	 * @param numberOfrecords
	 * @return <code>List<<code>CounterfeitBuyingSellingEntityDTO</code>></code>
	 */
	@RequestMapping(value = "/counterfeit/buying/entities/{tenantId}/{brandId}/{numberOfRecords}", method = RequestMethod.GET)
	public List<CounterfeitBuyingSellingEntityDTO> getTopCounterfeitBuyingEntity(

	@PathVariable("tenantId") long tenantId,

	@PathVariable("brandId") long brandId,
			@PathVariable("numberOfRecords") Integer numberOfRecords) {

		List<CounterfeitBuyingSellingEntityDTO> counterFeitBuyerList = new ArrayList<>();
		counterFeitBuyerList = counterfeitEntityService
				.getCounterfeitBuyingEntities(tenantId, brandId,

				numberOfRecords);

		if (CollectionUtils.isEmpty(counterFeitBuyerList)) {
			throw new ResourceNotFoundException(NO_RECORD_FOUND_FOR_TENANT_ID
					+ tenantId + BRAND_ID + brandId);
		}

		return counterFeitBuyerList;
	}

	/**
	 * This method is used to fetch all counterfeit selling entities for given
	 * tenantId and brandId. Use below end point to call this web service
	 * http://localhost:8080/vpa-saas-app/entity/counterfeit/selling/entities/{
	 * tenantId}/{brandId}
	 * 
	 * @param tenantId
	 * @param brandId
	 * @return <code>List<<code>CounterfeitBuyingSellingEntityDTO</code>></code>
	 */
	@RequestMapping(value = "/counterfeit/selling/entities/{tenantId}/{brandId}", method = RequestMethod.GET)
	public List<CounterfeitBuyingSellingEntityDTO> getAllCounterfeitSellingEntity(
			@PathVariable("tenantId") long tenantId,
			@PathVariable("brandId") long brandId) {
		logger.info("Calling getAllCounterfeitSellingEntity method with parameter tenantId: "
				+ tenantId + BRAND_ID + brandId);

		List<CounterfeitBuyingSellingEntityDTO> counterFeitBuyerList = new ArrayList<>();
		counterFeitBuyerList = counterfeitEntityService
				.getCounterfeitSellingEntities(tenantId, brandId);
		if (CollectionUtils.isEmpty(counterFeitBuyerList)) {

			logger.info(NO_RECORD_FOUND_FOR_TENANT_ID + tenantId + BRAND_ID
					+ brandId);

			throw new ResourceNotFoundException(NO_RECORD_FOUND_FOR_TENANT_ID
					+ tenantId + BRAND_ID + brandId);
		}
		return counterFeitBuyerList;
	}

	/**
	 * This method is used to fetch top n counterfeit selling entities for given
	 * tenantId and brandId. Use below end point to call this web service
	 * http://localhost:8080/vpa-saas-app/entity/counterfeit/selling/entities/{
	 * tenantId}/{brandId}/{numberOfrecords}
	 * 
	 * @param tenantId
	 * @param brandId
	 * @param numberOfrecords
	 * @return <code>List<<code>CounterfeitBuyingSellingEntityDTO</code>></code>
	 */
	@RequestMapping(value = "/counterfeit/selling/entities/{tenantId}/{brandId}/{numberOfRecords}", method = RequestMethod.GET)
	public List<CounterfeitBuyingSellingEntityDTO> getTopCounterfeitSellingEntity(

	@PathVariable("tenantId") long tenantId,

	@PathVariable("brandId") long brandId,
			@PathVariable("numberOfRecords") Integer numberOfRecords) {
		logger.info("Calling getAllCounterfeitSellingEntity method with parameter tenantId: "
				+ tenantId
				+ BRAND_ID
				+ brandId
				+ " numberOfRecords: "
				+ numberOfRecords);
		List<CounterfeitBuyingSellingEntityDTO> counterFeitBuyerList = new ArrayList<>();
		counterFeitBuyerList = counterfeitEntityService
				.getCounterfeitSellingEntities(tenantId, brandId,

				numberOfRecords);

		if (CollectionUtils.isEmpty(counterFeitBuyerList)) {

			logger.info(NO_RECORD_FOUND_FOR_TENANT_ID + tenantId + BRAND_ID
					+ brandId);
			throw new ResourceNotFoundException(NO_RECORD_FOUND_FOR_TENANT_ID
					+ tenantId + BRAND_ID + brandId);
		}
		return counterFeitBuyerList;
	}

	/**
	 * This method is used to fetch the record of counterfeit buying entities
	 * for tenant, brand, and numberOfRecords for revenue page
	 * 
	 * @param tenantId
	 * @param brandId
	 * @param numberOfRecords
	 * @return List<CounterfeitBuyingSellingEntityDTO>
	 */
	@RequestMapping(value = "/counterfeit/buying/authentication", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<CounterfeitBuyingSellingEntityDTO> getCounterfeitBuyingEntitiesForAuthentication(
			@RequestBody CounterfeitEntityForm counterfeitEntityForm) {
		logger.info("Calling getCounterfeitBuyingEntitiesForAuthentication method with parameter: "
				+ counterfeitEntityForm.toString());
		if (counterfeitEntityForm.getTenantId() <= 0
				&& counterfeitEntityForm.getBrandId() <= 0) {
			final String message = "Fields tenantId: "
					+ counterfeitEntityForm.getTenantId() + " and brandId: "
					+ counterfeitEntityForm.getBrandId()
					+ " should not have 0 value";
			logger.error(message);
			throw new CustomIllegalArgumentException(message);
		}
		List<CounterfeitBuyingSellingEntityDTO> counterFeitBuyerList = new ArrayList<>();
		counterfeitEntityForm.setOrderBy(Sorting.SUSPECTAUTHENTICATION.toString());
		counterFeitBuyerList = counterfeitEntityService
				.getCounterfeitBuyingEntitiesForAuthentication(counterfeitEntityForm);
		if (CollectionUtils.isEmpty(counterFeitBuyerList)) {
			final String message = NO_RECORD_FOUND_FOR_TENANT_ID
					+ counterfeitEntityForm.getTenantId() + BRAND_ID
					+ counterfeitEntityForm.getBrandId();
			logger.info(message);
			throw new ResourceNotFoundException(message);
		}
		return counterFeitBuyerList;
	}

	/**
	 * This method is used to fetch the record of counterfeit selling entities
	 * for tenant, brand, and Number of record for authentication page
	 * 
	 * @param counterfeitEntityForm
	 * @return <code>List<<code>CounterfeitBuyingSellingEntityDTO</code>></code>
	 */
	@RequestMapping(value = "/counterfeit/selling/authentication", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<CounterfeitBuyingSellingEntityDTO> getCounterfeitSellingEntitiesForAuthentication(
			@RequestBody CounterfeitEntityForm counterfeitEntityForm) {
		logger.info("Calling getCounterfeitSellingEntitiesForAuthentication method with parameter: "
				+ counterfeitEntityForm.toString());
		if (counterfeitEntityForm.getTenantId() <= 0
				&& counterfeitEntityForm.getBrandId() <= 0) {
			final String message = "Fields tenantId: "
					+ counterfeitEntityForm.getTenantId() + " and brandId: "
					+ counterfeitEntityForm.getBrandId()
					+ " should not have 0 value";
			logger.error(message);
			throw new CustomIllegalArgumentException(message);
		}
		List<CounterfeitBuyingSellingEntityDTO> counterFeitSellerList = new ArrayList<>();
		counterfeitEntityForm.setOrderBy(Sorting.SUSPECTAUTHENTICATION.toString());
		counterFeitSellerList = counterfeitEntityService
				.getCounterfeitSellingEntitiesForAuthentication(counterfeitEntityForm);
		if (CollectionUtils.isEmpty(counterFeitSellerList)) {
			final String message = "No records found for "
					+ counterfeitEntityForm.toString();
			logger.info(message);
			throw new ResourceNotFoundException(message);
		}
		return counterFeitSellerList;
	}
	
	
	/**
	 * This method is used to fetch the record of counterfeit buying entities
	 * for tenant, brand, and numberOfRecords for revenue page
	 * 
	 * @param tenantId
	 * @param brandId
	 * @param numberOfRecords
	 * @return List<CounterfeitBuyingSellingEntityDTO>
	 */
	@RequestMapping(value = "/counterfeit/buying/revenue", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<CounterfeitBuyingSellingEntityDTO> getCounterfeitBuyingEntitiesForRevenue(
			@RequestBody CounterfeitEntityForm counterfeitEntityForm) {
		logger.info("Calling getCounterfeitBuyingEntitiesForRevenue method with parameter: "
				+ counterfeitEntityForm.toString());
		if (counterfeitEntityForm.getTenantId() <= 0
				&& counterfeitEntityForm.getBrandId() <= 0) {
			final String message = "Fields tenantId: "
					+ counterfeitEntityForm.getTenantId() + " and brandId: "
					+ counterfeitEntityForm.getBrandId()
					+ " should not have 0 value";
			logger.error(message);
			throw new CustomIllegalArgumentException(message);
		}
		List<CounterfeitBuyingSellingEntityDTO> counterFeitBuyerList = new ArrayList<>();
		counterfeitEntityForm.setOrderBy(Sorting.REVENUE.toString());
		counterFeitBuyerList = counterfeitEntityService
				.getCounterfeitBuyingEntitiesForAuthentication(counterfeitEntityForm);
		if (CollectionUtils.isEmpty(counterFeitBuyerList)) {
			final String message = NO_RECORD_FOUND_FOR_TENANT_ID
					+ counterfeitEntityForm.getTenantId() + BRAND_ID
					+ counterfeitEntityForm.getBrandId();
			logger.info(message);
			throw new ResourceNotFoundException(message);
		}
		return counterFeitBuyerList;
	}

	/**
	 * This method is used to fetch the record of counterfeit selling entities
	 * for tenant, brand, and Number of record for revenue page
	 * 
	 * @param counterfeitEntityForm
	 * @return <code>List<<code>CounterfeitBuyingSellingEntityDTO</code>></code>
	 */
	@RequestMapping(value = "/counterfeit/selling/revenue", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<CounterfeitBuyingSellingEntityDTO> getCounterfeitSellingEntitiesForRevenue(
			@RequestBody CounterfeitEntityForm counterfeitEntityForm) {
		logger.info("Calling getCounterfeitSellingEntitiesForRevenue method with parameter: "
				+ counterfeitEntityForm.toString());
		if (counterfeitEntityForm.getTenantId() <= 0
				&& counterfeitEntityForm.getBrandId() <= 0) {
			final String message = "Fields tenantId: "
					+ counterfeitEntityForm.getTenantId() + " and brandId: "
					+ counterfeitEntityForm.getBrandId()
					+ " should not have 0 value";
			logger.error(message);
			throw new CustomIllegalArgumentException(message);
		}
		List<CounterfeitBuyingSellingEntityDTO> counterFeitSellerList = new ArrayList<>();
		counterfeitEntityForm.setOrderBy(Sorting.REVENUE.toString());
		counterFeitSellerList = counterfeitEntityService
				.getCounterfeitSellingEntitiesForAuthentication(counterfeitEntityForm);
		if (CollectionUtils.isEmpty(counterFeitSellerList)) {
			final String message = "No records found for "
					+ counterfeitEntityForm.toString();
			logger.info(message);
			throw new ResourceNotFoundException(message);
		}
		return counterFeitSellerList;
	}
	
	/**
	 * This method is used to fetch the record of buying entities
	 * for tenant, brand, and Number of record for revenue page
	 * 
	 * @param counterfeitEntityForm
	 * @return <code>List<<code>CounterfeitBuyingSellingEntityDTO</code>></code>
	 */
	@RequestMapping(value = "/buying", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<CounterfeitBuyingSellingEntityDTO> getBuyingEntities(
			@RequestBody CounterfeitEntityForm counterfeitEntityForm) {
		logger.info("Calling getBuyingEntities method with parameter: "
				+ counterfeitEntityForm.toString());
		if (counterfeitEntityForm.getTenantId() <= 0
				&& counterfeitEntityForm.getBrandId() <= 0) {
			final String message = "Fields tenantId: "
					+ counterfeitEntityForm.getTenantId() + " and brandId: "
					+ counterfeitEntityForm.getBrandId()
					+ " should not have 0 value";
			logger.error(message);
			throw new CustomIllegalArgumentException(message);
		}
		/*List<CounterfeitBuyingSellingEntityDTO> buyingEntityList = new ArrayList<>();
		buyingEntityList = counterfeitEntityService.getBuyingEntities(counterfeitEntityForm);
		if (CollectionUtils.isEmpty(buyingEntityList)) {
			final String message = "No records found for "
					+ counterfeitEntityForm.toString();
			logger.info(message);
			throw new ResourceNotFoundException(message);
		}*/
		return counterfeitEntityService.getBuyingEntities(counterfeitEntityForm);
	}
	
	/**
	 * This method is used to fetch the record of selling entities
	 * for tenant, brand, and Number of record for revenue page
	 * 
	 * @param counterfeitEntityForm
	 * @return <code>List<<code>CounterfeitBuyingSellingEntityDTO</code>></code>
	 */
	@RequestMapping(value = "/selling", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<CounterfeitBuyingSellingEntityDTO> getSellingEntities(
			@RequestBody CounterfeitEntityForm counterfeitEntityForm) {
		logger.info("Calling getSellingEntities method with parameter: "
				+ counterfeitEntityForm.toString());
		if (counterfeitEntityForm.getTenantId() <= 0
				&& counterfeitEntityForm.getBrandId() <= 0) {
			final String message = "Fields tenantId: "
					+ counterfeitEntityForm.getTenantId() + " and brandId: "
					+ counterfeitEntityForm.getBrandId()
					+ " should not have 0 value";
			logger.error(message);
			throw new CustomIllegalArgumentException(message);
		}
		/*List<CounterfeitBuyingSellingEntityDTO> sellingEntityList = new ArrayList<>();
		sellingEntityList = counterfeitEntityService
				.getSellingEntities(counterfeitEntityForm);
		if (CollectionUtils.isEmpty(sellingEntityList)) {
			final String message = "No records found for "
					+ counterfeitEntityForm.toString();
			logger.info(message);
			throw new ResourceNotFoundException(message);
		}*/
		return counterfeitEntityService
				.getSellingEntities(counterfeitEntityForm);
	}
	
	/**
	 * This method is used to fetch the record of buying and selling entities
	 * for tenant, brand, and Number of record for revenue page
	 * 
	 * @param counterfeitEntityForm
	 * @return <code>List<<code>CounterfeitBuyingSellingEntityDTO</code>></code>
	 */
	@RequestMapping(value = "/all", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<CounterfeitBuyingSellingEntityDTO> getAllEntities(
			@RequestBody CounterfeitEntityForm counterfeitEntityForm) {
		logger.info("Calling getAllEntities method with parameter: "
				+ counterfeitEntityForm.toString());
		if (counterfeitEntityForm.getTenantId() <= 0
				&& counterfeitEntityForm.getBrandId() <= 0) {
			final String message = "Fields tenantId: "
					+ counterfeitEntityForm.getTenantId() + " and brandId: "
					+ counterfeitEntityForm.getBrandId()
					+ " should not have 0 value";
			logger.error(message);
			throw new CustomIllegalArgumentException(message);
		}
		/*List<CounterfeitBuyingSellingEntityDTO> sellingAndBuyingEntities = new ArrayList<>();
		sellingAndBuyingEntities = counterfeitEntityService
				.getSellingAndBuyingEntities(counterfeitEntityForm);
		if (CollectionUtils.isEmpty(sellingAndBuyingEntities)) {
			final String message = "No records found for "
					+ counterfeitEntityForm.toString();
			logger.info(message);
			throw new ResourceNotFoundException(message);
		}*/
		return counterfeitEntityService
				.getSellingAndBuyingEntities(counterfeitEntityForm);
	}
}
