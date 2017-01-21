package com.rajesh.lambda.functional.api;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.UnaryOperator;

import org.junit.Test;

import com.rajesh.lambda.api.developent.Country;

public class FunctionalAPI {
 
	@Test
	public void BiConsumer() {
		 
		 
		BiConsumer<String, Integer> b = (String str, Integer i) -> {
 
			System.out.println(str);
			System.out.println(i);

		};

		b.accept("Hello", 10);
	}

	@Test
	public void BiFunction() {
		BiFunction<Integer, Integer, Integer> b = (x, y) -> {

			System.out.println(x);
			System.out.println(y);
			return x + y;

		};

		b.apply(10, 20);
	}

	@Test
	public void BinaryOperator() {
		BinaryOperator<Integer> b = (x, y) -> {

			System.out.println(x);
			System.out.println(y);
			return x + y;

		};

		b.apply(10, 20);
	}

	@Test
	public void BiPredicate() {
		BiPredicate<String, String> b = (x, y) -> {

			System.out.println(x);
			System.out.println(y);
			return x.equalsIgnoreCase(y);

		};

		System.out.println(b.test("India", "India"));
		System.out.println(b.test("India", "USA"));
	}

	@Test
	public void BooleanSupplier() {
		BooleanSupplier b = () -> 10 > 20;

		System.out.println(b.getAsBoolean());

	}

	@Test
	public void Consumer() {
		Consumer<String> b = str -> System.out.println(str);

		b.accept("hello");

	}

	@Test
	public void DoubleBinaryOperator() {
		DoubleBinaryOperator d = (a, b) -> a + b;

		System.out.println(d.applyAsDouble(10.3, 20.4));
		// Int,Long

	}

	@Test
	public void DoubleConsumer() {
		DoubleConsumer d = (a) -> {
			System.out.println(a);
		};

		d.accept(10.3);
		// Int,Long
	}

	@Test
	public void DoubleFunction() {
		DoubleFunction<String> d = (a) -> " discount is " + a;

		System.out.println(d.apply(10.3));
		// Int,Long
	}

	@Test
	public void DoublePredicate() {
		DoublePredicate d = (a) -> a == 10.3;

		System.out.println(d.test(10.3));
		// Int,Long
	}

	@Test
	public void DoubleSupplier() {
		DoubleSupplier d = () -> 10.3;

		System.out.println(d.getAsDouble());
		// Int,Long
	}

	@Test
	public void DoubleToIntFunction() {
		DoubleToIntFunction d = (x) -> Double.MAX_EXPONENT;

		System.out.println(d.applyAsInt(10.3));
		// Int,Long
	}

	@Test
	public void DoubleToLongFunction() {
		DoubleToLongFunction d = (x) -> 10L;

		System.out.println(d.applyAsLong(10.3));
		// Int,Long
	}

	@Test
	public void DoubleUnaryOperator() {
		DoubleUnaryOperator d = (x) -> x / 10.3;

		System.out.println(d.applyAsDouble(10.3));
		// Int,Long
	}

	@Test
	public void Function() {
		Function<Integer, String> d = (x) -> "hello world";

		System.out.println(d.apply(10));

	}

	@Test
	public void ObjDoubleConsumer() {
		ObjDoubleConsumer<Country> d = (c, y) -> System.out.println(c.getPopulation() / y);

		d.accept(new Country("India", 20000L, "Asia"), 10.3);
		// Int,Long
	}

	@Test
	public void Predicate() {
		Predicate<Integer> d = (x) -> x < 10;

		System.out.println(d.test(10));

	}

	@Test
	public void Supplier() {
		Supplier<Integer> d = () -> 10;

		System.out.println(d.get());

	}

	@Test
	public void ToDoubleBiFunction() {
		ToDoubleBiFunction<Integer, String> d = (x, y) -> 10.3;

		System.out.println(d.applyAsDouble(11, "hi"));
		// int long
	}

	@Test
	public void ToDoubleFunction() {
		ToDoubleFunction<Integer> d = (x) -> 10.3;

		System.out.println(d.applyAsDouble(11));
		// int long
	}

	@Test
	public void UnaryOperator() {
		UnaryOperator<String> d = (x) -> x + " world";

		System.out.println(d.apply("hello"));

	}
}