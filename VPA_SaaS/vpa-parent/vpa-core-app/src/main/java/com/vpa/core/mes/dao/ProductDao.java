package com.vpa.core.mes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vpa.core.models.Product;

public interface ProductDao extends JpaRepository<Product, Long> {

	@Query("select  p from Product p where id = ? and enable=1")
	Product findEnabledProduct(Long id);

	@Query("select p from Product p where id = ? and enable=1")
	Product findEnabledGenuineProduct(Long id);
	
}
