 

package com.vpa.saas.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vpa.core.exceptions.CustomIllegalArgumentException;
import com.vpa.core.exceptions.DuplicateRecordException;
import com.vpa.core.exceptions.ResourceNotFoundException;
import com.vpa.saas.dto.MESProductInstanceDTO;
import com.vpa.saas.forms.MESDataFrom;
import com.vpa.saas.services.MESService;

@RestController
@RequestMapping("/mes")
public class MESDataController {

	private static final Logger LOGGER = Logger.getLogger(MESDataController.class);
	
	@Autowired
	private MESService mESService; 
	
	
	@RequestMapping(value = "/productinstance", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public MESProductInstanceDTO saveProductInstance(
			@RequestBody MESDataFrom mesDataFrom) {
		LOGGER.info("Calling saveProductInstance with parameters "+ mesDataFrom.toString());
		if(mesDataFrom.getSiteName()<1){
			final String message = "SiteName should not be blank";
			throw new CustomIllegalArgumentException(message);
		}
		if(mesDataFrom.getPartNumber()<1){
			final String message = "Part number should not be blank";
			throw new CustomIllegalArgumentException(message);
		}
		
		if(StringUtils.isBlank(mesDataFrom.getProductSerialNumber()) ){
			final String message = "Product Serial number should not be blank";
			throw new CustomIllegalArgumentException(message);
		}
		if(StringUtils.isBlank(mesDataFrom.getSecuritySerialNumber()) ){
			final String message = "Security Serial Number should not be blank";
			throw new CustomIllegalArgumentException(message);
		}
		 MESProductInstanceDTO mesProductInstanceDTO = null;
		try{
			mesProductInstanceDTO = mESService.saveProductInstance(mesDataFrom);	
		}catch(DuplicateRecordException de){
			final String message = "Product Serial Number "+ mesDataFrom.getProductSerialNumber().trim()+ " does already exist";
			LOGGER.info(mesDataFrom);
			throw new DuplicateRecordException(message);
		}catch (ResourceNotFoundException re) {			
			final String message = "The security Serial number you have entered: "+ mesDataFrom.getSecuritySerialNumber() +" , doesn't exixt into DB.";
			LOGGER.info(message);
			throw new ResourceNotFoundException(message);
		}
		return  mesProductInstanceDTO;
	}
	
	@RequestMapping(value = "/productinstance/list", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<MESProductInstanceDTO> getParentProductInstance(
			@RequestBody MESDataFrom mesDataFrom) {
		LOGGER.info("Calling getParentProductInstance with parameters "+ mesDataFrom.toString());
		if(mesDataFrom.getTenantId()<1){
			final String message = "Tenant Id should not be blank";
			throw new CustomIllegalArgumentException(message);
		}
		if(mesDataFrom.getBrandId()<1){
			final String message = "Brand should not be blank";
			throw new CustomIllegalArgumentException(message);
		}
	
		 List<MESProductInstanceDTO> manufacturerProduct = new ArrayList<>();
		try{
		 manufacturerProduct = mESService.getProductInstanceList(mesDataFrom);
		}catch(DuplicateRecordException de){
			final String message = "Product Serial Number "+ mesDataFrom.getProductSerialNumber().trim()+ " does already exist";
			LOGGER.info(mesDataFrom);
			throw new DuplicateRecordException(message);
		}
		return  manufacturerProduct;
	}
	
	
}
 
 
