package com.abookadabra.utils.amazon.api;

public abstract class AmazonSecrets {
	private static final String awsAccessKeyId = "awsAccessKeyIdHere";
	private static final String awsSecretKey = "awsSecretKeyHere";
	
	protected static String getAwsAccessKeyId() {
		return awsAccessKeyId;
	}
	protected static String getAwsSecretKey() {
		return awsSecretKey;
	}
}
