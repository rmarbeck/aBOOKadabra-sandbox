package com.abookadabra.utils.amazon;

import java.util.List;

import com.abookadabra.utils.amazon.models.answerelements.Arguments;
import com.abookadabra.utils.amazon.models.answerelements.BrowseNode;
import com.abookadabra.utils.amazon.models.answerelements.Request;
import com.abookadabra.utils.amazon.models.answerelements.Item;

public interface AmazonApiParser {
	public void initialise(Object o) throws UnableToLoadThisKindOfObject;
	
	@SuppressWarnings("serial")
	public static class UnableToLoadThisKindOfObject extends Exception {
		public UnableToLoadThisKindOfObject(String s) {
			super(s);
		}
	}
	
	public boolean isValid();
	
	public boolean hasErrors();
	
	public void checkRequestSummaryForErrors() throws Exception;
	
	public Item loadItem() throws Exception;
	
	public List<Item> loadItems() throws Exception;
	
	public Request loadRequest();
	
	public Arguments loadArguments();
	
	public long loadTotalResults();
	
	public long loadTotalPages();
	
	public List<BrowseNode> loadBrowseNodes();
}
