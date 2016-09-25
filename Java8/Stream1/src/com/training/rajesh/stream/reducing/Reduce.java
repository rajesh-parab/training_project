package com.training.rajesh.stream.reducing;

import static java.util.stream.Collectors.reducing;

import java.util.Arrays;
import java.util.List;

import com.training.rajesh.stream.Cities;
import com.training.rajesh.stream.City;
 
public class Reduce {

	public static void main(String[] args) {
		
		//explain flow
		List<Integer> numbers = Arrays.asList(2,4,6,10);
		int sum = numbers.stream().reduce(0, (a, b) -> a + b);
		System.out.println(sum);
		int sum2 = numbers.stream().reduce(0, Integer::sum);
		System.out.println(sum2);
		int max = numbers.stream().reduce(0, Integer::max);
		System.out.println(max);
		// with collect
		List<City> cities = Cities.getCities();
		long sum3 = cities.stream().collect(reducing(0L,City::getPopulation, Long::sum));
		System.out.println(sum3);
		
		
			
	}

}
