package com.training.rajesh.stream;

import java.util.Arrays;
import java.util.List;

public class SkipFiltering {

	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(1, 2, 8, 3, 6, 10, 4);
		numbers.stream().filter(i -> i % 2 == 0).
		skip(3).
		forEach(System.out::println);
	}

}
