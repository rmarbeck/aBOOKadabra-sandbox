package com.abookadabra.utils.amazon.api.models;

import java.util.ArrayList;

import com.abookadabra.utils.amazon.api.models.answerelements.Item;
import com.abookadabra.utils.amazon.api.models.answerelements.ItemLookupRequest;

public class LookupAnswer extends Answer {

	private LookupAnswer() {
		super();
		items = new ArrayList<Item>();
	}
	
	public static LookupAnswer createInstanceFrom(Object answerFromAmazonToParse) {
		LookupAnswer newInstance = new LookupAnswer();
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
