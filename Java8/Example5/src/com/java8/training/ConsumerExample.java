package com.java8.training;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerExample {
	static List<Integer> list=Arrays.asList(1,3,4,5);
	static List<Integer> list1=Arrays.asList(1,3,4,5);
	public static   void forEach(Consumer<Integer> consumer){
		
		for(Integer item : list){
			consumer.accept(item);
		}
	}

	public static void main(String[] args) {
 
		 
		forEach((Integer i)-> System.out.println(i));
	}

}
