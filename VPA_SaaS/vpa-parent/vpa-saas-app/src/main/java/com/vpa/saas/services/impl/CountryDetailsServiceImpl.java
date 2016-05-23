package com.vpa.saas.services.impl;

import com.vpa.core.mes.dao.CityDao;
import com.vpa.core.models.LabelValueBean;
import com.vpa.saas.services.CountryDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryDetailsServiceImpl implements CountryDetailsService {

    @Autowired
    private CityDao cityDao;

    public List<LabelValueBean> getCitiesByCountryAndBrand(int countryId, int brandId){

        List<LabelValueBean> mapped = new ArrayList<>();
        for(Object[] row : cityDao.getCitiesByCountryAndBrand(countryId, brandId)){
            mapped.add(new LabelValueBean(((Integer) row[0]).intValue(), ((String) row[1])));
        }

        return mapped;
    }
}
