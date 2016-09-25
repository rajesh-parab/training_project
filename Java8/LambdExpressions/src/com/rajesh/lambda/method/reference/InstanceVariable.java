package com.rajesh.lambda.method.reference;

public class InstanceVariable {
   private	String name;
   private int i=0;

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
