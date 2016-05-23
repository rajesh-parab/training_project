package com.vpa.saas.services;

import com.vpa.saas.dto.DimAuthenticationDTO;

public interface DimAuthenticationService {

    DimAuthenticationDTO getWorldWideAuthenticationKPI(Long tenantId,Long brandId);

    DimAuthenticationDTO getRegionWideAuthenticationTotals(Long tenantId,Long brandId);
}
