package com.vpa.core.mes.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vpa.core.models.Scan;

@Repository
public interface ScanDao extends JpaRepository<Scan, Long> {
	
	
	@Query("select  count(s.id) FROM Scan s,Brand b where (s.brand.id=b.id and s.createdDate > ? and  s.scanResult.id <> 6  and  b.tenant.id=? and s.brand.id=? ) ")
	long getLiveFlagCount(Date createdDate,long tenantId,long brandId);

	@Query(value="Select CURRENT_TIMESTAMP;",nativeQuery = true)
	String getCurrentTimeStamp();
	
	@Query(value="Select DATE_FORMAT(CURRENT_TIMESTAMP,'%c/%y') from scan limit 1;",nativeQuery = true)
	String getCurrentMonthYear();

	////Start of Pilot enhancement
	@Query("Select s from Scan s,Brand b where (s.brand.id=b.id and s.createdDate > ? and  s.scanResult.id <> 6  and  b.tenant.id=? and s.brand.id=?) order by s.createdDate desc")
	List<Scan> getLiveAuthDetails(Date createdDate,long tenantId,long brandId,Pageable pageable);
	

	@Query("Select s from Scan s,Brand b where s.brand.id=b.id and  s.scanResult.id <> 6  and  b.tenant.id=? and s.brand.id=? order by s.createdDate desc")
	List<Scan> getLastFiveAuthDetails(long tenantId,long brandId,Pageable pageable);
	////End of Pilot enhancement
}
