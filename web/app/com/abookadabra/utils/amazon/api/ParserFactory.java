package com.abookadabra.utils.amazon.api;

import org.w3c.dom.Document;

import com.abookadabra.utils.amazon.api.Parser.UnableToLoadThisKindOfObject;

public abstract class ParserFactory {
	private static ParserFactory factory = null;
	
	public static void setInstance(ParserFactory factory) {
		ParserFactory.factory = factory;
	}
	
	protected abstract Parser _getParser();
	
	public static Parser getParser() {
		if (factory == null)
				setDefaultImplementation();
		return factory._getParser();
	}
	
	public static Parser getParserFromObjectToParse(Object o) {
		if (o instanceof Document) 
			return new XMLDefaultAmazonApiParserFactory()._getParser();
		return getParser();
	}

	public static Parser getInitialisedParserFromObjectToParse(Object o) throws UnableToLoadThisKindOfObject {
		if (o instanceof Document) {
			Parser parser =  new XMLDefaultAmazonApiParserFactory()._getParser();
			parser.initialise(o);
			return parser;
		}
		throw new UnableToLoadThisKindOfObject("Only "+Document.class+" can be parsed, not "+o.getClass().getName()+".");
	}
	
	private static void setDefaultImplementation() {
		factory = new XMLDefaultAmazonApiParserFactory();
	}
}
