package com.abookadabra.utils.amazon.api;

import java.util.List;

import static com.abookadabra.utils.amazon.api.RequestBuilderConstants.*;

public class SimilarityLookupRequestBuilder extends RequestBuilder {
	
	public class SimilarityLookupRequestReady extends RequestReady {
		protected SimilarityLookupRequestReady() {
			super(builtRequest);
		}
		
		public SimilarityLookupRequestReady random() {
			return setSimilarityType(AMAZON_SIMILARY_TYPE_PARAM_RANDOM_VALUE);
		}
		public SimilarityLookupRequestReady intersection() {
			return setSimilarityType(AMAZON_SIMILARY_TYPE_PARAM_INTERSECTION_VALUE);
		}
		
		private SimilarityLookupRequestReady setSimilarityType(String value) {
			return addParam(AMAZON_SIMILARY_TYPE_PARAM, value);
		}
		
		protected SimilarityLookupRequestReady addParam(String key, String value) {
			return (SimilarityLookupRequestReady) super.addParam(key, value);
		}
		
	}
	
	public static SimilarityLookupRequestBuilder build() {
		return new SimilarityLookupRequestBuilder();
	}
	
	protected void initialise() {
		builtRequest.addParam(AMAZON_OPERATION_PARAM, AMAZON_OPERATION_PARAM_ITEM_SIMILARITY);
	}
	

	public SimilarityLookupRequestReady forASIN(String asin) {
		return forListOfASINs(RequestBuilderHelper.putStringInNewList(asin));
	}

	public SimilarityLookupRequestReady forListOfASINs(List<String> asins) {
		return setItems(asins);
	}
	
	private SimilarityLookupRequestReady setItems(List<String> values) {
		builtRequest.addParam(AMAZON_ITEM_ID_PARAM, RequestBuilderHelper.formatListOfItemsParam(values));
		return new SimilarityLookupRequestReady();
	}
}
