package com.vpa.core.mes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vpa.core.models.ScanResult;
@Repository
public interface ScanResultDao extends JpaRepository<ScanResult, Integer>{

}
