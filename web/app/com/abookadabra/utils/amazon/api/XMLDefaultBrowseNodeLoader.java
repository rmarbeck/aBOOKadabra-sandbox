package com.abookadabra.utils.amazon.api;

import java.util.ArrayList;
import java.util.List;

import com.abookadabra.utils.amazon.api.models.answerelements.BrowseNode;

import static com.abookadabra.utils.amazon.api.XMLDefaultAnswerConstants.*;

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
	
	protected String tryToGetTextValueForOptionnalField(XMLAmazonNode node, String key) {
		return node.childOrEmpty(key).retrieveTextValue();
	}
	
	private boolean hasAtLeastOneBrowseNode() {
		return (rootBrowseNodesXmlNodes != null && rootBrowseNodesXmlNodes.size() != 0); 
	}
	
	private List<BrowseNode> diveIntoBrowseNodeToLoadThem() {
		List<BrowseNode> itemRootBrowseNodes = new ArrayList<BrowseNode>();
		for (XMLAmazonNode currrentBrowseNodesXmlNode: rootBrowseNodesXmlNodes) {
			BrowseNode newBrowseNode = new BrowseNode(
					currrentBrowseNodesXmlNode.childOrEmpty(AMAZON_XML_FIELD_BROWSE_NODE_ID).retrieveLongValue(),
					tryToGetTextValueForOptionnalField(currrentBrowseNodesXmlNode, AMAZON_XML_FIELD_BROWSE_NODE_NAME));
			List<BrowseNode> children = tryToGetChildren(currrentBrowseNodesXmlNode);
			newBrowseNode.setChildren(children);
			if (hasAncestor(currrentBrowseNodesXmlNode))
				newBrowseNode.setAncestors(load(currrentBrowseNodesXmlNode.childOrEmpty(AMAZON_XML_FIELD_BROWSE_NODE_ANCESTORS)));
			itemRootBrowseNodes.add(newBrowseNode);
		}
		return itemRootBrowseNodes;
	}
	
	private List<BrowseNode> tryToGetChildren(XMLAmazonNode itemNode) {
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
