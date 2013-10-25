package com.abookadabra.akka.digger;

import java.util.concurrent.TimeUnit;

import play.Logger;
import play.libs.WS;
import akka.actor.UntypedActor;

import com.abookadabra.utils.amazon.api.Request;
import com.abookadabra.utils.amazon.api.RequestHelper;
import com.abookadabra.utils.amazon.api.SearchRequestBuilder;
import com.abookadabra.utils.amazon.api.models.SearchAnswer;

public class NumberOfResponseReader extends UntypedActor {
	private static int MAX_NUMBER_OF_PAGES = 10;
	private int timeAllocatedForThisQueryInMillis;
	
	public NumberOfResponseReader(int timeout) {
		timeAllocatedForThisQueryInMillis = timeout;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof AuthorRequest) {
			AuthorRequest authorRequest = (AuthorRequest)msg;
			int answer = readNumberOfPages(authorRequest.getAuthor());
			if (answer>0) {
				getSender().tell(Integer.valueOf(Math.min(answer, MAX_NUMBER_OF_PAGES)), getSelf());
			} else {
				getSender().tell(Integer.valueOf(0), getSelf());
			}
		} else {
			unhandled(msg);
		}
	}

	private int readNumberOfPages(String author) {
		Logger.info("Try to retrieve page number for " + author);
		Request request = SearchRequestBuilder.build().forKeywords(author).getRequest();
		//Logger.info(request.getFullUrl());
		WS.Response response = RequestHelper.getWSRequestHolder(request).get().get(timeAllocatedForThisQueryInMillis, TimeUnit.MILLISECONDS);
		Logger.info("We have received an answer.");
		
		SearchAnswer answer = SearchAnswer.createInstanceFrom(response.asXml());
		
		//Logger.info("Item => "+answer.getItem());
		if (answer.hasResults()) {
			Logger.info("Answer is : "+answer.getTotalPages());
			return (int) answer.getTotalPages();
		} else {
			return -1;
		}
	}
}
