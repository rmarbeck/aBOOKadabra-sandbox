package com.abookadabra.utils.amazon.api;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import play.libs.F.Promise;
import play.libs.WS.Response;

import com.abookadabra.utils.amazon.api.models.Answer.AnswerIsNotValidException;
import com.abookadabra.utils.amazon.api.models.BrowseNodeAnswer;
import com.abookadabra.utils.amazon.api.models.LookupAnswer;
import com.abookadabra.utils.amazon.api.models.SearchAnswer;
import com.abookadabra.utils.amazon.api.models.SimilarityLookupAnswer;


/**
*
* Simple (JUnit) tests for testing AmazonApi.
*
*/
public class AmazonApiEndToEndTest {
	static int SUPPOSED_TO_BE_INCORRECT_BROWSE_NODE_ID = 161698;
	static int KNOWN_TO_EXIST_BROWSE_NODE_ID = 1350198031;
	static String DA_VINCI_CODE_ISBN = "2266198351";
	
	static Response searchWithEmptyKeywordResponse;
	static Response searchRequestForTestKeywordResponse;
	static Response searchRequestForDaVinciLikeKeywordResponse;
	static Response searchRequestForDaVinciKeywordResponse;
	static Response lookupWithInvalidASINResponse;
	static Response lookupWithBookThatMayExistResponse;
	static Response similarityLookupWithInvalidASINResponse;
	static Response similarityLookupWithBookThatMayExistResponse;
	static Response browseUnkownNodeResponse;
	static Response browseKnownToBeValidNodeResponse;
	
	static SearchAnswer searchWithEmptyKeywordAnswer;
	static SearchAnswer searchRequestForTestKeywordAnswer;
	static SearchAnswer searchRequestForDaVinciLikeKeywordAnswer;
	static SearchAnswer searchRequestForDaVinciKeywordAnswer;
	static LookupAnswer lookupWithInvalidASINAnswer;
	static LookupAnswer lookupWithBookThatMayExistAnswer;
	static SimilarityLookupAnswer similarityLookupWithInvalidASINAnswer;
	static SimilarityLookupAnswer similarityLookupWithBookThatMayExistAnswer;
	static BrowseNodeAnswer browseUnkownNodeAnswer;
	static BrowseNodeAnswer browseKnownToBeValidNodeAnswer;
	
