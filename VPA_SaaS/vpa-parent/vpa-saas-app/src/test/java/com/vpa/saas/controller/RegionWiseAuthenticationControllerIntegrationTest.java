package com.vpa.saas.controller;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.vpa.saas.dto.DimAuthenticationDTO;
 

public class RegionWiseAuthenticationControllerIntegrationTest {

    private static final String AUTHENTICATION_KPI_URL = "http://localhost:8080/vpa-saas-app/dashboard/kpi";

    @Test
    public void regionWiseDashboardSelectableKPITest() {
         

        RestTemplate restTemplate = new RestTemplate();
        DimAuthenticationDTO request = new DimAuthenticationDTO();
        request.setId(1);
        restTemplate.postForObject(AUTHENTICATION_KPI_URL, request, DimAuthenticationDTO.class);
        DimAuthenticationDTO kpi=    restTemplate.getForObject(AUTHENTICATION_KPI_URL, DimAuthenticationDTO.class);
        System.out.println(kpi.getAuthenticationKpis());
    }
}
