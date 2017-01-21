package com.rajesh.lambda.scope;

public class AnanymousVSlambda4 extends AnanymousVSlambda3{

 	 int ctr; 
	 public void ann(){
		  
		 new AnnClass (){
			  int j;
			@Override
			public int testMe(int mi) {
				 
				this.ctr=2;
				super.ctr=2;
				AnanymousVSlambda4.this.ctr=4;
				this.j=0;
				 super.i=4;
				return 0;
			}};
		 
	 }
	 public void lambda(){
		 
		 AnnClass	a=  (int m)-> { 
				this.ctr=2;
				super.ctr=2;
				 
				 super.i=4;
		return 0; }; 
	 } 
	public static void main(String[] args) {
		 

	}
 
	 

}
