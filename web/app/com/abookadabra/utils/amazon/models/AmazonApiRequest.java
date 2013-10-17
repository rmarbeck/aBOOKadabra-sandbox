package com.abookadabra.utils.amazon.models;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.abookadabra.utils.amazon.AmazonApiRequestBuilderHelper;

public class AmazonApiRequest {
	protected Map<String, String> params;
	
	public AmazonApiRequest() {
		this.params = new ConcurrentHashMap<String, String>();
	}
	
	public AmazonApiRequest(Map<String, String> params) {
		this.params = params;
	}
	
	public String getFullUrl() {
		return AmazonApiRequestBuilderHelper.getFullUrl(this);
	}
	
	public Map<String, String> getParams() {
		return params;
	}
	
	public void addParam(String key, String value) {
		params.put(key, value);
	}
}
