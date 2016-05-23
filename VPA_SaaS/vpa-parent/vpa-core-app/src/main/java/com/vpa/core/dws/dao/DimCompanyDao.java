package com.vpa.core.dws.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vpa.core.dws.models.DimCompany;

@Repository

public interface DimCompanyDao  extends  JpaRepository<DimCompany, Long> ,CounterfeitEntityDao{

}
