package com.abookadabra.utils.amazon.api;

import com.abookadabra.utils.amazon.api.models.Request;

public class RequestReady implements RequestBuilder {
	private Request builtRequest;
	
	protected RequestReady(Request request) {
		builtRequest = request;
	}
	
	public Request getRequest() {
		return builtRequest;
	}
	
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
		builtRequest.addParam(AMAZON_RESPONSE_GROUP_PARAM, responseGroupValue);
		return this;
	}
	
	public RequestReady inBooksIndex() {
		return setSearchIndex(AMAZON_SEARCH_INDEX_FOR_BOOKS_PARAM_VALUE);
	}
	
	public RequestReady customizedSearchIndex(String searchIndexValue) {
		return setSearchIndex(searchIndexValue);
	}
	
	private RequestReady setSearchIndex(String searchIndexValue) {
		builtRequest.addParam(AMAZON_SEARCH_INDEX_PARAM, searchIndexValue);
		return this;
	}
	
	public RequestReady page(int page) {
		if (isItAValidPage(page))
			return setVariationPage(Integer.toString(page));
		return setVariationPage(Integer.toString(VARIATION_PAGE_MIN));
	}
	
	private RequestReady setVariationPage(String page) {
		builtRequest.addParam(AMAZON_VARIATION_PAGE_PARAM, page);
		return this;
	}
	private boolean isItAValidPage(int page) {
		return (page >= VARIATION_PAGE_MIN && page <= VARIATION_PAGE_MAX);
	}
}
