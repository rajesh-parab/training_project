package com.rajesh.lambda.scope;

public class AnanymousVSlambda {

 
	public static void main(String[] args) {
		AnanymousVSlambda obj = new AnanymousVSlambda();
		 
		obj.annclass(new AnnClass(){

			@Override
			public int testMe(int i) {
				 
				return i+10;
			} });
		
		obj.lambda(i -> i+ 20);
		

	}
 
	private void annclass(AnnClass annClass) {
		 
		System.out.println(annClass.testMe(5));
	}
	private void lambda(AnnClass annClass) {
		 
		System.out.println(annClass.testMe(5));
	}

}
