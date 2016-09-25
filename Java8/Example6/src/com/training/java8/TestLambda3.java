package com.training.java8;

import java.util.function.Function;

public class TestLambda3 {

	public static void main(String[] args) {
		Function<Long, Long> square = x -> x * x;
		Function<Long, Long> addOne = x -> x + 1;

		// Compose functions from the two functions
		Function<Long, Long> squareAddOne = square.andThen(addOne);
		System.out.println("Square and then add one: " + squareAddOne.apply(5L));

		Function<Long, Long> chainedFunction = ((Function<Long, Long>) (x -> x * x)).andThen(x -> x + 1)
				.andThen(x -> x * x);
		System.out.println(chainedFunction.apply(3L));

	}

}