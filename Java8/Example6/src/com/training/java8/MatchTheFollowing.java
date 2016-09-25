package com.training.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;

public class MatchTheFollowing {

	public static void main(String[] args) {
		Predicate<List<String>> a = 	(List<String> list) -> list.isEmpty();
		System.out.println(a.test(new ArrayList<String>()));
		//****************
		Supplier<Student> b = () -> new Student(10);
		System.out.println(b.get().getAge());
		//****************
		 Function<String, Integer> c = (String s) -> s.length();
		 ToIntFunction<String> d = (String s) -> s.length();
		 System.out.println(c.apply("hello"));

		 System.out.println(d.applyAsInt("zensar"));
		//****************
		 
		 IntBinaryOperator e = (int i, int j) -> i * j;
		 System.out.println(e.applyAsInt(3, 7));
		//****************
		 ToIntBiFunction< Student,Student > f = (Student a1, Student a2) -> a1.getAge().compareTo (a2. getAge ());
		 




	}

	

}
