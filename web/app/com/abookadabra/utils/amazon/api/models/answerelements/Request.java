package com.abookadabra.utils.amazon.api.models.answerelements;

public class Request {
	private boolean isValid;

	public Request() {
		this.isValid = false;;
	}
	
	public Request(boolean isValid) {
		this.isValid = isValid;
	}

	public static Request createInvalidRequest() {
		return new Request(false);
	}
	
	public boolean isItValid() {
		return (isValid);
	}
}
