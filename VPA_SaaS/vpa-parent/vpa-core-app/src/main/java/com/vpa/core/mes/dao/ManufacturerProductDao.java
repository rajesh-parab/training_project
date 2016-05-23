 
package com.vpa.core.mes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vpa.core.models.ManufacturerProduct;


public interface ManufacturerProductDao extends JpaRepository<ManufacturerProduct, Long>{
	
	@Query("select mf from ManufacturerProduct mf where mf.id = ? and mf.enable =1")
	ManufacturerProduct getOneActiveManufacturerProdcut(final long id);  

	
}
 
 
