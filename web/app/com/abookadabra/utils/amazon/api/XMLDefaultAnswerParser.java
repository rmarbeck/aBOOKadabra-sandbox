package com.abookadabra.utils.amazon.api;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Document;

import com.abookadabra.utils.amazon.api.XMLAmazonNode.NodeNotFoundException;
import com.abookadabra.utils.amazon.api.models.answerelements.*;
import com.abookadabra.utils.amazon.api.models.answerelements.Attributes.ListPrice;

import static com.abookadabra.utils.amazon.api.XMLDefaultAnswerConstants.*;

public class XMLDefaultAnswerParser implements AnswerParser {
	
	private Document rootOfXmlToParse;	

	@Override
	public void initialise(Object o) throws UnableToLoadThisKindOfObject {
		if (isNotaDocument(o))
			throw new UnableToLoadThisKindOfObject(o.getClass().getName());
		rootOfXmlToParse = (Document) o;
	}
	
	private boolean isNotaDocument(Object o) {
		return !(o instanceof Document);
	}
	
	public void load(Document xmlDocumentToParse) {
		rootOfXmlToParse = xmlDocumentToParse;
		normalizeDocument();
	}
	
	private void normalizeDocument() {
		rootOfXmlToParse.getDocumentElement().normalize();
	}
	
	public void checkRequestSummaryForErrors() throws Exception {
		if (hasErrors() || !isValid())
			throw new Exception();
	}
	
	public boolean hasErrors() {
		try {
			List<XMLAmazonNode> containsErrors = XMLAmazonNode.wrap(rootOfXmlToParse).children(AMAZON_XML_FIELD_ERROR);
			if (containsErrors.size() > 0)
				return true;
			return false;
		} catch (NodeNotFoundException e) {
			return true;
		}
	}
	
	public boolean isValid() {
		try {
			XMLAmazonNode isValid = XMLAmazonNode.wrap(rootOfXmlToParse).child(AMAZON_XML_FIELD_IS_VALID);
			if (isValid.retrieveTextValue().equalsIgnoreCase("true"))
				return true;
			return false;
		} catch (NodeNotFoundException e) {
			return false;
		}
	}
	
	public Item loadItem() throws Exception {
		return loadItem(XMLAmazonNode.wrap(rootOfXmlToParse).child(AMAZON_XML_FIELD_ITEM));
	}
	
	public List<Item> loadItems() throws Exception  {
		List<Item> items = new ArrayList<Item>();
		try {
			List<XMLAmazonNode> itemNodes = XMLAmazonNode.wrap(rootOfXmlToParse).children(AMAZON_XML_FIELD_ITEM);
			for (XMLAmazonNode itemNode: itemNodes) {
				items.add(loadItem(itemNode));
			}
			return items;	
		} catch (Exception e) {
			throw new Exception("Unable to load an items, root cause is :"+e.getMessage());
		}
	}
	
	private Item loadItem(XMLAmazonNode rootNodeWhereIsItem) throws Exception {
		try {
			Item item = new Item();
			loadAsinOrRaiseException(rootNodeWhereIsItem, item);
			loadFirstLevelFields(rootNodeWhereIsItem, item);
			loadAttributes(rootNodeWhereIsItem, item);
			tryToLoadEditorialReview(rootNodeWhereIsItem, item);
			//loadImages(node, item);
			tryToLoadSimilarProducts(rootNodeWhereIsItem, item);
			tryToLoadBrowseNodes(rootNodeWhereIsItem, item);
			return item;	
		} catch (Exception e) {
			throw new Exception("Unable to load an item, root cause is :"+e.getMessage());
		}
		
	}
	
	public List<BrowseNode> loadBrowseNodes() {
		return XMLDefaultBrowseNodeLoader.load(XMLAmazonNode.wrap(rootOfXmlToParse));
	}
	
	private static void loadAsinOrRaiseException(XMLAmazonNode node, Item item) throws NodeNotFoundException {
		item.setAsin(node.child(AMAZON_XML_FIELD_ASIN).firstChild().retrieveTextValue());
	}
	
	private static void loadFirstLevelFields(XMLAmazonNode node, Item item) throws Exception {
		item.setSalesRank(node.childOrEmpty(AMAZON_XML_FIELD_SALES_RANK).retrieveIntValue());
		item.setDetailPageUrl(node.child(AMAZON_XML_FIELD_GOTO_URL).retrieveTextValue());
	}
	
