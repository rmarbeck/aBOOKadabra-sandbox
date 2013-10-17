package com.abookadabra.utils.amazon;

public class XMLDefaultAmazonApiParserFactory extends AmazonApiParserFactory {

	@Override
	protected AmazonApiParser _getParser() {
		return new XMLDefaultAmazonApiParser();
	}

}
