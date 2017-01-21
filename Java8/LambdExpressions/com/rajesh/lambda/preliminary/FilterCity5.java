package com.rajesh.lambda.preliminary;

import java.util.ArrayList;
import java.util.List;

public class FilterCity5 {

	public static void main(String[] args) {
		List<City> cities = Cities.getCities();
		List<City> results = filterCities(cities, new CityMHStatePredicate());
		System.out.println(results);
		List<City> results2 = filterCities(cities,new CityPopulation30lakhPredicate());
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
