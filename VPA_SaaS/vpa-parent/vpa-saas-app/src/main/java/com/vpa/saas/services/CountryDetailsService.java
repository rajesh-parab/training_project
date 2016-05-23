package com.vpa.saas.services;


import com.vpa.core.models.LabelValueBean;

import java.util.List;

public interface CountryDetailsService {

    /**
     * Fetch the list of Cities in a country for which this
     * brand has actual authentications
     * @param countryId
     * @param brandId
     * @return a list of city id -> city name
     */
    List<LabelValueBean> getCitiesByCountryAndBrand(int countryId, int brandId);
}
