package com.abookadabra.utils.amazon.api.models;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.abookadabra.utils.amazon.api.RequestBuilderHelper;

public class Request {
	protected Map<String, String> params;
	
	public Request() {
		this.params = new ConcurrentHashMap<String, String>();
	}
	
	public Request(Map<String, String> params) {
		this.params = params;
	}
	
	public String getFullUrl() {
		return RequestBuilderHelper.getFullUrl(this);
	}
	
	public Map<String, String> getParams() {
		return params;
	}
	
	public void addParam(String key, String value) {
		params.put(key, value);
	}
}
