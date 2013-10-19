package com.abookadabra.utils.amazon.api;

import java.util.List;

import com.abookadabra.utils.amazon.api.models.Request;

public class SimilarityLookupRequestBuilder implements RequestBuilder {
	private Request builtRequest;
	
	public class AmazonApiSimilarityLookupRequestReady extends RequestReady {
		protected AmazonApiSimilarityLookupRequestReady() {
			super(builtRequest);
		}
		
		public AmazonApiSimilarityLookupRequestReady random() {
			return setSimilarityType(AMAZON_SIMILARY_TYPE_PARAM_RANDOM_VALUE);
		}
		public AmazonApiSimilarityLookupRequestReady intersection() {
			return setSimilarityType(AMAZON_SIMILARY_TYPE_PARAM_INTERSECTION_VALUE);
		}
		
		private AmazonApiSimilarityLookupRequestReady setSimilarityType(String value) {
			builtRequest.addParam(AMAZON_SIMILARY_TYPE_PARAM, value);
			return this;
		}
		
	}
	
	public SimilarityLookupRequestBuilder() {
		builtRequest = new Request();
		initialise();
		RequestBuilderHelper.addCommonParams(builtRequest);
	}
	
	public static SimilarityLookupRequestBuilder lookup() {
		return new SimilarityLookupRequestBuilder();
	}
	
	private void initialise() {
		builtRequest.addParam(AMAZON_OPERATION_PARAM, AMAZON_OPERATION_PARAM_ITEM_SIMILARITY);
	}
	

	public AmazonApiSimilarityLookupRequestReady forASIN(String asin) {
		return forListOfASINs(RequestBuilderHelper.putStringInNewList(asin));
	}

	public AmazonApiSimilarityLookupRequestReady forListOfASINs(List<String> asins) {
		return setItems(asins);
	}
	
	private AmazonApiSimilarityLookupRequestReady setItems(List<String> values) {
		builtRequest.addParam(AMAZON_ITEM_ID_PARAM, RequestBuilderHelper.formatListOfItemsParam(values));
		return new AmazonApiSimilarityLookupRequestReady();
	}
}
