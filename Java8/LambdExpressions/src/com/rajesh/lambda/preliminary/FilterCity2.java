package com.rajesh.lambda.preliminary;

import java.util.ArrayList;
import java.util.List;

public class FilterCity2 {

	public static void main(String[] args) {
		 List<City> cities = Cities.getCities();
		 List<City> results = filterCitiesByState(cities,"MH");
		 System.out.println(results);
		 List<City> results2 = filterCitiesByState(cities,"GJ");
		 System.out.println(results2);
	}

	private static List<City> filterCitiesByState(List<City> cities,String state) {
		List<City> results =  new ArrayList<>();
	
		 for(City c : cities){
			 if(c.getState().equals(state)){
				 results.add(c);
			 }
		 }
		return results;
	}

}
