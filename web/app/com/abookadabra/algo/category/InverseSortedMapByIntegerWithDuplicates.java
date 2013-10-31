package com.abookadabra.algo.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class InverseSortedMapByIntegerWithDuplicates<T> {
	private SortedMap<Integer, List<T>> map;

	public InverseSortedMapByIntegerWithDuplicates() {
		map = new TreeMap<Integer, List<T>>(Collections.reverseOrder());
	}

	public List<T> getListOfContent() {
		return flattenMap();
	}

	public int getNumberOfElements() {
		return flattenMap().size();
	}

	public int size() {
		return getNumberOfKeys();
	}

	public int getNumberOfKeys() {
		return map.size();
	}

	public void put(Integer key, T value) {
		if (!map.containsKey(key)) {
			map.put(key, new ArrayList<T>());
		}
		map.get(key).add(value);
	}

	public void removeOneElementForKey(Integer key) {
		if (map.containsKey(key)) {
			map.get(key).remove(0);
			removeKeyIfListIsEmpty(key);
		}
	}

	private void removeKeyIfListIsEmpty(Integer key) {
		if (map.get(key).isEmpty())
			map.remove(key);
	}

	public void removeKey(Integer key) {
		if (map.containsKey(key)) {
			map.remove(key);
		}
	}

	public void remove(Integer key, T value) {
		if (map.containsKey(key)) {
			map.get(key).remove(value);
		}
	}
	
	public int lastKey() {
		return map.lastKey();
	}

	public int firstKey() {
		return map.firstKey();
	}

	private List<T> flattenMap() {
		List<T> flatmap= new ArrayList<T>();
		
		for (List<T> currentList : map.values()) {
			for (T currentContent : currentList) {
				flatmap.add(currentContent);
			}
		}
		return flatmap;
	}
}
