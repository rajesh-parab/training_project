package com.training.rajesh.stream.collectors;

import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;

import com.training.rajesh.stream.Countries;
import com.training.rajesh.stream.Country;
public class GroupBy {

	public static void main(String[] args) {
		
		List<Country> countries = Countries.getCountries();
		Map<String, List<Country>> counriesByContient = countries.stream()
				.collect(groupingBy(Country::getContinent));
		
	   for(String continent :    counriesByContient.keySet()){
		   System.out.println(continent);
		   System.out.println(counriesByContient.get(continent));
	   }

	}

}
