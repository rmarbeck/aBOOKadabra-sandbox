package com.abookadabra.utils.amazon.api.models;

import java.util.ArrayList;

import com.abookadabra.utils.amazon.api.models.answerelements.Item;
import com.abookadabra.utils.amazon.api.models.answerelements.SimilarityLookupRequest;

public class SimilarityLookupAnswer extends Answer {

	private SimilarityLookupAnswer() {
		super();
		items = new ArrayList<Item>();
	}
	
	public static SimilarityLookupAnswer createInstanceFrom(Object answerFromAmazonToParse) {
		SimilarityLookupAnswer newInstance = new SimilarityLookupAnswer();
		newInstance.loadFrom(answerFromAmazonToParse);
		return newInstance;
	}

	public SimilarityLookupRequest getRequest() throws AnswerIsNotValidException {
		if (isItAValidAnswer())
			return (SimilarityLookupRequest) request;
		throw new AnswerIsNotValidException("Request is not a valid SimilarityLookup Request.");
	}
}
