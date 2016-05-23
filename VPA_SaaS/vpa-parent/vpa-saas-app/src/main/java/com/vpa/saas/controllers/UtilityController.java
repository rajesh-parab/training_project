package com.vpa.saas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vpa.saas.services.geocoding.GeoLocation;
import com.vpa.saas.services.geocoding.ReverseGeoCoding;

@RestController
@RequestMapping("/utility")
public class UtilityController {

	@Autowired
	private ReverseGeoCoding reverseGeoCoding;

	@RequestMapping(value = "/country/isoalpha2", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void updateISO2AlphaCountryCode() {
		reverseGeoCoding.updateISO2alphaCountryCode();
	}

	@RequestMapping(value = "/geolocation/latlong/{lat}/{long}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public GeoLocation getGeoLocationByLatLong(@PathVariable("lat")  String latitude,@PathVariable("long") String longitude) {
	  return	reverseGeoCoding.getLocation(latitude, longitude);
	}
}
