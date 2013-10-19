package com.abookadabra.utils.amazon.api;

import static com.abookadabra.utils.amazon.api.RequestBuilderConstants.AMAZON_ASSOCIATE_TAG_PARAM;
import static com.abookadabra.utils.amazon.api.RequestBuilderConstants.AMAZON_ASSOCIATE_TAG_PARAM_VALUE;
import static com.abookadabra.utils.amazon.api.RequestBuilderConstants.AMAZON_SERVICE_PARAM;
import static com.abookadabra.utils.amazon.api.RequestBuilderConstants.AMAZON_SERVICE_PARAM_VALUE;
import static com.abookadabra.utils.amazon.api.RequestBuilderConstants.AMAZON_VERSION_PARAM;
import static com.abookadabra.utils.amazon.api.RequestBuilderConstants.AMAZON_VERSION_PARAM_VALUE;

public abstract class RequestBuilder {
	protected Request builtRequest;

	protected RequestBuilder() {
		builtRequest = new Request();
		initialise();
		addCommonParams(builtRequest);
	}
	
	protected static void addCommonParams(Request request) {
		request.addParam(AMAZON_SERVICE_PARAM, AMAZON_SERVICE_PARAM_VALUE);
		request.addParam(AMAZON_ASSOCIATE_TAG_PARAM, AMAZON_ASSOCIATE_TAG_PARAM_VALUE);
		request.addParam(AMAZON_VERSION_PARAM, AMAZON_VERSION_PARAM_VALUE);
	}
	
	protected abstract void initialise();
}
