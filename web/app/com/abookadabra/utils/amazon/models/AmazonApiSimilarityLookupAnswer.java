package com.abookadabra.utils.amazon.models;

import java.util.ArrayList;

import com.abookadabra.utils.amazon.models.answerelements.Item;
import com.abookadabra.utils.amazon.models.answerelements.SimilarityLookupRequest;

public class AmazonApiSimilarityLookupAnswer extends AmazonApiAnswer {

	private AmazonApiSimilarityLookupAnswer() {
		super();
		items = new ArrayList<Item>();
	}
	
	public static AmazonApiSimilarityLookupAnswer createInstanceFrom(Object answerFromAmazonToParse) {
		AmazonApiSimilarityLookupAnswer newInstance = new AmazonApiSimilarityLookupAnswer();
		newInstance.loadFrom(answerFromAmazonToParse);
		return newInstance;
	}

	public SimilarityLookupRequest getRequest() throws AnswerIsNotValidException {
		if (isItAValidAnswer())
			return (SimilarityLookupRequest) request;
		throw new AnswerIsNotValidException("Request is not a valid SimilarityLookup Request.");
	}
}
