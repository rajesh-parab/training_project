package com.vpa.saas.controllers;

import com.vpa.core.models.LabelValueBean;
import com.vpa.saas.services.CountryDetailsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/country-details")
public class CountryDetailsController {

    private static final Logger logger = Logger.getLogger(CountryController.class);

    @Autowired
    CountryDetailsService countryDetailsService;

    @RequestMapping(value = "/cities/{countryId}/{brandId}", method = RequestMethod.GET)
    public List<LabelValueBean> getCitiesByCountryAndBrand(
            @PathVariable("countryId") int countryId,
            @PathVariable("brandId") int brandId) {

        return countryDetailsService.getCitiesByCountryAndBrand(countryId, brandId);
    }
}
