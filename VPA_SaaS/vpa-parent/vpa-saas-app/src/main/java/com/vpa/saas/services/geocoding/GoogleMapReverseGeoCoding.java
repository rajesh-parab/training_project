/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   GoogleMapReverseGeoCoding.java
 ** Version     :   1.0
 ** Description :   This implementation use Google Map services to fetch address information  .
 **
 ** Author      :   Rajesh Parab
 ** Created Date :  Friday, 16 Oct 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 *
 **************************************************************************************************************/

package com.vpa.saas.services.geocoding;

import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderAddressComponent;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderResultType;
import com.google.code.geocoder.model.LatLng;
import com.vpa.core.mes.dao.CountryMasterDao;
import com.vpa.core.models.CountryMaster;

/**
 * This class should not call within transaction except utility method
 * updateISO2alphaCountryCode.
 * 
 * @author rp25984
 *
 */
@Service
@Transactional(value = "transactionManager", propagation = Propagation.NEVER)
public class GoogleMapReverseGeoCoding implements ReverseGeoCoding {

	private static final Logger logger = Logger
			.getLogger(GoogleMapReverseGeoCoding.class);

	@Autowired
	private CountryMasterDao countryMasterDao;

	/**
	 * @see ReverseGeoCoding#getLocation(String, String)
	 */
	@Override
	@Transactional(value = "transactionManager", propagation = Propagation.NEVER)
	public GeoLocation getLocation(String latitude, String longitude) {

		GeocoderRequest geocoderRequest;
		GeocodeResponse geocoderResponse;
		GeoLocation geoLocation = new GeoLocation();
		try {
			Geocoder geocoder = new Geocoder();

			geocoderRequest = new GeocoderRequestBuilder()
					.setLocation(new LatLng(latitude, longitude))
					.setLanguage("en").getGeocoderRequest();
			geocoderResponse = geocoder.geocode(geocoderRequest);

			List<GeocoderResult> results = geocoderResponse.getResults();
			List<GeocoderAddressComponent> firstResult = results.get(0)
					.getAddressComponents();
			setCountryDetails(firstResult, geoLocation);
			setCityName(firstResult, geoLocation);
		} catch (Exception e) {
			// fail safe allow code to continue in case of exception.
			logger.error("Error while calling Google geocoding API", e);
		}

		return geoLocation;
	}

	/**
	 * set country long and short ISO code name
	 * 
	 * @param results
	 * @param geoLocation
	 */
	private void setCountryDetails(
			final List<GeocoderAddressComponent> results,
			GeoLocation geoLocation) {

		final ListIterator<GeocoderAddressComponent> iterator = results
				.listIterator(results.size());

		while (iterator.hasPrevious()) {
			GeocoderAddressComponent address = iterator.previous();

			List<String> types = address.getTypes();
			if (types.contains(GeocoderResultType.COUNTRY.value())) {
				geoLocation.setIsoCountryCode(address.getShortName());
				geoLocation.setCountryName(address.getLongName());
				break;
			}
		}

	}

	/**
	 * Set city name. Assumptions are locality is city name, some countries does
	 * not have localities or some lat long does not fall over city. In that
	 * case City will be considered in following order
	 * ADMINISTRATIVE_AREA_LEVEL_3 ADMINISTRATIVE_AREA_LEVEL_2
	 * ADMINISTRATIVE_AREA_LEVEL_1
	 * 
	 * @param results
	 * @return
	 */
	private void setCityName(List<GeocoderAddressComponent> results,
			GeoLocation geoLocation) {

		String city = null;
		for (GeocoderAddressComponent result : results) {

			List<String> types = result.getTypes();

			if (types.contains(GeocoderResultType.LOCALITY.value())) {
				city = result.getShortName();

			} else if (types
					.contains(GeocoderResultType.ADMINISTRATIVE_AREA_LEVEL_3
							.value())) {
				city = result.getShortName();

			} else if (types
					.contains(GeocoderResultType.ADMINISTRATIVE_AREA_LEVEL_2
							.value())) {
				city = result.getShortName();

			} else if (types
					.contains(GeocoderResultType.ADMINISTRATIVE_AREA_LEVEL_1
							.value())) {
				city = result.getShortName();
			}
			if (city != null) {
				geoLocation.setCityName(result.getShortName());
				break;
			}

		}

	}

	/**
	 * This is Utility service only to update isocode in db. This method need in
	 * transaction
	 */
	@Override
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW)
	public void updateISO2alphaCountryCode() {

		GeocoderRequest geocoderRequest;
		GeocodeResponse geocoderResponse;

		Geocoder geocoder = new Geocoder();

		List<CountryMaster> countries = countryMasterDao.findAll();

		for (CountryMaster country : countries) {
			String name = "";
			try {
				GeocoderRequestBuilder rqBuilder = new GeocoderRequestBuilder();
				name = country.getName();
				if (StringUtils.hasText(name)) {
					name = name.trim();
				} else {
					continue;
				}

				geocoderRequest = rqBuilder.setAddress(name)
						.getGeocoderRequest();

				geocoderResponse = geocoder.geocode(geocoderRequest);
				Thread.sleep(3000L);

				String shortName = geocoderResponse.getResults().get(0)
						.getAddressComponents().get(0).getShortName();
				if (StringUtils.hasText(shortName)) {
					shortName = shortName.trim();
					if (shortName.length() < 3) {
						country.setIsoCode(shortName);
						countryMasterDao.saveAndFlush(country);
					}

				}

			} catch (Exception e) {
				logger.error(" error while saving country", e);
			}
		}

	}

}
