package com.training.java8;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Supplier;

public class TestLambda2 {
	
	public void test(Supplier<String>  s){
		System.out.println(s.get());
		
	}
	 

	public static void main(String[] args) throws Exception {
		TestLambda2 obj = new TestLambda2();
		obj.test(()-> "this is test");
		obj.test(()-> { String data="" ;  
							try(BufferedReader bReader = new BufferedReader  (new FileReader("src/com/training/java8/data.txt"))){
								String tempdata=bReader.readLine();
								while(tempdata != null){
									data=data+"  "+tempdata;
									tempdata=bReader.readLine();
									
								}
							} catch (Exception e) {
								 
								e.printStackTrace();
							}
		              return data;
		             });
		}
	}

 
