package com.abookadabra.utils.amazon.api.models;

import com.abookadabra.utils.amazon.api.models.answerelements.SimilarityLookupRequest;

public class SimilarityLookupAnswer extends Answer {

	private SimilarityLookupAnswer() {
		initialise();
	}
	
	public static SimilarityLookupAnswer createInstanceFrom(Object answerFromAmazonToParse) {
		SimilarityLookupAnswer newInstance = new SimilarityLookupAnswer();
		newInstance.loadFrom(answerFromAmazonToParse);
		return newInstance;
	}

	public SimilarityLookupRequest getRequest() throws AnswerIsNotValidException {
		return (SimilarityLookupRequest) super.getRequest();
	}
}
