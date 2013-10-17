package com.abookadabra.utils.amazon;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Document;

import com.abookadabra.utils.amazon.XMLAmazonNode.NodeNotFoundException;
import com.abookadabra.utils.amazon.models.answerelements.Arguments;
import com.abookadabra.utils.amazon.models.answerelements.BrowseNodeRequest;
import com.abookadabra.utils.amazon.models.answerelements.Request;
import com.abookadabra.utils.amazon.models.answerelements.Attributes;
import com.abookadabra.utils.amazon.models.answerelements.BrowseNode;
import com.abookadabra.utils.amazon.models.answerelements.Item;
import com.abookadabra.utils.amazon.models.answerelements.ItemImage;
import com.abookadabra.utils.amazon.models.answerelements.ItemLookupRequest;
import com.abookadabra.utils.amazon.models.answerelements.ItemSearchRequest;
import com.abookadabra.utils.amazon.models.answerelements.SimilarProduct;
import com.abookadabra.utils.amazon.models.answerelements.Attributes.ListPrice;
import com.abookadabra.utils.amazon.models.answerelements.SimilarityLookupRequest;

public class XMLDefaultAmazonApiParser implements AmazonApiParser {
	private Document rootOfXmlToParse;
	
	private static String AMAZON_DATE_FORMAT 						= "yyyy-MM-dd";
	
	private static String AMAZON_XML_FIELD_IS_VALID					= "IsValid";
	private static String AMAZON_XML_FIELD_ERROR 					= "Errors";
	private static String AMAZON_XML_FIELD_ITEMS 					= "Items";
	private static String AMAZON_XML_FIELD_ITEM 					= "Item";
	
	private static String AMAZON_XML_FIELD_ASIN						= "ASIN";
	private static String AMAZON_XML_FIELD_SALES_RANK				= "SalesRank";
	private static String AMAZON_XML_FIELD_ATTRIBUTES				= "ItemAttributes";
	private static String AMAZON_XML_FIELD_LABEL					= "Label";
	private static String AMAZON_XML_FIELD_WRITER 					= "Author";
	private static String AMAZON_XML_FIELD_ISBN   					= "ISBN";
	private static String AMAZON_XML_FIELD_EAN   					= "EAN";
	private static String AMAZON_XML_FIELD_TITLE 					= "Title";
	private static String AMAZON_XML_FIELD_PUBLISHER 				= "Publisher";
	private static String AMAZON_XML_FIELD_GOTO_URL 				= "DetailPageURL";
	private static String AMAZON_XML_FIELD_LARGE_THUMB 				= "LargeImage";
	private static String AMAZON_XML_FIELD_LARGE_THUMB_URL 			= "URL";
	private static String AMAZON_XML_FIELD_NB_PAGES 				= "NumberOfPages";
	private static String AMAZON_XML_FIELD_PUBLICATION_DATE 		= "PublicationDate";
	private static String AMAZON_XML_FIELD_PRICE					= "LowestNewPrice";
	private static String AMAZON_XML_FIELD_PRICE_AMOUNT 			= "Amount";
	private static String AMAZON_XML_FIELD_MANUFACTURER 			= "Manufacturer";
	private static String AMAZON_XML_FIELD_BINDING 					= "Binding";
	private static String AMAZON_XML_FIELD_EDITION 					= "Edition";
	private static String AMAZON_XML_FIELD_PRODUCT_TYPE_NAME		= "ProductTypeName";
	private static String AMAZON_XML_FIELD_PRODUCT_GROUP			= "ProductGroup";
	private static String AMAZON_XML_FIELD_SKU	 					= "SKU";
	private static String AMAZON_XML_FIELD_STUDIO 					= "Studio";
	private static String AMAZON_XML_FIELD_LANGUAGE					= "Language";
	private static String AMAZON_XML_FIELD_LANGUAGE_NAME			= "Name";
	private static String AMAZON_XML_FIELD_LANGUAGE_TYPE			= "Type";
	private static String AMAZON_XML_FIELD_LIST_PRICE				= "ListPrice";
	private static String AMAZON_XML_FIELD_LIST_PRICE_AMOUNT		= "Amount";
	private static String AMAZON_XML_FIELD_LIST_PRICE_CURRENCY_CODE	= "CurrencyCode";
	private static String AMAZON_XML_FIELD_LIST_PRICE_FORMATTED		= "FormattedPrice";
	
