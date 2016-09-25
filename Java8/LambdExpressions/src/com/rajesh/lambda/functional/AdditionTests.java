package com.rajesh.lambda.functional;

import java.util.ArrayList;
import java.util.List;

public class AdditionTests {

	public static void main(String[] args) {
		
		 Addition add = (x,y)-> x + y;
		 
		 System.out.println(add.add(30, 20));
		 System.out.println(add((x,y)-> x + y));

	}
   public static int add(Addition add){
	   return add.add(10, 20);
	  

   }
}
