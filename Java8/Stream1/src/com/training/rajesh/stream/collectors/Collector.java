package com.training.rajesh.stream.collectors;

 
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.summarizingLong;
import static java.util.stream.Collectors.summingLong;

import java.math.BigDecimal;
import java.util.List;
import java.util.LongSummaryStatistics;

import com.training.rajesh.stream.Countries;
import com.training.rajesh.stream.Country;
public class Collector {

	public static void main(String[] args) {
		BigDecimal v= new BigDecimal(1);
		BigDecimal j=new BigDecimal(2);
		 
		for(int i=1;i<64;i++){
			 v=v.add(j);
			System.out.println( v);
			j=j.multiply(BigDecimal.valueOf(2));
			
		}
		BigDecimal k=	BigDecimal.valueOf(1024);
		BigDecimal a=v.divide(k).divide(k).divide(k).divide(k);
		
		System.out.println( a.toBigInteger() + " GB");
		List<Country> countries = Countries.getCountries();
		long count = countries.stream().collect(counting());
		System.out.println(count);
		long sum = countries.stream().collect(summingLong(Country::getPopulation));
		System.out.println(sum);
		LongSummaryStatistics  sumstat = countries.stream().collect(summarizingLong (Country::getPopulation));
		System.out.println(sumstat);
	}

}
