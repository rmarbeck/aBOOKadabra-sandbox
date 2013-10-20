package com.abookadabra.utils.amazon.api;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLAmazonNode {
	private final Node node;
	private boolean isAnEmptyNode;
	
	public XMLAmazonNode(Node node) {
		this.node = node;
	}
	
	public static XMLAmazonNode createEmptyNode() {
		XMLAmazonNode newNode = new XMLAmazonNode(null);
		newNode.isAnEmptyNode = true;
		return newNode; 
	}

	public static XMLAmazonNode wrap(Node node) {
		if (node == null)
			return createEmptyNode();
		return new XMLAmazonNode(node);
	}
	
	public boolean isItEmpty() {
		return isAnEmptyNode;
	}
	
	public XMLAmazonNode child(String key) throws NodeNotFoundException {
		return new XMLAmazonNode(nodeOfGivenIndexFromListOfChildren(key, 0));
	}
	
	public List<XMLAmazonNode> children(String key) throws NodeNotFoundException {
		List<XMLAmazonNode> resultList = new ArrayList<XMLAmazonNode>();
		NodeList list = getListOfChildren(key);
		for (int index = 0; index < list.getLength(); index ++) {
			resultList.add(new XMLAmazonNode(list.item(index)));
		}
		return resultList;
	}
	
	public List<XMLAmazonNode> directChildren(String key) throws NodeNotFoundException {
		return getListOfFirstLevelChildren(key);
	}
	
	public XMLAmazonNode firstChild() throws NodeNotFoundException {
		return new XMLAmazonNode(node.getFirstChild());
	}

	public XMLAmazonNode childOrEmpty(String key) {
		try {	
			return child(key);
		} catch (NodeNotFoundException e) {
			return createEmptyNode();
		}
	}
	
	public List<XMLAmazonNode> childrenOrEmpty(String key) {
		try {
			return children(key);
		} catch (NodeNotFoundException e) {
			return new ArrayList<XMLAmazonNode>();
		}
	}

	public List<XMLAmazonNode> directChildrenOrEmpty(String key) {
		try {
			return directChildren(key);
		} catch (NodeNotFoundException e) {
			return new ArrayList<XMLAmazonNode>();
		}
	}
	
	public XMLAmazonNode firstChildOrEmpty() {
		try {
			if (isAnEmptyNode)
				return createEmptyNode();
			return firstChild();
		} catch (NodeNotFoundException e) {
			return createEmptyNode();
		}
	}
	
	public String retrieveTextValue() {
		if (isAnEmptyNode)
			return "";
		return node.getTextContent();
	}
	
	public int retrieveIntValue() {
		try {
			if (isAnEmptyNode)
				return -1;
			return Integer.parseInt(node.getTextContent());
		} catch (NumberFormatException n) {
			return -1;
		}
	}
	
	public Long retrieveLongValue() {
		try {
			if (isAnEmptyNode)
				return -1L;
			return Long.parseLong(node.getTextContent());
		} catch (NumberFormatException n) {
			return -1L;
		}
	}
	
	private Node nodeOfGivenIndexFromListOfChildren(String key, int indexStaringAt0) throws NodeNotFoundException {
		NodeList listOfElements = getListOfChildren(key);
		if (listOfElements != null && listOfElements.getLength() >= (indexStaringAt0 + 1)) {
			return listOfElements.item(indexStaringAt0);
		}
		throw new NodeNotFoundException(node.getNodeName()+" has not enough elements for key["+key+"] to retrieve child number ["+indexStaringAt0+"].");
	}
	
	private NodeList getListOfChildren(String key) throws NodeNotFoundException {
		if (canGetElementsByTagName())
			return getElementsByTagName(key);
		throw new NodeNotFoundException("Cannot get elements for node ["+node+"].");
	}
	
	private List<XMLAmazonNode> getListOfFirstLevelChildren(String key) throws NodeNotFoundException {
		if (canGetFirstLevelChildrenByName())
			return getFirstLevelChildrenForTagName(key);
		throw new NodeNotFoundException("Cannot get elements for node ["+node+"].");
	}
	
	private boolean isElement() {
		return (node != null && node.getNodeType() == Node.ELEMENT_NODE);
	}
	
	private boolean canGetElementsByTagName() {
		return (isElement() || node instanceof Document);
	}
	
	private boolean canGetFirstLevelChildrenByName() {
		return canGetElementsByTagName();
	}
	
	private NodeList getElementsByTagName(String key) {
		if (node instanceof Document)
			return ((Document)node).getElementsByTagName(key);
		return ((Element)node).getElementsByTagName(key);
	}
	
	private List<XMLAmazonNode> getFirstLevelChildrenForTagName(String key) {
		if (node instanceof Document)
			return getFirstLevelChildrenForTagName((Document)node, key);
		return getFirstLevelChildrenForTagName((Element)node, key);
	}
	
	private static List<XMLAmazonNode> getFirstLevelChildrenForTagName(Document document, String key) {
		Node fisrtChildMatching = document.getElementsByTagName(key).item(0);
		return getFirstLevelChildrenForTagName(fisrtChildMatching);
	}
	
	private static List<XMLAmazonNode> getFirstLevelChildrenForTagName(Element element, String key) {
		Node fisrtChildMatching = element.getElementsByTagName(key).item(0);
		return getFirstLevelChildrenForTagName(fisrtChildMatching);
	}
	
	private static List<XMLAmazonNode> getFirstLevelChildrenForTagName(Node firstChild) {
		List<XMLAmazonNode> firstLevelChildren = new ArrayList<XMLAmazonNode>();
		if (hasAtLeastOneFirstLevelChild(firstChild)) {
			firstLevelChildren.add(new XMLAmazonNode(firstChild));
			Node currentNode = firstChild;
			while (currentNode.getNextSibling()!=null) {
				firstLevelChildren.add(new XMLAmazonNode(currentNode.getNextSibling()));
				currentNode = currentNode.getNextSibling();
			}
		}
		return firstLevelChildren;
	}
	
	private static boolean hasAtLeastOneFirstLevelChild(Node node) {
		return (node != null);
	}
	
	@SuppressWarnings("serial")
	protected class NodeNotFoundException extends Exception {
		public NodeNotFoundException(String s) {
			super(s);
		}
	}

}
