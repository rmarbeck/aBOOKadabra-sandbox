package com.abookadabra.akka.digger;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import models.SimpleBook;
import play.Logger;
import play.libs.F.Callback;
import play.libs.F.Promise;
import play.libs.WS;
import play.libs.F.Function;
import play.mvc.Result;
import views.html.amazon.index;
import views.html.amazon.result;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import com.abookadabra.utils.amazon.api.Request;
import com.abookadabra.utils.amazon.api.RequestHelper;
import com.abookadabra.utils.amazon.api.SearchRequestBuilder;
import com.abookadabra.utils.amazon.api.models.SearchAnswer;
import com.abookadabra.utils.amazon.api.models.answerelements.Item;

public class BooksRetriever extends UntypedActor {
	private int timeAllocatedForThisQueryInMillis;
	private ActorRef sender;
	private Request currentRequest;
	private WS.Response currentResponse;
	private SearchAnswer currentAnswer;
	private BooksResponse currentResult;
	
	public BooksRetriever(int timeout) {
		timeAllocatedForThisQueryInMillis = timeout;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof SubAuthorRequest) {
			sender = getSender();
			SubAuthorRequest subAuthorRequest = (SubAuthorRequest)msg;
			Logger.info("I ("+getSelf().path().name()+") am receiving work, looking for "+subAuthorRequest.getAuthorRequest().getAuthor()+" on index "+subAuthorRequest.getIndex()+".");
			getAnswerForPage(subAuthorRequest.getAuthorRequest().getAuthor(), subAuthorRequest.getIndex());
		} else if (msg.equals("ready")) {
			readBooksInThisPage();
			sender.tell(currentResult, getSelf());
		} else {
			unhandled(msg);
		}
	}

	private void getAnswerForPage(String author, int pageNumber) {
		currentRequest = SearchRequestBuilder.build().forKeywords(author).browseOnlyNode(17).itemPage(pageNumber).largeResponse().getRequest();
		try {
			//Logger.info(currentRequest.getFullUrl());
			RequestHelper.getWSRequestHolder(currentRequest).setTimeout(timeAllocatedForThisQueryInMillis).get().map(
	    			new Function<WS.Response, SearchAnswer>() {
	        	        public SearchAnswer apply(WS.Response response) throws Exception {
	        	        	currentResponse = response;
	        	        	currentAnswer = SearchAnswer.createInstanceFrom(currentResponse.asXml());
	        	        	Logger.info("Not sending !!!");
	        	        	getSelf().tell("ready", getSelf());
	        	        	return currentAnswer;
	        	        } 
	        	      }
	        	    , context().dispatcher()).onFailure(new Callback<Throwable>() {
	        	    	@Override
	        			public void invoke(Throwable arg0) throws Throwable {
	        				Logger.info("Timeout occured !!!");
	        			}
	        	    }, context().dispatcher());
			
			//promisedAnswer.onFailure(new CallbackMethod<Throwable>(), context().dispatcher());
			
			//currentResponse = RequestHelper.getWSRequestHolder(currentRequest).get().get(timeAllocatedForThisQueryInMillis, TimeUnit.MILLISECONDS);
			//currentAnswer = SearchAnswer.createInstanceFrom(currentResponse.asXml());
		} catch (RuntimeException e) {
			Logger.info("Timeout for query number :" + pageNumber);
		}
	}
	
	private void readBooksInThisPage() throws Exception {
		currentResult = new BooksResponse();
		if (currentAnswer.hasResults()) {
			for (Item item : currentAnswer.getItems()) {
				SimpleBook currentBook = new SimpleBook();
				currentBook.isbn = item.getAttributes().getIsbn();
				currentBook.description = item.getEditorialReview();
				currentBook.title = item.getAttributes().getTitle();
				if (item.getImages().size()>0)
					currentBook.imageUrl = item.getImages().get(0).getUrl();
				currentBook.authors = new ArrayList<String>();
				for (String author : item.getAttributes().getAuthors()) {
					currentBook.authors.add(author);
				}
				currentResult.getBooks().add(currentBook);
			}
		}
		Logger.info("I ("+getSelf().path().name()+") am about to answer with "+currentResult.getBooks().size()+" answers.");
		if (currentResult.getBooks().size()==0) {
			Logger.info("Query : "+currentRequest.getFullUrl());
			Logger.info("Response : "+currentResponse.getBody().toString());
		}
	}

}
