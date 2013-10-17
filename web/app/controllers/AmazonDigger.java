package controllers;

import static play.data.Form.form;

import com.abookadabra.utils.amazon.AmazonApiRequestBuilder;
import com.abookadabra.utils.amazon.AmazonApiRequestBuilderHelper;
import com.abookadabra.utils.amazon.AmazonApiSearchRequestBuilder;
import com.abookadabra.utils.amazon.models.AmazonApiRequest;
import com.abookadabra.utils.amazon.models.AmazonApiSearchAnswer;

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
    			new Function<AmazonApiSearchAnswer, Result>() {
        	        public Result apply(AmazonApiSearchAnswer answer) throws Exception {
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
    
    private static Promise<AmazonApiSearchAnswer> getAmazonResultForGivenExpression(String expression) {
    	AmazonApiRequest request = AmazonApiSearchRequestBuilder.search().forLikeKeywords(expression).getRequest();
    	Logger.info(AmazonApiRequestBuilderHelper.getFullUrl(request));
    	return AmazonApiRequestBuilderHelper.getWSRequestHolder(request).get().map(
				new Function<WS.Response, AmazonApiSearchAnswer>() {
					public AmazonApiSearchAnswer apply(WS.Response response) {
						//Logger.info("Amazon result : "+ response.getBody());
						return AmazonApiSearchAnswer.createInstanceFrom(response.asXml());
					}
				}
				);
    }
}
