package com.abookadabra.akka.requestbuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import models.SimpleBook;
import play.Logger;
import play.libs.Akka;
import play.libs.F.Callback;
import play.libs.F.Function;
import play.libs.WS;
import scala.concurrent.duration.FiniteDuration;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.japi.Procedure;

import com.abookadabra.akka.requestbuilder.SubMission;
import com.abookadabra.utils.amazon.api.Request;
import com.abookadabra.utils.amazon.api.RequestHelper;
import com.abookadabra.utils.amazon.api.SearchRequestBuilder;
import com.abookadabra.utils.amazon.api.models.Answer.AnswerIsNotValidException;
import com.abookadabra.utils.amazon.api.models.SearchAnswer;
import com.abookadabra.utils.amazon.api.models.answerelements.Item;

public class AmazonRequester extends UntypedActor {
	private final static int BROWSE_NODE = 301132;
	private Integer identifier;
	private long startTime;
	private long endTime;
	private long startTimeAmazon;
	private long endTimeAmazon;
	private SubMission myMission;
	private long maxTimeToWork;
	private Request requestToAmazon;
	private WS.Response responseFromAmazon;
	private SearchAnswer answerParsedFromAmazonResponse;
	private Result resultOfThisWork;
	private Error errorInThisWork;
	private ActorRef missioner;
	
	protected AmazonRequester(long maxTimeToWork, Integer identifier) {
		this.maxTimeToWork = maxTimeToWork;
		this.identifier = identifier;
	}
	
