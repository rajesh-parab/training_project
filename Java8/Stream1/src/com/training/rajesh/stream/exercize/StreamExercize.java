package com.training.rajesh.stream.exercize;

import static org.junit.Assert.*;

import org.junit.Test;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Collectors.joining;
import java.util.List;

import com.training.rajesh.stream.Countries;
import com.training.rajesh.stream.Country;
public class StreamExercize {

	@Test
	public void sortCountryByName() {
		 
		List<Country> countries = Countries.getCountries();
		 countries.stream()
				.sorted(comparing(Country::getName))
				.forEach(System.out::println);; 
		 
	}
	@Test
	public void sortCountryByNameGroupByContient() {
		 
		List<Country> countries = Countries.getCountries();
		 countries.stream()
		 		.sorted(comparing(Country::getName))
		 		.sorted(comparing(Country::getContinent))
				.forEach(System.out::println);; 
		 
	}
	
	@Test
	public void allCountriesBelowPopulation5milllionAndSortByName() {
		 
		List<Country> countries = Countries.getCountries();
		List<Country> answer1 =countries.stream().
				filter(c-> c.getPopulation() <=5000000L).
				sorted(comparing(Country::getName)).
				collect(toList()); 
		System.out.println(answer1);
	}

	@Test
	public void uniqueContinentWhereCountryWithPopulationBelow5Million() {
		 
		List<Country> countries = Countries.getCountries();
		 countries.stream()
				.filter(c-> c.getPopulation() <=5000000L)
				.map(Country::getContinent)
				.distinct() //.collect(toSet())
				.forEach(System.out::println);; 
		 
	}
	@Test
	public void allCountriesFromAsiaAndSortByName() {
		 
		List<Country> countries = Countries.getCountries();
		 countries.stream()
				.filter(c-> c.getContinent().equalsIgnoreCase("ASIA"))
				.sorted(comparing(Country::getName))
				.forEach(System.out::println);; 
		 
	}
	@Test
	public void isThereAnyCountryWithPoulation1Billion() {
		 
		List<Country> countries = Countries.getCountries();
		if(countries.stream()
				 .anyMatch(c-> c.getPopulation().equals(1000000000L))){
			System.out.println(" There is a country with poulation 1 billion");
			
		}
				 
		 
	}
	@Test
	public void printNameOfTheCountryWithPopulation1Billion() {
		 
		List<Country> countries = Countries.getCountries();
		String country= countries.stream()
		 		 .filter(c-> c.getPopulation().equals(1000000000L))
				 .findAny().get().getName();
		System.out.println(country);
				 
		 
	}
	@Test
	public void printHigestAndLowestPopulation() {
		 
		List<Country> countries = Countries.getCountries();
				Long max=	countries.stream()
				   .map(Country::getPopulation)
		 		  .reduce(Long::max).get();
				 
		System.out.println(max);
		 
		Long min=	countries.stream()
		   .map(Country::getPopulation)
 		  .reduce(Long::max).get();
		 
System.out.println(min);
				 
		 
	}
	@Test
	public void ttt() {
		 
		List<Country> countries = Countries.getCountries();
				Long max=	countries.stream()
				   .mapToLong(Country::getPopulation)
		 		  .max().getAsLong();
				 
		System.out.println(max);
	 
				 
		 
	}
	@Test
	public void printAllCountryNameSeperatedByComma() {
		 
		List<Country> countries = Countries.getCountries();
		String str=	 countries.stream()
				   .map(Country::getName)
		 		   .sorted()
		 		   .collect(joining(","));
		System.out.println(str);
		 			 
		 
	}
	
}
