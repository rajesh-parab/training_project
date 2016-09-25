package com.training.rajesh.stream;

import java.util.Arrays;
import java.util.List;

public class DistinctFiltering {

	public static void main(String[] args) {

		List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
		numbers.stream().filter(i -> i % 2 == 0).distinct().forEach(System.out::println);

		/*
		 * List<Person> persons = PersonListFactory.getPersons();
		 * persons.stream(). filter(Person::isMiddleAge). distinct().
		 * forEach(System.out::println);
		 */
		// System.out.println(middleAgePersons);
	}

}
