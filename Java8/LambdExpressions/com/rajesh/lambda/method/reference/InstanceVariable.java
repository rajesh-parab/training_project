package com.rajesh.lambda.method.reference;

import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;

public class InstanceVariable {
   private	String name;
   private int i=0;

   
	public static void example1() {

		Supplier<String> s =    String::new;
		System.out.println(s.get().toString());
		
		Function<String, String> f =  String::new;
		System.out.println(f.apply("hi").toString());
	}

	
	public static void main(String[] args) {
		InstanceVariable v = new InstanceVariable();
		 Printer p1 = v.createLambda();
		 p1.print("hi");
		 Printer p2 = v.createLambda();
		 p2.print("bye");
		 p1.print("hi");
		 p2.print("bye");

	}
	 public   Printer createLambda() {    
		 Printer printer = msg -> {                 
			 // Accesses instance and local variables     
			    name = "rajesh"+i++;          
			 System.out.println(msg + ": name = " + name);  
			 }; 
	 
         return printer;       
         } 

}
