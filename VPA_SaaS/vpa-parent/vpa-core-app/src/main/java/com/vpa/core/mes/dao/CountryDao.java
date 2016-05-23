
package com.vpa.core.mes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vpa.core.models.Country;

@Repository
public interface CountryDao extends JpaRepository<Country, Long> {
	public Country getCountryByName(String countryName);

}