	private static void loadAttributes(XMLAmazonNode itemNode, Item item) throws Exception {
		try {
			XMLAmazonNode aNode = itemNode.child(AMAZON_XML_FIELD_ATTRIBUTES);
			Attributes attributes = new Attributes();
			tryToLoadSimpleAttributes(aNode, attributes);
			tryToLoadComplexAttributes(aNode, attributes);
			item.setAttributes(attributes);
		} catch (NodeNotFoundException e) {
			throw new Exception("No attributes fields :"+e.getMessage());
		}
		
	}
	
	public RequestInAnswer loadRequest() {
		try {
			XMLAmazonNode request = XMLAmazonNode.wrap(rootOfXmlToParse).child(AMAZON_XML_FIELD_REQUEST);
			return loadRequestContent(request);
		} catch (Exception e) {
			return RequestInAnswer.createInvalidRequest();
		}
	}
	
	private RequestInAnswer loadRequestContent(XMLAmazonNode request) throws Exception {
		return XMLDefaultRequestLoaderFactory.getLoaderFromRequestNode(request).load();
	}

	public Arguments loadArguments() {
		//TODO : Not implemented yet
		return null;
	}

	public long loadTotalResults() {
		return XMLAmazonNode.wrap(rootOfXmlToParse).childOrEmpty(AMAZON_XML_FIELD_TOTAL_RESULTS).retrieveLongValue(); 
	}

	public long loadTotalPages() {
		return XMLAmazonNode.wrap(rootOfXmlToParse).childOrEmpty(AMAZON_XML_FIELD_TOTAL_PAGES).retrieveLongValue(); 
	}
	
	private static void tryToLoadEditorialReview(XMLAmazonNode node, Item item) {
		XMLAmazonNode firstReview = node.childOrEmpty(AMAZON_XML_FIELD_EDITOR_REVIEWS).firstChildOrEmpty();
		item.setEditorialReview(firstReview.childOrEmpty(AMAZON_XML_FIELD_EDITOR_REVIEW_CONTENT).retrieveTextValue());
	}
	
	private static void tryToLoadLowestPrice(XMLAmazonNode node, Item item) {
		//TODO
		XMLAmazonNode lowestPrice = node.childOrEmpty(AMAZON_XML_FIELD_PRICE);
		//item.setLowestPrice(lowestPrice.child(AMAZON_XML_FIELD_PRICE_AMOUNT).retrieveLongValue()/100);
	}
	
	private static void tryToLoadImages(XMLAmazonNode node, Item item) {
		//TODO
		List<ItemImage> images = new ArrayList<ItemImage>();
		//List<XMLAmazonNode> values = node.children(AMazon);
		
		XMLAmazonNode lowestPrice = node.childOrEmpty(AMAZON_XML_FIELD_PRICE);
		//item.setLowestPrice(lowestPrice.child(AMAZON_XML_FIELD_PRICE_AMOUNT).retrieveLongValue()/100);
	}
	
	
	private static void tryToLoadSimpleAttributes(XMLAmazonNode node, Attributes attributes) {
		attributes.setTitle(tryToGetTextValueForOptionnalField(node, AMAZON_XML_FIELD_TITLE));
		attributes.setBinding(tryToGetTextValueForOptionnalField(node, AMAZON_XML_FIELD_BINDING));
		attributes.setEan(tryToGetTextValueForOptionnalField(node, AMAZON_XML_FIELD_EAN));
		attributes.setIsbn(tryToGetTextValueForOptionnalField(node, AMAZON_XML_FIELD_ISBN));
		attributes.setEdition(tryToGetTextValueForOptionnalField(node, AMAZON_XML_FIELD_EDITION));
		attributes.setLabel(tryToGetTextValueForOptionnalField(node, AMAZON_XML_FIELD_LABEL));
		attributes.setManufacturer(tryToGetTextValueForOptionnalField(node, AMAZON_XML_FIELD_MANUFACTURER));
		attributes.setPublisher(tryToGetTextValueForOptionnalField(node, AMAZON_XML_FIELD_PUBLISHER));
		attributes.setProductGroup(tryToGetTextValueForOptionnalField(node, AMAZON_XML_FIELD_PRODUCT_GROUP));
		attributes.setProductTypeName(tryToGetTextValueForOptionnalField(node, AMAZON_XML_FIELD_PRODUCT_TYPE_NAME));
		attributes.setSku(tryToGetTextValueForOptionnalField(node, AMAZON_XML_FIELD_SKU));
		attributes.setStudio(tryToGetTextValueForOptionnalField(node, AMAZON_XML_FIELD_STUDIO));

		attributes.setNumberOfPages(
				node.childOrEmpty(AMAZON_XML_FIELD_NB_PAGES).retrieveLongValue());
		
	}
	
