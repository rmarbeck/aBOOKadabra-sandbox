package controllers;

import static play.data.Form.form;

import com.abookadabra.utils.BrowseNodeAnswerWrapper;
import com.abookadabra.utils.amazon.api.BrowseNodeRequestBuilder;
import com.abookadabra.utils.amazon.api.Request;
import com.abookadabra.utils.amazon.api.RequestBuilder;
import com.abookadabra.utils.amazon.api.RequestBuilderHelper;
import com.abookadabra.utils.amazon.api.RequestHelper;
import com.abookadabra.utils.amazon.api.SearchRequestBuilder;
import com.abookadabra.utils.amazon.api.models.BrowseNodeAnswer;
import com.abookadabra.utils.amazon.api.models.SearchAnswer;

import play.*;
import play.libs.WS;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.mvc.*;
import views.html.amazon.*;

public class AmazonDigger extends Controller {

    public static SimpleResult GO_INDEX = redirect(
            routes.AmazonDigger.index()
        );
	
    public static Result index() {
        return ok(index.render());
    }

    public static Promise<Result> search() {
    	try {
	    	String expression = lookForStringInForm("expression");
	    	return getAmazonResultForGivenExpression(expression).map(
    			new Function<SearchAnswer, Result>() {
        	        public Result apply(SearchAnswer answer) throws Exception {
        	        	Logger.info("Amazon answer : "+ answer.isItValid());
        	        	if (answer.isItValid())
        	        		return ok(result.render(answer));
        	        	flash("error", "Answer is not valid");
        	    		return badRequest(index.render());
        	        } 
        	      }
        	    );
    	} catch (Exception e) {
    		flash("error", e.getMessage());
    		return Promise.pure((Result) badRequest(index.render()));
    	}
    }
    
    public static Promise<Result> browseNode() {
    	try {
	    	int id = lookForIntInForm("id");
	    	return browserAmazonNodeForGivenID(id).map(
    			new Function<BrowseNodeAnswerWrapper, Result>() {
        	        public Result apply(BrowseNodeAnswerWrapper answerWrapper) throws Exception {
        	        	Logger.info("Amazon answer : "+ answerWrapper.getAnswer().isItValid());
        	        	if (answerWrapper.getAnswer().isItValid())
        	        		return ok(node.render(answerWrapper));
        	        	flash("error", "Answer is not valid");
        	    		return badRequest(index.render());
        	        } 
        	      }
        	    );
    	} catch (Exception e) {
    		flash("error", e.getMessage());
    		return Promise.pure((Result) badRequest(index.render()));
    	}
    }
    
    public static Promise<Result> nodeContent() {
    	try {
	    	int id = lookForIntInForm("id");
	    	return getAmazonResultForGivenNodeId(id).map(
	    			new Function<SearchAnswer, Result>() {
	        	        public Result apply(SearchAnswer answer) throws Exception {
	        	        	Logger.info("Amazon answer : "+ answer.isItValid());
	        	        	if (answer.isItValid())
	        	        		return ok(result.render(answer));
	        	        	flash("error", "Answer is not valid");
	        	    		return badRequest(index.render());
	        	        } 
	        	      }
	        	    );
    	} catch (Exception e) {
    		flash("error", e.getMessage());
    		return Promise.pure((Result) badRequest(index.render()));
    	}
    }
    
    
    private static String lookForStringInForm(String fieldName) throws Exception {
    	String valueFound = form().bindFromRequest().get(fieldName);
    	if (valueFound!= null)
    		return valueFound;
    	throw new Exception("Field ["+fieldName+"] not found in form.");
    }
    
    private static int lookForIntInForm(String fieldName) throws Exception {
    	String valueFound = form().bindFromRequest().get(fieldName);
    	if (valueFound!= null )
    		return Integer.parseInt(valueFound);
    	throw new Exception("Field ["+fieldName+"] not found in form.");
    }
    
    private static Promise<SearchAnswer> getAmazonResultForGivenExpression(String expression) {
    	Request request = SearchRequestBuilder.build().forLikeKeywords(expression).getRequest();
    	Logger.info(request.getFullUrl());
    	return RequestHelper.getWSRequestHolder(request).get().map(
				new Function<WS.Response, SearchAnswer>() {
					public SearchAnswer apply(WS.Response response) {
						//Logger.info("Amazon result : "+ response.getBody());
						return SearchAnswer.createInstanceFrom(response.asXml());
					}
				}
				);
    }
    
    private static Promise<SearchAnswer> getAmazonResultForGivenNodeId(int id) {
    	Request request = SearchRequestBuilder.build().forBrowseNode(id).getRequest();
    	Logger.info(request.getFullUrl());
    	return RequestHelper.getWSRequestHolder(request).get().map(
				new Function<WS.Response, SearchAnswer>() {
					public SearchAnswer apply(WS.Response response) {
						//Logger.info("Amazon result : "+ response.getBody());
						return SearchAnswer.createInstanceFrom(response.asXml());
					}
				}
				);
    }
    
    private static Promise<BrowseNodeAnswerWrapper> browserAmazonNodeForGivenID(int id) {
    	final Request request = BrowseNodeRequestBuilder.build().forId(id).getRequest();
    	Logger.info(request.getFullUrl());
    	return RequestHelper.getWSRequestHolder(request).get().map(
				new Function<WS.Response, BrowseNodeAnswerWrapper>() {
					public BrowseNodeAnswerWrapper apply(WS.Response response) {
						//Logger.info("Amazon result : "+ response.getBody());
						BrowseNodeAnswerWrapper answer = new BrowseNodeAnswerWrapper(BrowseNodeAnswer.createInstanceFrom(response.asXml()), request.getFullUrl());
						return answer;
					}
				}
				);
    }
}
