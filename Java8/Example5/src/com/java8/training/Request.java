package com.java8.training;

public class Request {

	public static void main(String[] args) {

		Person p = new Person();
		method1(p);
	 	method2(p);
		method3(p);

	}

	public static void method1(Person p) {
	 
		p.setName("rajesh");
		System.out.println(p.getName());
	}

	public static void method2(Person p) {
		p.setName("parab");
		System.out.println(p.getName());
	}

	public static void method3(Person p) {

		System.out.println(p.getName());
	}
}
