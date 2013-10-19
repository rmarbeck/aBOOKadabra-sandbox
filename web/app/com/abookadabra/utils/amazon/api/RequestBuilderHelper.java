package com.abookadabra.utils.amazon.api;

import java.util.ArrayList;
import java.util.List;

import play.libs.WS.WSRequestHolder;

public class RequestBuilderHelper {

	protected static String formatListOfItemsParam(List<String> values) {
		StringBuffer formattedListOfValues = new StringBuffer();
		int index = 0;
		for (String value : values) {
			if (index++ != 0)
				formattedListOfValues.append(", ");	
			formattedListOfValues.append(value);
		}
		return formattedListOfValues.toString();
	}
	
	protected static List<String> putStringInNewList(String value) {
		List<String> values = new ArrayList<String>();
		values.add(value);
		return values;
	}
}
