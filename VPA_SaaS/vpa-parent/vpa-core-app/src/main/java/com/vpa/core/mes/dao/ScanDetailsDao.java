package com.vpa.core.mes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vpa.core.models.ScanDetail;

@Repository
public interface ScanDetailsDao extends JpaRepository<ScanDetail, Long> {

//	@Query("select  sd from ScanDetail sd where scanId = ? order by sd.userEnteredKey.key")
	List<ScanDetail> findAllByScanId(Long scanId);

}
