package com.abookadabra.utils.amazon.api;

import static com.abookadabra.utils.amazon.api.RequestBuilderConstants.*;

public class RequestReady {
	private Request readyToBeExecutedRequest;
	
	protected RequestReady(Request allreadyInitialisedRequest) {
		readyToBeExecutedRequest = allreadyInitialisedRequest;
	}
	
	protected void initialise() {
	}
	
	public Request getRequest() {
		return readyToBeExecutedRequest;
	}
	
	/********************************************
	 * Response Group
	 ********************************************/
	public RequestReady largeResponse() {
		return setResponseGroup(AMAZON_RESPONSE_GROUP_PARAM_LARGE_VALUE);
	}
	
	public RequestReady smallResponse() {
		return setResponseGroup(AMAZON_RESPONSE_GROUP_PARAM_SMALL_VALUE);
	}
	
	public RequestReady mediumResponse() {
		return setResponseGroup(AMAZON_RESPONSE_GROUP_PARAM_MEDIUM_VALUE);
	}
	
	public RequestReady customizedResponse(String responseGroupValue) {
		return setResponseGroup(responseGroupValue);
	}
	
	private RequestReady setResponseGroup(String responseGroupValue) {
		return addParam(AMAZON_RESPONSE_GROUP_PARAM, responseGroupValue);
	}
	
	/********************************************
	 * Search Index
	 ********************************************/
	public RequestReady inBooksIndex() {
		return setSearchIndex(AMAZON_SEARCH_INDEX_FOR_BOOKS_PARAM_VALUE);
	}
	
	public RequestReady customizedSearchIndex(String searchIndexValue) {
		return setSearchIndex(searchIndexValue);
	}
	
	private RequestReady setSearchIndex(String searchIndexValue) {
		return addParam(AMAZON_SEARCH_INDEX_PARAM, searchIndexValue);
	}

	/********************************************
	 * Variation Page
	 ********************************************/
	public RequestReady variationPage(int page) {
		if (isItAValidPage(page))
			return setVariationPage(Integer.toString(page));
		return setVariationPage(Integer.toString(VARIATION_PAGE_MIN));
	}
	
	private RequestReady setVariationPage(String page) {
		return addParam(AMAZON_VARIATION_PAGE_PARAM, page);
	}
	
	private boolean isItAValidPage(int page) {
		return (page >= VARIATION_PAGE_MIN && page <= VARIATION_PAGE_MAX);
	}
	
	protected RequestReady addParam(String key, String value) {
		readyToBeExecutedRequest.addParam(key, value);
		return this;
	}
}
