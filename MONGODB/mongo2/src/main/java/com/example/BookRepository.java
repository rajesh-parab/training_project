package com.example;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {

	public Book findByTitle(String title);

	public List<Book> findByType(String type);

	public List<Book> findByAuthor(String author);

}
