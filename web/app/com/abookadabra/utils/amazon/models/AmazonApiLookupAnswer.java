package com.abookadabra.utils.amazon.models;

import java.util.ArrayList;

import com.abookadabra.utils.amazon.models.answerelements.Item;
import com.abookadabra.utils.amazon.models.answerelements.ItemLookupRequest;

public class AmazonApiLookupAnswer extends AmazonApiAnswer {

	private AmazonApiLookupAnswer() {
		super();
		items = new ArrayList<Item>();
	}
	
	public static AmazonApiLookupAnswer createInstanceFrom(Object answerFromAmazonToParse) {
		AmazonApiLookupAnswer newInstance = new AmazonApiLookupAnswer();
		newInstance.loadFrom(answerFromAmazonToParse);
		return newInstance;
	}
	
	public Item getItem() throws AnswerIsNotValidException {
		if (isItAValidAnswer())
			return items.get(0);
		throw new AnswerIsNotValidException("Unable to get item, this is not a valid lookup answer.");
	}
	
	public ItemLookupRequest getRequest() throws AnswerIsNotValidException {
		if (isItAValidAnswer())
			return (ItemLookupRequest) request;
		throw new AnswerIsNotValidException("Request is not a valid Lookup Request.");
	}

}