	@BeforeClass
    public static void oneTimeSetUp() {
		/***** Search *****/
		Request searchWithEmptyKeyword = SearchRequestBuilder.build().forLikeKeywords("").getRequest();
		Promise<Response> searchWithEmptyKeywordPromised = RequestHelper.getWSRequestHolder(searchWithEmptyKeyword).get();
		
		Request searchRequestForTestKeyword = SearchRequestBuilder.build().forLikeKeywords("test").getRequest();
		Promise<Response> searchRequestForTestKeywordPromised = RequestHelper.getWSRequestHolder(searchRequestForTestKeyword).get();
		
		Request searchRequestForDaVinciLikeKeyword = SearchRequestBuilder.build().forLikeKeywords("da vinci").itemPage(2).largeResponse().getRequest();
		Promise<Response> searchRequestForDaVinciLikeKeywordPromised = RequestHelper.getWSRequestHolder(searchRequestForDaVinciLikeKeyword).get();
		
		Request searchRequestForDaVinciKeyword = SearchRequestBuilder.build().forKeywords("da vinci").getRequest();
		Promise<Response> searchRequestForDaVinciKeywordPromised = RequestHelper.getWSRequestHolder(searchRequestForDaVinciKeyword).get();
		
		/***** Lookup ******/
		Request lookupWithInvalidASIN = LookupRequestBuilder.build().forASIN("BADASIN").getRequest();
		Promise<Response> lookupWithInvalidASINPromised = RequestHelper.getWSRequestHolder(lookupWithInvalidASIN).get();
		
		Request lookupWithBookThatMayExist = LookupRequestBuilder.build().forIsbn(DA_VINCI_CODE_ISBN).largeResponse().getRequest();
		Promise<Response> lookupWithBookThatMayExistPromised = RequestHelper.getWSRequestHolder(lookupWithBookThatMayExist).get();
		
		/***** Similarity Lookup ******/
		Request similarityLookupWithInvalidASIN = SimilarityLookupRequestBuilder.build().forASIN("BADASIN").getRequest();
		Promise<Response> similarityLookupWithInvalidASINPromised = RequestHelper.getWSRequestHolder(similarityLookupWithInvalidASIN).get();
		
		Request similarityLookupWithBookThatMayExist = SimilarityLookupRequestBuilder.build().forASIN(DA_VINCI_CODE_ISBN).random().inBooksIndex().largeResponse().getRequest();
		Promise<Response> similarityLookupWithBookThatMayExistPromised = RequestHelper.getWSRequestHolder(similarityLookupWithBookThatMayExist).get();
		
		
		/***** BrowseNodes ******/
		Request browseUnkownNode = BrowseNodeRequestBuilder.build().forId(SUPPOSED_TO_BE_INCORRECT_BROWSE_NODE_ID).getRequest();
		Promise<Response> browseUnkownNodePromised = RequestHelper.getWSRequestHolder(browseUnkownNode).get();
		
		Request browseKnownToBeValidNode = BrowseNodeRequestBuilder.build().forId(KNOWN_TO_EXIST_BROWSE_NODE_ID).newReleases().getRequest();
		Promise<Response> browseKnownToBeValidNodePromised = RequestHelper.getWSRequestHolder(browseKnownToBeValidNode).get();		
		
		//System.out.println(browseKnownToBeValidNode.getFullUrl());
		
		searchWithEmptyKeywordResponse = searchWithEmptyKeywordPromised.get(5000);
		searchRequestForTestKeywordResponse = searchRequestForTestKeywordPromised.get(5000);
		searchRequestForDaVinciLikeKeywordResponse = searchRequestForDaVinciLikeKeywordPromised.get(5000);
		searchRequestForDaVinciKeywordResponse = searchRequestForDaVinciKeywordPromised.get(5000);
		
		lookupWithInvalidASINResponse = lookupWithInvalidASINPromised.get(5000);
		lookupWithBookThatMayExistResponse = lookupWithBookThatMayExistPromised.get(5000);
		
		similarityLookupWithInvalidASINResponse = similarityLookupWithInvalidASINPromised.get(5000);
		similarityLookupWithBookThatMayExistResponse = similarityLookupWithBookThatMayExistPromised.get(5000);
		
		browseUnkownNodeResponse = browseUnkownNodePromised.get(5000);
		browseKnownToBeValidNodeResponse = browseKnownToBeValidNodePromised.get(5000);
		
		searchWithEmptyKeywordAnswer = SearchAnswer.createInstanceFrom(searchWithEmptyKeywordResponse.asXml());
		searchRequestForTestKeywordAnswer = SearchAnswer.createInstanceFrom(searchRequestForTestKeywordResponse.asXml());
		searchRequestForDaVinciLikeKeywordAnswer = SearchAnswer.createInstanceFrom(searchRequestForDaVinciLikeKeywordResponse.asXml());
		searchRequestForDaVinciKeywordAnswer = SearchAnswer.createInstanceFrom(searchRequestForDaVinciKeywordResponse.asXml());
		
		lookupWithInvalidASINAnswer = LookupAnswer.createInstanceFrom(lookupWithInvalidASINResponse.asXml());
		lookupWithBookThatMayExistAnswer = LookupAnswer.createInstanceFrom(lookupWithBookThatMayExistResponse.asXml());
	
		similarityLookupWithInvalidASINAnswer = SimilarityLookupAnswer.createInstanceFrom(similarityLookupWithInvalidASINResponse.asXml());
		similarityLookupWithBookThatMayExistAnswer = SimilarityLookupAnswer.createInstanceFrom(similarityLookupWithBookThatMayExistResponse.asXml());
		
		browseUnkownNodeAnswer = BrowseNodeAnswer.createInstanceFrom(browseUnkownNodeResponse.asXml());
		browseKnownToBeValidNodeAnswer = BrowseNodeAnswer.createInstanceFrom(browseKnownToBeValidNodeResponse.asXml());
		
    }
	
	
    /****************************************************************************/
    /*						Empty Search										*/
    /****************************************************************************/

    @Test
    public void searchWithEmptyKeywordAnswerIsValid() throws AnswerIsNotValidException {
        assertThat(searchWithEmptyKeywordAnswer.isItValid()).isEqualTo(true);
    }

