package com.abookadabra.utils.amazon.api;
import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

import com.abookadabra.utils.amazon.api.models.LookupAnswer;
import com.abookadabra.utils.amazon.api.models.SearchAnswer;
import com.abookadabra.utils.amazon.api.models.Answer.AnswerIsNotValidException;


/**
*
* Simple (JUnit) tests for testing AmazonApi.
*
*/
public class AmazonApiFromLocalTest {
	static Document localLookupExampleDoc;
	static Document localSearchExampleDoc;
	static Document localEmptySearchExampleDoc;
	static LookupAnswer lookupAnswer;
	static SearchAnswer searchAnswer;
	static SearchAnswer emptySearchAnswer;
	
	@BeforeClass
    public static void loadTestXml() {
		URL urlLookup = AmazonApiFromLocalTest.class.getResource("/lookup-amazon-example.xml");
		URL urlSearch = AmazonApiFromLocalTest.class.getResource("/search-amazon-example.xml");
		URL urlEmptySearch = AmazonApiFromLocalTest.class.getResource("/empty-search-amazon-example.xml");
		File lookupFile = new File(urlLookup.getFile());
		File searchFile = new File(urlSearch.getFile());
		File emptySearchFile = new File(urlEmptySearch.getFile());
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			localLookupExampleDoc = dBuilder.parse(lookupFile);
			lookupAnswer = LookupAnswer.createInstanceFrom(localLookupExampleDoc);
			localSearchExampleDoc = dBuilder.parse(searchFile);
			searchAnswer = SearchAnswer.createInstanceFrom(localSearchExampleDoc);
			localEmptySearchExampleDoc = dBuilder.parse(emptySearchFile);
			emptySearchAnswer = SearchAnswer.createInstanceFrom(localEmptySearchExampleDoc);
		} catch (Exception e) {
			assertThat(e).isNull();
		}
	}
	
    /****************************************************************************/
    /*						Lookup												*/
    /****************************************************************************/

    @Test
    public void parsingLocalLookupFileWithoutErrors() throws AnswerIsNotValidException {
        assertThat(lookupAnswer.hasResults()).isEqualTo(true);
    }

    @Test
    public void parsingLocalLookupToGetItemASIN() throws AnswerIsNotValidException {
        assertThat(lookupAnswer.getItem().getAsin()).isEqualTo("2747033236");
    }

    @Test
    public void parsingLocalLookupToGetGotoUrl() throws AnswerIsNotValidException {
        assertThat(lookupAnswer.getItem().getDetailPageUrl()).isEqualTo("http://www.amazon.fr/Petit-Ours-Brun-%C3%A0-l%C3%A9cole/dp/2747033236%3FSubscriptionId%3DAKIAIWRUACJI5WMGILTA%26tag%3Dabookadabra-21%26linkCode%3Dxm2%26camp%3D2025%26creative%3D165953%26creativeASIN%3D2747033236");
    }

    @Test
    public void parsingLocalLookupToGetSalesRank() throws AnswerIsNotValidException {
        assertThat(lookupAnswer.getItem().getSalesRank()).isEqualTo(7606);
    }
    
    @Test
    public void parsingLocalLookupToGetEditorialReview() throws AnswerIsNotValidException {
        assertThat(lookupAnswer.getItem().getEditorialReview()).isEqualTo("");
    }
    
    @Test
    public void parsingLocalLookupToGetAuthors() throws AnswerIsNotValidException {
		assertThat(lookupAnswer.getItem().getAttributes().getAuthors().size()).isEqualTo(1);
		assertThat(lookupAnswer.getItem().getAttributes().getAuthors().get(0)).isEqualTo("Danièle Bour");
    }
    
    @Test
    public void parsingLocalLookupToGetBinding() throws AnswerIsNotValidException {
		assertThat(lookupAnswer.getItem().getAttributes().getBinding()).isEqualTo("Broché");
    }
    
    @Test
    public void parsingLocalLookupToGetEan() throws AnswerIsNotValidException {
		assertThat(lookupAnswer.getItem().getAttributes().getEan()).isEqualTo("9782747033237");
    }
    
    @Test
    public void parsingLocalLookupToGetEdition() throws AnswerIsNotValidException {
		assertThat(lookupAnswer.getItem().getAttributes().getEdition()).isEqualTo("");
    }
    
    @Test
    public void parsingLocalLookupToGetIsbn() throws AnswerIsNotValidException {
		assertThat(lookupAnswer.getItem().getAttributes().getIsbn()).isEqualTo("2747033236");
    }

    
    @Test
    public void parsingLocalLookupToGetLabel() throws AnswerIsNotValidException {
		assertThat(lookupAnswer.getItem().getAttributes().getLabel()).isEqualTo("Bayard Jeunesse");
    }

    @Test
    public void parsingLocalLookupToGetLanguages() throws AnswerIsNotValidException {
		assertThat(lookupAnswer.getItem().getAttributes().getLanguages().size()).isEqualTo(3);
		assertThat(lookupAnswer.getItem().getAttributes().getLanguages().get(0).getName()).isEqualTo("Français");
		assertThat(lookupAnswer.getItem().getAttributes().getLanguages().get(0).getType()).isEqualTo("Unknown");
		assertThat(lookupAnswer.getItem().getAttributes().getLanguages().get(1).getName()).isEqualTo("Français");
		assertThat(lookupAnswer.getItem().getAttributes().getLanguages().get(1).getType()).isEqualTo("Original Language");
		assertThat(lookupAnswer.getItem().getAttributes().getLanguages().get(2).getName()).isEqualTo("Français");
		assertThat(lookupAnswer.getItem().getAttributes().getLanguages().get(2).getType()).isEqualTo("Published");
    }
    
    @Test
    public void parsingLocalLookupToGetListPrice() throws AnswerIsNotValidException {
		assertThat(lookupAnswer.getItem().getAttributes().getListPrice().getAmount()).isEqualTo("220");
		assertThat(lookupAnswer.getItem().getAttributes().getListPrice().getCurrencyCode()).isEqualTo("EUR");
		assertThat(lookupAnswer.getItem().getAttributes().getListPrice().getFormattedPrice()).isEqualTo("EUR 2,20");
    }
    
    @Test
    public void parsingLocalLookupToGetManufacturer() throws AnswerIsNotValidException {
		assertThat(lookupAnswer.getItem().getAttributes().getManufacturer()).isEqualTo("Bayard Jeunesse");
    }
    
    @Test
    public void parsingLocalLookupToGetNbOfPages() throws AnswerIsNotValidException {
		assertThat(lookupAnswer.getItem().getAttributes().getNumberOfPages()).isEqualTo(14);
    }
    
    @Test
    public void parsingLocalLookupToGetProductGroup() throws AnswerIsNotValidException {
		assertThat(lookupAnswer.getItem().getAttributes().getProductGroup()).isEqualTo("Book");
    }
    

    @Test
    public void parsingLocalLookupToGetProductTypeName() throws AnswerIsNotValidException {
		assertThat(lookupAnswer.getItem().getAttributes().getProductTypeName()).isEqualTo("ABIS_BOOK");
    }
    
    @Test
    public void parsingLocalLookupToGetPublisher() throws AnswerIsNotValidException {
		assertThat(lookupAnswer.getItem().getAttributes().getPublisher()).isEqualTo("Bayard Jeunesse");
    }

    @Test
    public void parsingLocalLookupToGetSku() throws AnswerIsNotValidException {
		assertThat(lookupAnswer.getItem().getAttributes().getSku()).isEqualTo("379782747033237");
    }
    

    @Test
    public void parsingLocalLookupToGetStudio() throws AnswerIsNotValidException {
		assertThat(lookupAnswer.getItem().getAttributes().getStudio()).isEqualTo("Bayard Jeunesse");
    }
    
    @Test
    public void parsingLocalLookupToGetTitle() throws AnswerIsNotValidException {
        assertThat(lookupAnswer.getItem().getAttributes().getTitle()).isEqualTo("Petit Ours Brun va à l'école");
    }
    
    @Test
    public void parsingLocalLookupFileCheckIfRequestIsAValidLookup() {
        assertThat(lookupAnswer.isItValid()).isEqualTo(true);
    }
    
    @Test
    public void parsingLocalLookupFileCheckIDType() throws AnswerIsNotValidException {
        assertThat(lookupAnswer.getRequest().getIdType()).isEqualTo("ISBN");
    }

    @Test
    public void parsingLocalLookupFileCheckItemId() throws AnswerIsNotValidException {
        assertThat(lookupAnswer.getRequest().getItemId()).isEqualTo("2747033236");
    }
    
    @Test
    public void parsingLocalLookupFileCheckResponseGroup() throws AnswerIsNotValidException {
        assertThat(lookupAnswer.getRequest().getResponseGroup()).isEqualTo("Large");
    }
    
    @Test
    public void parsingLocalLookupFileCheckSearchIndex() throws AnswerIsNotValidException {
        assertThat(lookupAnswer.getRequest().getSearchIndex()).isEqualTo("Books");
    }

    @Test
    public void parsingLocalLookupFileCheckVariationPage() throws AnswerIsNotValidException {
        assertThat(lookupAnswer.getRequest().getVariationPage()).isEqualTo("All");
    }
    
    @Test
    public void parsingLocalLookupFileCheckSimilarProducts() throws AnswerIsNotValidException {
        assertThat(lookupAnswer.getItem().getSimilarProducts().size()).isEqualTo(5);
    }
    
    @Test
    public void parsingLocalLookupFileCheckBrowseNodes() throws AnswerIsNotValidException {
        assertThat(lookupAnswer.getItem().getBrowseNodes().size()).isEqualTo(1);
    }
    
    @Test
    public void parsingLocalLookupFileCheckImages() throws AnswerIsNotValidException {
        assertThat(lookupAnswer.getItem().getImages().size()).isEqualTo(9);
    }
    
    @Test
    public void parsingLocalLookupFileCheckImagesUrls() throws AnswerIsNotValidException {
    	for (int index = 0; index < 9; index++) {
    		assertThat(lookupAnswer.getItem().getImages().get(index).getUrl()).contains("http://");
    	}
    }
    
    /****************************************************************************/
    /*						Search												*/
    /****************************************************************************/
    @Test
    public void parsingLocalSearchFileWithoutErrors() throws AnswerIsNotValidException {
        assertThat(searchAnswer.hasResults()).isEqualTo(true);
    }
    
    @Test
    public void parsingLocalSearchCheckNumberOfItems() throws AnswerIsNotValidException {
        assertThat(searchAnswer.getItems().size()).isEqualTo(10);
    }
    
    @Test
    public void parsingLocalSearchCheckNumberOfPages() throws AnswerIsNotValidException {
        assertThat(searchAnswer.getTotalPages()).isEqualTo(22794);
    }
    
    @Test
    public void parsingLocalSearchCheckNumberOfResults() throws AnswerIsNotValidException {
        assertThat(searchAnswer.getTotalResults()).isEqualTo(227935);
    }

    @Test
    public void parsingLocalSearchToGetASINs() throws AnswerIsNotValidException {
        assertThat(searchAnswer.getItems().get(0).getAsin()).isEqualTo("2278062921");
        assertThat(searchAnswer.getItems().get(1).getAsin()).isEqualTo("2278063642");
        assertThat(searchAnswer.getItems().get(2).getAsin()).isEqualTo("2278058533");
        assertThat(searchAnswer.getItems().get(3).getAsin()).isEqualTo("2278069136");
        assertThat(searchAnswer.getItems().get(4).getAsin()).isEqualTo("2226238549");
        assertThat(searchAnswer.getItems().get(5).getAsin()).isEqualTo("2091614793");
        assertThat(searchAnswer.getItems().get(6).getAsin()).isEqualTo("2848901748");
        assertThat(searchAnswer.getItems().get(7).getAsin()).isEqualTo("2253147648");
        assertThat(searchAnswer.getItems().get(8).getAsin()).isEqualTo("2035865980");
        assertThat(searchAnswer.getItems().get(9).getAsin()).isEqualTo("B00006RJNS");
    }
    
    @Test
    public void parsingLocalSearchToGetAuthors() throws AnswerIsNotValidException {
		assertThat(searchAnswer.getItems().get(0).getAttributes().getAuthors().size()).isEqualTo(2);
		assertThat(searchAnswer.getItems().get(0).getAttributes().getAuthors().get(0)).isEqualTo("Odile Martin-Cocher");
		assertThat(searchAnswer.getItems().get(0).getAttributes().getAuthors().get(1)).isEqualTo("Collectif");
    }
    
    @Test
    public void parsingLocalSearchToGetEdition() throws AnswerIsNotValidException {
		assertThat(searchAnswer.getItems().get(0).getAttributes().getEdition()).isEqualTo("édition 2008");
    }
    
    @Test
    public void parsingLocalSearchCheckSimilarProducts() throws AnswerIsNotValidException {
        assertThat(searchAnswer.getItems().get(0).getSimilarProducts().size()).isEqualTo(5);
        assertThat(searchAnswer.getItems().get(1).getSimilarProducts().size()).isEqualTo(5);
        assertThat(searchAnswer.getItems().get(2).getSimilarProducts().size()).isEqualTo(5);
        assertThat(searchAnswer.getItems().get(3).getSimilarProducts().size()).isEqualTo(5);
        assertThat(searchAnswer.getItems().get(4).getSimilarProducts().get(1).getAsin()).isEqualTo("2738120873");
        assertThat(searchAnswer.getItems().get(4).getSimilarProducts().get(1).getTitle()).isEqualTo("Trop intelligent pour être heureux ? L'adulte surdoué");
    }
    
    @Test
    public void parsingLocalSearchCheckBrowseNodes() throws AnswerIsNotValidException {
        assertThat(searchAnswer.getItems().get(0).getBrowseNodes().size()).isEqualTo(4);
        assertThat(searchAnswer.getItems().get(1).getBrowseNodes().size()).isEqualTo(4);
        assertThat(searchAnswer.getItems().get(2).getBrowseNodes().size()).isEqualTo(3);
        assertThat(searchAnswer.getItems().get(3).getBrowseNodes().size()).isEqualTo(1);
        assertThat(searchAnswer.getItems().get(4).getBrowseNodes().size()).isEqualTo(3);
    }
    
    @Test
    public void parsingLocalSearchCheckBrowseNodesChildren() throws AnswerIsNotValidException {
        assertThat(searchAnswer.getItems().get(4).getBrowseNodes().get(0).getChildren().size()).isEqualTo(0);
        assertThat(searchAnswer.getItems().get(4).getBrowseNodes().get(1).getChildren().size()).isEqualTo(11);
    }
    
    @Test
    public void parsingLocalSearchCheckBrowseNodesDeepness() throws AnswerIsNotValidException {
        assertThat(searchAnswer.getItems().get(4).getBrowseNodes().get(0).getAncestorDeepness()).isEqualTo(3);
        assertThat(searchAnswer.getItems().get(4).getBrowseNodes().get(1).getAncestorDeepness()).isEqualTo(4);
    }
    
    /****************************************************************************/
    /*						Empty Search										*/
    /****************************************************************************/
    
    @Test
    public void firstAnswerIsNotValid() throws AnswerIsNotValidException {
        assertThat(emptySearchAnswer.isItValid()).isEqualTo(true);
    }

    @Test
    public void firstAnswerKeywordIsTheOneRequested() throws AnswerIsNotValidException {
        assertThat(emptySearchAnswer.getRequest().getKeywords()).isEqualTo("%%");
    }
    
    @Test
    public void firstAnswerErrorCodeIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(emptySearchAnswer.getError().getCode()).isEqualTo("AWS.ECommerceService.NoExactMatches");
    }
    
    @Test
    public void firstAnswerErrorMessageIsTheOneExpected() throws AnswerIsNotValidException {
        assertThat(emptySearchAnswer.getError().getMessage()).isEqualTo("We did not find any matches for your request.");
    }

    @Test
    public void firstAnswerCheckTotalResults() throws AnswerIsNotValidException {
        assertThat(emptySearchAnswer.getTotalResults()).isEqualTo(0);
    }
    
    @Test
    public void firstAnswerCheckTotalPages() throws AnswerIsNotValidException {
    	assertThat(emptySearchAnswer.getTotalPages()).isEqualTo(0);
    }
}
