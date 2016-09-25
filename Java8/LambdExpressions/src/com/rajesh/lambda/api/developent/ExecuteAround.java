package com.rajesh.lambda.api.developent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteAround {
	
	public static String processFile() throws FileNotFoundException, IOException {
		
		try(BufferedReader br = new BufferedReader(new FileReader("src/com/java8/training/data.txt"))){
			return br.readLine();
		}
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		 
		System.out.println(processFile());
	}

}