	Procedure<Object> working = new Procedure<Object>() {
		@Override
		public void apply(Object msg) throws AnswerIsNotValidException {
			if (msg.equals("Answer received")) {
				replyToSenderWithCorrectAnswer();
				getContext().unbecome();
			} else if (msg.equals("Query timedout")) {
				logInfo("Query timedout");
				errorInThisWork = new Error("Inner query was to slow");
				replyToSenderWithError();
				getContext().unbecome();
			} else if (msg.equals("It's time baby")) {
				logInfo("It's time baby");
				errorInThisWork = new Error("Sorry... it's me");
				replyToSenderWithError();
				getContext().unbecome();
			} else {
				unhandled(msg);
			}
		}
	};
	
	
	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof SubMission) {
			startTime = System.currentTimeMillis();
			logInfo("J'ai du boulot");
			myMission = (SubMission) msg;
			missioner = getSender();
			getContext().become(working);
			Akka.system().scheduler().scheduleOnce(getTimeToSucceed(), getSelf(), "It's time baby", getContext().dispatcher(), getSelf());
			startWorking();
		} else if (msg.equals("It's time baby")) {
			logInfo("Won once again !, in "+(endTime-startTime)+"ms [oh:"+((endTime-startTime)-(endTimeAmazon-startTimeAmazon))+"].");
		}else {
			unhandled(msg);
		}
	}
	
	private void startWorking() {
		createQuery();
		sendQuery();
	}
	
	private void createQuery() {
		switch(myMission.getSortBy()) {
			case SalesRank :
				requestToAmazon = SearchRequestBuilder.build()
				.forKeywords(myMission.getMissionQuery())
				.browseOnlyNode(BROWSE_NODE)
				.sortBySalesRank()
				.largeResponse()
				.getRequest();
				break;
			case OldestFirst :
				requestToAmazon = SearchRequestBuilder.build()
				.forKeywords(myMission.getMissionQuery())
				.browseOnlyNode(BROWSE_NODE)
				.sortOldestFirst()
				.largeResponse()
				.getRequest();
				break;
			case MoreExpensive :
				requestToAmazon = SearchRequestBuilder.build()
				.forKeywords(myMission.getMissionQuery())
				.browseOnlyNode(BROWSE_NODE)
				.sortMoreExpensiveFirst()
				.largeResponse()
				.getRequest();
				break;
			case LessExpensive :
				requestToAmazon = SearchRequestBuilder.build()
				.forKeywords(myMission.getMissionQuery())
				.browseOnlyNode(BROWSE_NODE)
				.sortCheapestFirst()
				.largeResponse()
				.getRequest();
				break;
			case AtoZ :
				requestToAmazon = SearchRequestBuilder.build()
				.forKeywords(myMission.getMissionQuery())
				.browseOnlyNode(BROWSE_NODE)
				.sortAtoZTitle()
				.largeResponse()
				.getRequest();
				break;
			case ZtoA :
				requestToAmazon = SearchRequestBuilder.build()
				.forKeywords(myMission.getMissionQuery())
				.browseOnlyNode(BROWSE_NODE)
				.sortZtoATitle()
				.largeResponse()
				.getRequest();
				break;
			default:
				requestToAmazon = SearchRequestBuilder.build()
				.forKeywords(myMission.getMissionQuery())
				.browseOnlyNode(BROWSE_NODE)
				.largeResponse()
				.getRequest();
				break;
		}
	}
	
	private void sendQuery() {
		startTimeAmazon = System.currentTimeMillis();
		RequestHelper.getWSRequestHolder(requestToAmazon).setTimeout((int)(maxTimeToWork*0.95)).get().map(
    			new Function<WS.Response, SearchAnswer>() {
        	        public SearchAnswer apply(WS.Response response) throws Exception {
        	        	endTimeAmazon = System.currentTimeMillis();
        	        	responseFromAmazon = response;
        	        	answerParsedFromAmazonResponse = SearchAnswer.createInstanceFrom(responseFromAmazon.asXml());
        	        	getSelf().tell("Answer received", getSelf());
        	        	return answerParsedFromAmazonResponse;
        	        } 
        	      }
        	    , context().dispatcher()).onFailure(new Callback<Throwable>() {
        	    	@Override
        			public void invoke(Throwable arg0) throws Throwable {
        	    		getSelf().tell("Query timedout", getSelf());
        			}
        	    }, context().dispatcher());
	}
	
	private void replyToSenderWithCorrectAnswer() throws AnswerIsNotValidException {
		buildAnswer();
		sendAnswer();
	}
	
	private void replyToSenderWithError() throws AnswerIsNotValidException {
		missioner.tell(errorInThisWork, getSelf());
		endTime = System.currentTimeMillis();
	}
	
	private void buildAnswer() throws AnswerIsNotValidException {
		try {
			resultOfThisWork = new Result();
			for (Item item : answerParsedFromAmazonResponse.getItems()) {
				resultOfThisWork.addBook(getBookFromItem(item));
			}
		} catch (AnswerIsNotValidException e) {
			Logger.warn("Answer is invalid for this query : "+requestToAmazon.getFullUrl());
			throw e;
		}
	}
	
	private SimpleBook getBookFromItem(Item item) {
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
		return currentBook;
	}
	
	private void sendAnswer() {
		missioner.tell(resultOfThisWork, getSelf());
		endTime = System.currentTimeMillis();
	}
	
	private FiniteDuration getTimeToSucceed() {
		return FiniteDuration.create(maxTimeToWork, TimeUnit.MILLISECONDS);
	}
	
	protected class Result {
		private final List<SimpleBook> books;
		private final Integer identifier;
		private final String request;
		
		public Result() {
			books = new ArrayList<SimpleBook>();
			identifier = AmazonRequester.this.identifier;
			request = AmazonRequester.this.requestToAmazon.getFullUrl();
		}
		
		protected List<SimpleBook> getBooks() {
			return books;
		}

		protected void addBook(SimpleBook book) {
			books.add(book);
		}

		public Integer getIdentifier() {
			return identifier;
		}

		public String getRequest() {
			return request;
		}
	}
	
	protected class Error {
		private final String reason;
		private final Integer identifier;
		
		public Error(String reason) {
			this.reason = reason;
			identifier = AmazonRequester.this.identifier;
		}
		
		protected String getReason() {
			return reason;
		}

		public Integer getIdentifier() {
			return identifier;
		}
	}
	
	private void logInfo(String message) {
		Logger.info(getSelf().path().name()+" - "+message);
	}
}
