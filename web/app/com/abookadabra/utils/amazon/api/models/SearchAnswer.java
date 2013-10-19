package com.abookadabra.utils.amazon.api.models;

import java.util.ArrayList;

import com.abookadabra.utils.amazon.api.models.answerelements.Item;
import com.abookadabra.utils.amazon.api.models.answerelements.ItemSearchRequest;

public class SearchAnswer extends Answer {
	private long totalResults;
	private long totalPages;

	public SearchAnswer() {
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
	
	public static SearchAnswer createInstanceFrom(Object answerFromAmazonToParse) {
		SearchAnswer newInstance = new SearchAnswer();
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
