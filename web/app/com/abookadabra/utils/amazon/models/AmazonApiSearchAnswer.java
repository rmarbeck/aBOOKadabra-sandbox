package com.abookadabra.utils.amazon.models;

import java.util.ArrayList;

import com.abookadabra.utils.amazon.models.answerelements.Item;
import com.abookadabra.utils.amazon.models.answerelements.ItemSearchRequest;

public class AmazonApiSearchAnswer extends AmazonApiAnswer {
	private long totalResults;
	private long totalPages;

	public AmazonApiSearchAnswer() {
		super();
		items = new ArrayList<Item>();
	}
	
	protected void loadFrom(Object answerFromAmazonToParse) {
		try {
			super.loadFrom(answerFromAmazonToParse);
			loadResultsSize();
		} catch (Exception e) {
			fillAnswerFromEmptyResult();
		}
	}
	
	private void loadResultsSize() throws Exception {
		totalResults = parser.loadTotalResults();
		totalPages = parser.loadTotalPages();
	}
	
	public static AmazonApiSearchAnswer createInstanceFrom(Object answerFromAmazonToParse) {
		AmazonApiSearchAnswer newInstance = new AmazonApiSearchAnswer();
		newInstance.loadFrom(answerFromAmazonToParse);
		return newInstance;
	}

	public ItemSearchRequest getRequest() throws AnswerIsNotValidException {
		if (isItAValidAnswer())
			return (ItemSearchRequest) request;
		throw new AnswerIsNotValidException("Request is not a valid Search Request.");
	}

	public long getTotalResults() {
		return totalResults;
	}

	public long getTotalPages() {
		return totalPages;
	}
}
