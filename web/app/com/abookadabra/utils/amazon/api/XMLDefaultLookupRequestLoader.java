package com.abookadabra.utils.amazon.api;

import static com.abookadabra.utils.amazon.api.XMLDefaultAnswerConstants.*;

import com.abookadabra.utils.amazon.api.models.answerelements.ItemLookupRequest;
import com.abookadabra.utils.amazon.api.models.answerelements.RequestInAnswer;

public class XMLDefaultLookupRequestLoader extends XMLDefaultRequestLoader {
	
	protected XMLDefaultLookupRequestLoader(XMLAmazonNode rootToParseFrom) {
		requestNode = rootToParseFrom;
	}
	
	protected RequestInAnswer loadContent() throws Exception {
		ItemLookupRequest lookupRequest = new ItemLookupRequest(true);
		lookupRequest.setIdType(tryToGetTextValueForOptionnalField(AMAZON_XML_FIELD_REQUEST_LOOKUP_ID_TYPE));
		lookupRequest.setItemId(tryToGetTextValueForOptionnalField(AMAZON_XML_FIELD_REQUEST_LOOKUP_ITEM_ID));
		lookupRequest.setResponseGroup(tryToGetTextValueForOptionnalField(AMAZON_XML_FIELD_REQUEST_LOOKUP_RESP_GRP));
		lookupRequest.setSearchIndex(tryToGetTextValueForOptionnalField(AMAZON_XML_FIELD_REQUEST_LOOKUP_SEARCH_IDX));
		lookupRequest.setVariationPage(tryToGetTextValueForOptionnalField(AMAZON_XML_FIELD_REQUEST_LOOKUP_VAR_PAGE));
		return lookupRequest;
	}
}
