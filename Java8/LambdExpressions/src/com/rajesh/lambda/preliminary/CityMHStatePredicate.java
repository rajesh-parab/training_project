package com.rajesh.lambda.preliminary;

public class CityMHStatePredicate implements CityPredicate {

	@Override
	public boolean test(City city) {

		return city.getState().equals("MH");
	}

}
