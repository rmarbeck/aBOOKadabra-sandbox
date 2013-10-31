package com.abookadabra.utils;

import com.abookadabra.utils.amazon.api.models.BrowseNodeAnswer;

public class BrowseNodeAnswerWrapper {
	BrowseNodeAnswer answer;
	String requestFullUrl;
	
	public BrowseNodeAnswerWrapper(BrowseNodeAnswer answer, String requestFullUrl) {
		this.answer = answer;
		this.requestFullUrl = requestFullUrl;
	}
	
	public BrowseNodeAnswer getAnswer() {
		return answer;
	}
	
	public String getRequestUrl() {
		return requestFullUrl;
	}
}
