package com.vpa.core.dws.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.vpa.core.bo.CounterfeitEntityBO;
import com.vpa.core.dws.models.DimCompany;

/**
 * @author PD42694
 *
 */
@Repository
public interface CounterfeitEntityDao{
	
	/**
	 * This method returns Counterfeit Buying Entity List
	 * @param tenentId
	 * @param brandId
	 * @return <code>List<<code>DimCompany</code>></code>
	 */
	List<DimCompany> getCounterfeitBuyingEntities(final long tenantId, final long brandId);
	
	/**
	 * This method returns Counterfeit Buying Entity List
	 * @param tenantId
	 * @param brandId
	 * @param totalNumberOfRecords
	 * @return <code>List<<code>DimCompany</code>></code>
	 */
	List<DimCompany> getCounterfeitBuyingEntities(final long tenantId, final long brandId, final int totalNumberOfRecords);
	
	/**
	 * This method returns Counterfeit Selling Entity List
	 * @param tenentId
	 * @param brandId
	 * @return <code>List<<code>DimCompany</code>></code>
	 */
	List<DimCompany> getCounterfeitSellingEntities(final long tenantId, final long brandId);
	

	/**
	 * This method returns Counterfeit Selling Entity List
	 * @param tenantId
	 * @param brandId
	 * @param numberOfRecords
	 * @return <code>List<<code>DimCompany</code>></code>
	 */
	List<DimCompany> getCounterfeitSellingEntities(final long tenantId, final long brandId, final int numberOfRecords);
	
	/**
	 * This method returns total number of authentications for given tenantIs, brandId, and company name. 
	 * @param companyId
	 * @return <code>BigDecimal</code>
	 */
	BigDecimal getTotalAuthenticationByComapny(final long tenantId, final long brandId, final String companyName);
	
	/**
	 * This method returns total number of authentications for given tenantIs, brandId, and sellerId.
	 * @param tenantId
	 * @param brandId
	 * @param sellerId
	 * @return <code>BigDecimal</code>
	 */
	BigDecimal getTotalAuthenticationBySeller(final long tenantId, final long brandId, final Integer sellerId);
	
	
	/**
	 * This method returns Counterfeit Buying Entity List for Authentication Page
	 * @param counterfeitEntityBO
	 * @return <code>List<<code>DimCompany</code>></code>
	 */
	List<DimCompany> getCounterfeitBuyingEntitiesForAuthentication(final CounterfeitEntityBO counterfeitEntityBO);
	
		
	/**
	 * This method returns Counterfeit Selling Entity List for Authentication Page for Given parameters 
	 * @param tenantId
	 * @param brandId
	 * @param numberOfRecords
	 * @param region
	 * @param timePeriod
	 * @param entityType
	 * @param layer
	 * @return <code>List<<code>DimCompany</code>></code>
	 */
	List<DimCompany> getCounterfeitSellingEntitiesForAuthentication(final CounterfeitEntityBO counterfeitEntityBO);
	
	
	/**
	 * This method will return the list of Selling and Scan result which has Scan result as Counterfeit for Entity Page
	 * 
	 * @param counterfeitEntityBO
	 * @return
	 */
	List<DimCompany> getSellingEntities(final CounterfeitEntityBO counterfeitEntityBO);
	
	/**
	 * This method will return the list of Buying and Scan result which has Scan result as Counterfeit for Entity Page
	 *
	 * @param counterfeitEntityBO
	 * @return
	 */
	List<DimCompany> getBuyingEntities(final CounterfeitEntityBO counterfeitEntityBO);
	
}
