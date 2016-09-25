package com.training.rajesh.stream;

public class Person {
	
	int age;
	
	int weight;
	
	

	public Person(int age, int weight) {
		super();
		this.age = age;
		this.weight = weight;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public boolean isHeavyWeight() {
		 
		return (weight>=100);
	}
	public boolean isMiddleAge() {
		 
		return (age>=40 && age<=55);
	}

	@Override
	public String toString() {
		return "Person [age=" + age + ", weight=" + weight + "]";
	}

/*	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (age != other.age)
			return false;
		return true;
	}*/

	 
	

}
