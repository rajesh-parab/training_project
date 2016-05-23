package com.vpa.core.dws.dao;

import java.util.List;

import org.springframework.stereotype.Repository;


import com.vpa.core.dws.models.UnprotectedProductViewAll;

/**
 * Dao Interface class for Unprotected product with viewAll functionality.
 * @author NS60097
 *
 */
@Repository
public interface UnprotectedProductViewAllDao {
	public List<UnprotectedProductViewAll> viewAll(String tenantId);
	
	public Object []  unprotectedProductDetails(String tenantId);
	
}
	


