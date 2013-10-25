package com.abookadabra.akka.requestbuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import models.SimpleBook;

public class MissionResult {
	private final Map<Integer, List<SimpleBook>> books;
	private final Map<Integer, String> requests;

	protected MissionResult() {
		books = new TreeMap<Integer, List<SimpleBook>>();
		requests = new TreeMap<Integer, String>();
	}

	public Map<Integer, List<SimpleBook>> getBooks() {
		return books;
	}
	
	public Map<Integer, String> getRequests() {
		return requests;
	}
	
	protected void addBook(Integer key, SimpleBook book) {
		if (books.get(key) == null)
			books.put(key, new ArrayList<SimpleBook>());
		books.get(key).add(book);
	}
	
	protected void putRequest(Integer key, String request) {
		requests.put(key, request);
	}
}
