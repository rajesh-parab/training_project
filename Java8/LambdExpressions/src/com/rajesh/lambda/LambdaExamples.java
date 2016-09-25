package com.rajesh.lambda;

import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.Supplier;

import org.junit.Test;

public class LambdaExamples {

	@Test
	public void example1() {

		Supplier<Integer> s = () -> 42;
		System.out.println(s.get());
	}

	@Test
	public void example2() {

		Function<Integer, Integer> f = x -> x + 1;

		System.out.println(f.apply(4));
	}

	@Test
	public void example3() {

		IntBinaryOperator f = (int x, int y) -> x + y;

		System.out.println(f.applyAsInt(4, 5));
	}
	
	@Test
	public void example4() {

		VarArgConsumer f = (String fmt, Object... args) -> String.format(fmt,args);

		System.out.println(f.apply("%s %d %s","I have", 5,"Ruppes"));
	}

}
