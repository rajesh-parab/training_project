package com.rajesh.lambda.scope;

import java.util.ArrayList;

import com.rajesh.lambda.api.developent.ListFactory;

public class GenricNotsupported {

	public static void main(String[] args) {
		ListFactory a = ()->   new ArrayList<String>(); 
	}

}
