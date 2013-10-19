package com.abookadabra.utils.amazon.api.models.answerelements;

public class ItemSearchRequest extends RequestInAnswer {
	private String itemPage;
	private String keywords;
	private String responseGroup;
	private String searchIndex;
	private String sort;
	
	public ItemSearchRequest(boolean isValid) {
		super(isValid);
	}

	public String getItemPage() {
		return itemPage;
	}
	public void setItemPage(String itemPage) {
		this.itemPage = itemPage;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
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
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
}
