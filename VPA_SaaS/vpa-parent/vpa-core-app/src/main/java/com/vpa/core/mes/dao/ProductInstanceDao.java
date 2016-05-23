package com.vpa.core.mes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vpa.core.models.ProductInstance;

@Repository
public interface ProductInstanceDao extends
        JpaRepository<ProductInstance, Long> {
	
	@Query(value="SELECT t1.* FROM  productinstance AS t1 LEFT JOIN productinstance AS t2 ON t2.parentId = t1.securityLabelId where t1.parentId = :parentSecurityLabel",nativeQuery=true)
	List<ProductInstance> getChildSecurityLabels(@Param("parentSecurityLabel") String parentSecurityLabel);

	@Query(value="SELECT t1.* FROM  productinstance AS t1 LEFT JOIN productinstance AS t2 ON t2.parentId = t1.securityLabelId where t2.securityLabelId = :securityLabelId and t1.enable=1 limit 1",nativeQuery=true)
	ProductInstance getParent(@Param("securityLabelId") Long securityLabelId);
	
	@Query(value="SELECT * FROM vfasp.productinstance where parentId= :parentSecurityLabelId and enable=1 limit 1",nativeQuery=true)
	ProductInstance getChild(@Param("parentSecurityLabelId") Long parentProductInstaanceId);
	
	@Query("select pi from ProductInstance pi where pi.productSerialNumber = ? and pi.enable =1")
	ProductInstance findActiveProductInstanceByProductSerialNumber(final String productserialNumber); 
	
	@Query("SELECT pi FROM Tenant t, Brand b, BusinessUnit bu, Productfamily pf, Product p, ManufacturerProduct mp, ProductInstance pi WHERE b.tenant = t.id and bu.brand=b.id and pf.businessUnit=bu.id and p.productfamily=pf.id and mp.product=p.id and pi.manufacturerProduct=mp.id and t.id=? and b.id=? ")
	List<ProductInstance> getProductInstanceListByTenantAndBrand(final long tenantId, final long brandId);

}
