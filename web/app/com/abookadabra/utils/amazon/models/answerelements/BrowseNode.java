package com.abookadabra.utils.amazon.models.answerelements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BrowseNode {
	private long nodeId;
	private String name;
	private List<BrowseNode> ancestors;
	private List<BrowseNode> children;
	
	public BrowseNode(long nodeId, String name) {
		this(nodeId, name, (List<BrowseNode>)new ArrayList<BrowseNode>());
	}
	
	public BrowseNode(long nodeId, String name, List<BrowseNode> ancestors) {
		this(nodeId, name, ancestors, (List<BrowseNode>)new ArrayList<BrowseNode>());
	}
	
	public BrowseNode(long nodeId, String name, List<BrowseNode> ancestors, List<BrowseNode> children) {
		this.nodeId = nodeId;
		this.name = name;
		this.ancestors = ancestors;
		this.children = children;
	}
	
	public long getNodeId() {
		return nodeId;
	}
	public String getName() {
		return name;
	}
	public List<BrowseNode> getAncestors() {
		return ancestors;
	}

	public List<BrowseNode> getChildren() {
		return children;
	}
	
	public void addDirectAncestor(BrowseNode ancestor) {
		ancestors.add(ancestor);
	}

	public void addDirectChild(BrowseNode child) {
		children.add(child);
	}
	
	public boolean hasAncestor() {
		return (ancestors.size()!=0);
	}
	
	public boolean hasChildren() {
		return (children.size()!=0);
	}

	public void setAncestors(List<BrowseNode> ancestors) {
		this.ancestors = ancestors;
	}

	public void setChildren(List<BrowseNode> children) {
		this.children = children;
	}
	
	public int getAncestorDeepness() {
		return howDeepIsDeeperAncestor();
	}
	
	private int howDeepIsDeeperAncestor() {
		return measureDepth(ancestors, 0);
	}
	
	private int measureDepth(List<BrowseNode> ancestors, int currentDepth) {
		Iterator<BrowseNode> iterator = ancestors.iterator();
		int maxDepth = currentDepth;
		while (iterator.hasNext()) {
			maxDepth = Math.max(measureDepth(iterator.next().ancestors, currentDepth+1),maxDepth);
		}
		return maxDepth;
	}
}
