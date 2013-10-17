package com.abookadabra.utils.amazon;

import java.util.List;

import com.abookadabra.utils.amazon.models.AmazonApiRequest;

public class AmazonApiSimilarityLookupRequestBuilder implements AmazonApiRequestBuilder {
	private AmazonApiRequest builtRequest;
	
	public class AmazonApiSimilarityLookupRequestReady extends AmazonApiRequestReady {
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
	
	public AmazonApiSimilarityLookupRequestBuilder() {
		builtRequest = new AmazonApiRequest();
		initialise();
		AmazonApiRequestBuilderHelper.addCommonParams(builtRequest);
	}
	
	public static AmazonApiSimilarityLookupRequestBuilder lookup() {
		return new AmazonApiSimilarityLookupRequestBuilder();
	}
	
	private void initialise() {
		builtRequest.addParam(AMAZON_OPERATION_PARAM, AMAZON_OPERATION_PARAM_ITEM_SIMILARITY);
	}
	

	public AmazonApiSimilarityLookupRequestReady forASIN(String asin) {
		return forListOfASINs(AmazonApiRequestBuilderHelper.putStringInNewList(asin));
	}

	public AmazonApiSimilarityLookupRequestReady forListOfASINs(List<String> asins) {
		return setItems(asins);
	}
	
	private AmazonApiSimilarityLookupRequestReady setItems(List<String> values) {
		builtRequest.addParam(AMAZON_ITEM_ID_PARAM, AmazonApiRequestBuilderHelper.formatListOfItemsParam(values));
		return new AmazonApiSimilarityLookupRequestReady();
	}
}
