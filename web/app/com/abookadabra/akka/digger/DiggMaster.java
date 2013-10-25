package com.abookadabra.akka.digger;

import java.util.ArrayList;
import java.util.List;

import play.Logger;
import models.SimpleBook;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Procedure;

public class DiggMaster extends UntypedActor {
	private static int MAX_NUMBER_OF_CHILDREN = 10;
	private int currentWorkToDo;
	private int nextToWorkOn;
	private int numberOfAnswerNotCollated;
	AuthorRequest currentRequest = null;
	BooksResponse currentResponse = null;
	ActorRef reader = null;
	List<ActorRef> workers = null;
	ActorRef caller = null;
	
	Procedure<Object> lookingForTheNumberOfTasksToDo = new Procedure<Object>() {
		@Override
		public void apply(Object msg) {
			if (msg instanceof Integer) {
				int numberOfPages = ((Integer)msg).intValue();
				currentWorkToDo = numberOfPages;
				nextToWorkOn = 1;
				numberOfAnswerNotCollated = numberOfPages;
				createChildrenIfNeeded(numberOfPages);
				//Logger.info("Switching back from 'lookingForTheNumberOfTasksToDo'.");
				getContext().unbecome();
				tellAvailableChildrenToWork(numberOfPages);
			} else {
				unhandled(msg);
			}
		}
	};
	
	Procedure<Object> collatingResponse = new Procedure<Object>() {
		@Override
		public void apply(Object msg) {
			if (msg instanceof BooksResponse) {
				Logger.info("Answer from "+getSender().path().name());
				buildAnswer((BooksResponse)msg);
				if (numberOfAnswerNotCollated == 0) {
					caller.tell(currentResponse, getSelf());
					reinit();
					//Logger.info("Switching back from 'collatingResponse'.");
					getContext().unbecome();
				} else if (currentWorkToDo != 0) {
					tellThisChildToWork(getSender());
				}
			} else {
				unhandled(msg);
			}
		}
	};
	

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof AuthorRequest) {
			Logger.info("J'ai du boulot");
			currentRequest = (AuthorRequest) msg;
			if (reader == null)
				reader = context().actorOf(Props.create(NumberOfResponseReader.class, 1500), "reader");
			caller = getSender();
			reader.tell(msg, getSelf());
			//Logger.info("Switching back to 'lookingForTheNumberOfTasksToDo'.");
			getContext().become(lookingForTheNumberOfTasksToDo);
		} else if (msg instanceof BooksResponse) {
			//Logger.info("Switching back to 'collatingResponse'.");
			getContext().become(collatingResponse);
			getSelf().forward(msg, context());
		} else	{
			unhandled(msg);
		}
	}

	private void createChildrenIfNeeded(int size) {
		if (workers == null) {
			workers = new ArrayList<ActorRef>();
		}
		int index = workers.size();
		while (workers.size() <= Math.min(MAX_NUMBER_OF_CHILDREN, size)) {
			workers.add(context().actorOf(Props.create(BooksRetriever.class, 3000), "worker"+(index++)));
		}
	}
	
	
	private void buildAnswer(BooksResponse toAdd) {
		//Logger.info("Entering in build ("+numberOfAnswerNotCollated+")");
		if (currentResponse == null) {
			currentResponse = new BooksResponse();
		}
		for (SimpleBook book : toAdd.getBooks()) {
			currentResponse.getBooks().add(book);
		}
		Logger.info("Existing from build ("+numberOfAnswerNotCollated+", now "+currentResponse.getBooks().size()+" books)");
		numberOfAnswerNotCollated--;
	}
	
	private void tellThisChildToWork(ActorRef child) {
		//Logger.info("Ask to work on "+nextToWorkOn+" to worker "+child.path().name());
		child.tell(currentRequest.createSubAuthorRequest(nextToWorkOn++), getSelf());
		currentWorkToDo--;
	}
	
	private void tellAvailableChildrenToWork(int size) {
		int index = 1;
		while(index <= Math.min(MAX_NUMBER_OF_CHILDREN, size)) {
			//Logger.info("Ask to work on "+nextToWorkOn+" to worker "+workers.get(index-1).path().name());
			workers.get(index-1).tell(currentRequest.createSubAuthorRequest(nextToWorkOn++), getSelf());
			currentWorkToDo--;
			index++;
		}
	}
	
	private void reinit() {
		caller = null;
		currentRequest = null;
		currentResponse = null;
	}
}
