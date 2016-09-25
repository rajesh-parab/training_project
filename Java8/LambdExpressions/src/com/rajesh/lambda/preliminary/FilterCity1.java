package com.rajesh.lambda.preliminary;

import java.util.ArrayList;
import java.util.List;

public class FilterCity1 {

	public static void main(String[] args) {
		List<City> cities = Cities.getCities();
		 List<City> results = filterCitiesInMaharashtra(cities);
		 System.out.println(results);
	}

	private static List<City> filterCitiesInMaharashtra(List<City> cities) {
		List<City> results =  new ArrayList<>();
		 
		 for(City c : cities){
			 if(c.getState().equals("MH")){
				 results.add(c);
			 }
		 }
		return results;
	}

}