	private static String AMAZON_XML_FIELD_REQUEST					= "Request";
	private static String AMAZON_XML_FIELD_REQUEST_SEARCH			= "ItemSearchRequest";
	private static String AMAZON_XML_FIELD_REQUEST_SEARCH_ITEM_PAGE	= "ItemPage";
	private static String AMAZON_XML_FIELD_REQUEST_SEARCH_KEYWORDS	= "Keywords";
	private static String AMAZON_XML_FIELD_REQUEST_SEARCH_RESP_GROUP= "ResponseGroup";
	private static String AMAZON_XML_FIELD_REQUEST_SEARCH_SEARCH_IDX= "SearchIndex";
	private static String AMAZON_XML_FIELD_REQUEST_SEARCH_SORT		= "Sort";

	private static String AMAZON_XML_FIELD_REQUEST_LOOKUP			= "ItemLookupRequest";
	private static String AMAZON_XML_FIELD_REQUEST_LOOKUP_ID_TYPE	= "IdType";
	private static String AMAZON_XML_FIELD_REQUEST_LOOKUP_ITEM_ID	= "ItemId";
	private static String AMAZON_XML_FIELD_REQUEST_LOOKUP_RESP_GRP	= "ResponseGroup";
	private static String AMAZON_XML_FIELD_REQUEST_LOOKUP_SEARCH_IDX= "SearchIndex";
	private static String AMAZON_XML_FIELD_REQUEST_LOOKUP_VAR_PAGE	= "VariationPage";

	private static String AMAZON_XML_FIELD_REQUEST_SIMILARITY_LOOKUP= "SimilarityLookupRequest";
	private static String AMAZON_XML_FIELD_REQUEST_SIMILARITY_LOOKUP_ITEM_ID		= "ItemId";
	private static String AMAZON_XML_FIELD_REQUEST_SIMILARITY_LOOKUP_RESPONSE_GRP	= "ResponseGroup";
	
	private static String AMAZON_XML_FIELD_REQUEST_BROWSE_NODE		= "BrowseNodeLookupRequest";
	private static String AMAZON_XML_FIELD_REQUEST_BROWSE_NODE_ID	= "BrowseNodeId";
	
	private static String AMAZON_XML_FIELD_EDITOR_REVIEWS 			= "EditorialReviews";
	private static String AMAZON_XML_FIELD_EDITOR_REVIEW   			= "EditorialReview";
	private static String AMAZON_XML_FIELD_EDITOR_REVIEW_SOURCE		= "Source";
	private static String AMAZON_XML_FIELD_EDITOR_REVIEW_CONTENT	= "Content";
	
	private static String AMAZON_XML_FIELD_TOTAL_RESULTS			= "TotalResults";
	private static String AMAZON_XML_FIELD_TOTAL_PAGES				= "TotalPages";
	private static String AMAZON_XML_FIELD_ITEM_PAGE				= "ItemPage";
	
	private static String AMAZON_XML_FIELD_SIMILAR_PRODUCT			= "SimilarProduct";
	private static String AMAZON_XML_FIELD_SIMILAR_PRODUCT_ASIN		= "ASIN";
	private static String AMAZON_XML_FIELD_SIMILAR_PRODUCT_TITLE	= "Title";
	
	private static String AMAZON_XML_FIELD_BROWSE_NODE				= "BrowseNode";
	private static String AMAZON_XML_FIELD_BROWSE_NODE_ID			= "BrowseNodeId";
	private static String AMAZON_XML_FIELD_BROWSE_NODE_NAME			= "Name";
	private static String AMAZON_XML_FIELD_BROWSE_NODE_ANCESTORS	= "Ancestors";
	private static String AMAZON_XML_FIELD_BROWSE_NODE_CHILDREN		= "Children";
	

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
		return tryToLoadBrowseNodes(XMLAmazonNode.wrap(rootOfXmlToParse));
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

	public Request loadRequest() {
		try {
			XMLAmazonNode request = XMLAmazonNode.wrap(rootOfXmlToParse).child(AMAZON_XML_FIELD_REQUEST);
			if (request.child(AMAZON_XML_FIELD_IS_VALID).retrieveTextValue().equalsIgnoreCase("true"))
				return loadRequestContent(request);
			return Request.createInvalidRequest();
		} catch (Exception e) {
			return Request.createInvalidRequest();
		}
	}
	
