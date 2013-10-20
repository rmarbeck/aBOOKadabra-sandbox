package com.abookadabra.utils.amazon.api;

import java.util.ArrayList;
import java.util.List;

import static com.abookadabra.utils.amazon.api.XMLDefaultParserAndLoaderHelper.tryToGetTextValueForOptionnalField;

import com.abookadabra.utils.amazon.api.models.answerelements.SimilarProduct;

import static com.abookadabra.utils.amazon.api.XMLDefaultAnswerConstants.*;

public class XMLDefaultSimilarProductsLoader {
	List<XMLAmazonNode> similarProductsNodes;

	protected static List<SimilarProduct> load(XMLAmazonNode rootToParseFrom) {
		return new XMLDefaultSimilarProductsLoader(rootToParseFrom).load();
	}
	
	protected XMLDefaultSimilarProductsLoader(XMLAmazonNode rootToParseFrom) {
		similarProductsNodes = rootToParseFrom.directChildrenOrEmpty(AMAZON_XML_FIELD_SIMILAR_PRODUCT);
	}

	private List<SimilarProduct> load() {
		if (hasAtLeastOneSimilarProduct())
			return loadSimilarProductsFound();
		return  new ArrayList<SimilarProduct>();
	}
	
	private boolean hasAtLeastOneSimilarProduct() {
		return (similarProductsNodes != null && similarProductsNodes.size() != 0); 
	}
	
	private List<SimilarProduct> loadSimilarProductsFound() {
		List<SimilarProduct> similarProducts = new ArrayList<SimilarProduct>();
		for (XMLAmazonNode currrentSimilarProductNode: similarProductsNodes) {
			SimilarProduct newSimilarProduct = new SimilarProduct(
					tryToGetTextValueForOptionnalField(currrentSimilarProductNode, AMAZON_XML_FIELD_SIMILAR_PRODUCT_ASIN),
					tryToGetTextValueForOptionnalField(currrentSimilarProductNode, AMAZON_XML_FIELD_SIMILAR_PRODUCT_TITLE));
			similarProducts.add(newSimilarProduct);
		}
		return similarProducts;
	}

	
}
