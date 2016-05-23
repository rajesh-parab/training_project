package com.vpa.core.dws.dao;

import com.vpa.core.dws.models.ProductDetails;
import org.springframework.data.repository.Repository;

/**
 * Created by anatoly on 11/3/15.
 */

public interface ProductDetailsDao extends  Repository<ProductDetails, Long>{
    public ProductDetails getOne(Long id);
}
