package com.abookadabra.utils.amazon.api.models.answerelements;

public class SimilarityLookupRequest extends RequestInAnswer {
	private String itemId;
	private String similarityType;
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

	public String getSimilarityType() {
		return similarityType;
	}

	public void setSimilarityType(String similarityType) {
		this.similarityType = similarityType;
	}
}
