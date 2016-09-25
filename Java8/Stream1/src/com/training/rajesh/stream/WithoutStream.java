package com.training.rajesh.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WithoutStream {

	public static void main(String[] args) {
		WithoutStream obj = new WithoutStream();
		
		 List<Person> persons = obj.getHeavyWeightPersonByAge();  
		  System.out.println(persons);

	}

	private List<Person> getHeavyWeightPersonByAge() {
		List<Person> persons =  PersonListFactory.getPersons();
		List<Person> heavyPersons = new ArrayList<>();
		for(Person p : persons){
			if(p.isHeavyWeight()){
				heavyPersons.add(p);
			}
		}
		Collections.sort(heavyPersons,new Comparator<Person>(){

			@Override
			public int compare(Person o1, Person o2) {
				 
				return Integer.compare(o1.getAge(),o2.getAge());
			}});
		
		
		
		return heavyPersons;
	}

}
