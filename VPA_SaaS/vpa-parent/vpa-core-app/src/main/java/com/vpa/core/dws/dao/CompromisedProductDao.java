package com.vpa.core.dws.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import com.vpa.core.dws.models.CompromisedProductViewAll;


@Repository
public interface CompromisedProductDao extends JpaRepository<CompromisedProductViewAll, Long>,CompromisedProductViewAllDao{
	
	

}
