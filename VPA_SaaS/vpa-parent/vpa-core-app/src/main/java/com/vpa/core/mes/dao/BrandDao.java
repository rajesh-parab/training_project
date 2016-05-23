package com.vpa.core.mes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vpa.core.models.Brand;

@Repository
public interface BrandDao extends JpaRepository<Brand, Long> {

	@Query(value = "select tenantId from brand   where  id = :id and enable=1", nativeQuery = true)
	Long getTenantId(@Param("id") Long id);

}
