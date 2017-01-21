package com.rajesh.lambda.scope;

public class AnanymousVSlambda3 {

 	 public int i=0;
	 public static int j=0;
	 int x=0;
	  int y=0;
	 public void ann(){
		 int z=0;
		 int f=1;
		 new AnnClass(){
			 int j;
			 int s=0;
			@Override
			public int testMe(int i) {
				int x;
				int f=0;
                j++;
				y++;
				z++;
				f++;
				s++;
				// TODO Auto-generated method stub
				return 0;
			}};
		 
	 }
	 public void lambda(){
		 int z=0;
		 int s=5;
		 AnnClass	a=  (int i)-> { 
			 int x;  
			 int f=0;
			 j++; 
			 y++;
			 z++; 
			 f++; 
	    	s=3; 
		//int z; 
		return 0; }; 
	 } 
	public static void main(String[] args) {
		 
		

	}
 
	 
}
