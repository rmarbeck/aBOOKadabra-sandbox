package com.abookadabra.utils.amazon.api;

import org.w3c.dom.Document;

import com.abookadabra.utils.amazon.api.AnswerParser.UnableToLoadThisKindOfObject;

public abstract class AnswerParserFactory {
	private static AnswerParserFactory factory = null;
	
	public static void setInstance(AnswerParserFactory factory) {
		AnswerParserFactory.factory = factory;
	}
	
	protected abstract AnswerParser _getParser();
	
	public static AnswerParser getParser() {
		if (factory == null)
				setDefaultImplementation();
		return factory._getParser();
	}
	
	public static AnswerParser getParserFromObjectToParse(Object o) {
		if (o instanceof Document) 
			return new XMLDefaultAnswerParserFactory()._getParser();
		return getParser();
	}

	public static AnswerParser getInitialisedParserFromObjectToParse(Object o) throws UnableToLoadThisKindOfObject {
		if (o instanceof Document) {
			AnswerParser parser =  new XMLDefaultAnswerParserFactory()._getParser();
			parser.initialise(o);
			return parser;
		}
		throw new UnableToLoadThisKindOfObject("Only "+Document.class+" can be parsed, not "+o.getClass().getName()+".");
	}
	
	private static void setDefaultImplementation() {
		factory = new XMLDefaultAnswerParserFactory();
	}
}
