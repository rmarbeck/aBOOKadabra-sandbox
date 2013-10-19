package com.abookadabra.utils.amazon.api;

import static com.abookadabra.utils.amazon.api.XMLDefaultAnswerConstants.*;

public abstract class XMLDefaultRequestLoaderFactory {
	
	protected static XMLDefaultRequestLoader getLoaderFromRequestNode(XMLAmazonNode request) throws Exception {
		if (isItASearchRequest(request))
			return new XMLDefaultSearchRequestLoader(request);
		if (isItALookupRequest(request))
			return new XMLDefaultLookupRequestLoader(request);
		if (isItASimilarityLookupRequest(request))
			return new XMLDefaultSimilarityLookupRequestLoader(request);
		if (isItABrowseNodeRequest(request))
			return new XMLDefaultBrowseNodeRequestLoader(request);
		throw new Exception("Not suppoted request");
	}
	
	private static boolean isItASearchRequest(XMLAmazonNode requestNode) {
		return (!requestNode.childOrEmpty(AMAZON_XML_FIELD_REQUEST_SEARCH).isItEmpty());
	}

	private static boolean isItALookupRequest(XMLAmazonNode requestNode) {
		return (!requestNode.childOrEmpty(AMAZON_XML_FIELD_REQUEST_LOOKUP).isItEmpty());
	}
	
	private static boolean isItASimilarityLookupRequest(XMLAmazonNode requestNode) {
		return (!requestNode.childOrEmpty(AMAZON_XML_FIELD_REQUEST_SIMILARITY_LOOKUP).isItEmpty());
	}
	
	private static boolean isItABrowseNodeRequest(XMLAmazonNode requestNode) {
		return (!requestNode.childOrEmpty(AMAZON_XML_FIELD_REQUEST_BROWSE_NODE).isItEmpty());
	}

}
