package com.abookadabra.utils.amazon.models;

import java.util.ArrayList;
import java.util.List;

import com.abookadabra.utils.amazon.AmazonApiParser;
import com.abookadabra.utils.amazon.AmazonApiParserFactory;
import com.abookadabra.utils.amazon.AmazonApiParser.UnableToLoadThisKindOfObject;
import com.abookadabra.utils.amazon.models.answerelements.Arguments;
import com.abookadabra.utils.amazon.models.answerelements.Request;
import com.abookadabra.utils.amazon.models.answerelements.Item;

public abstract class AmazonApiAnswer {
	protected AmazonApiParser parser;
	protected Request request;
	protected Arguments arguments;
	protected List<Item> items;

	protected void prepareForLoadingObjectsFromContent (Object answerFromAmazonToParse) throws Exception {
		registerInitialisedParser(answerFromAmazonToParse);
		request = loadRequest();
		//TODO
		//arguments = loadArguments();
		checkRequestSummaryForErrors();
	}
	
	private void registerInitialisedParser(Object answerFromAmazonToParse) throws UnableToLoadThisKindOfObject {
		parser = AmazonApiParserFactory.getInitialisedParserFromObjectToParse(answerFromAmazonToParse);	
	}
	
	private Request loadRequest() {
		return parser.loadRequest();
	}
	
	protected void loadFrom(Object answerFromAmazonToParse) {
		try {
			prepareForLoadingObjectsFromContent(answerFromAmazonToParse);
			loadItems();
		} catch (Exception e) {
			fillAnswerFromEmptyResult();
		}
	}

	private void loadItems() throws Exception {
		items = parser.loadItems();
	}
	
	public List<Item> getItems() throws AnswerIsNotValidException {
		if (isItAValidAnswer())
			return items;
		throw new AnswerIsNotValidException("Unable to get items, this is not a valid search answer.");
	}

	
	public Item getItem() throws AnswerIsNotValidException {
		if (isItAValidAnswer())
			return items.get(0);
		throw new AnswerIsNotValidException("Unable to get item, this is not a valid lookup answer.");
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
		request = Request.createInvalidRequest();
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
