package com.training.java8.stream.parallel;

import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.training.rajesh.stream.Countries;
import com.training.rajesh.stream.Country;

public class ParallelStreamExample {

	public static long measureSumPerf(Function<Long, Long> adder, long n) {
		 
	 
			long start = System.nanoTime();
			long sum = adder.apply(n);
			long duration = (System.nanoTime() - start) / 1_000_000;
			System.out.println("Result: " + sum);
			 
		 
		return duration;
	}
	
	public static long method1(long max){
		Long sum = Stream.iterate(1L, i -> i + 1).limit(max).parallel().reduce(0L, Long::sum);
		 
		return sum;
	}
	public static long method2(long max){
		Long sum2 = LongStream.rangeClosed(1L, max).parallel().reduce(0L, Long::sum);
		 
		return sum2;
	}
	public static long method3(long max){
		Accumulator acumulator = new Accumulator();
		LongStream.rangeClosed(1L, 100L).forEach(acumulator::add);
		 
		return acumulator.total;
	}
	public static void method4(){ 
		List<Country> countries = Countries.getCountries();
	Map<String, List<Country>> counriesByContient = countries.stream().peek(x-> System.out.println(x))
			.collect(groupingBy(Country::getContinent));
	}
	public static void main(String[] args) {
		
		System.out.println("method 1 "+ measureSumPerf(ParallelStreamExample::method1,100L) + " millisecond");
		System.out.println("method 2 "+measureSumPerf(ParallelStreamExample::method2,100L) + " millisecond");
		System.out.println("method 3 "+measureSumPerf(ParallelStreamExample::method3,100L) + " millisecond");
		ParallelStreamExample.method4();
	}

}
