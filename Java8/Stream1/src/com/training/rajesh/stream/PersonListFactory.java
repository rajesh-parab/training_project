package com.training.rajesh.stream;

import java.util.ArrayList;
import java.util.List;

public class PersonListFactory {

	public static List<Person> getPersons() {
		 List<Person> persons = new ArrayList<>();
		 
		 Person p1 = new Person(22,100);
		persons.add(p1 );
		Person p2 = new Person(44,67);
		persons.add(p2 );
		Person p3 = new Person(26,45);
		persons.add(p3 );
		Person p4 = new Person(12,34);
		persons.add(p4 );
		Person p5 = new Person(54,120);
		persons.add(p5 );
		Person p6 = new Person(34,160);
		persons.add(p6);
		Person p7 = new Person(22,56);
		persons.add(p7);
		Person p8 = new Person(44,55);
		persons.add(p8);
		return persons;
	}

}
