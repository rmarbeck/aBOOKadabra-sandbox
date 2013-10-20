package com.abookadabra.utils.amazon.api.models.answerelements;

public class ErrorInAnswer {
	private boolean containsError;
	private String code;
	private String message;

	private ErrorInAnswer() {
		this.containsError = false;
	}
	
	public ErrorInAnswer(String errorCode, String errorMessage) {
		this.containsError = true;
		this.code = errorCode;
		this.message = errorMessage;
	}
	
	public static ErrorInAnswer createNoError() {
		return new ErrorInAnswer();
	}
	
	public boolean containsError() {
		return (containsError);
	}
	
	public String getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
}
