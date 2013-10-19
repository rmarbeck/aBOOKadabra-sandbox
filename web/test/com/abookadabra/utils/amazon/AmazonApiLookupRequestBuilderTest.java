package com.abookadabra.utils.amazon;
import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import com.abookadabra.utils.amazon.api.LookupRequestBuilder;
import com.abookadabra.utils.amazon.api.models.LookupAnswer;
import com.abookadabra.utils.amazon.api.models.SearchAnswer;
import com.abookadabra.utils.amazon.api.models.Answer.AnswerIsNotValidException;


/**
*
* Simple (JUnit) tests for testing AmazonApi.
*
*/
public class AmazonApiLookupRequestBuilderTest {
	
	@Before
    public void load() {

	}
	
    
    @Test
    public void buildLookupForOneISBN() {
    	assertThat(LookupRequestBuilder.lookup().forIsbn("2747033236").largeResponse().inBooksIndex().getRequest().getFullUrl()).contains("2747033236").contains("Large").doesNotContain("W747033236");
    	assertThat(LookupRequestBuilder.lookup().forIsbn("2747033236").getRequest().getFullUrl()).contains("2747033236").doesNotContain("Large").doesNotContain("W747033236");
    }
    
    @Test
    public void buildLookupForOneSeveralEANs() {
    	List<String> eans = new ArrayList<String>();
    	eans.add("9782290308202");
    	eans.add("9782264042071");
    	eans.add("9782848104102");
    	assertThat(LookupRequestBuilder.lookup().forListOfEANs(eans).largeResponse().inBooksIndex().getRequest().getFullUrl()).contains("9782290308202").contains("Large").contains("9782264042071").contains("9782848104102").doesNotContain("W747033236");
    }
    
    
    @Test
    public void buildLookupLastParamWins() {
    	assertThat(LookupRequestBuilder.lookup().forIsbn("2747033236").largeResponse().smallResponse().inBooksIndex().getRequest().getFullUrl()).doesNotContain("Large").contains("Small");
    }
}
