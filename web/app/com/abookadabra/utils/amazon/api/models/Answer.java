package com.abookadabra.utils.amazon.api.models;

import java.util.ArrayList;
import java.util.List;

import com.abookadabra.utils.amazon.api.AnswerParser;
import com.abookadabra.utils.amazon.api.AnswerParser.UnableToLoadThisKindOfObject;
import com.abookadabra.utils.amazon.api.AnswerParserFactory;
import com.abookadabra.utils.amazon.api.models.answerelements.Arguments;
import com.abookadabra.utils.amazon.api.models.answerelements.Item;
import com.abookadabra.utils.amazon.api.models.answerelements.RequestInAnswer;

public abstract class Answer {
	protected AnswerParser parser;
	protected RequestInAnswer request;
	protected Arguments arguments;
	protected List<Item> items;

	protected void initialise() {
		items = new ArrayList<Item>();
	}
	
	protected void loadFrom(Object answerFromAmazonToParse) {
		try {
			prepareForLoadingObjectsFromContent(answerFromAmazonToParse);
			loadItems();
		} catch (Exception e) {
			fillAnswerFromEmptyResult();
		}
	}
	
	protected void prepareForLoadingObjectsFromContent (Object answerFromAmazonToParse) throws Exception {
		registerInitialisedParser(answerFromAmazonToParse);
		loadRequest();
		//TODO
		//loadArguments();
		checkRequestSummaryForErrors();
	}
	
	private void registerInitialisedParser(Object answerFromAmazonToParse) throws UnableToLoadThisKindOfObject {
		parser = AnswerParserFactory.getInitialisedParserFromObjectToParse(answerFromAmazonToParse);	
	}
	
	private void loadRequest() {
		request = parser.loadRequest();
	}

	private void loadItems() throws Exception {
		items = parser.loadItems();
	}
	
	public RequestInAnswer getRequest() throws AnswerIsNotValidException {
		if (isItAValidAnswer())
			return request;
		throw new AnswerIsNotValidException("Request is not a valid Request.");
	}
	
	public List<Item> getItems() throws AnswerIsNotValidException {
		if (isItAValidAnswer())
			return items;
		throw new AnswerIsNotValidException("Unable to get items, this is not a valid answer.");
	}

	public Item getItem() throws AnswerIsNotValidException {
		if (isItAValidAnswer())
			return items.get(0);
		throw new AnswerIsNotValidException("Unable to get item, this is not a valid answer.");
	}
	
	private void checkRequestSummaryForErrors() throws Exception {
		if (isItInvalid() || hasErrors())
			throw new Exception("Request contains errors or is invalid.");
	}
	
	public boolean hasResults() {
		return isItValid() && !hasErrors();
	}
	
	public boolean isItValid() {
		return request.isItValid();
	}
	
	private boolean isItInvalid() {
		return !isItValid();
	}
	
	private boolean hasErrors() {
		return parser.hasErrors();
	}
	
	protected void fillAnswerFromEmptyResult() {
		request = RequestInAnswer.createInvalidRequest();
		arguments = new Arguments();
		items = new ArrayList<Item>();
	}
	
	protected boolean isItAValidAnswer() {
		return (request.isItValid() && items.size() >= 1);
	}
	
	@SuppressWarnings("serial")
	public class AnswerIsNotValidException extends Exception {
		public AnswerIsNotValidException(String s) {
			super(s);
		}
	}
}
