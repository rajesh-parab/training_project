package com.vpa.core.dws.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.vpa.core.dws.dao.UnprotectedProductViewAllDao;
import com.vpa.core.dws.models.UnprotectedProductViewAll;


/** 
 * @author NS60097
 *
 */
public class UnprotectedProductDaoImpl implements UnprotectedProductViewAllDao {

	private static final int UNPROTECTED_PRODUCT = 0;
	
	private static final int ONLY_CHASISS = 3;
	
	private static final String UNPROTECTED_PRODUCT_VIEW_ALL = " select a.id,a.product_name,IFNULL(sum(b.revenue),0) as revenue_risk,"
			+ "c.product_family_name as product_family ,d.business_unit_name as business_unit , a.glp as glp "
			+ "from (select id, product_name, glp,product_family_id "
			+ "from dim_product where protected=:protected and tenant_id=:tenantId and level_id=:LevelId) a "
			+ "left join fact_revenue_monthly b on a.id=b.product_id "
			+ "left join dim_product_family c on a.product_family_id=c.id "
			+ "left join dim_business_unit d on c.business_unit_id=d.id "
			+ "group by a.id,a.product_name,c.product_family_name,d.business_unit_name "
			+ "order by revenue_risk desc;";

	private static final String UNPROTECTED_PRODUCT_DETAILS = "select v.glp, v.total_product, v.unprotected_product,"
			+ " round ((v.unprotected_product/v.total_product)*100,2) as percentage "
			+ " from (select max(glp_update_date) as glp,(select count(*) from dim_product where  tenant_id=:tenantId and level_id=:LevelId ) as total_product , "
			+ " (select count(*) from dim_product where tenant_id=:tenantId and protected=:protected and level_id=:LevelId ) as unprotected_product "
			+ " FROM dim_product where tenant_id =:tenantId and level_id=:LevelId) v";
	
	@PersistenceContext(unitName = "vpaDWPU")
	private EntityManager em;

	@Override
	public List<UnprotectedProductViewAll> viewAll(String tenantId) {
		@SuppressWarnings("unchecked")
		List<UnprotectedProductViewAll> result = (List<UnprotectedProductViewAll>) em
				.createNativeQuery(UNPROTECTED_PRODUCT_VIEW_ALL,
						UnprotectedProductViewAll.class)
				.setParameter("tenantId", tenantId)
				.setParameter("LevelId", ONLY_CHASISS)
				.setParameter("protected", UNPROTECTED_PRODUCT).getResultList();
		return result;
	}

	@Override
	public Object[] unprotectedProductDetails(String tenantId) {
		Object[] result = (Object[]) em
				.createNativeQuery(UNPROTECTED_PRODUCT_DETAILS)
				.setParameter("tenantId", tenantId)
				.setParameter("LevelId", ONLY_CHASISS)
				.setParameter("protected", UNPROTECTED_PRODUCT)
				.getSingleResult();
		return result;
	}

}
