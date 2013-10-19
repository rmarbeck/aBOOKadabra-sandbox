package com.abookadabra.utils.amazon.api;

import static com.abookadabra.utils.amazon.api.XMLDefaultAnswerConstants.*;

import com.abookadabra.utils.amazon.api.models.answerelements.ItemSearchRequest;
import com.abookadabra.utils.amazon.api.models.answerelements.RequestInAnswer;

public class XMLDefaultSearchRequestLoader extends XMLDefaultRequestLoader {
	
	protected XMLDefaultSearchRequestLoader(XMLAmazonNode rootToParseFrom) {
		requestNode = rootToParseFrom;
	}
	
	protected RequestInAnswer loadContent() throws Exception {
		ItemSearchRequest searchRequest = new ItemSearchRequest(true);
		searchRequest.setItemPage(tryToGetTextValueForOptionnalField(AMAZON_XML_FIELD_REQUEST_SEARCH_ITEM_PAGE));
		searchRequest.setKeywords(tryToGetTextValueForOptionnalField(AMAZON_XML_FIELD_REQUEST_SEARCH_KEYWORDS));
		searchRequest.setResponseGroup(tryToGetTextValueForOptionnalField(AMAZON_XML_FIELD_REQUEST_SEARCH_RESP_GROUP));
		searchRequest.setSearchIndex(tryToGetTextValueForOptionnalField(AMAZON_XML_FIELD_REQUEST_SEARCH_SEARCH_IDX));
		searchRequest.setSort(tryToGetTextValueForOptionnalField(AMAZON_XML_FIELD_REQUEST_SEARCH_SORT));
		return searchRequest;
	}
}
