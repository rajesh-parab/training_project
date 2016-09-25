package com.training.java8.feature;

public interface B extends A {
	default  void sayHello() {
		 System.out.println(" B hello");
	 }
}
