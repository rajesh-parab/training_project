package com.training.rajesh.stream.reducing;

import java.util.Arrays;
import java.util.stream.Stream;

public class ReduceVsCollect {

	public static void main(String[] args) {
		
		String [] arr = new String[]{"I","Told","You","So"};
		Stream<String>strStream = Arrays.stream(arr);
		int sum=strStream.mapToInt(s -> s.length()).reduce(0,(l1,l2)-> l1+l2);
		System.out.println(sum);
		Stream<String>strStream2 = Arrays.stream(arr);
		int sum2=strStream2.parallel().mapToInt(s -> s.length()).reduce(0,(l1,l2)-> l1+l2);
		System.out.println(sum2);
		 
		// CHANGE INITIAL VALUE TO OTHER THAN 0 WILL GIVE WRONG RESULT OF PARRALEL STREAM
		// BECAUSE PARRALLED STREAM INITAL VALUE WILL BE ADDED N TIMES OF NUMBER OF THREAD OR FORKS
	}

}
