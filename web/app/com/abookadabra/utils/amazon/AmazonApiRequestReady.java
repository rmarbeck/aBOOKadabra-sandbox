package com.abookadabra.utils.amazon;

import com.abookadabra.utils.amazon.models.AmazonApiRequest;

public class AmazonApiRequestReady implements AmazonApiRequestBuilder {
	private AmazonApiRequest builtRequest;
	
	protected AmazonApiRequestReady(AmazonApiRequest request) {
		builtRequest = request;
	}
	
	public AmazonApiRequest getRequest() {
		return builtRequest;
	}
	
	public AmazonApiRequestReady largeResponse() {
		return setResponseGroup(AMAZON_RESPONSE_GROUP_PARAM_LARGE_VALUE);
	}
	
	public AmazonApiRequestReady smallResponse() {
		return setResponseGroup(AMAZON_RESPONSE_GROUP_PARAM_SMALL_VALUE);
	}
	
	public AmazonApiRequestReady mediumResponse() {
		return setResponseGroup(AMAZON_RESPONSE_GROUP_PARAM_MEDIUM_VALUE);
	}
	
	public AmazonApiRequestReady customizedResponse(String responseGroupValue) {
		return setResponseGroup(responseGroupValue);
	}
	
	private AmazonApiRequestReady setResponseGroup(String responseGroupValue) {
		builtRequest.addParam(AMAZON_RESPONSE_GROUP_PARAM, responseGroupValue);
		return this;
	}
	
	public AmazonApiRequestReady inBooksIndex() {
		return setSearchIndex(AMAZON_SEARCH_INDEX_FOR_BOOKS_PARAM_VALUE);
	}
	
	public AmazonApiRequestReady customizedSearchIndex(String searchIndexValue) {
		return setSearchIndex(searchIndexValue);
	}
	
	private AmazonApiRequestReady setSearchIndex(String searchIndexValue) {
		builtRequest.addParam(AMAZON_SEARCH_INDEX_PARAM, searchIndexValue);
		return this;
	}
	
	public AmazonApiRequestReady page(int page) {
		if (isItAValidPage(page))
			return setVariationPage(Integer.toString(page));
		return setVariationPage(Integer.toString(VARIATION_PAGE_MIN));
	}
	
	private AmazonApiRequestReady setVariationPage(String page) {
		builtRequest.addParam(AMAZON_VARIATION_PAGE_PARAM, page);
		return this;
	}
	private boolean isItAValidPage(int page) {
		return (page >= VARIATION_PAGE_MIN && page <= VARIATION_PAGE_MAX);
	}
}
