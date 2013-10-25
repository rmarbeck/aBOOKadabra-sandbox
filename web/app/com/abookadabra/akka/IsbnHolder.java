package com.abookadabra.akka;

public class IsbnHolder {

	private final String isbn;

	private IsbnHolder(String isbn) {
		this.isbn = isbn;
	}

	public static IsbnHolder fromIsbn(String isbn) {
		return new IsbnHolder(isbn);
	}

	public String getIsbn() {
		return isbn;
	}

}
