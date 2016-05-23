package com.vpa.core.dws.dao.impl;

import org.junit.Ignore;
import org.junit.Test;

import com.vpa.core.bo.CounterfeitEntityBO;

public class DimCompanyDaoImplTest {
	
	@Ignore
	@Test
	public void getCounterfeitBuyingEntitiesForAuthentication(){
		
		DimCompanyDaoImpl dimCompanyDao = new DimCompanyDaoImpl();
		CounterfeitEntityBO counterfeitEntityBO = new CounterfeitEntityBO();
		counterfeitEntityBO.setEntityTypeId(10);
		dimCompanyDao.getCounterfeitBuyingEntitiesForAuthentication(counterfeitEntityBO);
		
	}

}
