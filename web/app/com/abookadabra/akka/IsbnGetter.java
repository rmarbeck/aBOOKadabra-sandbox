package com.abookadabra.akka;

import java.util.concurrent.TimeUnit;

import com.abookadabra.utils.amazon.api.LookupRequestBuilder;
import com.abookadabra.utils.amazon.api.Request;
import com.abookadabra.utils.amazon.api.RequestHelper;
import com.abookadabra.utils.amazon.api.SearchRequestBuilder;
import com.abookadabra.utils.amazon.api.models.LookupAnswer;
import com.abookadabra.utils.amazon.api.models.SearchAnswer;

import play.Logger;
import play.libs.WS;
import akka.actor.UntypedActor;

public class IsbnGetter extends UntypedActor {

	public static enum Msg {
		GREET, DONE;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof IsbnHolder) {
			String isbnToFind = ((IsbnHolder) msg).getIsbn();
			Logger.info("Have to work with " + isbnToFind);
			Request request = LookupRequestBuilder.build().forIsbn(isbnToFind).getRequest();
			Logger.info(request.getFullUrl());
			WS.Response response = RequestHelper.getWSRequestHolder(request).get().get(2000, TimeUnit.MILLISECONDS);
			Logger.info("We have received an answer.");
			
			LookupAnswer answer = LookupAnswer.createInstanceFrom(response.asXml());
			//Logger.info("Item => "+answer.getItem());
			if (answer.hasResults()) {
				getSender().tell(answer.getItem().getAttributes().getTitle(), getSelf());
			} else {
				getSender().tell(answer.getError().getMessage(), getSelf());
			}
		} else
			unhandled(msg);
	}

}
