package com.vpa.core.mes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vpa.core.models.Manufacturersite;

public interface ManufacturersiteDao extends
		JpaRepository<Manufacturersite, Long> {

	@Query("select ms from Manufacturersite ms where ms.id = ? and ms.enable =1")
	Manufacturersite getOneActiveManufacturerSite(final long id);

}
