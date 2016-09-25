package com.training.java8;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TestLambda {
	Comparator cmp = null;   
	public void reverse_sort(Comparator arg) {
		
		cmp = (Object lhs, Object rhs)  -> {    
			  cmp = arg;        
			// fine; no name collision 
			return cmp.compare(rhs,lhs);   }; 
			  
 } 
	 

	public static void main(String[] args) {
		
		String a= "hello";
	
		 /*Consumer <String>a2 = b-> {
				Function <String,Integer>f2=   "ds"::length;
			 System.out.println(""+ f2.apply(b)); };
		a2.accept(a);*/
		 
		Supplier<String> e1 = () -> "hi";
		Supplier<String> e2 = () -> {
			return "hi";
		};
		Function<Integer, String> e3 = (Integer i) -> {
			return "hi" + i;
		};
		Function<String, String> e4 = (String s) -> "iorn man";
		Command c = () -> {
		};
		Command c2 = () -> {
			System.out.println(" my command interface");
		};
		c2.test();
	}

}
