package com.abookadabra.utils.amazon.api;

public class XMLDefaultAmazonApiParserFactory extends ParserFactory {

	@Override
	protected Parser _getParser() {
		return new XMLDefaultAmazonApiParser();
	}

}
