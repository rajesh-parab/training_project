package com.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class Mongo2Application implements CommandLineRunner {

	@Autowired
	private   BookRepository repository;
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Bean
	public static MongoExample getBean() {
		return new MongoExample();
	}

	public static void main(String[] args) {
		SpringApplication.run(Mongo2Application.class, args);
		 
		System.out.println("Collection deleted");

	}

	@Override
	public void run(String... arg0) throws Exception {
		repository.deleteAll();
		repository.save(new  Book("A Tale Of Two Cities", "Charles        Dickens","Novel", 10)); 
		//show dbs, show collections,db.mybooks.find()
	List<Book> books =	mongoTemplate.findAll(Book.class);
		System.out.println(books);
	}
}