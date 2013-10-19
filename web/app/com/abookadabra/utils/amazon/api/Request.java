package com.abookadabra.utils.amazon.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Request {
	protected Map<String, String> params;
	
	protected Request() {
		this.params = new ConcurrentHashMap<String, String>();
	}
	
	protected Request(Map<String, String> params) {
		this.params = params;
	}
	
	public String getFullUrl() {
		return RequestHelper.getFullUrl(this);
	}
	
	public Map<String, String> getParams() {
		return params;
	}
	
	protected void addParam(String key, String value) {
		params.put(key, value);
	}
}
