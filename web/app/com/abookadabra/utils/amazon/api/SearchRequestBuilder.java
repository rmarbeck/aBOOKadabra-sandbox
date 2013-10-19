package com.abookadabra.utils.amazon.api;

import com.abookadabra.utils.amazon.api.models.Request;

public class SearchRequestBuilder implements RequestBuilder {
	private Request builtRequest;
	
	public class AmazonApiSearchRequestReady extends RequestReady {
		protected AmazonApiSearchRequestReady() {
			super(builtRequest);
		}
		
		public AmazonApiSearchRequestReady sortBySalesRank() {
			return setSortBy(AMAZON_SORT_PARAM_SALES_RANK_VALUE);
		}
		public AmazonApiSearchRequestReady sortAtoZTitle() {
			return setSortBy(AMAZON_SORT_PARAM_A_TO_Z_TITLE_RANK_VALUE);
		}
		public AmazonApiSearchRequestReady sortZtoATitle() {
			return setSortBy(AMAZON_SORT_PARAM_Z_TO_A_TITLE_RANK_VALUE);
		}
		public AmazonApiSearchRequestReady sortMoreExpensiveFirst() {
			return setSortBy(AMAZON_SORT_PARAM_INVERSE_PRICE_RANK_VALUE);
		}
		public AmazonApiSearchRequestReady sortCheapestFirst() {
			return setSortBy(AMAZON_SORT_PARAM_PRICE_RANK_VALUE);
		}
		public AmazonApiSearchRequestReady sortOldestFirst() {
			return setSortBy(AMAZON_SORT_PARAM_OLD_TO_NEW__DATE_RANK_VALUE);
		}
		
		private AmazonApiSearchRequestReady setSortBy(String value) {
			builtRequest.addParam(AMAZON_SORT_PARAM, value);
			return this;
		}
		
		public AmazonApiSearchRequestReady browseOnlyNode(int node) {
			if (isItAValidNode(node))
				return setBrowseNode(Integer.toString(node));
			return setBrowseNode(Integer.toString(BROWSE_NODE_MIN));
		}
		
		private AmazonApiSearchRequestReady setBrowseNode(String value) {
			builtRequest.addParam(AMAZON_BROWSE_NODE_PARAM, value);
			return this;
		}
		
		private boolean isItAValidNode(int node) {
			return (node >= BROWSE_NODE_MIN);
		}
		
		public AmazonApiSearchRequestReady itemPage(int page) {
			if (isItAValidItemPage(page))
				return setItemPage(Integer.toString(page));
			return setItemPage(Integer.toString(ITEM_PAGE_MIN));
		}
		
		private AmazonApiSearchRequestReady setItemPage(String value) {
			builtRequest.addParam(AMAZON_ITEM_PAGE_PARAM, value);
			return this;
		}
		
		private boolean isItAValidItemPage(int page) {
			return (page >= ITEM_PAGE_MIN && page <= ITEM_PAGE_MAX);
		}
		
		public AmazonApiSearchRequestReady onlyNew() {
			return setCondition(AMAZON_CONDITION_PARAM_NEW_VALUE);
		}
		public AmazonApiSearchRequestReady onlyUsed() {
			return setCondition(AMAZON_CONDITION_PARAM_USED_VALUE);
		}
		public AmazonApiSearchRequestReady onlyCollectible() {
			return setCondition(AMAZON_CONDITION_PARAM_COLLECTIBLE_VALUE);
		}
		public AmazonApiSearchRequestReady onlyRefurb() {
			return setCondition(AMAZON_CONDITION_PARAM_REFURB_VALUE);
		}
		public AmazonApiSearchRequestReady noConditionFilter() {
			return setCondition(AMAZON_CONDITION_PARAM_ALL_VALUE);
		}
		
		private AmazonApiSearchRequestReady setCondition(String value) {
			builtRequest.addParam(AMAZON_CONDITION_PARAM, value);
			return this;
		}
	}
	
	public SearchRequestBuilder() {
		builtRequest = new Request();
		initialise();
		RequestBuilderHelper.addCommonParams(builtRequest);
	}
	
	public static SearchRequestBuilder search() {
		return new SearchRequestBuilder();
	}
	
	private void initialise() {
		builtRequest.addParam(AMAZON_OPERATION_PARAM, AMAZON_OPERATION_PARAM_ITEM_SEARCH);
		builtRequest.addParam(AMAZON_SEARCH_INDEX_PARAM, AMAZON_SEARCH_INDEX_FOR_BOOKS_PARAM_VALUE);
	}

	public AmazonApiSearchRequestReady forAuthor(String author) {
		return setItem(AMAZON_AUTHOR_PARAM, author);
	}
	
	public AmazonApiSearchRequestReady forKeywords(String keywords) {
		return setItem(AMAZON_KEYWORDS_PARAM, keywords);
	}

	public AmazonApiSearchRequestReady forLikeKeywords(String keywords) {
		return setItem(AMAZON_KEYWORDS_PARAM, "%"+keywords+"%");
	}
	
	public AmazonApiSearchRequestReady forTitle(String title) {
		return setItem(AMAZON_TITLE_PARAM, title);
	}

	private AmazonApiSearchRequestReady setItem(String key, String value) {
		builtRequest.addParam(key, value);
		return new AmazonApiSearchRequestReady();
	}
}
