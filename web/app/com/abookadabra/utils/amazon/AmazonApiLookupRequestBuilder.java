package com.abookadabra.utils.amazon;

import java.util.ArrayList;
import java.util.List;

import com.abookadabra.utils.amazon.models.AmazonApiRequest;

public class AmazonApiLookupRequestBuilder implements AmazonApiRequestBuilder {
	private AmazonApiRequest builtRequest;
	
	public class AmazonApiLookupRequestReady extends AmazonApiRequestReady {
		protected AmazonApiLookupRequestReady() {
			super(builtRequest);
		}
	}
	
	public AmazonApiLookupRequestBuilder() {
		builtRequest = new AmazonApiRequest();
		initialise();
		AmazonApiRequestBuilderHelper.addCommonParams(builtRequest);
	}
	
	public static AmazonApiLookupRequestBuilder lookup() {
		return new AmazonApiLookupRequestBuilder();
	}
	
	private void initialise() {
		builtRequest.addParam(AMAZON_OPERATION_PARAM, AMAZON_OPERATION_PARAM_ITEM_LOOKUP);
	}
	

	public AmazonApiLookupRequestReady forASIN(String asin) {
		return forListOfASINs(AmazonApiRequestBuilderHelper.putStringInNewList(asin));
	}
	
	public AmazonApiLookupRequestReady forIsbn(String isbn) {
		return forListOfIsbns(AmazonApiRequestBuilderHelper.putStringInNewList(isbn));
	}
	
	public AmazonApiLookupRequestReady forEAN(String ean) {
		return forListOfEANs(AmazonApiRequestBuilderHelper.putStringInNewList(ean));
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
		builtRequest.addParam(AMAZON_ITEM_ID_PARAM, AmazonApiRequestBuilderHelper.formatListOfItemsParam(values));
		return new AmazonApiLookupRequestReady();
	}
	
	private boolean guessIfItIsAnIsbn(String eanOrIsbn) {
		if (eanOrIsbn.length() == 10)
			return true;
		return false;
	}
}
