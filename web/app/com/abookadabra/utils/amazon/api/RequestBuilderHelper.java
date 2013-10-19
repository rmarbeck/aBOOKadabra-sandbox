package com.abookadabra.utils.amazon.api;

import java.util.ArrayList;
import java.util.List;

import play.libs.WS.WSRequestHolder;

import com.abookadabra.utils.amazon.api.RequestBuilder;
import com.abookadabra.utils.amazon.api.helpers.AmazonSignedRequestsHelper;
import com.abookadabra.utils.amazon.api.models.Request;

public class RequestBuilderHelper {
	public static String getFullUrl(Request request) {
		AmazonSignedRequestsHelper helper;
		try {
			helper = new AmazonSignedRequestsHelper();
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
		return helper.getFullUrlAsString(request.getParams());
	}
	
	public static WSRequestHolder getWSRequestHolder(Request request) {
		AmazonSignedRequestsHelper helper;
		try {
			helper = new AmazonSignedRequestsHelper();
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
		return helper.getUrl(request.getParams());
	}
	
	protected static void addCommonParams(Request request) {
		request.addParam(RequestBuilder.AMAZON_SERVICE_PARAM, RequestBuilder.AMAZON_SERVICE_PARAM_VALUE);
		request.addParam(RequestBuilder.AMAZON_ASSOCIATE_TAG_PARAM, RequestBuilder.AMAZON_ASSOCIATE_TAG_PARAM_VALUE);
		request.addParam(RequestBuilder.AMAZON_VERSION_PARAM, RequestBuilder.AMAZON_VERSION_PARAM_VALUE);
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
