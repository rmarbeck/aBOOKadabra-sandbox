package com.abookadabra.utils.amazon.api.models;

import java.util.ArrayList;
import java.util.List;

import com.abookadabra.utils.amazon.api.AnswerParser;
import com.abookadabra.utils.amazon.api.AnswerParser.UnableToLoadThisKindOfObject;
import com.abookadabra.utils.amazon.api.AnswerParserFactory;
import com.abookadabra.utils.amazon.api.models.answerelements.Arguments;
import com.abookadabra.utils.amazon.api.models.answerelements.ErrorInAnswer;
import com.abookadabra.utils.amazon.api.models.answerelements.Item;
import com.abookadabra.utils.amazon.api.models.answerelements.RequestInAnswer;

public abstract class Answer {
	protected AnswerParser parser;
	protected RequestInAnswer request;
	protected Arguments arguments;
	protected ErrorInAnswer error;
	protected List<Item> items;

	protected void initialise() {
		items = new ArrayList<Item>();
	}
	
	protected void loadFrom(Object answerFromAmazonToParse) {
		try {
			prepareForLoadingObjectsFromContent(answerFromAmazonToParse);
			loadItems();
		} catch (UnableToLoadThisKindOfObject e) {
			fillAnswerFromEmptyResult();
		} catch (AnswerIsNotValidException e) {
			fillAnswerFromEmptyResult();
		} catch(Exception e) {
			//Format of request is valid but the answer contains an error 
		}
	}
	
	protected void prepareForLoadingObjectsFromContent (Object answerFromAmazonToParse) throws Exception {
		registerInitialisedParser(answerFromAmazonToParse);
		loadError();
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
	
	private void loadError() {
		error = parser.loadError();
	}

	private void loadItems() throws Exception {
		items = parser.loadItems();
	}
	
	public RequestInAnswer getRequest() throws AnswerIsNotValidException {
		if (isItValid())
			return request;
		throw new AnswerIsNotValidException("Request is not a valid Request.");
	}
	
	public List<Item> getItems() throws AnswerIsNotValidException {
		if (isItAValidAnswerWithAtLeastOneItem())
			return items;
		throw new AnswerIsNotValidException("Unable to get items, this is not a valid answer.");
	}

	public Item getItem() throws AnswerIsNotValidException {
		if (isItAValidAnswerWithAtLeastOneItem())
			return items.get(0);
		throw new AnswerIsNotValidException("Unable to get item, this is not a valid answer.");
	}
	
	private void checkRequestSummaryForErrors() throws Exception {
		if (isItInvalid())
			throw new AnswerIsNotValidException("Request is not valid");
		if (hasErrors())
			throw new AnswerContainsErrorException("Request contains error.");
	}
	
	public boolean hasResults() {
		return (!hasErrors() && items.size() >= 1);
	}
	
	public boolean isItValid() {
		return request.isItValid();
	}
	
	private boolean isItInvalid() {
		return !isItValid();
	}
	
	private boolean hasErrors() {
		return error.containsError();
	}
	
	public ErrorInAnswer getError() {
		return error;
	}
	
	protected void fillAnswerFromEmptyResult() {
		request = RequestInAnswer.createInvalidRequest();
		arguments = new Arguments();
		items = new ArrayList<Item>();
	}
	
	protected boolean isItAValidAnswerWithAtLeastOneItem() {
		return (request.isItValid() && items.size() >= 1);
	}
	
	@SuppressWarnings("serial")
	public class AnswerIsNotValidException extends Exception {
		public AnswerIsNotValidException(String s) {
			super(s);
		}
	}
	
	@SuppressWarnings("serial")
	public static class AnswerContainsErrorException extends Exception {
		public AnswerContainsErrorException(String s) {
			super(s);
		}
	}
}
