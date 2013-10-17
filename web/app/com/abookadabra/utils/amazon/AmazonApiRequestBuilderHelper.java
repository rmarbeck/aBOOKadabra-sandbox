package com.abookadabra.utils.amazon;

import java.util.ArrayList;
import java.util.List;

import play.libs.WS.WSRequestHolder;

import com.abookadabra.utils.amazon.AmazonApiRequestBuilder;
import com.abookadabra.utils.amazon.helpers.AmazonSignedRequestsHelper;
import com.abookadabra.utils.amazon.models.AmazonApiRequest;

public class AmazonApiRequestBuilderHelper {
	public static String getFullUrl(AmazonApiRequest request) {
		AmazonSignedRequestsHelper helper;
		try {
			helper = new AmazonSignedRequestsHelper();
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
		return helper.getFullUrlAsString(request.getParams());
	}
	
	public static WSRequestHolder getWSRequestHolder(AmazonApiRequest request) {
		AmazonSignedRequestsHelper helper;
		try {
			helper = new AmazonSignedRequestsHelper();
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
		return helper.getUrl(request.getParams());
	}
	
	protected static void addCommonParams(AmazonApiRequest request) {
		request.addParam(AmazonApiRequestBuilder.AMAZON_SERVICE_PARAM, AmazonApiRequestBuilder.AMAZON_SERVICE_PARAM_VALUE);
		request.addParam(AmazonApiRequestBuilder.AMAZON_ASSOCIATE_TAG_PARAM, AmazonApiRequestBuilder.AMAZON_ASSOCIATE_TAG_PARAM_VALUE);
		request.addParam(AmazonApiRequestBuilder.AMAZON_VERSION_PARAM, AmazonApiRequestBuilder.AMAZON_VERSION_PARAM_VALUE);
	}
	
	protected static String formatListOfItemsParam(List<String> values) {
		StringBuffer formattedListOfValues = new StringBuffer();
		int index = 0;
		for (String value : values) {
			if (index++ != 0)
				formattedListOfValues.append(", ");	
			formattedListOfValues.append(value);
		}
		return formattedListOfValues.toString();
	}
	
	protected static List<String> putStringInNewList(String value) {
		List<String> values = new ArrayList<String>();
		values.add(value);
		return values;
	}
}
