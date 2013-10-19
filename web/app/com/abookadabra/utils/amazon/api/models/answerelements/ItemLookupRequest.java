package com.abookadabra.utils.amazon.api.models.answerelements;

public class ItemLookupRequest extends RequestInAnswer {
	private String idType;
	private String itemId;
	private String responseGroup;
	private String searchIndex;
	private String variationPage;
	
	public ItemLookupRequest(boolean isValid) {
		super(isValid);
	}

	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
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
	public String getSearchIndex() {
		return searchIndex;
	}
	public void setSearchIndex(String searchIndex) {
		this.searchIndex = searchIndex;
	}
	public String getVariationPage() {
		return variationPage;
	}
	public void setVariationPage(String variationPage) {
		this.variationPage = variationPage;
	}
}