    @Test
    public void searchWithEmptyKeywordAnswerRequestIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(searchWithEmptyKeywordAnswer.getRequest().getKeywords()).isEqualTo("%%");
        assertThat(searchWithEmptyKeywordAnswer.getRequest().getResponseGroup()).isEqualTo("Small");
        assertThat(searchWithEmptyKeywordAnswer.getRequest().getSearchIndex()).isEqualTo("Books");
    }
    
    @Test
    public void searchWithEmptyKeywordAnswerErrorCodeIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(searchWithEmptyKeywordAnswer.getError().getCode()).isEqualTo("AWS.ECommerceService.NoExactMatches");
    }
    
    @Test
    public void searchWithEmptyKeywordAnswerErrorMessageIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(searchWithEmptyKeywordAnswer.getError().getMessage()).isEqualTo("We did not find any matches for your request.");
    }

    @Test
    public void searchWithEmptyKeywordAnswerCheckTotalResults() throws AnswerIsNotValidException {
        assertThat(searchWithEmptyKeywordAnswer.getTotalResults()).isEqualTo(0);
    }
    
    @Test
    public void searchWithEmptyKeywordAnswerCheckTotalPages() throws AnswerIsNotValidException {
    	assertThat(searchWithEmptyKeywordAnswer.getTotalPages()).isEqualTo(0);
    }
    
    /****************************************************************************/
    /*						Test Search											*/
    /****************************************************************************/

    @Test
    public void searchRequestForTestKeywordAnswerIsValid() throws AnswerIsNotValidException {
        assertThat(searchRequestForTestKeywordAnswer.isItValid()).isEqualTo(true);
    }

    @Test
    public void searchRequestForTestKeywordAnswerRequestIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(searchRequestForTestKeywordAnswer.getRequest().getKeywords()).isEqualTo("%test%");
        assertThat(searchRequestForTestKeywordAnswer.getRequest().getResponseGroup()).isEqualTo("Small");
        assertThat(searchRequestForTestKeywordAnswer.getRequest().getSearchIndex()).isEqualTo("Books");
    }
    
    @Test
    public void searchRequestForTestKeywordAnswerContainsNoError() throws AnswerIsNotValidException {
        assertThat(searchRequestForTestKeywordAnswer.getError().containsError()).isEqualTo(false);
    }

    @Test
    public void searchRequestForTestKeywordAnswerCheckTotalResults() throws AnswerIsNotValidException {
        assertThat(searchRequestForTestKeywordAnswer.getTotalResults()).isNotEqualTo(0);
    }
    
    @Test
    public void searchRequestForTestKeywordAnswerCheckTotalPages() throws AnswerIsNotValidException {
    	assertThat(searchRequestForTestKeywordAnswer.getTotalPages()).isNotEqualTo(0);
    	assertThat(searchRequestForTestKeywordAnswer.getTotalPages()*10).isGreaterThanOrEqualTo(searchRequestForTestKeywordAnswer.getTotalResults());
    }
    
    @Test
    public void searchRequestForTestKeywordAnswerHasResults() throws AnswerIsNotValidException {
    	assertThat(searchRequestForTestKeywordAnswer.hasResults()).isEqualTo(true);
    }
    
    @Test
    public void searchRequestForTestKeywordAnswerIsNotEmpty() throws AnswerIsNotValidException {
    	assertThat(searchRequestForTestKeywordAnswer.getItems().isEmpty()).isEqualTo(false);
    }
    
    @Test
    public void searchRequestForTestKeywordAnswerFirstItemIsPopulated() throws AnswerIsNotValidException {
    	assertThat(searchRequestForTestKeywordAnswer.getItems().get(0).getAsin()).isNotNull();
    	assertThat(searchRequestForTestKeywordAnswer.getItems().get(0).getDetailPageUrl()).isNotNull();
    	assertThat(searchRequestForTestKeywordAnswer.getItems().get(0).getAttributes().getAuthors()).isNotNull();
    	assertThat(searchRequestForTestKeywordAnswer.getItems().get(0).getAttributes().getTitle()).isNotNull();
    }
    
    @Test
    public void searchRequestForTestKeywordAnswerLastItemInThePageIsPopulated() throws AnswerIsNotValidException {
    	assertThat(searchRequestForTestKeywordAnswer.getItems().get(9).getAsin()).isNotNull();
    	assertThat(searchRequestForTestKeywordAnswer.getItems().get(9).getDetailPageUrl()).isNotNull();
    	assertThat(searchRequestForTestKeywordAnswer.getItems().get(9).getAttributes().getAuthors()).isNotNull();
    	assertThat(searchRequestForTestKeywordAnswer.getItems().get(9).getAttributes().getTitle()).isNotNull();
    }
    
    @Test
    public void searchRequestForTestKeywordAnswerAnyItemInThePageHasNoSimilarProductAsItIsASmallRequest() throws AnswerIsNotValidException {
    	assertThat(searchRequestForTestKeywordAnswer.getItems().get(0).getSimilarProducts().isEmpty()).isEqualTo(true);
    	assertThat(searchRequestForTestKeywordAnswer.getItems().get(1).getSimilarProducts().isEmpty()).isEqualTo(true);
    	assertThat(searchRequestForTestKeywordAnswer.getItems().get(2).getSimilarProducts().isEmpty()).isEqualTo(true);
    	assertThat(searchRequestForTestKeywordAnswer.getItems().get(9).getSimilarProducts().isEmpty()).isEqualTo(true);
    }
    
    @Test
    public void searchRequestForTestKeywordAnswerAnyItemInThePageHasNoBrowseNodetAsItIsASmallRequest() throws AnswerIsNotValidException {
    	assertThat(searchRequestForTestKeywordAnswer.getItems().get(0).getBrowseNodes().isEmpty()).isEqualTo(true);
    	assertThat(searchRequestForTestKeywordAnswer.getItems().get(1).getBrowseNodes().isEmpty()).isEqualTo(true);
    	assertThat(searchRequestForTestKeywordAnswer.getItems().get(2).getBrowseNodes().isEmpty()).isEqualTo(true);
    	assertThat(searchRequestForTestKeywordAnswer.getItems().get(9).getBrowseNodes().isEmpty()).isEqualTo(true);
    }
    
    /****************************************************************************/
    /*						Like Da Vinci Search								*/
    /****************************************************************************/

    @Test
    public void searchRequestForDaVinciLikeKeywordAnswerIsValid() throws AnswerIsNotValidException {
        assertThat(searchRequestForDaVinciLikeKeywordAnswer.isItValid()).isEqualTo(true);
    }

    @Test
    public void searchRequestForDaVinciLikeKeywordAnswerRequestIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(searchRequestForDaVinciLikeKeywordAnswer.getRequest().getKeywords()).isEqualTo("%da vinci%");
        assertThat(searchRequestForDaVinciLikeKeywordAnswer.getRequest().getItemPage()).isEqualTo("2");
        assertThat(searchRequestForDaVinciLikeKeywordAnswer.getRequest().getResponseGroup()).isEqualTo("Large");
        assertThat(searchRequestForDaVinciLikeKeywordAnswer.getRequest().getSearchIndex()).isEqualTo("Books");
    }
    
    @Test
    public void searchRequestForDaVinciLikeKeywordAnswerContainsNoError() throws AnswerIsNotValidException {
        assertThat(searchRequestForDaVinciLikeKeywordAnswer.getError().containsError()).isEqualTo(false);
    }

    @Test
    public void searchRequestForDaVinciLikeKeywordAnswerCheckTotalResults() throws AnswerIsNotValidException {
        assertThat(searchRequestForDaVinciLikeKeywordAnswer.getTotalResults()).isNotEqualTo(0);
    }
    
    @Test
    public void searchRequestForDaVinciLikeKeywordAnswerCheckTotalPages() throws AnswerIsNotValidException {
    	assertThat(searchRequestForDaVinciLikeKeywordAnswer.getTotalPages()).isNotEqualTo(0);
    	assertThat(searchRequestForDaVinciLikeKeywordAnswer.getTotalPages()*10).isGreaterThanOrEqualTo(searchRequestForDaVinciLikeKeywordAnswer.getTotalResults());
    }
    
    @Test
    public void searchRequestForDaVinciLikeKeywordAnswerHasResults() throws AnswerIsNotValidException {
    	assertThat(searchRequestForDaVinciLikeKeywordAnswer.hasResults()).isEqualTo(true);
    }
    
    @Test
    public void searchRequestForDaVinciLikeKeywordAnswerIsNotEmpty() throws AnswerIsNotValidException {
    	assertThat(searchRequestForDaVinciLikeKeywordAnswer.getItems().isEmpty()).isEqualTo(false);
    }
    
    @Test
    public void searchRequestForDaVinciLikeKeywordAnswerFirstItemIsPopulated() throws AnswerIsNotValidException {
    	assertThat(searchRequestForDaVinciLikeKeywordAnswer.getItems().get(0).getAsin()).isNotNull();
    	assertThat(searchRequestForDaVinciLikeKeywordAnswer.getItems().get(0).getDetailPageUrl()).isNotNull();
    	assertThat(searchRequestForDaVinciLikeKeywordAnswer.getItems().get(0).getAttributes().getAuthors()).isNotNull();
    	assertThat(searchRequestForDaVinciLikeKeywordAnswer.getItems().get(0).getAttributes().getTitle()).isNotNull();
    }
    
    @Test
    public void searchRequestForDaVinciLikeKeywordAnswerLastItemInThePageIsPopulated() throws AnswerIsNotValidException {
    	assertThat(searchRequestForDaVinciLikeKeywordAnswer.getItems().get(9).getAsin()).isNotNull();
    	assertThat(searchRequestForDaVinciLikeKeywordAnswer.getItems().get(9).getDetailPageUrl()).isNotNull();
    	assertThat(searchRequestForDaVinciLikeKeywordAnswer.getItems().get(9).getAttributes().getAuthors()).isNotNull();
    	assertThat(searchRequestForDaVinciLikeKeywordAnswer.getItems().get(9).getAttributes().getTitle()).isNotNull();
    }
    
    @Test
    public void searchRequestForDaVinciLikeKeywordAnswerAnyItemInThePageHasBrowseNodetAsItIsALargeRequest() throws AnswerIsNotValidException {
    	assertThat(searchRequestForDaVinciLikeKeywordAnswer.getItems().get(0).getBrowseNodes().isEmpty()).isEqualTo(false);
    	assertThat(searchRequestForDaVinciLikeKeywordAnswer.getItems().get(1).getBrowseNodes().isEmpty()).isEqualTo(false);
    	assertThat(searchRequestForDaVinciLikeKeywordAnswer.getItems().get(2).getBrowseNodes().isEmpty()).isEqualTo(false);
    	assertThat(searchRequestForDaVinciLikeKeywordAnswer.getItems().get(9).getBrowseNodes().isEmpty()).isEqualTo(false);
    }
    
    /****************************************************************************/
    /*						Da Vinci Search										*/
    /****************************************************************************/

    @Test
    public void searchRequestForDaVinciKeywordAnswerIsValid() throws AnswerIsNotValidException {
        assertThat(searchRequestForDaVinciKeywordAnswer.isItValid()).isEqualTo(true);
    }
    
    @Test
    public void searchRequestForDaVinciKeywordAnswerRequestIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(searchRequestForDaVinciKeywordAnswer.getRequest().getKeywords()).isEqualTo("da vinci");
        assertThat(searchRequestForDaVinciKeywordAnswer.getRequest().getResponseGroup()).isEqualTo("Small");
        assertThat(searchRequestForDaVinciKeywordAnswer.getRequest().getSearchIndex()).isEqualTo("Books");
    }
    
    /****************************************************************************/
    /*						Lookup with invalid ASIN							*/
    /****************************************************************************/

    @Test
    public void lookupWithInvalidASINAnswerIsValid() throws AnswerIsNotValidException {
        assertThat(lookupWithInvalidASINAnswer.isItValid()).isEqualTo(true);
    }

    @Test
    public void lookupWithInvalidASINAnswerRequestIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(lookupWithInvalidASINAnswer.getRequest().getItemId()).isEqualTo("BADASIN");
        assertThat(lookupWithInvalidASINAnswer.getRequest().getResponseGroup()).isEqualTo("Small");
        assertThat(lookupWithInvalidASINAnswer.getRequest().getIdType()).isEqualTo("ASIN");
    }
    
    @Test
    public void lookupWithInvalidASINAnswerErrorCodeIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(lookupWithInvalidASINAnswer.getError().getCode()).isEqualTo("AWS.InvalidParameterValue");
    }
    
    @Test
    public void lookupWithInvalidASINAnswerErrorMessageIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(lookupWithInvalidASINAnswer.getError().getMessage()).isEqualTo("BADASIN is not a valid value for ItemId. Please change this value and retry your request.");
    }
    
    /****************************************************************************/
    /*						Lookup with book that may exist						*/
    /****************************************************************************/

    @Test
    public void lookupWithBookThatMayExistAnswerIsValid() throws AnswerIsNotValidException {
        assertThat(lookupWithBookThatMayExistAnswer.isItValid()).isEqualTo(true);
    }
    
    @Test
    public void lookupWithBookThatMayExistAnswerContainsNoError() throws AnswerIsNotValidException {
        assertThat(lookupWithBookThatMayExistAnswer.getError().containsError()).isEqualTo(false);
    }

    @Test
    public void lookupWithBookThatMayExistAnswerRequestIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(lookupWithBookThatMayExistAnswer.getRequest().getItemId()).isEqualTo(DA_VINCI_CODE_ISBN);
        assertThat(lookupWithBookThatMayExistAnswer.getRequest().getResponseGroup()).isEqualTo("Large");
        assertThat(lookupWithBookThatMayExistAnswer.getRequest().getIdType()).isEqualTo("ISBN");
        assertThat(lookupWithBookThatMayExistAnswer.getRequest().getSearchIndex()).isEqualTo("Books");
    }
    
    @Test
    public void lookupWithBookThatMayExistAnswerItemIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAsin()).isEqualTo(DA_VINCI_CODE_ISBN);
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getDetailPageUrl()).contains("Da-Vinci-code-Dan-Brown");
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getEditorialReview()).contains("Da Vinci Code");
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getSalesRank()).isNotEqualTo(0);
    }

    @Test
    public void lookupWithBookThatMayExistAnswerItemAttributesAreTheOneExpected() throws AnswerIsNotValidException {
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getAuthors()).isNotEmpty();
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getTitle()).isEqualTo("Da Vinci code");
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getEan()).isEqualTo("9782266198356");
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getBinding()).isEqualTo("Poche");
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getIsbn()).isEqualTo("2266198351");
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getLabel()).isEqualTo("Pocket");
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getManufacturer()).isEqualTo("Pocket");
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getNumberOfPages()).isEqualTo(744);
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getPublicationDate().getTime()).isEqualTo(1246485600000L);
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getReleaseDate().getTime()).isEqualTo(1246485600000L);
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getPublisher()).isEqualTo("Pocket");
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getSku()).isEqualTo("NL9782266198356");
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getStudio()).isEqualTo("Pocket");
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getProductGroup()).isEqualTo("Book");
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getProductTypeName()).isEqualTo("ABIS_BOOK");
    }
    
    @Test
    public void lookupWithBookThatMayExistAnswerLaguagesAreTheOneExpected() throws AnswerIsNotValidException {
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getLanguages()).isNotEmpty();
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getLanguages().get(0).getType()).isEqualTo("Unknown");
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getLanguages().get(0).getName()).isEqualTo("Français");
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getLanguages().get(1).getType()).isEqualTo("Original Language");
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getLanguages().get(1).getName()).isEqualTo("Français");
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getLanguages().get(2).getType()).isEqualTo("Published");
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getLanguages().get(2).getName()).isEqualTo("Français");
    }

    @Test
    public void lookupWithBookThatMayExistAnswerListPriceIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getListPrice()).isNotNull();
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getListPrice().getCurrencyCode()).isEqualTo("EUR");
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getAttributes().getListPrice().getFormattedPrice()).contains("EUR");
    }
    
    @Test
    public void lookupWithBookThatMayExistAnswerHasSimilarProducts() throws AnswerIsNotValidException {
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getSimilarProducts()).isNotEmpty();
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getSimilarProducts().size()).isGreaterThan(2);
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getSimilarProducts().get(1).getTitle()).isNotNull();
    }
    
    @Test
    public void lookupWithBookThatMayExistAnswerHasBrowseNodes() throws AnswerIsNotValidException {
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getBrowseNodes()).isNotEmpty();
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getBrowseNodes().size()).isGreaterThan(3);
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getBrowseNodes().get(2).hasAncestor()).isEqualTo(true);
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getBrowseNodes().get(2).getName()).isNotEmpty();
    }
    
    @Test
    public void lookupWithBookThatMayExistCheckImages() throws AnswerIsNotValidException {
        assertThat(lookupWithBookThatMayExistAnswer.getItem().getImages().size()).isGreaterThan(1);
    }
    
    @Test
    public void lookupWithBookThatMayExistCheckImagesUrls() throws AnswerIsNotValidException {
   		assertThat(lookupWithBookThatMayExistAnswer.getItem().getImages().get(0).getUrl()).contains("http://");
    }
    
    /****************************************************************************/
    /*						Similarity Lookup with invalid ASIN					*/
    /****************************************************************************/

    @Test
    public void similarityLookupWithInvalidASINAnswerIsValid() throws AnswerIsNotValidException {
        assertThat(similarityLookupWithInvalidASINAnswer.isItValid()).isEqualTo(true);
    }

    @Test
    public void similarityLookupWithInvalidASINAnswerRequestIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(similarityLookupWithInvalidASINAnswer.getRequest().getItemId()).isEqualTo("BADASIN");
        assertThat(similarityLookupWithInvalidASINAnswer.getRequest().getResponseGroup()).isEqualTo("Small");
    }
    
    @Test
    public void similarityLookupWithInvalidASINAnswerErrorCodeIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(similarityLookupWithInvalidASINAnswer.getError().getCode()).isEqualTo("AWS.ECommerceService.NoSimilarities");
    }
    
    @Test
    public void similarityLookupWithInvalidASINAnswerErrorMessageIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(similarityLookupWithInvalidASINAnswer.getError().getMessage()).isEqualTo("There are no similar items for this ASIN: BADASIN.");
    }
    
    /****************************************************************************/
    /*					Similarity Lookup with book that may exist				*/
    /****************************************************************************/

    @Test
    public void similarityLookupWithBookThatMayExistAnswerIsValid() throws AnswerIsNotValidException {
        assertThat(similarityLookupWithBookThatMayExistAnswer.isItValid()).isEqualTo(true);
    }
    
    @Test
    public void similarityLookupWithBookThatMayExistAnswerContainsNoError() throws AnswerIsNotValidException {
        assertThat(similarityLookupWithBookThatMayExistAnswer.getError().containsError()).isEqualTo(false);
    }

    @Test
    public void similarityLookupWithBookThatMayExistAnswerHasResults() throws AnswerIsNotValidException {
        assertThat(similarityLookupWithBookThatMayExistAnswer.hasResults()).isEqualTo(true);
    }
    
    @Test
    public void similarityLookupWithBookThatMayExistAnswerRequestIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(similarityLookupWithBookThatMayExistAnswer.getRequest().getItemId()).isEqualTo(DA_VINCI_CODE_ISBN);
        assertThat(similarityLookupWithBookThatMayExistAnswer.getRequest().getResponseGroup()).isEqualTo("Large");
        assertThat(similarityLookupWithBookThatMayExistAnswer.getRequest().getSimilarityType()).isEqualTo("Random");
    }
    
    @Test
    public void similarityLookupWithBookThatMayExistAnswerItemIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(similarityLookupWithBookThatMayExistAnswer.getItems().get(0).getAsin()).isNotEmpty();
        assertThat(similarityLookupWithBookThatMayExistAnswer.getItems().get(0).getDetailPageUrl()).isNotEmpty();
        assertThat(similarityLookupWithBookThatMayExistAnswer.getItems().get(0).getSalesRank()).isNotEqualTo(0);
    }
    
    @Test
    public void similarityLookupWithBookThatMayExistAnswerHasNestedSimilarProducts() throws AnswerIsNotValidException {
        assertThat(similarityLookupWithBookThatMayExistAnswer.getItems().get(0).getSimilarProducts()).isNotEmpty();
        assertThat(similarityLookupWithBookThatMayExistAnswer.getItems().get(0).getSimilarProducts().size()).isGreaterThan(2);
        assertThat(similarityLookupWithBookThatMayExistAnswer.getItems().get(0).getSimilarProducts().get(1).getTitle()).isNotNull();
    }
    
    @Test
    public void similarityLookupWithBookThatMayExistAnswerHasBrowseNodes() throws AnswerIsNotValidException {
        assertThat(similarityLookupWithBookThatMayExistAnswer.getItems().get(0).getBrowseNodes()).isNotEmpty();
        assertThat(similarityLookupWithBookThatMayExistAnswer.getItems().get(0).getBrowseNodes().size()).isGreaterThan(3);
        assertThat(similarityLookupWithBookThatMayExistAnswer.getItems().get(0).getBrowseNodes().get(2).hasAncestor()).isEqualTo(true);
        assertThat(similarityLookupWithBookThatMayExistAnswer.getItems().get(0).getBrowseNodes().get(2).getName()).isNotEmpty();
    }
    
    /****************************************************************************/
    /*						Browse Node with invalid Node ID					*/
    /****************************************************************************/

    @Test
    public void browseUnkownNodeAnswerIsValid() throws AnswerIsNotValidException {
        assertThat(browseUnkownNodeAnswer.isItValid()).isEqualTo(true);
    }

    @Test
    public void browseUnkownNodeAnswerRequestIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(browseUnkownNodeAnswer.getRequest().getBrowseNodeId()).isEqualTo(""+SUPPOSED_TO_BE_INCORRECT_BROWSE_NODE_ID);
    }
    
    @Test
    public void browseUnkownNodeAnswerErrorCodeIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(browseUnkownNodeAnswer.getError().getCode()).isEqualTo("AWS.InvalidParameterValue");
    }
    
    @Test
    public void browseUnkownNodeAnswerErrorMessageIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(browseUnkownNodeAnswer.getError().getMessage()).isEqualTo(SUPPOSED_TO_BE_INCORRECT_BROWSE_NODE_ID+" is not a valid value for BrowseNodeId. Please change this value and retry your request.");
    }
    
    /****************************************************************************/
    /*					Browse Node with ID that exists							*/
    /****************************************************************************/

    @Test
    public void browseKnownToBeValidNodeAnswerIsValid() throws AnswerIsNotValidException {
        assertThat(browseKnownToBeValidNodeAnswer.isItValid()).isEqualTo(true);
    }
    
    @Test
    public void browseKnownToBeValidNodeAnswerContainsNoError() throws AnswerIsNotValidException {
        assertThat(browseKnownToBeValidNodeAnswer.getError().containsError()).isEqualTo(false);
    }

    @Test
    public void browseKnownToBeValidNodeAnswerHasResults() throws AnswerIsNotValidException {
        assertThat(browseKnownToBeValidNodeAnswer.hasResults()).isEqualTo(true);
    }
    
    @Test
    public void browseKnownToBeValidNodeAnswerRequestIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(browseKnownToBeValidNodeAnswer.getRequest().getBrowseNodeId()).isEqualTo(""+KNOWN_TO_EXIST_BROWSE_NODE_ID);
        assertThat(browseKnownToBeValidNodeAnswer.getRequest().getResponseGroup()).isEqualTo("NewReleases");
    }
    
    @Test
    public void browseKnownToBeValidNodeAnswerItemIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(browseKnownToBeValidNodeAnswer.getItems().size()).isEqualTo(1);
    }
    
 /*   @Test
    public void browseKnownToBeValidNodeAnswerHasNestedSimilarProducts() throws AnswerIsNotValidException {
        assertThat(browseKnownToBeValidNodeAnswer.getItems().get(0).getSimilarProducts()).isNotEmpty();
        assertThat(browseKnownToBeValidNodeAnswer.getItems().get(0).getSimilarProducts().size()).isGreaterThan(2);
        assertThat(browseKnownToBeValidNodeAnswer.getItems().get(0).getSimilarProducts().get(1).getTitle()).isNotNull();
    }
    
    @Test
    public void browseKnownToBeValidNodeAnswerHasBrowseNodes() throws AnswerIsNotValidException {
        assertThat(browseKnownToBeValidNodeAnswer.getItems().get(0).getBrowseNodes()).isNotEmpty();
        assertThat(browseKnownToBeValidNodeAnswer.getItems().get(0).getBrowseNodes().size()).isGreaterThan(3);
        assertThat(browseKnownToBeValidNodeAnswer.getItems().get(0).getBrowseNodes().get(2).hasAncestor()).isEqualTo(true);
        assertThat(browseKnownToBeValidNodeAnswer.getItems().get(0).getBrowseNodes().get(2).getName()).isNotEmpty();
    }*/
    
}
