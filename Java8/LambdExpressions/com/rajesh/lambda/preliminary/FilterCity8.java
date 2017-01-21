package com.rajesh.lambda.preliminary;

import java.util.ArrayList;
import java.util.List;

public class FilterCity8 {

	public static void main(String[] args) {
		List<City> cities = Cities.getCities();
		List<City> results = filter (cities,  (City city)-> city.getState().equals("MH")) ;
		System.out.println(results);
		List<City> results2 = filter (cities, (City city)-> city.getState().equals("GJ"));
		System.out.println(results2);
	 
		List<Country> countries = Countries.getCountries();
		List<Country> results3 = filter (countries, (Country country)-> country.getName().equals("India"));
		System.out.println(results3);
		
		List<Country> results4 = filter (countries, (Country country)-> country.getName().startsWith("U"));
		System.out.println(results4);
	}

	private static <T> List<T> filter (List<T> items, MyPreidicate<T> predicate) {
		List<T> results = new ArrayList<>();

		for (T c : items) {
			if (predicate.test(c)) {

				results.add(c);

			}
		}

		return results;
	}

}