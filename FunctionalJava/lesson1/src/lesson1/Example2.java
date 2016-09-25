package lesson1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
 import  static java.util.Comparator.comparing;
import lesson1.Apple;

public class Example2 {

	ArrayList<String> names = new ArrayList<>();

	public static void main(String[] args) {

		List<Apple> inventory = new ArrayList<>();
		
		Apple a1 = new Apple(24,120);
		inventory.add(a1 );
		Apple a2 = new Apple(12,60);
		inventory.add(a2 );
		Apple a3 = new Apple(27,70);
		inventory.add(a3 );
		
		System.out.println(inventory);
		Collections.sort(inventory, new Comparator<Apple>() {
			public int compare(Apple x, Apple y) {
				return x.getWeight().compareTo(y.getWeight());
			}
		});
		System.out.println(inventory);
		
		inventory.sort(comparing(Apple::getWeight));

	}

}
