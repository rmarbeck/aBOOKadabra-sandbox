package com.abookadabra.utils.amazon.helpers;
import static org.fest.assertions.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;



/**
*
* Simple (JUnit) tests for testing AmazonSignedRequests.
*
*/
public class AmazonSignedRequestsBuilderTest {
	AmazonSignedRequestsHelper helper;
	
	@Before
	public void loadHelper() throws Throwable {
		helper = new AmazonSignedRequestsHelper();
	}
	
	@Test
    public void getUrlForNoParams() {
		Map<String, String> params = new HashMap<String, String>();
		assertThat(helper.getFullUrlAsString(params)).contains("http");
    }
	
	@Test
    public void urlForNoParamsContainsSignature() {
		Map<String, String> params = new HashMap<String, String>();
		assertThat(helper.getFullUrlAsString(params)).contains("Signature");
    }
	
	@Test
    public void urlForNoParamsContainsTimestamp() {
		Map<String, String> params = new HashMap<String, String>();
		assertThat(helper.getFullUrlAsString(params)).contains("Timestamp");
    }
	
	@Test
    public void urlWithParamContainsThisParam() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("MyParam", "myparamvalue");
		assertThat(helper.getFullUrlAsString(params)).contains("MyParam");
		assertThat(helper.getFullUrlAsString(params)).contains("MyParam=myparamvalue");
		assertThat(helper.getFullUrlAsString(params)).doesNotContain("MyParam2");
		assertThat(helper.getFullUrlAsString(params)).contains("Signature");
    }
    
}
