package com.abookadabra.utils.amazon.api.models;

import java.util.ArrayList;

import com.abookadabra.utils.amazon.api.AnswerParser.UnableToLoadThisKindOfObject;
import com.abookadabra.utils.amazon.api.models.answerelements.BrowseNodeRequest;
import com.abookadabra.utils.amazon.api.models.answerelements.Item;

public class BrowseNodeAnswer extends Answer {

	private BrowseNodeAnswer() {
		initialise();
	}
	
	public static BrowseNodeAnswer createInstanceFrom(Object answerFromAmazonToParse) {
		BrowseNodeAnswer newInstance = new BrowseNodeAnswer();
		newInstance.loadFrom(answerFromAmazonToParse);
		return newInstance;
	}
	
	@Override
	protected void loadFrom(Object answerFromAmazonToParse) {
		try {
			prepareForLoadingObjectsFromContent(answerFromAmazonToParse);
			loadBrowseNodes();
		} catch (UnableToLoadThisKindOfObject e) {
			fillAnswerFromEmptyResult();
		} catch (AnswerIsNotValidException e) {
			fillAnswerFromEmptyResult();
		} catch(Exception e) {
			//Format of request is valid but the answer contains an error 
		}
	}
	
	private void loadBrowseNodes() throws Exception {
		items = new ArrayList<Item>();
		Item item = new Item();
		item.setBrowseNodes(parser.loadBrowseNodes());
		items.add(item);
	}
	
	@Override
	protected boolean isItAValidAnswerWithAtLeastOneItem() {
		return (request.isItValid());
	}
	
	public BrowseNodeRequest getRequest() throws AnswerIsNotValidException {
		return (BrowseNodeRequest) super.getRequest();
	}
}
