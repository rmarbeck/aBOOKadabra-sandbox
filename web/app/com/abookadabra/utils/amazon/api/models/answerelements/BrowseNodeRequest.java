package com.abookadabra.utils.amazon.api.models.answerelements;

public class BrowseNodeRequest extends RequestInAnswer {
	private String browseNodeId;
	private String responseGroup;

	public BrowseNodeRequest(boolean isValid) {
		super(isValid);
	}

	public String getBrowseNodeId() {
		return browseNodeId;
	}

	public void setBrowseNodeId(String browseNodeId) {
		this.browseNodeId = browseNodeId;
	}

	public String getResponseGroup() {
		return responseGroup;
	}

	public void setResponseGroup(String responseGroup) {
		this.responseGroup = responseGroup;
	}
	

}
