package com.rajesh.lambda.scope;

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

public class LambdaScope {
	
	int i=5;
	int x;
	
	public void getDetails(){
		int i=3;
		System.out.println(i+ " instnace " +this.i);
	}
	
	public void getDetails2(){
	//	int i;
		int j=3;
	     
		IntFunction<Integer> r = (i)->{
			int x;
			int xyz=2;;
			//j=4;
			return i+j;
			};
		 
		r.apply(4);
		int xyz=5;
		
	}

	public static void main(String[] args) {
	 
		LambdaScope ls = new LambdaScope();
		ls.getDetails();
		 
		Supplier<String>supplier = String::new;//   ()-> { return new String("hello"); };  
		Function<String,Integer> f = String::length; // (s) -> { return s.length(); };
		
		System.out.println(f.apply("wefderg"));

		 
	}

}
