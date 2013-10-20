package com.abookadabra.utils.amazon.api;

import static com.abookadabra.utils.amazon.api.XMLDefaultAnswerConstants.*;

import com.abookadabra.utils.amazon.api.models.answerelements.RequestInAnswer;
import com.abookadabra.utils.amazon.api.models.answerelements.SimilarityLookupRequest;

public class XMLDefaultSimilarityLookupRequestLoader extends XMLDefaultRequestLoader {
	
	protected XMLDefaultSimilarityLookupRequestLoader(XMLAmazonNode rootToParseFrom) {
		requestNode = rootToParseFrom;
	}
	
	protected RequestInAnswer loadContent() throws Exception {
		SimilarityLookupRequest similarityRequest = new SimilarityLookupRequest(true);
		similarityRequest.setItemId(tryToGetTextValueForOptionnalField(AMAZON_XML_FIELD_REQUEST_SIMILARITY_LOOKUP_ITEM_ID));
		similarityRequest.setSimilarityType(tryToGetTextValueForOptionnalField(AMAZON_XML_FIELD_REQUEST_SIMILARITY_LOOKUP_SIMILARITY_TYPE));
		similarityRequest.setResponseGroup(tryToGetTextValueForOptionnalField(AMAZON_XML_FIELD_REQUEST_SIMILARITY_LOOKUP_RESPONSE_GRP));
		return similarityRequest;
	}
}
