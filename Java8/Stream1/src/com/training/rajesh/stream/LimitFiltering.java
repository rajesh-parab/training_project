package com.training.rajesh.stream;

import java.util.List;

public class LimitFiltering {

	public static void main(String[] args) {
		List<Person> persons = PersonListFactory.getPersons();
		persons.stream().filter(Person::isHeavyWeight).
		limit(2).
		forEach(System.out::println);

	}

}
