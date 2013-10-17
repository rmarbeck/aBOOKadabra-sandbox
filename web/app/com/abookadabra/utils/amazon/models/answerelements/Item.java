package com.abookadabra.utils.amazon.models.answerelements;

import java.util.*;

public class Item {
	private String asin;
	private String detailPageUrl;
	private long salesRank;
	private String editorialReview;
	private List<ItemImage> images;
	private Attributes attributes;
	private List<BrowseNode> browseNodes;
	private List<SimilarProduct> similarProducts;

	private static String AMAZON_BOOK_PRODUCT_TYPE = "ABIS_BOOK";
	
	public Item() {
		images = new ArrayList<ItemImage>();
		attributes = new Attributes();
	}
	
	public boolean isABook() {
		if (AMAZON_BOOK_PRODUCT_TYPE.equals(attributes.getProductTypeName()))
				return true;
		return false;
	}
	
	public Book asBook() {
		return (Book) this;
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public String getDetailPageUrl() {
		return detailPageUrl;
	}

	public void setDetailPageUrl(String detailPageUrl) {
		this.detailPageUrl = detailPageUrl;
	}

	public long getSalesRank() {
		return salesRank;
	}

	public void setSalesRank(long salesRank) {
		this.salesRank = salesRank;
	}

	public List<ItemImage> getImages() {
		return images;
	}

	public void setImages(List<ItemImage> images) {
		this.images = images;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	public String getEditorialReview() {
		return editorialReview;
	}

	public void setEditorialReview(String editorialReview) {
		this.editorialReview = editorialReview;
	}

	public List<BrowseNode> getBrowseNodes() {
		return browseNodes;
	}

	public void setBrowseNodes(List<BrowseNode> browseNodes) {
		this.browseNodes = browseNodes;
	}

	public List<SimilarProduct> getSimilarProducts() {
		return similarProducts;
	}

	public void setSimilarProducts(List<SimilarProduct> similarProducts) {
		this.similarProducts = similarProducts;
	}

}
