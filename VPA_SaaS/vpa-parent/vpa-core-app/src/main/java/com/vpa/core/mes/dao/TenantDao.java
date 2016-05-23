/**
 * 
 */
package com.vpa.core.mes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vpa.core.models.Tenant;

/**
 * @author PD42694
 *
 */
@Repository
public interface TenantDao extends JpaRepository<Tenant, Long> {

	@Query("SELECT t from Tenant t where t.enable=1")
	List<Tenant> findAllTenants();
 

}