	private static void tryToLoadComplexAttributes(XMLAmazonNode node, Attributes attributes) {
		attributes.setPublicationDate(tryToRetrieveDate(node));
		attributes.setLanguages(tryToRetrieveLanguages(node));
		attributes.setListPrice(tryToRetrieveListPrice(node));
		attributes.setAuthors(tryToRetrieveAuthors(node));
	}
	
	private static List<String> tryToRetrieveAuthors(XMLAmazonNode node) {
		List<String> authors = new ArrayList<String>();
		List<XMLAmazonNode> values = node.childrenOrEmpty(AMAZON_XML_FIELD_WRITER);
		for (XMLAmazonNode currrentAuthor: values) {
			authors.add(currrentAuthor.retrieveTextValue());
		}
		return authors;
	}
	
	private static List<Attributes.Language> tryToRetrieveLanguages(XMLAmazonNode node) {
		List<Attributes.Language> languages = new ArrayList<Attributes.Language>();
		List<XMLAmazonNode> languagesNodes = node.childrenOrEmpty(AMAZON_XML_FIELD_LANGUAGE);
		for (XMLAmazonNode currrentLanguageNode: languagesNodes) {
			Attributes.Language newLanguage = new Attributes.Language(
					tryToGetTextValueForOptionnalField(currrentLanguageNode, AMAZON_XML_FIELD_LANGUAGE_NAME),
					tryToGetTextValueForOptionnalField(currrentLanguageNode, AMAZON_XML_FIELD_LANGUAGE_TYPE));
			
			languages.add(newLanguage);
		}
		return languages;
	}
	
	private static Attributes.ListPrice tryToRetrieveListPrice(XMLAmazonNode node) {
		XMLAmazonNode listPriceNode = node.childOrEmpty(AMAZON_XML_FIELD_LIST_PRICE);
		ListPrice newPrice = new ListPrice(
				tryToGetTextValueForOptionnalField(listPriceNode, AMAZON_XML_FIELD_LIST_PRICE_AMOUNT),
				tryToGetTextValueForOptionnalField(listPriceNode, AMAZON_XML_FIELD_LIST_PRICE_CURRENCY_CODE),
				tryToGetTextValueForOptionnalField(listPriceNode, AMAZON_XML_FIELD_LIST_PRICE_FORMATTED));
		return newPrice;
	}
	
	private static Date tryToRetrieveDate(XMLAmazonNode node) {
		SimpleDateFormat sdf = new SimpleDateFormat(AMAZON_DATE_FORMAT);
		return sdf.parse(tryToGetTextValueForOptionnalField(node, AMAZON_XML_FIELD_PUBLICATION_DATE), new ParsePosition(0));
	}

	private static String tryToGetTextValueForOptionnalField(XMLAmazonNode node, String key) {
		return node.childOrEmpty(key).retrieveTextValue();
	}
	
	private static void tryToLoadSimilarProducts(XMLAmazonNode itemNode, Item item) {
		List<XMLAmazonNode> similarProductsNodes = itemNode.childrenOrEmpty(AMAZON_XML_FIELD_SIMILAR_PRODUCT);
		if (hasAtLeastOneSimilarProduct(similarProductsNodes)) {
			item.setSimilarProducts(loadSimilarProductsFound(similarProductsNodes));
		} else {
			item.setSimilarProducts(new ArrayList<SimilarProduct>());
		}
	}
	
	private static boolean hasAtLeastOneSimilarProduct(List<XMLAmazonNode> similarProductsNodes) {
		return (similarProductsNodes!= null && similarProductsNodes.size() != 0); 
	}
	
	private static List<SimilarProduct> loadSimilarProductsFound(List<XMLAmazonNode> similarProductsNodes) {
		List<SimilarProduct> similarProducts = new ArrayList<SimilarProduct>();
		for (XMLAmazonNode currrentSimilarProductNode: similarProductsNodes) {
			SimilarProduct newSimilarProduct = new SimilarProduct(
					tryToGetTextValueForOptionnalField(currrentSimilarProductNode, AMAZON_XML_FIELD_SIMILAR_PRODUCT_ASIN),
					tryToGetTextValueForOptionnalField(currrentSimilarProductNode, AMAZON_XML_FIELD_SIMILAR_PRODUCT_TITLE));
			similarProducts.add(newSimilarProduct);
		}
		return similarProducts;
	}

	
	private static void tryToLoadBrowseNodes(XMLAmazonNode itemNode, Item item) {
		item.setBrowseNodes(XMLDefaultBrowseNodeLoader.load(itemNode));
	}	
}
