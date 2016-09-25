package com.training.rajesh.stream;

import java.util.List;
import java.util.Optional;

public class Find {

	public static void main(String[] args) {
		List<Person> persons =  PersonListFactory.getPersons();
		 Optional<Person>  person = persons.stream().parallel().filter(Person::isHeavyWeight ).findAny();
		 System.out.println(person);
			 
		 persons.stream().filter(Person::isHeavyWeight ).findAny().ifPresent(p-> 	 System.out.println(p));;
		 
		 Optional<Person>  person2 = persons.stream().filter(Person::isHeavyWeight ).findFirst();
		 System.out.println(person2);
	}

}
