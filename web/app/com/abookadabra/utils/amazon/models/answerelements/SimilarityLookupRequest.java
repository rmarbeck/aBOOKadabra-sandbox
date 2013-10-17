package com.abookadabra.utils.amazon.models.answerelements;

public class SimilarityLookupRequest extends Request {
	private String itemId;
	private String responseGroup;

	public SimilarityLookupRequest(boolean isValid) {
		super(isValid);
	}
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getResponseGroup() {
		return responseGroup;
	}
	public void setResponseGroup(String responseGroup) {
		this.responseGroup = responseGroup;
	}
}
