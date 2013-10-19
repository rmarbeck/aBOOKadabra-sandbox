package com.abookadabra.utils.amazon.api;

import com.abookadabra.utils.amazon.api.models.Request;

public class BrowseNodeRequestBuilder implements RequestBuilder {
	private Request builtRequest;
	
	public class AmazonApiBrowseNodeRequestReady extends RequestReady {
		protected AmazonApiBrowseNodeRequestReady() {
			super(builtRequest);
		}
		
		public AmazonApiBrowseNodeRequestReady mostGifted() {
			return setResponseGroup(AMAZON_RESPONSE_GROUP_PARAM_BROWSE_NODE_SPECIFIC_MOST_GIFTED);
		}
		public AmazonApiBrowseNodeRequestReady wishedFor() {
			return setResponseGroup(AMAZON_RESPONSE_GROUP_PARAM_BROWSE_NODE_SPECIFIC_MOST_WISHED_FOR);
		}
		public AmazonApiBrowseNodeRequestReady newReleases() {
			return setResponseGroup(AMAZON_RESPONSE_GROUP_PARAM_BROWSE_NODE_SPECIFIC_NEW_RELEASES);
		}
		public AmazonApiBrowseNodeRequestReady topSellers() {
			return setResponseGroup(AMAZON_RESPONSE_GROUP_PARAM_BROWSE_NODE_SPECIFIC_TOP_SELLERS);
		}
		
		private AmazonApiBrowseNodeRequestReady setResponseGroup(String responseGroupValue) {
			builtRequest.addParam(AMAZON_RESPONSE_GROUP_PARAM, responseGroupValue);
			return this;
		}
	}
	
	public BrowseNodeRequestBuilder() {
		builtRequest = new Request();
		initialise();
		RequestBuilderHelper.addCommonParams(builtRequest);
	}
	
	public static BrowseNodeRequestBuilder lookup() {
		return new BrowseNodeRequestBuilder();
	}
	
	private void initialise() {
		builtRequest.addParam(AMAZON_OPERATION_PARAM, AMAZON_OPERATION_PARAM_BROWSE_NODE);
	}

	public AmazonApiBrowseNodeRequestReady forId(int browseNodeId) {
		if (isItAValidNode(browseNodeId))
			return setBrowseNode(Integer.toString(browseNodeId));
		return setBrowseNode(Integer.toString(BROWSE_NODE_MIN));
	}
	
	private AmazonApiBrowseNodeRequestReady setBrowseNode(String value) {
		builtRequest.addParam(AMAZON_BROWSE_NODE_PARAM, value);
		return new AmazonApiBrowseNodeRequestReady();
	}
	
	private boolean isItAValidNode(int node) {
		return (node >= BROWSE_NODE_MIN);
	}
	
}
