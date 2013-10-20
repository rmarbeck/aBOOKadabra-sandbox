package com.abookadabra.utils.amazon.api;

import com.abookadabra.utils.amazon.api.XMLAmazonNode.NodeNotFoundException;


public abstract class XMLDefaultParserAndLoaderHelper {
	protected static String tryToGetTextValueForOptionnalField(XMLAmazonNode node, String key) {
		return node.childOrEmpty(key).retrieveTextValue();
	}
	
	protected static String getTextValueForMandatoryField(XMLAmazonNode node, String key) throws NodeNotFoundException {
		return node.child(key).retrieveTextValue();
	}
}
