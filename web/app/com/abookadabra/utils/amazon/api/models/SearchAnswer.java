package com.abookadabra.utils.amazon.api.models;

import com.abookadabra.utils.amazon.api.models.Answer.AnswerIsNotValidException;
import com.abookadabra.utils.amazon.api.models.answerelements.ItemLookupRequest;
import com.abookadabra.utils.amazon.api.models.answerelements.ItemSearchRequest;

public class SearchAnswer extends Answer {
	private long totalResults;
	private long totalPages;

	public SearchAnswer() {
		super();
		initialise();
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
		return (ItemSearchRequest) super.getRequest();
	}

	public long getTotalResults() {
		return totalResults;
	}

	public long getTotalPages() {
		return totalPages;
	}
}
