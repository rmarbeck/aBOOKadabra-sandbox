package com.abookadabra.utils.amazon.api.models;

import com.abookadabra.utils.amazon.api.models.answerelements.Item;
import com.abookadabra.utils.amazon.api.models.answerelements.ItemLookupRequest;

public class LookupAnswer extends Answer {

	private LookupAnswer() {
		initialise();
	}
	
	public static LookupAnswer createInstanceFrom(Object answerFromAmazonToParse) {
		LookupAnswer newInstance = new LookupAnswer();
		newInstance.loadFrom(answerFromAmazonToParse);
		return newInstance;
	}
	
	public Item getItem() throws AnswerIsNotValidException {
		if (isItAValidAnswerWithAtLeastOneItem())
			return items.get(0);
		throw new AnswerIsNotValidException("Unable to get item, this is not a valid lookup answer.");
	}
	
	public ItemLookupRequest getRequest() throws AnswerIsNotValidException {
		return (ItemLookupRequest) super.getRequest();
	}

}
