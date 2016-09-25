package com.training.rajesh.stream;

import java.util.List;

public class AnyMatch {

	public static void main(String[] args) {
		List<Person> persons =  PersonListFactory.getPersons();
		 if(persons.stream().anyMatch(Person::isHeavyWeight ) ){
			 System.out.println("atlease one person is heavy weight");
		 }
			 
		 if(persons.stream().allMatch(Person::isHeavyWeight ) ){
			 System.out.println("all persons are heavy weight");
		 }	
		 if(persons.stream().noneMatch(p-> p.getWeight()>200) ){
			 System.out.println("No person is having weight greater than 200");
		 }

	}

}
