package com.abookadabra.utils.amazon.api;

import static com.abookadabra.utils.amazon.api.RequestBuilderConstants.*;

public class SearchRequestBuilder extends RequestBuilder {
	
	public class SearchRequestReady extends RequestReady {
		protected SearchRequestReady() {
			super(builtRequest);
		}
		
		public SearchRequestReady sortBySalesRank() {
			return setSortBy(AMAZON_SORT_PARAM_SALES_RANK_VALUE);
		}
		public SearchRequestReady sortAtoZTitle() {
			return setSortBy(AMAZON_SORT_PARAM_A_TO_Z_TITLE_RANK_VALUE);
		}
		public SearchRequestReady sortZtoATitle() {
			return setSortBy(AMAZON_SORT_PARAM_Z_TO_A_TITLE_RANK_VALUE);
		}
		public SearchRequestReady sortMoreExpensiveFirst() {
			return setSortBy(AMAZON_SORT_PARAM_INVERSE_PRICE_RANK_VALUE);
		}
		public SearchRequestReady sortCheapestFirst() {
			return setSortBy(AMAZON_SORT_PARAM_PRICE_RANK_VALUE);
		}
		public SearchRequestReady sortOldestFirst() {
			return setSortBy(AMAZON_SORT_PARAM_OLD_TO_NEW__DATE_RANK_VALUE);
		}
		
		private SearchRequestReady setSortBy(String value) {
			return addParam(AMAZON_SORT_PARAM, value);
		}
		
		public SearchRequestReady browseOnlyNode(int node) {
			if (isItAValidNode(node))
				return setBrowseNode(Integer.toString(node));
			return setBrowseNode(Integer.toString(BROWSE_NODE_MIN));
		}
		
		private SearchRequestReady setBrowseNode(String value) {
			return addParam(AMAZON_BROWSE_NODE_PARAM, value);
		}
		
		private boolean isItAValidNode(int node) {
			return (node >= BROWSE_NODE_MIN);
		}
		
		public SearchRequestReady itemPage(int page) {
			if (isItAValidItemPage(page))
				return setItemPage(Integer.toString(page));
			return setItemPage(Integer.toString(ITEM_PAGE_MIN));
		}
		
		private SearchRequestReady setItemPage(String value) {
			return addParam(AMAZON_ITEM_PAGE_PARAM, value);
		}
		
		private boolean isItAValidItemPage(int page) {
			return (page >= ITEM_PAGE_MIN && page <= ITEM_PAGE_MAX);
		}
		
		public SearchRequestReady onlyNew() {
			return setCondition(AMAZON_CONDITION_PARAM_NEW_VALUE);
		}
		public SearchRequestReady onlyUsed() {
			return setCondition(AMAZON_CONDITION_PARAM_USED_VALUE);
		}
		public SearchRequestReady onlyCollectible() {
			return setCondition(AMAZON_CONDITION_PARAM_COLLECTIBLE_VALUE);
		}
		public SearchRequestReady onlyRefurb() {
			return setCondition(AMAZON_CONDITION_PARAM_REFURB_VALUE);
		}
		public SearchRequestReady noConditionFilter() {
			return setCondition(AMAZON_CONDITION_PARAM_ALL_VALUE);
		}
		
		private SearchRequestReady setCondition(String value) {
			return addParam(AMAZON_CONDITION_PARAM, value);
		}
		
		protected SearchRequestReady addParam(String key, String value) {
			return (SearchRequestReady) super.addParam(key, value);
		}
	}
	
	public static SearchRequestBuilder build() {
		return new SearchRequestBuilder();
	}
	
	protected void initialise() {
		builtRequest.addParam(AMAZON_OPERATION_PARAM, AMAZON_OPERATION_PARAM_ITEM_SEARCH);
		builtRequest.addParam(AMAZON_SEARCH_INDEX_PARAM, AMAZON_SEARCH_INDEX_FOR_BOOKS_PARAM_VALUE);
	}

	public SearchRequestReady forAuthor(String author) {
		return setItem(AMAZON_AUTHOR_PARAM, author);
	}
	
	public SearchRequestReady forKeywords(String keywords) {
		return setItem(AMAZON_KEYWORDS_PARAM, keywords);
	}

	public SearchRequestReady forLikeKeywords(String keywords) {
		return setItem(AMAZON_KEYWORDS_PARAM, "%"+keywords+"%");
	}
	
	public SearchRequestReady forTitle(String title) {
		return setItem(AMAZON_TITLE_PARAM, title);
	}

	public SearchRequestReady forBrowseNode(int nodeId) {
		if (nodeId >= BROWSE_NODE_MIN)
			return setItem(AMAZON_BROWSENODE_PARAM, Integer.toString(nodeId));
		return setItem(AMAZON_BROWSENODE_PARAM, BROWSE_NODE_DEFAULT);
	}

	private SearchRequestReady setItem(String key, String value) {
		builtRequest.addParam(key, value);
		return new SearchRequestReady();
	}
}
