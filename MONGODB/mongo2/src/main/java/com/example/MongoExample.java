package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public   class MongoExample {

	@Autowired
	private BookRepository repository;
	
	
	
	public void setRepository(BookRepository repository) {
		this.repository = repository;
	}



	public void delete(){
		repository.deleteAll();
	}
	
}
