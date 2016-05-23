package com.vpa.core.dws.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vpa.core.dws.models.DimAuthentication;
@Repository
 
public interface DimAuthenticationDao extends JpaRepository<DimAuthentication, Long>,RegionWiseAuthenticationDao{

}
