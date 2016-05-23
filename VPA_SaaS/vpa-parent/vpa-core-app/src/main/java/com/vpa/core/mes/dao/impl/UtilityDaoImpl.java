package com.vpa.core.mes.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.vpa.core.mes.dao.UtilityDao;
import com.vpa.core.models.ProductAgainstBrand;

@Repository
public class UtilityDaoImpl implements UtilityDao {

	private static final String SQL = "SELECT distinct new com.vpa.core.models.ProductAgainstBrand(b.id,b.name,p.id,p.name) FROM SecurityLabel sl "
			+ " JOIN sl.productInstance.manufacturerProduct.product p JOIN sl.brand b";

	@PersistenceContext(unitName = "vpaCorePU")
	private EntityManager em;

	@Override
	public List<ProductAgainstBrand> getProductBrandMappings() {
		return em.createQuery(SQL,
				ProductAgainstBrand.class).getResultList();
	}
}
