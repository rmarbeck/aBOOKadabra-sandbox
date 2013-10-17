package com.abookadabra.utils.amazon;

import org.w3c.dom.Document;

import com.abookadabra.utils.amazon.AmazonApiParser.UnableToLoadThisKindOfObject;

public abstract class AmazonApiParserFactory {
	private static AmazonApiParserFactory factory = null;
	
	public static void setInstance(AmazonApiParserFactory factory) {
		AmazonApiParserFactory.factory = factory;
	}
	
	protected abstract AmazonApiParser _getParser();
	
	public static AmazonApiParser getParser() {
		if (factory == null)
				setDefaultImplementation();
		return factory._getParser();
	}
	
	public static AmazonApiParser getParserFromObjectToParse(Object o) {
		if (o instanceof Document) 
			return new XMLDefaultAmazonApiParserFactory()._getParser();
		return getParser();
	}

	public static AmazonApiParser getInitialisedParserFromObjectToParse(Object o) throws UnableToLoadThisKindOfObject {
		if (o instanceof Document) {
			AmazonApiParser parser =  new XMLDefaultAmazonApiParserFactory()._getParser();
			parser.initialise(o);
			return parser;
		}
		throw new UnableToLoadThisKindOfObject("Only "+Document.class+" can be parsed, not "+o.getClass().getName()+".");
	}
	
	private static void setDefaultImplementation() {
		factory = new XMLDefaultAmazonApiParserFactory();
	}
}
