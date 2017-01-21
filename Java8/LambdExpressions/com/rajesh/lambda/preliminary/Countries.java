package com.rajesh.lambda.preliminary;

import java.util.ArrayList;
import java.util.List;

public class Countries {
	public static List<Country> getCountries(){
		
		List<Country> countries = new ArrayList<>();
		
		Country c1 = new Country("India", 10000000L);
		countries.add(c1 );
		Country c2 = new Country("Japan", 4000000L);
		countries.add(c2 );
		Country c3 = new Country("Germany" ,9000000L);
		countries.add(c3 );
		Country c4 = new Country("UK", 6000000L);
		countries.add(c4 );
		Country c5 = new Country("USA", 4000000L);
		countries.add(c5 );
		 
		return countries;
		
		
	}
}
