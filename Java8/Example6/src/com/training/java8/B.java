package com.training.java8;

public class B extends A {

	 

	public static void main(String[] args) {

		A ir = new B();
		B cr = new B();
	//	ir.foo(); // error
	//	cr.foo(); // fine
		A.foo(); 
		B.foo();

	}

}
