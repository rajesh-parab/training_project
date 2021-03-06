package com.example;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "mybooks") 
public class Book {
	@Id
	private String id;
	private String title;
	private String author;
	private int price;
	private String type;

	@Override
	public String toString() {
		return "Book{" + "id=" + id + ", title=" + title + ", author=" + author + ", price=" + price + "}";
	}

	public Book(String title, String author, String type, int price) {
		this.title = title;
		this.author = author;
		this.price = price;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
