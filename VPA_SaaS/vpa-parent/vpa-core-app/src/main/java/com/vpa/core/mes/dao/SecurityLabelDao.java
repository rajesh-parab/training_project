package com.vpa.core.mes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vpa.core.models.SecurityLabel;

@Repository
public interface SecurityLabelDao  extends JpaRepository<SecurityLabel, Long>{
  @Query("select sl from SecurityLabel sl where serialNumber = ? and enable =1")
  SecurityLabel getActiveSecurityLabel(String serialNumber);  

}
