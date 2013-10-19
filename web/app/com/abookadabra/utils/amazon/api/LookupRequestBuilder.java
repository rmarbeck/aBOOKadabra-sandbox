package com.abookadabra.utils.amazon.api;

import java.util.ArrayList;
import java.util.List;

import com.abookadabra.utils.amazon.api.models.Request;

public class LookupRequestBuilder implements RequestBuilder {
	private Request builtRequest;
	
	public class AmazonApiLookupRequestReady extends RequestReady {
		protected AmazonApiLookupRequestReady() {
			super(builtRequest);
		}
	}
	
	public LookupRequestBuilder() {
		builtRequest = new Request();
		initialise();
		RequestBuilderHelper.addCommonParams(builtRequest);
	}
	
	public static LookupRequestBuilder lookup() {
		return new LookupRequestBuilder();
	}
	
	private void initialise() {
		builtRequest.addParam(AMAZON_OPERATION_PARAM, AMAZON_OPERATION_PARAM_ITEM_LOOKUP);
	}
	

	public AmazonApiLookupRequestReady forASIN(String asin) {
		return forListOfASINs(RequestBuilderHelper.putStringInNewList(asin));
	}
	
	public AmazonApiLookupRequestReady forIsbn(String isbn) {
		return forListOfIsbns(RequestBuilderHelper.putStringInNewList(isbn));
	}
	
	public AmazonApiLookupRequestReady forEAN(String ean) {
		return forListOfEANs(RequestBuilderHelper.putStringInNewList(ean));
	}
	
	public AmazonApiLookupRequestReady forEANorIsbn(String eanOrIsbn) {
		if (guessIfItIsAnIsbn(eanOrIsbn))
			return forIsbn(eanOrIsbn);
		return forEAN(eanOrIsbn);
	}

	public AmazonApiLookupRequestReady forListOfASINs(List<String> asins) {
		return setItems(AMAZON_ID_TYPE_PARAM_ASIN_VALUE, asins);
	}
	
	public AmazonApiLookupRequestReady forListOfIsbns(List<String> isbns) {
		return setItems(AMAZON_ID_TYPE_PARAM_ISBN_VALUE, isbns);
	}
	
	public AmazonApiLookupRequestReady forListOfEANs(List<String> eans) {
		return setItems(AMAZON_ID_TYPE_PARAM_EAN_VALUE, eans);
	}
	
	private AmazonApiLookupRequestReady setItems(String key, List<String> values) {
		builtRequest.addParam(AMAZON_ID_TYPE_PARAM, key);
		builtRequest.addParam(AMAZON_ITEM_ID_PARAM, RequestBuilderHelper.formatListOfItemsParam(values));
		return new AmazonApiLookupRequestReady();
	}
	
	private boolean guessIfItIsAnIsbn(String eanOrIsbn) {
		if (eanOrIsbn.length() == 10)
			return true;
		return false;
	}
}
