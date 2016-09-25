package com.java8.training;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class FunctionTest {
	public static <T,R> List<R> map(List <T> list,Function<T,R> function){
		List<R> result = new ArrayList<>();
		for(T item : list){
			result.add(function.apply(item));
		}
		return result;
	}

	public static void main(String[] args) {
		List<String> list=Arrays.asList("zensar","technlolgis","ltd");
		 
		List l = map(list,(String s)-> s.length());
		System.out.println(l);
		
	String s = "hello";
	BiFunction <String,Integer,String>f = ( s1,i)->s1.substring(i);
	System.out.println(f.apply(s, 2));
	BiFunction <String,Integer,String>f2 = String::substring;
	System.out.println(f2.apply(s, 2));
	
	}

}
