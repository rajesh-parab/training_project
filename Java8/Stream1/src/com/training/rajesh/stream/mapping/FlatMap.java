package com.training.rajesh.stream.mapping;

import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;
public class FlatMap {

	public static void main(String[] args) {
		List<String> words = Arrays.asList("This","is","Test");
		
		List<String> characters	= words.stream() .map(c -> c.split("")).flatMap(Arrays::stream).distinct().collect(toList());
		System.out.println(characters);
	}

}
