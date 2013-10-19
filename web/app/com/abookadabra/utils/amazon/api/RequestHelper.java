package com.abookadabra.utils.amazon.api;

import play.libs.WS.WSRequestHolder;

public class RequestHelper {
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
}
