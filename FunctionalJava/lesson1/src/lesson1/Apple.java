package lesson1;

public class Apple {
	
	@Override
	public String toString() {
		return "Apple [weight=" + weight + "]";
	}
	Integer weight;
	Integer price;
	public Apple(Integer weight, Integer price) {
		super();
		this.weight = weight;
		this.price = price;
	}
	public Integer getWeight() {
		return weight;
	}
	public Integer getPrice() {
		return price;
	}
	 
	
	

}
