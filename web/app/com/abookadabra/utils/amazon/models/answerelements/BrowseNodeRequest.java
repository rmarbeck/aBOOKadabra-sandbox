package com.abookadabra.utils.amazon.models.answerelements;

public class BrowseNodeRequest extends Request {
	private String browseNodeId;

	public BrowseNodeRequest(boolean isValid) {
		super(isValid);
	}

	public String getBrowseNodeId() {
		return browseNodeId;
	}

	public void setBrowseNodeId(String browseNodeId) {
		this.browseNodeId = browseNodeId;
	}
	

}
