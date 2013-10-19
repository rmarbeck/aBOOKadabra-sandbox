package com.abookadabra.utils.amazon.api;

public class XMLDefaultAnswerParserFactory extends AnswerParserFactory {

	@Override
	protected AnswerParser _getParser() {
		return new XMLDefaultAnswerParser();
	}

}
