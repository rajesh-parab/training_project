/**
 * 
 */
package com.vpa.core.dws.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vpa.core.dws.models.DimProduct;
import com.vpa.core.models.LabelValueBean;

/**
 * @author PD42694,NS60097
 *
 */
public interface DimProductDao extends JpaRepository<DimProduct, Long> {

	@Query("select p from DimProduct p where p.tenantId=:tenantId")
	List<DimProduct> getProductListByTenant(@Param("tenantId") Integer tenantId);

	/**
	 * 
	 * this method return the product list based on tenant id and product is
	 * compromised.
	 * 
	 * @param tenantId
	 * @param compromised
	 * @param protectedProduct
	 * @param levelId
	 * @return list of LabelValueBean
	 */
	@Query("select new com.vpa.core.models.LabelValueBean(p.id,p.product) from DimProduct p "
			+ "where p.tenantId=:tenantId and p.compromised=1 and p.protectedProduct=1 "
			+ " and p.levelId=:levelId order by product")
	List<LabelValueBean> getCompromisedProductListForCountry(
			@Param("tenantId") int tenantId, @Param("levelId") String levelId);
}
