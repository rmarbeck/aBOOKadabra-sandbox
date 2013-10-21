package com.abookadabra.utils.facebook.login;

public class Constants {
	protected static String FACEBOOK_URI = "https://graph.facebook.com";
	protected static String FACEBOOK_OAUTH_URI = "/oauth/access_token?";
	protected static String FACEBOOK_ME_URI = "/me?";
	protected static String FACEBOOK_PERMISSIONS_URI = "/me/permissions?";
	protected static String FACEBOOK_OAUTH_RESPONSE_REGEX = "^access_token=(.*)&expires=(.*)$";
	protected static String FACEBOOK_ME_RESPONSE_REGEX = "^id=(.*)$";
	protected static String FACEBOOK_RESPONSE_REGEX_SPLITTER = "[=&]+";
	protected static String FACEBOOK_DEFAULT_SECRET_FOR_UNCHEKCED_USER ="empty";
	protected static String FACEBOOK_SIGNED_REQUEST_REGEX = "^(.*)\\.(.*)$";
	protected static String FACEBOOK_SIGNED_REQUEST_REGEX_SPLITTER = "\\.";

}
