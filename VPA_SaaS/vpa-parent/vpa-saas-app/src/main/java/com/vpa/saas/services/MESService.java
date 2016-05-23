 	
package com.vpa.saas.services;

import java.util.List;

import com.vpa.saas.dto.MESProductInstanceDTO;
import com.vpa.saas.forms.MESDataFrom;

public interface MESService {
	
	public List<MESProductInstanceDTO> getManufacturerProductAndSite(final MESDataFrom mesDataFrom);
	
	public MESProductInstanceDTO saveProductInstance(final MESDataFrom mesDataFrom);
	
	public List<MESProductInstanceDTO> getProductInstanceList(final MESDataFrom mesDataFrom);
}
 
 
