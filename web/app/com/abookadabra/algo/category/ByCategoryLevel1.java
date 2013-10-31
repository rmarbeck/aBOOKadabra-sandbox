package com.abookadabra.algo.category;

import models.BookCategory;

import com.abookadabra.utils.StringHelper;

public class ByCategoryLevel1 {
	private String request;
	private BookCategory categoryFound;
	
	private ByCategoryLevel1(String request) {
		this.request = request;
	}
	
	public static Object start(String request) throws Exception {
		return new ByCategoryLevel1(request).start();
	}
	
	private Object start() throws Exception {
		checkRequestOrThrowException();
		if (canWeFindACategoryMatchingThisRequest()) {
			return ByCategoryLevel2.start(categoryFound);
		} else {
			// TODO
			return null;
			//return DefaultLevel2.start(request);
		}
	}
	
	private void checkRequestOrThrowException() throws Exception {
		if (isItAnInvalidRequest())
			throw new Exception("Request is invalid");
	}
	
	private boolean isItAnInvalidRequest() {
		return StringHelper.isStringEmptyOrNull(request);
	}
	
	private boolean canWeFindACategoryMatchingThisRequest() {
		// TODO
		return true;
	}
}
