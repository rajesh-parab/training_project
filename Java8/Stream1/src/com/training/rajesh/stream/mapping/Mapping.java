package com.training.rajesh.stream.mapping;

import java.util.List;

import com.training.rajesh.stream.Cities;
import com.training.rajesh.stream.City;
import static java.util.stream.Collectors.toList;

public class Mapping {

	public static void main(String[] args) {
		List<City> cities = Cities.getCities();
				
		List<String> cityNames	= cities.stream() .map(City::getName) .collect(toList());
		System.out.println(cityNames);
		List<Integer> lengths = cityNames.stream() .map(String::length) .collect(toList());
		System.out.println(lengths);
		List<Integer> lengths2 = cities.stream() .map(City::getName) .map(String::length) .collect(toList());
		System.out.println(lengths2);

	}

}
