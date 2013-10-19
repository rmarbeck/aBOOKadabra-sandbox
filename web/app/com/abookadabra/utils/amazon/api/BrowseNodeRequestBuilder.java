package com.abookadabra.utils.amazon.api;

import static com.abookadabra.utils.amazon.api.RequestBuilderConstants.*;

public class BrowseNodeRequestBuilder extends RequestBuilder {
	
	public class BrowseNodeRequestReady extends RequestReady {
		protected BrowseNodeRequestReady() {
			super(builtRequest);
		}
		
		public BrowseNodeRequestReady mostGifted() {
			return setResponseGroup(AMAZON_RESPONSE_GROUP_PARAM_BROWSE_NODE_SPECIFIC_MOST_GIFTED);
		}
		public BrowseNodeRequestReady wishedFor() {
			return setResponseGroup(AMAZON_RESPONSE_GROUP_PARAM_BROWSE_NODE_SPECIFIC_MOST_WISHED_FOR);
		}
		public BrowseNodeRequestReady newReleases() {
			return setResponseGroup(AMAZON_RESPONSE_GROUP_PARAM_BROWSE_NODE_SPECIFIC_NEW_RELEASES);
		}
		public BrowseNodeRequestReady topSellers() {
			return setResponseGroup(AMAZON_RESPONSE_GROUP_PARAM_BROWSE_NODE_SPECIFIC_TOP_SELLERS);
		}
		
		private BrowseNodeRequestReady setResponseGroup(String responseGroupValue) {
			return (BrowseNodeRequestReady) addParam(AMAZON_RESPONSE_GROUP_PARAM, responseGroupValue);
		}
	}
	
	public static BrowseNodeRequestBuilder build() {
		return new BrowseNodeRequestBuilder();
	}
	
	protected void initialise() {
		builtRequest.addParam(AMAZON_OPERATION_PARAM, AMAZON_OPERATION_PARAM_BROWSE_NODE);
	}

	public BrowseNodeRequestReady forId(int browseNodeId) {
		if (isItAValidNode(browseNodeId))
			return setBrowseNode(Integer.toString(browseNodeId));
		return setBrowseNode(Integer.toString(BROWSE_NODE_MIN));
	}
	
	private BrowseNodeRequestReady setBrowseNode(String value) {
		builtRequest.addParam(AMAZON_BROWSE_NODE_PARAM, value);
		return new BrowseNodeRequestReady();
	}
	
	private boolean isItAValidNode(int node) {
		return (node >= BROWSE_NODE_MIN);
	}
	
}
