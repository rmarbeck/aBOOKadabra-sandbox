package com.abookadabra.akka.digger;

import java.util.ArrayList;
import java.util.List;

import models.SimpleBook;

public class BooksResponse {
	private List<SimpleBook> books;

	public BooksResponse() {
		books = new ArrayList<SimpleBook>();
	}

	public List<SimpleBook> getBooks() {
		return books;
	}
	
	public void addBook(SimpleBook bookToAdd) {
		books.add(bookToAdd);
	}

}