	private Request loadRequestContent(XMLAmazonNode request) throws Exception {
		if (isItASearchRequest(request))
			return loadSearchRequestContent(request.childOrEmpty(AMAZON_XML_FIELD_REQUEST_SEARCH));
		if (isItALookupRequest(request))
			return loadLookupRequestContent(request.childOrEmpty(AMAZON_XML_FIELD_REQUEST_LOOKUP));
		if (isItASimilarityLookupRequest(request))
			return loadSimilarityLookupRequestContent(request.childOrEmpty(AMAZON_XML_FIELD_REQUEST_SIMILARITY_LOOKUP));
		if (isItABrowseNodeRequest(request))
			return loadBrowseNodeRequestContent(request.childOrEmpty(AMAZON_XML_FIELD_REQUEST_BROWSE_NODE));
		throw new Exception("Not suppoted request");
	}

	private Request loadSearchRequestContent(XMLAmazonNode requestNode) {
		ItemSearchRequest searchRequest = new ItemSearchRequest(true);
		searchRequest.setItemPage(tryToGetTextValueForOptionnalField(requestNode, AMAZON_XML_FIELD_REQUEST_SEARCH_ITEM_PAGE));
		searchRequest.setKeywords(tryToGetTextValueForOptionnalField(requestNode, AMAZON_XML_FIELD_REQUEST_SEARCH_KEYWORDS));
		searchRequest.setResponseGroup(tryToGetTextValueForOptionnalField(requestNode, AMAZON_XML_FIELD_REQUEST_SEARCH_RESP_GROUP));
		searchRequest.setSearchIndex(tryToGetTextValueForOptionnalField(requestNode, AMAZON_XML_FIELD_REQUEST_SEARCH_SEARCH_IDX));
		searchRequest.setSort(tryToGetTextValueForOptionnalField(requestNode, AMAZON_XML_FIELD_REQUEST_SEARCH_SORT));
		return searchRequest;
	}

	private Request loadLookupRequestContent(XMLAmazonNode requestNode) {
		ItemLookupRequest lookupRequest = new ItemLookupRequest(true);
		lookupRequest.setIdType(tryToGetTextValueForOptionnalField(requestNode, AMAZON_XML_FIELD_REQUEST_LOOKUP_ID_TYPE));
		lookupRequest.setItemId(tryToGetTextValueForOptionnalField(requestNode, AMAZON_XML_FIELD_REQUEST_LOOKUP_ITEM_ID));
		lookupRequest.setResponseGroup(tryToGetTextValueForOptionnalField(requestNode, AMAZON_XML_FIELD_REQUEST_LOOKUP_RESP_GRP));
		lookupRequest.setSearchIndex(tryToGetTextValueForOptionnalField(requestNode, AMAZON_XML_FIELD_REQUEST_LOOKUP_SEARCH_IDX));
		lookupRequest.setVariationPage(tryToGetTextValueForOptionnalField(requestNode, AMAZON_XML_FIELD_REQUEST_LOOKUP_VAR_PAGE));
		return lookupRequest;
	}
	
	private Request loadSimilarityLookupRequestContent(XMLAmazonNode requestNode) {
		SimilarityLookupRequest similarityRequest = new SimilarityLookupRequest(true);
		similarityRequest.setItemId(tryToGetTextValueForOptionnalField(requestNode, AMAZON_XML_FIELD_REQUEST_SIMILARITY_LOOKUP_ITEM_ID));
		similarityRequest.setResponseGroup(tryToGetTextValueForOptionnalField(requestNode, AMAZON_XML_FIELD_REQUEST_SIMILARITY_LOOKUP_RESPONSE_GRP));
		return similarityRequest;
	}

	private Request loadBrowseNodeRequestContent(XMLAmazonNode requestNode) {
		BrowseNodeRequest browseNodeRequest = new BrowseNodeRequest(true);
		browseNodeRequest.setBrowseNodeId(tryToGetTextValueForOptionnalField(requestNode, AMAZON_XML_FIELD_REQUEST_BROWSE_NODE_ID));
		return browseNodeRequest;
	}
	
	public Arguments loadArguments() {
		//TODO : Not implemented yet
		return null;
	}

