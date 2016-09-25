package com.rajesh.lambda.preliminary;

import java.util.ArrayList;
import java.util.List;

public class FilterCity7 {

	public static void main(String[] args) {
		List<City> cities = Cities.getCities();
		List<City> results = filterCities(cities,  (City city)-> city.getState().equals("MH")) ;
		System.out.println(results);
		List<City> results2 = filterCities(cities, (City city)-> city.getState().equals("GJ"));
		System.out.println(results2);
	 

	}

	private static List<City> filterCities(List<City> cities, CityPredicate predicate) {
		List<City> results = new ArrayList<>();

		for (City c : cities) {
			if (predicate.test(c)) {

				results.add(c);

			}
		}

		return results;
	}

}
