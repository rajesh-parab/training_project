package com.vpa.saas.services;

import java.util.List;

import com.vpa.saas.dto.CounterfeitBuyingSellingEntityDTO;
import com.vpa.saas.forms.CounterfeitEntityForm;

/**
 * @author PD42694
 *
 */
public interface CounterfeitEntityService {
	
	
	/**
	 * This method will return the list of Buying Entity/Company and Scan result which has Scan result as Counterfeit 
	 * @param tenantId
	 * @param brandId
	 * @return
	 */
	public List<CounterfeitBuyingSellingEntityDTO> getCounterfeitBuyingEntities(long tenantId, long brandId); 
	
	
	/**
	 * @param tenantId
	 * @param brandId
	 * @param totalNumberOfRecords
	 * @return
	 */
	public List<CounterfeitBuyingSellingEntityDTO> getCounterfeitBuyingEntities(long tenantId, long brandId, int totalNumberOfRecords); 
	
	/**
	 * This method will return the list of Selling Entity and Scan result which has Scan result as Counterfeit
	 * @param tenantId
	 * @param brandId
	 * @return List
	 */
	public List<CounterfeitBuyingSellingEntityDTO> getCounterfeitSellingEntities(long tenantId, long brandId);
	
	/**
	 * This method will return the list of Selling Entity and Scan result which has Scan result as Counterfeit
	 * @param tenantId
	 * @param brandId
	 * @param numberOfRecords
	 * @return
	 */
	public List<CounterfeitBuyingSellingEntityDTO> getCounterfeitSellingEntities(long tenantId, long brandId, int numberOfRecords);
	
	/**
	 * This method will return the list of Buying Entity/Company and Scan result which has Scan result as Counterfeit for Authentication Page
	 * @param counterfeitEntityForm
	 * @return List<CounterfeitBuyingSellingEntityDTO>
	 */
	public List<CounterfeitBuyingSellingEntityDTO> getCounterfeitBuyingEntitiesForAuthentication(final CounterfeitEntityForm counterfeitEntityForm);
	
	
	/**
	 * This method will return the list of Selling and Scan result which has Scan result as Counterfeit for Authentication Page
	 * @param tenantId
	 * @param brandId
	 * @param numberOfRecords
	 * @param region
	 * @param timePeriod
	 * @param entityType
	 * @param layer
	 * @return List<CounterfeitBuyingSellingEntityDTO>
	 */
	List<CounterfeitBuyingSellingEntityDTO> getCounterfeitSellingEntitiesForAuthentication(final CounterfeitEntityForm counterfeitEntityForm);
	
	/**
	 * This method will return the list of Selling and Scan result which has Scan result as Counterfeit for Entity Page
	 * @param counterfeitEntityForm
	 * @return <code>List<<code>CounterfeitBuyingSellingEntityDTO</code>></code>
	 */
	List<CounterfeitBuyingSellingEntityDTO> getSellingEntities(final CounterfeitEntityForm counterfeitEntityForm);
	
	/**
	 * This method will return the list of Buying and Scan result which has Scan result as Counterfeit for Entity Page
	 * @param counterfeitEntityForm
	 * @return <code>List<<code>CounterfeitBuyingSellingEntityDTO</code>></code>
	 */
	List<CounterfeitBuyingSellingEntityDTO> getBuyingEntities(final CounterfeitEntityForm counterfeitEntityForm);
	
	/**
	 * This method will return the list of Selling/Buying and Scan result which has Scan result as Counterfeit for Entity Page
	 * @param counterfeitEntityForm
	 * @return <code>List<<code>CounterfeitBuyingSellingEntityDTO</code>></code>
	 */
	List<CounterfeitBuyingSellingEntityDTO> getSellingAndBuyingEntities(final CounterfeitEntityForm counterfeitEntityForm);
}