	private boolean isItASearchRequest(XMLAmazonNode requestNode) {
		return (!requestNode.childOrEmpty(AMAZON_XML_FIELD_REQUEST_SEARCH).isItEmpty());
	}

	private boolean isItALookupRequest(XMLAmazonNode requestNode) {
		return (!requestNode.childOrEmpty(AMAZON_XML_FIELD_REQUEST_LOOKUP).isItEmpty());
	}
	
	private boolean isItASimilarityLookupRequest(XMLAmazonNode requestNode) {
		return (!requestNode.childOrEmpty(AMAZON_XML_FIELD_REQUEST_SIMILARITY_LOOKUP).isItEmpty());
	}
	
	private boolean isItABrowseNodeRequest(XMLAmazonNode requestNode) {
		return (!requestNode.childOrEmpty(AMAZON_XML_FIELD_REQUEST_BROWSE_NODE).isItEmpty());
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
		return (similarProductsNodes!= null || similarProductsNodes.size() != 0); 
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
		item.setBrowseNodes(tryToLoadBrowseNodes(itemNode));
	}
	
	private static List<BrowseNode> tryToLoadBrowseNodes(XMLAmazonNode itemNode) {
		List<XMLAmazonNode> rootBrowseNodesXmlNodes = itemNode.directChildrenOrEmpty(AMAZON_XML_FIELD_BROWSE_NODE);
		if (hasAtLeastOneBrowseNode(rootBrowseNodesXmlNodes))
			return diveIntoBrowseNodeToLoadThem(rootBrowseNodesXmlNodes);
		return new ArrayList<BrowseNode>();
	}
	
	private static boolean hasAtLeastOneBrowseNode(List<XMLAmazonNode> rootBrowseNodesXmlNodes) {
		return (rootBrowseNodesXmlNodes!= null || rootBrowseNodesXmlNodes.size() != 0); 
	}
	
	private static List<BrowseNode> diveIntoBrowseNodeToLoadThem(List<XMLAmazonNode> rootBrowseNodesXmlNodes) {
		List<BrowseNode> itemRootBrowseNodes = new ArrayList<BrowseNode>();
		for (XMLAmazonNode currrentBrowseNodesXmlNode: rootBrowseNodesXmlNodes) {
			BrowseNode newBrowseNode = new BrowseNode(
					currrentBrowseNodesXmlNode.childOrEmpty(AMAZON_XML_FIELD_BROWSE_NODE_ID).retrieveLongValue(),
					tryToGetTextValueForOptionnalField(currrentBrowseNodesXmlNode, AMAZON_XML_FIELD_BROWSE_NODE_NAME));
			List<BrowseNode> children = tryToGetChildren(currrentBrowseNodesXmlNode);
			newBrowseNode.setChildren(children);
			if (hasAncestor(currrentBrowseNodesXmlNode))
				newBrowseNode.setAncestors(tryToLoadBrowseNodes(currrentBrowseNodesXmlNode.childOrEmpty(AMAZON_XML_FIELD_BROWSE_NODE_ANCESTORS)));
			itemRootBrowseNodes.add(newBrowseNode);
		}
		return itemRootBrowseNodes;
	}
	
	private static List<BrowseNode> tryToGetChildren(XMLAmazonNode itemNode) {
		List<BrowseNode> itemChildren = new ArrayList<BrowseNode>();
		List<XMLAmazonNode> childrenXmlNodes = itemNode.childOrEmpty(AMAZON_XML_FIELD_BROWSE_NODE_CHILDREN).childrenOrEmpty(AMAZON_XML_FIELD_BROWSE_NODE);
		for (XMLAmazonNode currrentChildNode: childrenXmlNodes) {
			BrowseNode newBrowseNode = new BrowseNode(
					currrentChildNode.childOrEmpty(AMAZON_XML_FIELD_BROWSE_NODE_ID).retrieveLongValue(),
					tryToGetTextValueForOptionnalField(currrentChildNode, AMAZON_XML_FIELD_BROWSE_NODE_NAME));
			itemChildren.add(newBrowseNode);
		}
		return itemChildren;
	}
	
	private static boolean hasAncestor(XMLAmazonNode browseNodeXmlNode) {
		return (!"".equals(browseNodeXmlNode.childOrEmpty(AMAZON_XML_FIELD_BROWSE_NODE_ANCESTORS).retrieveTextValue()));
	}
	
}
