package com.abookadabra.akka.digger;

public class SubAuthorRequest {
	private AuthorRequest authorRequest;
	private int index;

	protected SubAuthorRequest(AuthorRequest authorRequest, int index) {
		this.authorRequest = authorRequest;
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
	
	public AuthorRequest getAuthorRequest() {
		return authorRequest;
	}

}
