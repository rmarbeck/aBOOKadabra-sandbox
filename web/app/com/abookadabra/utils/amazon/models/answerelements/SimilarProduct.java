package com.abookadabra.utils.amazon.models.answerelements;

public class SimilarProduct {
	private String asin;
	private String title;
	
	public SimilarProduct(String asin, String title) {
		this.asin = asin;
		this.title = title;
	}

	public String getAsin() {
		return asin;
	}
	
	public String getTitle() {
		return title;
	}
}
