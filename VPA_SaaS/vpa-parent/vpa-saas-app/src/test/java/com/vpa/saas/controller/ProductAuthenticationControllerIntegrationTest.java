package com.vpa.saas.controller;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.vpa.saas.dto.ProductScanDTO;
import com.vpa.saas.services.AbstractVPASaaSTest;
import com.vpa.saas.test.utils.SaaSDTOFacotry;
 

public class ProductAuthenticationControllerIntegrationTest extends AbstractVPASaaSTest{

    private static final String GENUINE_SCAN = "http://localhost:8080/vpa-saas-app/product/scan/verify";

    @Test
    public void productScanTest() {
        ProductScanDTO scannedProduct;
        String securityLabel= "VPASL00001";
        scannedProduct= SaaSDTOFacotry.createScannedProductDTO(securityLabel,getActiveBusinessUserId());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(GENUINE_SCAN, scannedProduct, ProductScanDTO.class);
    }
}
