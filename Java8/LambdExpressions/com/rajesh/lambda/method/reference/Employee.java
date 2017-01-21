package com.rajesh.lambda.method.reference;

import java.util.function.Function;
import java.util.function.Supplier;

public class Employee implements Person {
	
	public Employee(){
		
	}
	
	public Employee(String s){
		
	}
	
	@Override
	public String getName(){
		return "Rajesh";
	}
	
	public void test(){
		  // Uses Employee.getName method           
				Supplier<String> s1 = this::getName; 
				System.out.println(s1.get());
				// Uses Person.getName method         
				Supplier<String> s2 = Person.super::getName;
			
				System.out.println(s2.get());
				
			 
	}
	

	public static void main(String[] args) {
           Employee e = new Employee();
           e.test();
         
           Supplier<Employee> e2 = Employee::new;
           e2.get().test();
           
           Function<String,Employee> e3 = Employee::new;
           e3.apply("eee").test();
           
	}
	
static	void xyz(Supplier<Employee> e2){
		
	}

}
