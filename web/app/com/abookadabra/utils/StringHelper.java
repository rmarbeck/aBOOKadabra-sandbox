package com.abookadabra.utils;


/**
 * Helper for string operations.
 * 
 * @author Raphael
 *
 */

public class StringHelper {
	public static boolean isStringFilled(String s) {
		return (!isStringEmptyOrNull(s));
	}
	
	public static boolean isStringEmptyOrNull(String s) {
		return (s == null || "".equals(s));
	}
	
	public static void checkThatStringIsFilled(String s) throws StringIsEmptyException {
		if (isStringEmptyOrNull(s))
			throw new StringIsEmptyException();
	}
	
	public static void checkThatStringIsEmpyOrNull(String s) throws StringIsNotEmptyException {
		if (!isStringEmptyOrNull(s))
			throw new StringIsNotEmptyException(s);
	}
	
	public static String truncate(String s, int maxLength) {
		if (maxLength < 0)
			return s;
		return truncateIt(s, maxLength, "");
		
	}
	
    public static String truncateWithEndPattern(String s, int maxLength) {
    	if (maxLength < 0)
			return s;
    	String defaultPattern = "...";
    	if (maxLength < defaultPattern.length())
    		defaultPattern = defaultPattern.substring(0, maxLength);
   		return truncateIt(s, maxLength, defaultPattern);
	}
    
    private static String truncateIt(String s, int maxLength, String endPattern) {
    	if (isStringEmptyOrNull(s))
    		return endPattern;
    	return shortenIfNeeded(s, maxLength, endPattern);
    }
    
    private static String shortenIfNeeded(String s, int maxLength, String endPattern) {
    	if (s.length()>maxLength)
    		return shortenAndAppendPattern(s, maxLength, endPattern);
    	return s;
    }
    
    private static String shortenAndAppendPattern(String s, int maxLength, String endPattern) {
    	StringBuilder truncated = new StringBuilder();
    	if (maxLength > endPattern.length())
    		truncated.append(s.substring(0, maxLength-endPattern.length()));
		truncated.append(endPattern);
		return truncated.toString();
    }
    
	@SuppressWarnings("serial")
	public static class StringIsEmptyException extends Exception {
		public StringIsEmptyException() {
			super();
		}
	}
	
	@SuppressWarnings("serial")
	public static class StringIsNotEmptyException extends Exception {
		public StringIsNotEmptyException(String s) {
			super(s);
		}
	}
}
