package com.vpa.core.dws.dao.impl;

import com.vpa.core.dws.models.ProductDetails;
import com.vpa.core.dws.dao.ProductDetailsDao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by anatoly on 11/3/15.
 */
@Repository
public class ProductDetailsDaoImpl implements ProductDetailsDao {

    @PersistenceContext(unitName = "vpaDWPU")
    private EntityManager em;

    @Override
    public ProductDetails getOne(Long id) {
        String sqlQuery =
                "SELECT\n" +
                        "  dp.id AS id,\n" +
                        "  dp.product_name AS name,\n" +
                        "  dp.glp AS glp,\n" +
                        "  dp.compromised AS compromised,\n" +
                        "  dpf.product_family_name as family,\n" +
                        "  dbu.business_unit_name as business_unit\n" +
                        "FROM dim_product dp\n" +
                        "  INNER JOIN dim_product_family dpf ON dp.product_family_id = dpf.id\n" +
                        "  INNER JOIN dim_business_unit dbu ON dpf.business_unit_id = dbu.id\n" +
                        "WHERE dp.id=:productId";

        Query query = em
                .createNativeQuery(sqlQuery, ProductDetails.class);
        query.setParameter("productId", id);

        List<ProductDetails> queryResult = query.getResultList();

        ProductDetails currentProduct = null;
        if (queryResult.size() > 0) {
            currentProduct = queryResult.get(0);
        }

        return currentProduct;
    }

}
