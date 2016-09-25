package com.rajesh.lambda.preliminary;

import java.util.ArrayList;
import java.util.List;

public class Cities {
	
	public static List<City> getCities(){
		
		List<City> cities = new ArrayList<>();
		
		City c1 = new City("Mumbai","MH",10000000L);
		cities.add(c1 );
		City c2 = new City("Pune","MH",4000000L);
		cities.add(c2 );
		City c3 = new City("Kalkota","WB",9000000L);
		cities.add(c3 );
		City c4 = new City("Chennai","TN",6000000L);
		cities.add(c4 );
		City c5 = new City("Ahamdabad","GJ",4000000L);
		cities.add(c5 );
		City c6 = new City("Vadodara","GJ",3000000L);
		cities.add(c6 );
		return cities;
		
		
	}

}
