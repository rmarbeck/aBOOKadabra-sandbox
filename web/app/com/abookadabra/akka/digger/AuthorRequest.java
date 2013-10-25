package com.abookadabra.akka.digger;

public class AuthorRequest {
	private String author;

	private AuthorRequest(String author) {
		this.author = author;
	}

	public static AuthorRequest createAuthorRequest(String author) {
		return new AuthorRequest(author);
	}
	
	public SubAuthorRequest createSubAuthorRequest(int index) {
		return new SubAuthorRequest(this, index);
	}

	public String getAuthor() {
		return author;
	}

}
