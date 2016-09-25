package com.training.rajesh.stream;

import java.util.List;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class WithStream {

	public static void main(String[] args) {
		List<Person> persons =  PersonListFactory.getPersons();
	 List<Person> heavyWeightPersons = 	persons.stream().
													filter(Person::isHeavyWeight ).
													sorted(comparing(Person::getAge)).					
													collect(toList()); 
		 
		
		
		/*List<Person> heavyWeightPersons = 	persons.stream().
		filter(  p  ->  { System.out.println(" filtering " + p); return p.isHeavyWeight(); } ).
		sorted(comparing(p-> { System.out.println(" sorting " +p);  return p.getAge(); })).					
	 	collect(toList());*/
		System.out.println(heavyWeightPersons);
		

	}

}
