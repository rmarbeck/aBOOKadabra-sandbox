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

import static com.abookadabra.utils.amazon.api.XMLDefaultParserAndLoaderHelper.tryToGetTextValueForOptionnalField;
import static com.abookadabra.utils.amazon.api.XMLDefaultParserAndLoaderHelper.getTextValueForMandatoryField;
import static com.abookadabra.utils.amazon.api.XMLDefaultAnswerConstants.*;

public class XMLDefaultAnswerParser implements AnswerParser {
	
	private XMLAmazonNode rootNode;

	@Override
	public void initialise(Object o) throws UnableToLoadThisKindOfObject {
		if (isNotaDocument(o))
			throw new UnableToLoadThisKindOfObject(o.getClass().getName());
		Document rootOfXmlToParse = (Document) o;
		normalizeDocument(rootOfXmlToParse);
		rootNode = XMLAmazonNode.wrap(rootOfXmlToParse);
	}
	
	private boolean isNotaDocument(Object o) {
		return !(o instanceof Document);
	}
	
	private static void normalizeDocument(Document rootOfXmlToParse) {
		rootOfXmlToParse.getDocumentElement().normalize();
	}
	
	
	public RequestInAnswer loadRequest() {
		try {
			XMLAmazonNode request = rootNode.child(AMAZON_XML_FIELD_REQUEST);
			return loadRequestContent(request);
		} catch (Exception e) {
			return RequestInAnswer.createInvalidRequest();
		}
	}
	
	private RequestInAnswer loadRequestContent(XMLAmazonNode request) throws Exception {
		return XMLDefaultRequestLoaderFactory.getLoaderFromRequestNode(request).load();
	}
	
	public ErrorInAnswer loadError() {
		try {
			XMLAmazonNode error = rootNode.child(AMAZON_XML_FIELD_ERRORS);
			return loadErrorContent(error);
		} catch (Exception e) {
			return ErrorInAnswer.createNoError();
		}
	}
	
	private ErrorInAnswer loadErrorContent(XMLAmazonNode error) throws Exception {
		XMLAmazonNode errorNode = error.childOrEmpty(AMAZON_XML_FIELD_ERROR);
		ErrorInAnswer newError = new ErrorInAnswer(
				getTextValueForMandatoryField(errorNode, AMAZON_XML_FIELD_ERROR_CODE),
				getTextValueForMandatoryField(errorNode, AMAZON_XML_FIELD_ERROR_MESSAGE));
		return newError;
	}
	
	public boolean hasErrors() {
		try {
			List<XMLAmazonNode> containsErrors = rootNode.children(AMAZON_XML_FIELD_ERRORS);
			if (containsErrors.size() > 0)
				return true;
			return false;
		} catch (NodeNotFoundException e) {
			return true;
		}
	}
	
	public boolean isValid() {
		try {
			XMLAmazonNode isValid = rootNode.child(AMAZON_XML_FIELD_IS_VALID);
			if (isValid.retrieveTextValue().equalsIgnoreCase("true"))
				return true;
			return false;
		} catch (NodeNotFoundException e) {
			return false;
		}
	}
	
	public Item loadItem() throws Exception {
		return loadItem(rootNode.child(AMAZON_XML_FIELD_ITEM));
	}
	
	public List<Item> loadItems() throws Exception  {
		List<Item> items = new ArrayList<Item>();
		try {
			List<XMLAmazonNode> itemNodes = rootNode.children(AMAZON_XML_FIELD_ITEM);
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
			loadFirstLevelFieldsOrRaiseException(rootNodeWhereIsItem, item);
			loadAttributesOrRaiseException(rootNodeWhereIsItem, item);
			tryToLoadEditorialReview(rootNodeWhereIsItem, item);
			//loadImages(node, item);
			tryToLoadSimilarProducts(rootNodeWhereIsItem, item);
			tryToLoadBrowseNodes(rootNodeWhereIsItem, item);
			return item;	
		} catch (Exception e) {
			throw new Exception("Unable to load an item, root cause is <- "+e.getMessage());
		}
		
	}
	
	public List<BrowseNode> loadBrowseNodes() {
		return XMLDefaultBrowseNodeLoader.load(rootNode);
	}
	
	private static void loadAsinOrRaiseException(XMLAmazonNode node, Item item) throws Exception {
		try {
			item.setAsin(node.child(AMAZON_XML_FIELD_ASIN).firstChild().retrieveTextValue());
		} catch (NodeNotFoundException e) {
			throw new Exception("No ASIN for this item <- "+e.getMessage());
		}
	}
	
	private static void loadFirstLevelFieldsOrRaiseException(XMLAmazonNode node, Item item)  throws Exception {
		item.setSalesRank(node.childOrEmpty(AMAZON_XML_FIELD_SALES_RANK).retrieveIntValue());
		try {
			item.setDetailPageUrl(node.child(AMAZON_XML_FIELD_GOTO_URL).retrieveTextValue());
		} catch (NodeNotFoundException e) {
			throw new Exception("No Detail URL for this item <- "+e.getMessage());
		}
	}
	
	private static void loadAttributesOrRaiseException(XMLAmazonNode itemNode, Item item) throws Exception {
		try {
			XMLAmazonNode aNode = itemNode.child(AMAZON_XML_FIELD_ATTRIBUTES);
			Attributes attributes = new Attributes();
			tryToLoadSimpleAttributes(aNode, attributes);
			tryToLoadComplexAttributes(aNode, attributes);
			item.setAttributes(attributes);
		} catch (NodeNotFoundException e) {
			throw new Exception("No attributes fields <- "+e.getMessage());
		}
		
	}

	public Arguments loadArguments() {
		//TODO : Not implemented yet
		return null;
	}

	public long loadTotalResults() {
		return rootNode.childOrEmpty(AMAZON_XML_FIELD_TOTAL_RESULTS).retrieveLongValue(); 
	}

	public long loadTotalPages() {
		return rootNode.childOrEmpty(AMAZON_XML_FIELD_TOTAL_PAGES).retrieveLongValue(); 
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
		attributes.setPublicationDate(tryToRetrievePublicationDate(node));
		attributes.setReleaseDate(tryToRetrieveReleaseDate(node));
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
	
	private static Date tryToRetrievePublicationDate(XMLAmazonNode node) {
		return tryToRetrieveDate(node, AMAZON_XML_FIELD_PUBLICATION_DATE);
	}
	
	private static Date tryToRetrieveReleaseDate(XMLAmazonNode node) {
		return tryToRetrieveDate(node, AMAZON_XML_FIELD_RELEASE_DATE);
	}
	
	private static Date tryToRetrieveDate(XMLAmazonNode node, String key) {
		SimpleDateFormat sdf = new SimpleDateFormat(AMAZON_DATE_FORMAT);
		return sdf.parse(tryToGetTextValueForOptionnalField(node, key), new ParsePosition(0));
	}
	
	private static void tryToLoadSimilarProducts(XMLAmazonNode itemNode, Item item) {
		item.setSimilarProducts(XMLDefaultSimilarProductsLoader.load(itemNode));
	}
	
	private static void tryToLoadBrowseNodes(XMLAmazonNode itemNode, Item item) {
		item.setBrowseNodes(XMLDefaultBrowseNodeLoader.load(itemNode));
	}
	
}
