package com.abookadabra.utils.amazon.api;

import com.abookadabra.utils.amazon.api.models.answerelements.RequestInAnswer;

import static com.abookadabra.utils.amazon.api.XMLDefaultAnswerConstants.*;

public abstract class XMLDefaultRequestLoader {
	protected XMLAmazonNode requestNode;
	
	protected RequestInAnswer load() {
		try {
			return testAndload();
		} catch (Exception e) {
			return RequestInAnswer.createInvalidRequest();
		}
	}

	private RequestInAnswer testAndload() throws Exception {
		if (isRequestValid())
			return loadContent();
		return RequestInAnswer.createInvalidRequest();
	}
	
	protected boolean isRequestValid() {
		return (requestNode.childOrEmpty(AMAZON_XML_FIELD_IS_VALID).retrieveTextValue().equalsIgnoreCase("true"));
	}

	protected abstract RequestInAnswer loadContent() throws Exception;
	
	protected String tryToGetTextValueForOptionnalField(String key) {
		return XMLDefaultParserAndLoaderHelper.tryToGetTextValueForOptionnalField(requestNode, key);
	}
	
	
}
