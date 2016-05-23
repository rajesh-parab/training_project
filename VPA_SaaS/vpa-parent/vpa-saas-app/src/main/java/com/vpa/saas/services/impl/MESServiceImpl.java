package com.vpa.saas.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vpa.core.bo.MESBO;
import com.vpa.core.exceptions.DuplicateRecordException;
import com.vpa.core.exceptions.ResourceNotFoundException;
import com.vpa.core.mes.dao.ManufacturerProductDao;
import com.vpa.core.mes.dao.ManufacturersiteDao;
import com.vpa.core.mes.dao.ProductInstanceDao;
import com.vpa.core.mes.dao.SecurityLabelDao;
import com.vpa.core.models.ManufacturerProduct;
import com.vpa.core.models.Manufacturersite;
import com.vpa.core.models.ProductInstance;
import com.vpa.core.models.SecurityLabel;
import com.vpa.saas.dto.MESProductInstanceDTO;
import com.vpa.saas.forms.MESDataFrom;
import com.vpa.saas.services.MESService;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class MESServiceImpl implements MESService{

	@Autowired
	private ManufacturerProductDao manufacturerProductDao;
	
	@Autowired
	private SecurityLabelDao securityLabelDao; 

	@Autowired
	private ProductInstanceDao productInstanceDao;
	
	@Autowired
	private ManufacturersiteDao manufacturersiteDao; 
	
	
	@Override
	public List<MESProductInstanceDTO> getManufacturerProductAndSite(
			MESDataFrom mesDataFrom) {
		final MESBO mesbo = new MESBO();
		BeanUtils.copyProperties(mesDataFrom, mesbo);
		return null;
	}

	@Override
	public MESProductInstanceDTO saveProductInstance(MESDataFrom mesDataFrom) throws DuplicateRecordException,ResourceNotFoundException{
		ProductInstance instance = productInstanceDao.findActiveProductInstanceByProductSerialNumber(mesDataFrom.getProductSerialNumber().trim());
		if(null!=instance){
			throw new DuplicateRecordException("Product Serial Number "+ mesDataFrom.getProductSerialNumber().trim()+ " does already exist");
		}
		
		//Get value from DB
		final SecurityLabel securitylabel = securityLabelDao.getActiveSecurityLabel(mesDataFrom.getSecuritySerialNumber().trim());
		
		if(null==securitylabel){
			throw new ResourceNotFoundException("The security Serial number you have entered, doesn't exixt into DB.");
		}
		final ManufacturerProduct manufacturerProduct = manufacturerProductDao.getOneActiveManufacturerProdcut(mesDataFrom.getPartNumber());
		final Manufacturersite manufacturersite = manufacturersiteDao.getOneActiveManufacturerSite(mesDataFrom.getSiteName());
		//Get value from DB
		
		//Save product instance
		final ProductInstance productInstance = new ProductInstance();		
		productInstance.setParentId(new Long(mesDataFrom.getParentSerialNumber()).intValue());
		productInstance.setProductSerialNumber(mesDataFrom.getProductSerialNumber().trim());		
		productInstance.setSecuritylabel(securitylabel);
		productInstance.setManufacturerProduct(manufacturerProduct);
		productInstance.setManufacturersite(manufacturersite);
		productInstance.setUpdatedDate(new Date());
		productInstance.setCreatedDate(new Date());		
		ProductInstance productInstance2= productInstanceDao.saveAndFlush(productInstance);
		securitylabel.setProductInstance(productInstance);
		securityLabelDao.saveAndFlush(securitylabel);
		
		MESProductInstanceDTO manufacturerDTO = new MESProductInstanceDTO();
		manufacturerDTO.setId(productInstance2.getId());
		manufacturerDTO.setLabel(productInstance2.getProductSerialNumber());
		
		return manufacturerDTO;
	}

	@Override
	public List<MESProductInstanceDTO> getProductInstanceList(
			MESDataFrom mesDataFrom) {
		
		//Populate Parent
		final List<MESProductInstanceDTO> manufacturerDTOs = new ArrayList<MESProductInstanceDTO>();
		List<ProductInstance> productInstanceList = productInstanceDao.getProductInstanceListByTenantAndBrand(mesDataFrom.getTenantId(), mesDataFrom.getBrandId());
		for (ProductInstance productInstance : productInstanceList) {
			MESProductInstanceDTO manufacturerDTO = new MESProductInstanceDTO();
			manufacturerDTO.setId(productInstance.getId());
			manufacturerDTO.setLabel(productInstance.getProductSerialNumber());
			manufacturerDTOs.add(manufacturerDTO);
		}
		return manufacturerDTOs;
	}

}
 
 
