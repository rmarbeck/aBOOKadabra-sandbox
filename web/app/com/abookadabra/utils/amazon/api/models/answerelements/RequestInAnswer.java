package com.abookadabra.utils.amazon.api.models.answerelements;

public class RequestInAnswer {
	private boolean isValid;

	public RequestInAnswer() {
		this.isValid = false;;
	}
	
	public RequestInAnswer(boolean isValid) {
		this.isValid = isValid;
	}

	public static RequestInAnswer createInvalidRequest() {
		return new RequestInAnswer(false);
	}
	
	public boolean isItValid() {
		return (isValid);
	}
}
