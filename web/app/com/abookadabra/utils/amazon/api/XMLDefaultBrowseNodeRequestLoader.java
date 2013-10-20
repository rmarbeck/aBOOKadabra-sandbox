package com.abookadabra.utils.amazon.api;

import static com.abookadabra.utils.amazon.api.XMLDefaultAnswerConstants.*;

import com.abookadabra.utils.amazon.api.models.answerelements.BrowseNodeRequest;
import com.abookadabra.utils.amazon.api.models.answerelements.RequestInAnswer;

public class XMLDefaultBrowseNodeRequestLoader extends XMLDefaultRequestLoader {
	
	protected XMLDefaultBrowseNodeRequestLoader(XMLAmazonNode rootToParseFrom) {
		requestNode = rootToParseFrom;
	}
	
	protected RequestInAnswer loadContent() throws Exception {
		BrowseNodeRequest browseNodeRequest = new BrowseNodeRequest(true);
		browseNodeRequest.setBrowseNodeId(tryToGetTextValueForOptionnalField(AMAZON_XML_FIELD_REQUEST_BROWSE_NODE_ID));
		browseNodeRequest.setResponseGroup(tryToGetTextValueForOptionnalField(AMAZON_XML_FIELD_REQUEST_BROWSE_NODE_RESP_GRP));
		return browseNodeRequest;
	}
}
