package com.abookadabra.utils.amazon.api;

import java.util.ArrayList;
import java.util.List;

import static com.abookadabra.utils.amazon.api.XMLDefaultAnswerConstants.*;

import static com.abookadabra.utils.amazon.api.XMLDefaultParserAndLoaderHelper.tryToGetTextValueForOptionnalField;

import com.abookadabra.utils.amazon.api.models.answerelements.BrowseNode;

public class XMLDefaultBrowseNodeLoader {
	List<XMLAmazonNode> rootBrowseNodesXmlNodes;

	protected static List<BrowseNode> load(XMLAmazonNode rootToParseFrom) {
		return new XMLDefaultBrowseNodeLoader(rootToParseFrom).load();
	}
	
	protected XMLDefaultBrowseNodeLoader(XMLAmazonNode rootToParseFrom) {
		rootBrowseNodesXmlNodes = rootToParseFrom.directChildrenOrEmpty(AMAZON_XML_FIELD_BROWSE_NODE);
	}

	private List<BrowseNode> load() {
		if (hasAtLeastOneBrowseNode())
			return diveIntoBrowseNodeToLoadThem();
		return new ArrayList<BrowseNode>();
	}
	
	private boolean hasAtLeastOneBrowseNode() {
		return (rootBrowseNodesXmlNodes != null && rootBrowseNodesXmlNodes.size() != 0); 
	}
	
	private List<BrowseNode> diveIntoBrowseNodeToLoadThem() {
		List<BrowseNode> itemRootBrowseNodes = new ArrayList<BrowseNode>();
		for (XMLAmazonNode currrentBrowseNodesXmlNode: rootBrowseNodesXmlNodes) {
			itemRootBrowseNodes.add(loadNode(currrentBrowseNodesXmlNode));
		}
		return itemRootBrowseNodes;
	}
	
	private BrowseNode loadNode(XMLAmazonNode browseNode) {
		BrowseNode newBrowseNode = createFirstLevelBrowseNode(browseNode);
		newBrowseNode.setChildren(tryToGetChildren(browseNode));
		if (hasAncestor(browseNode))
			newBrowseNode.setAncestors(loadAncestors(browseNode));
		return newBrowseNode;
	}
	
	private BrowseNode createFirstLevelBrowseNode(XMLAmazonNode xmlBrowseNode) {
		return new BrowseNode(
				xmlBrowseNode.childOrEmpty(AMAZON_XML_FIELD_BROWSE_NODE_ID).retrieveLongValue(),
				tryToGetTextValueForOptionnalField(xmlBrowseNode, AMAZON_XML_FIELD_BROWSE_NODE_NAME));
	}
	
	private List<BrowseNode> loadAncestors(XMLAmazonNode xmlBrowseNode) {
		return load(xmlBrowseNode.childOrEmpty(AMAZON_XML_FIELD_BROWSE_NODE_ANCESTORS));
	}
	
	private List<BrowseNode> tryToGetChildren(XMLAmazonNode itemNode) {
		List<BrowseNode> itemChildren = new ArrayList<BrowseNode>();
		List<XMLAmazonNode> childrenXmlNodes = itemNode.childOrEmpty(AMAZON_XML_FIELD_BROWSE_NODE_CHILDREN).childrenOrEmpty(AMAZON_XML_FIELD_BROWSE_NODE);
		for (XMLAmazonNode currrentChildNode: childrenXmlNodes) {
			itemChildren.add(createFirstLevelBrowseNode(currrentChildNode));
		}
		return itemChildren;
	}
	
	private static boolean hasAncestor(XMLAmazonNode browseNodeXmlNode) {
		return (!"".equals(browseNodeXmlNode.childOrEmpty(AMAZON_XML_FIELD_BROWSE_NODE_ANCESTORS).retrieveTextValue()));
	}
	
}
