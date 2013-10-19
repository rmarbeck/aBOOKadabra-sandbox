package com.abookadabra.utils.amazon.api.models;

import java.util.ArrayList;

import com.abookadabra.utils.amazon.api.models.answerelements.BrowseNodeRequest;
import com.abookadabra.utils.amazon.api.models.answerelements.Item;

public class BrowseNodeAnswer extends Answer {

	private BrowseNodeAnswer() {
		super();
		items = new ArrayList<Item>();
	}
	
	public static BrowseNodeAnswer createInstanceFrom(Object answerFromAmazonToParse) {
		BrowseNodeAnswer newInstance = new BrowseNodeAnswer();
		newInstance.loadFrom(answerFromAmazonToParse);
		return newInstance;
	}
	
	protected void loadFrom(Object answerFromAmazonToParse) {
		try {
			prepareForLoadingObjectsFromContent(answerFromAmazonToParse);
			loadBrowseNodes();
		} catch (Exception e) {
			fillAnswerFromEmptyResult();
		}
	}
	
	private void loadBrowseNodes() throws Exception {
		items = new ArrayList<Item>();
		Item item = new Item();
		item.setBrowseNodes(parser.loadBrowseNodes());
		items.add(item);
	}
	
	@Override
	protected boolean isItAValidAnswer() {
		return (request.isItValid());
	}

	public BrowseNodeRequest getRequest() throws AnswerIsNotValidException {
		if (isItAValidAnswer())
			return (BrowseNodeRequest) request;
		throw new AnswerIsNotValidException("Request is not a valid BrowseNode Request.");
	}
}
