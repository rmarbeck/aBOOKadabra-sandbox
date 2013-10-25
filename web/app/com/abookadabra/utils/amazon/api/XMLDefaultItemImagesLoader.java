package com.abookadabra.utils.amazon.api;

import static com.abookadabra.utils.amazon.api.XMLDefaultAnswerConstants.*;
import static com.abookadabra.utils.amazon.api.XMLDefaultParserAndLoaderHelper.tryToGetTextValueForOptionnalField;

import java.util.ArrayList;
import java.util.List;

import com.abookadabra.utils.amazon.api.models.answerelements.ItemImage;

public class XMLDefaultItemImagesLoader {
	private XMLAmazonNode currentItemNode;
	private List<XMLAmazonNode> outterImageNodes;
	private List<XMLAmazonNode> innerImageNodes;

	protected static List<ItemImage> load(final XMLAmazonNode rootToParseFrom) {
		return new XMLDefaultItemImagesLoader(rootToParseFrom).load();
	}

	protected XMLDefaultItemImagesLoader(final XMLAmazonNode rootToParseFrom) {
		currentItemNode = rootToParseFrom;
		outterImageNodes = new ArrayList<XMLAmazonNode>();
		innerImageNodes = new ArrayList<XMLAmazonNode>();
	}

	private List<ItemImage> load() {
		findAndLoadImageNodes();
		if (hasAtLeastOneImage())
			return loadImages();
		return  new ArrayList<ItemImage>();
	}
	
	private boolean hasAtLeastOneImage() {
		return (hasAtLeastOneOutterImage() || hasAtLeastOneInnerImage()); 
	}

	private boolean hasAtLeastOneOutterImage() {
		return (outterImageNodes.size() != 0); 
	}

	private boolean hasAtLeastOneInnerImage() {
		return (innerImageNodes.size() != 0); 
	}

	private List<ItemImage> loadImages() {
		List<ItemImage> images = new ArrayList<ItemImage>();
		images.addAll(loadOutterImages());
		images.addAll(loadInnerImages());
		return images;
	}
	
	private void findAndLoadImageNodes() {
		outterImageNodes = new ArrayList<XMLAmazonNode>();
		findAndLoadImagesNodesForKey(AMAZON_XML_FIELD_ITEM_IMAGE_SET_TINY);
		findAndLoadImagesNodesForKey(AMAZON_XML_FIELD_ITEM_IMAGE_SET_SMALL);
		findAndLoadImagesNodesForKey(AMAZON_XML_FIELD_ITEM_IMAGE_SET_THUMBNAIL);
		findAndLoadImagesNodesForKey(AMAZON_XML_FIELD_ITEM_IMAGE_SET_SWATCH);
		findAndLoadImagesNodesForKey(AMAZON_XML_FIELD_ITEM_IMAGE_SET_MEDIUM);
		findAndLoadImagesNodesForKey(AMAZON_XML_FIELD_ITEM_IMAGE_SET_LARGE);
	}
	
	private void findAndLoadImagesNodesForKey(String key) {
		List<XMLAmazonNode> nodesFound = currentItemNode.childrenOrEmpty(key);
		for (XMLAmazonNode currentNode : nodesFound) {
			if (currentNode.isNotEmpty())
				addToCorrectList(currentNode);
		}
		
	}
	
	private void addToCorrectList(XMLAmazonNode node) {
		if (isItAnOutter(node)) {
			outterImageNodes.add(node);
		} else if (isItAnInner(node)) {
			innerImageNodes.add(node);
		} else {
			//Should not happen
		}
		
	}

	private boolean isItAnOutter(XMLAmazonNode node) {
		if (node.isNotEmpty())
			return (isMyDirectParentAnItem(node));
		return false;
	}

	private boolean isItAnInner(XMLAmazonNode node) {
		if (node.isNotEmpty())
			return (isMyDirectParentAnImageSet(node));
		return false;
	}

	private boolean isMyDirectParentAnItem(XMLAmazonNode node) {
		return isMyDirectParentIs(node, AMAZON_XML_FIELD_ITEM);
	}

	private boolean isMyDirectParentAnImageSet(XMLAmazonNode node) {
		return isMyDirectParentIs(node, AMAZON_XML_FIELD_ITEM_IMAGE_SET);
	}

	private boolean isMyDirectParentIs(XMLAmazonNode node, String parentKey) {
		return parentKey.equals(node.parentOrEmpty().nameOrEmpty());
	}
	
	private List<ItemImage> loadOutterImages() {
		if (hasAtLeastOneOutterImage())
			return loadImagesFromListOfNodes(outterImageNodes);
		return  new ArrayList<ItemImage>();
	}

	private List<ItemImage> loadInnerImages() {
		if (hasAtLeastOneInnerImage())
			return loadImagesFromListOfNodes(innerImageNodes);
		return  new ArrayList<ItemImage>();
	}
	
	private List<ItemImage> loadImagesFromListOfNodes(List<XMLAmazonNode> nodes) {
		List<ItemImage> images = new ArrayList<ItemImage>();
		for (XMLAmazonNode currentNode : nodes) {
			images.add(loadFromDirectParentNode(currentNode));
		}
		return images;
	}

	private ItemImage loadFromDirectParentNode(XMLAmazonNode directParentNode) {
		ItemImage newImage = new ItemImage(
				tryToGetTextValueForOptionnalField(directParentNode, AMAZON_XML_FIELD_ITEM_IMAGE_SET_URL),
				directParentNode.childOrEmpty(AMAZON_XML_FIELD_ITEM_IMAGE_SET_HEIGHT).retrieveLongValue(),
				directParentNode.childOrEmpty(AMAZON_XML_FIELD_ITEM_IMAGE_SET_HEIGHT).retrieveTextValueOfAttribute(AMAZON_XML_FIELD_ATTRIBUTE_ITEM_IMAGE_SET_UNITS),
				directParentNode.childOrEmpty(AMAZON_XML_FIELD_ITEM_IMAGE_SET_WIDTH).retrieveLongValue(),
				directParentNode.childOrEmpty(AMAZON_XML_FIELD_ITEM_IMAGE_SET_WIDTH).retrieveTextValueOfAttribute(AMAZON_XML_FIELD_ATTRIBUTE_ITEM_IMAGE_SET_UNITS));
		return newImage;
	}
}
