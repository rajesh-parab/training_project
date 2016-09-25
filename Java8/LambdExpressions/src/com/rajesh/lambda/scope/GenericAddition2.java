package com.rajesh.lambda.scope;

public class GenericAddition2 {

	public static void main(String[] args) {
		//A lambda expression cannot declare type parameters, and therefore, it cannot have a     
				// target type whose abstract method is generic
		//GenericAdd2  add = (Integer x,Integer y)-> x + y;
		//add.print(50, 40);
	//	 genericAdd((Integer x, Integer y)-> x + y);
	//	System.out.println(genericAddString((x,y)-> x + y));

	}
	 public static void genericAdd(GenericAdd2  add){
		 
		       add.print(10,20);
	   }
	 public static void genericAddString(GenericAdd2 add){
		     add.print("Hello", " world");
	   }
}
