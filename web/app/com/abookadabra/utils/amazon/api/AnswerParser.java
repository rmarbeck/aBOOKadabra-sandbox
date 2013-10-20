package com.abookadabra.utils.amazon.api;

import java.util.List;

import com.abookadabra.utils.amazon.api.models.answerelements.Arguments;
import com.abookadabra.utils.amazon.api.models.answerelements.BrowseNode;
import com.abookadabra.utils.amazon.api.models.answerelements.ErrorInAnswer;
import com.abookadabra.utils.amazon.api.models.answerelements.Item;
import com.abookadabra.utils.amazon.api.models.answerelements.RequestInAnswer;

public interface AnswerParser {
	public void initialise(Object o) throws UnableToLoadThisKindOfObject;
	
	@SuppressWarnings("serial")
	public static class UnableToLoadThisKindOfObject extends Exception {
		public UnableToLoadThisKindOfObject(String s) {
			super(s);
		}
	}
	
	public Item loadItem() throws Exception;
	
	public List<Item> loadItems() throws Exception;
	
	public RequestInAnswer loadRequest();
	
	public ErrorInAnswer loadError();
	
	public Arguments loadArguments();
	
	public long loadTotalResults();
	
	public long loadTotalPages();
	
	public List<BrowseNode> loadBrowseNodes();
}
