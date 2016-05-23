package com.vpa.saas.services.geocoding;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.vpa.core.mes.dao.CountryMasterDao;
import com.vpa.saas.services.AbstractVPASaaSTest;

public class GoogleMapReverseGeoCodingTest extends AbstractVPASaaSTest {

	@Autowired
	CountryMasterDao countryMasterDao;
	
	@Test
	public void countryIsoCode() throws IOException {

		GoogleMapReverseGeoCoding googleMapReverseGeoCoding = new GoogleMapReverseGeoCoding();
		GeoLocation geoLocation = googleMapReverseGeoCoding.getLocation("20.527537",
				"-103.464706");
		assertEquals("MX", geoLocation.getIsoCountryCode());
		assertEquals("Lomas de San Agustín", geoLocation.getCityName());
		geoLocation = googleMapReverseGeoCoding.getLocation("20",
				"19");
		assertEquals("TD", geoLocation.getIsoCountryCode());
		assertEquals("Borkou", geoLocation.getCityName());
		geoLocation = googleMapReverseGeoCoding.getLocation("1",
				"1");
		assertDefaultLocation(geoLocation);
		geoLocation = googleMapReverseGeoCoding.getLocation("17.0746557",
				"-61.8175207");
		assertEquals("AG", geoLocation.getIsoCountryCode());
		assertEquals("Buckleys", geoLocation.getCityName());
		
	}
	
	@Test
	public void invalidLatLong() throws IOException {

		GoogleMapReverseGeoCoding googleMapReverseGeoCoding = new GoogleMapReverseGeoCoding();
		GeoLocation geoLocation = googleMapReverseGeoCoding.getLocation(null,
				"-103.464706");
		assertDefaultLocation(geoLocation);
		geoLocation = googleMapReverseGeoCoding.getLocation("20",
				null);
		assertDefaultLocation(geoLocation);
		geoLocation = googleMapReverseGeoCoding.getLocation(null,
				null);
		assertDefaultLocation(geoLocation);
		geoLocation = googleMapReverseGeoCoding.getLocation("20",
				"");
		assertDefaultLocation(geoLocation);
		geoLocation = googleMapReverseGeoCoding.getLocation("",
				"20");
		assertDefaultLocation(geoLocation);
		geoLocation = googleMapReverseGeoCoding.getLocation("",
				"");
		assertDefaultLocation(geoLocation);
		geoLocation = googleMapReverseGeoCoding.getLocation("20",
				"abcd");
		assertDefaultLocation(geoLocation);
		
		
	}

	private void assertDefaultLocation(GeoLocation geoLocation) {
		assertEquals(GeoLocation.DEFAULT_LOCATION, geoLocation.getIsoCountryCode());
		assertEquals(GeoLocation.DEFAULT_LOCATION, geoLocation.getCityName());
	}
	@Ignore
	@Test
	public void countryInDb() throws IOException, InterruptedException {
		GeocoderRequest geocoderRequest;
		GeocodeResponse geocoderResponse;
	
		Geocoder geocoder = new Geocoder();
		  
   List<String> countriesNotMached= new ArrayList<>();
		List<String> country=super.getAllCountries();
		System.out.println(country.size());
		Path path=Paths.get("./country.txt");
		Integer i=0;
		byte[] bytes = "\r\n".getBytes();
		for(String name : country){
			GeocoderRequestBuilder rqBuilder=new GeocoderRequestBuilder();
		geocoderRequest =rqBuilder.setAddress(name.trim()).getGeocoderRequest();

		geocoderResponse = geocoder.geocode(geocoderRequest);
		Thread.sleep(3000L);
		System.out.println(name);
		
		
		try{
			i++;
			String count=i.toString();
			Files.write(path,bytes,StandardOpenOption.APPEND);
			Files.write(path,"-------------------------------".getBytes(),StandardOpenOption.APPEND);
			 	 Files.write(path, count.getBytes(),StandardOpenOption.APPEND);
			 	Files.write(path,bytes,StandardOpenOption.APPEND);
			 Files.write(path, name.getBytes(),StandardOpenOption.APPEND);
			 Files.write(path,bytes,StandardOpenOption.APPEND);
				
			String shortName = geocoderResponse.getResults().get(0).getAddressComponents().get(0).getShortName();
		 
		System.out.println(shortName);
		 name = "Country = " + name;
		 shortName = "Code = " + shortName;
		;
		 Files.write(path, shortName.getBytes(),StandardOpenOption.APPEND);
		 Files.write(path,bytes,StandardOpenOption.APPEND);
		}catch(Exception e){
			countriesNotMached.add(name);
		}
		System.out.println("-------------------------------------------");
		}
		System.out.println("====================================="+countriesNotMached.size());
		for(String name : countriesNotMached) {
		System.out.println(name);
		} 
		geocoderRequest = new GeocoderRequestBuilder().setAddress("Micronesia").getGeocoderRequest();

		geocoderResponse = geocoder.geocode(geocoderRequest);
	 
		 
	}
	
}
