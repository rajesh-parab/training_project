package com.vpa.core.dws.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vpa.core.dws.models.UnprotectedProductViewAll;

@Repository
public interface UnprotectedProductDao extends JpaRepository<UnprotectedProductViewAll, Long>,UnprotectedProductViewAllDao {
}
