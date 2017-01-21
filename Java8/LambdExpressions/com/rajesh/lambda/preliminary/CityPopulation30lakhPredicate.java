package com.rajesh.lambda.preliminary;

public class CityPopulation30lakhPredicate  implements CityPredicate {

	@Override
	public   boolean test(City city) {
		// TODO Auto-generated method stub
		return (city.getPopulation()==3000000L);
	}

}
