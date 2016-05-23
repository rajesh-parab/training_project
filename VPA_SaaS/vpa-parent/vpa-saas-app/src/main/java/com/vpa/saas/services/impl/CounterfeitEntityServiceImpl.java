package com.vpa.saas.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vpa.core.bo.CounterfeitEntityBO;
import com.vpa.core.dws.dao.DimCompanyDao;
import com.vpa.core.dws.models.DimCompany;
import com.vpa.saas.converter.CounterfeitEntityConverter;
import com.vpa.saas.dto.CounterfeitBuyingSellingEntityDTO;
import com.vpa.saas.forms.CounterfeitEntityForm;
import com.vpa.saas.helpers.CustomComparator;
import com.vpa.saas.services.CounterfeitEntityService;

/**
 * @author PD42694
 *
 */
@Service
@Transactional(value = "transactionManagerDWS", propagation = Propagation.SUPPORTS)
public class CounterfeitEntityServiceImpl implements CounterfeitEntityService {

	private static final Logger logger = Logger
			.getLogger(CounterfeitEntityService.class);

	@Autowired
	private DimCompanyDao dimCompanyDao;

	@Autowired
	CounterfeitEntityConverter counterfeitEntityConverter;
	@Autowired
	private CustomComparator customComparator;
	

	@Override
	public List<CounterfeitBuyingSellingEntityDTO> getCounterfeitBuyingEntities(
			long tenantId, long brandId) {
		logger.info("Calling getCounterfeitBuyingEntities for tenantId: "
				+ tenantId + " brandId: " + brandId);
		List<DimCompany> dimCompanies = dimCompanyDao
				.getCounterfeitBuyingEntities(tenantId, brandId);
		return counterfeitEntityConverter.convert(dimCompanies);

	}

	@Override
	public List<CounterfeitBuyingSellingEntityDTO> getCounterfeitBuyingEntities(
			long tenantId, long brandId, int totalNumberOfRecords) {
		logger.info("Calling getCounterfeitBuyingEntities for tenantId: "
				+ tenantId + " brandId: " + brandId + " totalNumberOfRecords: "
				+ totalNumberOfRecords);
		List<DimCompany> dimCompanies = dimCompanyDao
				.getCounterfeitBuyingEntities(tenantId, brandId,
						totalNumberOfRecords);
		return counterfeitEntityConverter.convert(dimCompanies);
	}

	@Override
	public List<CounterfeitBuyingSellingEntityDTO> getCounterfeitSellingEntities(
			long tenantId, long brandId) {
		logger.info("Calling getCounterfeitSellingEntities for tenantId: "
				+ tenantId + " brandId: " + brandId);
		List<DimCompany> dimCompanies = dimCompanyDao
				.getCounterfeitSellingEntities(tenantId, brandId);
		return counterfeitEntityConverter.convert(dimCompanies);
	}

	@Override
	public List<CounterfeitBuyingSellingEntityDTO> getCounterfeitSellingEntities(
			long tenantId, long brandId, int numberOfRecords) {
		logger.info("Calling getCounterfeitSellingEntities for tenantId: "
				+ tenantId + " brandId: " + brandId + " numberOfRecords: "
				+ numberOfRecords);
		List<DimCompany> dimCompanies = dimCompanyDao
				.getCounterfeitSellingEntities(tenantId, brandId,
						numberOfRecords);
		return counterfeitEntityConverter.convert(dimCompanies);
	}

	@Override
	public List<CounterfeitBuyingSellingEntityDTO> getCounterfeitBuyingEntitiesForAuthentication(final CounterfeitEntityForm counterfeitEntityForm) {
		logger.info("Calling getCounterfeitBuyingEntitiesForAuthentication for : "+counterfeitEntityForm.toString());
		final CounterfeitEntityBO counterfeitEntityBO = new CounterfeitEntityBO();
		BeanUtils.copyProperties(counterfeitEntityForm, counterfeitEntityBO);
		List<DimCompany> dimCompanies = dimCompanyDao
				.getCounterfeitBuyingEntitiesForAuthentication(counterfeitEntityBO);
		return counterfeitEntityConverter.convert(dimCompanies);
	}

	@Override
	public List<CounterfeitBuyingSellingEntityDTO> getCounterfeitSellingEntitiesForAuthentication(final CounterfeitEntityForm counterfeitEntityForm) {
		logger.info("Calling getCounterfeitSellingEntitiesForAuthentication for : "+counterfeitEntityForm.toString());
		final CounterfeitEntityBO counterfeitEntityBO = new CounterfeitEntityBO();
		BeanUtils.copyProperties(counterfeitEntityForm, counterfeitEntityBO);
		List<DimCompany> dimCompanies = dimCompanyDao
				.getCounterfeitSellingEntitiesForAuthentication(counterfeitEntityBO);
		return counterfeitEntityConverter.convert(dimCompanies);
	}

	@Override
	public List<CounterfeitBuyingSellingEntityDTO> getSellingEntities(
			CounterfeitEntityForm counterfeitEntityForm) {
		logger.info("Calling getSellingEntities for : "+counterfeitEntityForm.toString());
		final CounterfeitEntityBO counterfeitEntityBO = new CounterfeitEntityBO();
		BeanUtils.copyProperties(counterfeitEntityForm, counterfeitEntityBO);
		return counterfeitEntityConverter.convert(dimCompanyDao.getSellingEntities(counterfeitEntityBO));
	}

	@Override
	public List<CounterfeitBuyingSellingEntityDTO> getBuyingEntities(
			CounterfeitEntityForm counterfeitEntityForm) {
		logger.info("Calling getBuyingEntities for : "+counterfeitEntityForm.toString());
		final CounterfeitEntityBO counterfeitEntityBO = new CounterfeitEntityBO();
		BeanUtils.copyProperties(counterfeitEntityForm, counterfeitEntityBO);
		return counterfeitEntityConverter.convert(dimCompanyDao.getBuyingEntities(counterfeitEntityBO));
	}

	@Override
	public List<CounterfeitBuyingSellingEntityDTO> getSellingAndBuyingEntities(
			CounterfeitEntityForm counterfeitEntityForm) {
		
		logger.info("Calling getSellingAndBuyingEntities for : "+counterfeitEntityForm.toString());

		final List<CounterfeitBuyingSellingEntityDTO> counterfeitEntities = new ArrayList<CounterfeitBuyingSellingEntityDTO>();
		if (counterfeitEntityForm.getEntityTypeId() == 0) {
			counterfeitEntities.addAll(getSellingEntities(counterfeitEntityForm));
		}
		counterfeitEntities.addAll( getBuyingEntities(counterfeitEntityForm));
		customComparator.sortCounterfeitBuyingSellingEntityDTO(counterfeitEntityForm.getOrderBy(), counterfeitEntities);
		
		if(counterfeitEntities.size()<=counterfeitEntityForm.getNumberOfRecords() || counterfeitEntityForm.getNumberOfRecords()==0){
			return counterfeitEntities;
		}else{
			List<CounterfeitBuyingSellingEntityDTO> counterfeitEntitiesToReturn = new ArrayList<CounterfeitBuyingSellingEntityDTO>();
			for (int i = 0; i < counterfeitEntityForm.getNumberOfRecords(); i++) {
				counterfeitEntitiesToReturn.add(counterfeitEntities.get(i));				
			}
			return counterfeitEntitiesToReturn;
		}
	}
	
	
	

}
