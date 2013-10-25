package controllers;

import java.util.List;

import models.SimpleBook;
import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Props;
import play.Logger;
import play.libs.Akka;
import play.libs.F.Promise;
import play.libs.F.Function;
import play.mvc.Controller;
import play.mvc.Result;

import com.abookadabra.akka.*;
import com.abookadabra.akka.digger.AuthorRequest;
import com.abookadabra.akka.digger.BooksResponse;
import com.abookadabra.akka.requestbuilder.Mission;
import com.abookadabra.akka.requestbuilder.MissionFailed;
import com.abookadabra.akka.requestbuilder.MissionResult;

import static akka.pattern.Patterns.ask;
import static play.data.Form.form;
import views.html.akka.*;

public class AkkaHello extends Controller {
	private final static ActorRef father = Akka.system().actorOf(Props.create(Father.class), "father");

	
    public static Result index() {
        return ok(index.render());
    }
    
    public static Promise<Result> hello() {

        return Promise.wrap(ask(father, Greeter.Msg.GREET, 1000)).map(
            new Function<Object, Result>() {
                public Result apply(Object response) {
                    return ok(hello.render(response.toString()));
                }
            }
        );
    }
    
    public static Promise<Result> getFromAuthor() {
    	try {
	    	final String author = lookForStringInForm("author");
	        return Promise.wrap(ask(father, AuthorRequest.createAuthorRequest(author), 5000)).map(
	            new Function<Object, Result>() {
	                public Result apply(Object response) {
	                    return ok(authors.render(((BooksResponse) response).getBooks()));
	                }
	            }
	        );
    	} catch(Exception e) {
    		flash("error", e.getMessage());
    		return Promise.pure((Result) badRequest(index.render()));
    	}
    }
    
    public static Promise<Result> getFromSearch() {
    	try {
	    	final String search = lookForStringInForm("search");
	        return Promise.wrap(ask(father, Mission.createMission(search, 4000), 5000)).map(
	            new Function<Object, Result>() {
	                public Result apply(Object response) {
	                	if (response instanceof MissionResult) 
	                		return ok(requestResult.render(((MissionResult) response).getBooks(), ((MissionResult) response).getRequests()));
	                	flash("error", ((MissionFailed)response).getReason());
	                	return badRequest(index.render());
	                }
	            }
	        );
    	} catch(Exception e) {
    		flash("error", e.getMessage());
    		return Promise.pure((Result) badRequest(index.render()));
    	}
    }
    
    
    public static Promise<Result> getTitle() {
    	try {
	    	final String isbn = lookForStringInForm("isbn");
	        return Promise.wrap(ask(father, IsbnHolder.fromIsbn(isbn), 3000)).map(
	            new Function<Object, Result>() {
	                public Result apply(Object response) {
	                    return ok(title.render(isbn, response.toString()));
	                }
	            }
	        );
    	} catch(Exception e) {
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

}
