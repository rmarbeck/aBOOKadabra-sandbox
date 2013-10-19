package com.abookadabra.utils.amazon.api;

import java.util.List;

import static com.abookadabra.utils.amazon.api.RequestBuilderConstants.*;

public class LookupRequestBuilder extends RequestBuilder {
	
	public class LookupRequestReady extends RequestReady {
		protected LookupRequestReady() {
			super(builtRequest);
		}
	}
	
	public static LookupRequestBuilder build() {
		return new LookupRequestBuilder();
	}
	
	protected void initialise() {
		builtRequest.addParam(AMAZON_OPERATION_PARAM, AMAZON_OPERATION_PARAM_ITEM_LOOKUP);
	}
	
	public LookupRequestReady forASIN(String asin) {
		return forListOfASINs(RequestBuilderHelper.putStringInNewList(asin));
	}
	
	public LookupRequestReady forIsbn(String isbn) {
		return forListOfIsbns(RequestBuilderHelper.putStringInNewList(isbn));
	}
	
	public LookupRequestReady forEAN(String ean) {
		return forListOfEANs(RequestBuilderHelper.putStringInNewList(ean));
	}
	
	public LookupRequestReady forEANorIsbn(String eanOrIsbn) {
		if (guessIfItIsAnIsbn(eanOrIsbn))
			return forIsbn(eanOrIsbn);
		return forEAN(eanOrIsbn);
	}

	public LookupRequestReady forListOfASINs(List<String> asins) {
		return setItems(AMAZON_ID_TYPE_PARAM_ASIN_VALUE, asins);
	}
	
	public LookupRequestReady forListOfIsbns(List<String> isbns) {
		return setItems(AMAZON_ID_TYPE_PARAM_ISBN_VALUE, isbns);
	}
	
	public LookupRequestReady forListOfEANs(List<String> eans) {
		return setItems(AMAZON_ID_TYPE_PARAM_EAN_VALUE, eans);
	}
	
	private LookupRequestReady setItems(String key, List<String> values) {
		builtRequest.addParam(AMAZON_ID_TYPE_PARAM, key);
		builtRequest.addParam(AMAZON_ITEM_ID_PARAM, RequestBuilderHelper.formatListOfItemsParam(values));
		return new LookupRequestReady();
	}
	
	private boolean guessIfItIsAnIsbn(String eanOrIsbn) {
		if (eanOrIsbn.length() == 10)
			return true;
		return false;
	}
}
