package com.rajesh.lambda.scope;

public class AnanymousVSlambda2 {
	//Anonymous inner classes have support for this kind of variable binding
	//(also called variable capture):  an inner class has access to all final 
	//variables of its enclosing context.
  int i=0;
	  
	 public void ann(){
		  int j=0;
		  final int k=0;
		 new AnnClass(){
			 
			@Override
			public int testMe(int j) {
				int k;
				 i++;
				 j++;
				 k++;
				return 0;
			}};
		 
	 }
	 public void lambda(){
	     int j=0;
	     
	    
		 AnnClass	a=  (int m)-> { 
		 
			   i++;
		      i = j;
		return 0;
		}; 
	 } 
	public static void main(String[] args) {
		 		

	}
 
	 

}
