package com.vpa.saas.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vpa.core.dws.dao.DimAuthenticationDao;
import com.vpa.core.dws.models.RegionWiseAuthentication;
import com.vpa.core.exceptions.UnprocessableResourceException;
import com.vpa.saas.dto.DimAuthenticationDTO;
import com.vpa.saas.services.DimAuthenticationService;

@Service
@Transactional(value = "transactionManagerDWS", propagation = Propagation.SUPPORTS)
public class DimAuthenticationServiceImpl implements DimAuthenticationService {

	@Autowired
	DimAuthenticationDao dimAuthenticationDao;

	@Override
	public DimAuthenticationDTO getWorldWideAuthenticationKPI(Long tenantId,
			Long brandId) {
		DimAuthenticationDTO dimentionAuthentication = new DimAuthenticationDTO();
		List<RegionWiseAuthentication> result = dimAuthenticationDao
				.getWorldWideAuthenticationCount(tenantId, brandId);

		long totalAuthentication = 0L;
		long totalGenuineAuthentication = 0L;

		for (RegionWiseAuthentication dimension : result) {
			if (dimension == null) {
				throw new UnprocessableResourceException(
						"getWorldWideAuthenticationKPI KPI indicator failed Data is not loaded in MES");
			}
			totalAuthentication += dimension.getTotalAuthentications();
			if (isGenuine(dimension)) {
				totalGenuineAuthentication += dimension
						.getTotalAuthentications();
			}

		}

		Long totalInLast24Hrs = 0L;
		Long totalGenuineInLast24Hrs = 0L;
		result = dimAuthenticationDao
				.getWorldWideAuthenticationCountForLast24Hrs(tenantId, brandId);

		for (RegionWiseAuthentication dimension : result) {
			if (dimension == null) {
				throw new UnprocessableResourceException(
						" getWorldWideAuthenticationKPI KPI indicator last 24 hours failed Data is not loaded in MES");
			}
			totalInLast24Hrs += dimension.getTotalAuthentications();
			if (isGenuine(dimension)) {
				totalGenuineInLast24Hrs += dimension.getTotalAuthentications();
			}
		}

		dimentionAuthentication.addAuthenticationKpi("Total",
				totalAuthentication, totalInLast24Hrs);
		dimentionAuthentication.addAuthenticationKpi("Genuine",
				totalGenuineAuthentication, totalGenuineInLast24Hrs);
		dimentionAuthentication.addAuthenticationKpi("Suspect",
				totalAuthentication - totalGenuineAuthentication,
				totalInLast24Hrs - totalGenuineInLast24Hrs);

		return dimentionAuthentication;
	}

	private boolean isGenuine(RegionWiseAuthentication dimension) {
		return "Genuine".equalsIgnoreCase(dimension.getFinalResult());
	}

	@Override
	public DimAuthenticationDTO getRegionWideAuthenticationTotals(
			Long tenantId, Long brandId) {
		DimAuthenticationDTO dimentionAuthentication = new DimAuthenticationDTO();
		List<RegionWiseAuthentication> result = dimAuthenticationDao
				.getRegionWiseAuthenticationCount(tenantId, brandId);

		 
		for (RegionWiseAuthentication dimension : result) {
			if (dimension == null) {
				throw new UnprocessableResourceException(
						" getWorldWideAuthentication Authentication Regionwise World Map failed Data is not loaded in MES");

			}
			dimentionAuthentication.addRegionWiseAuthenticationCount(
					dimension.getRegionName(),
					dimension.getRegionWiseTotalCount(),
					dimension.getRegionWiseGenuineCount(),
					dimension.getRegionWiseSuspectedCount(),
					dimension.getGenuineEntitiesCount(),
					dimension.getSuspectedEntitiesCount(),
					dimension.getTotalEntitiesCount());
		}

		return dimentionAuthentication;
	}

}
