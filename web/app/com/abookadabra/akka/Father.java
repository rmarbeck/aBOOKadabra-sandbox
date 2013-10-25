package com.abookadabra.akka;

import play.Logger;

import com.abookadabra.akka.digger.AuthorRequest;
import com.abookadabra.akka.digger.DiggMaster;
import com.abookadabra.akka.requestbuilder.Master;
import com.abookadabra.akka.requestbuilder.Mission;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class Father extends UntypedActor {
	ActorRef myIsbnGetter = null;
	ActorRef myGreeter = null;
	ActorRef masterDigger = null;
	ActorRef requestBuilderMaster = null;

	@Override
	public void onReceive(Object msg) throws Exception {
		Logger.info("Father a du boulot");
		if (msg instanceof IsbnHolder) {
			if (myIsbnGetter==null)
				myIsbnGetter = context().actorOf(Props.create(IsbnGetter.class), "getisbn");
			myIsbnGetter.tell(msg, context().sender());
		} else if (msg instanceof AuthorRequest) {
			Logger.info("Pour un digger");
			if (masterDigger==null)
				masterDigger = context().actorOf(Props.create(DiggMaster.class), "masterDigger");
			masterDigger.tell(msg, context().sender());
		} else if (msg instanceof Greeter.Msg) {
			if (myGreeter==null)
				myGreeter = context().actorOf(Props.create(Greeter.class), "greeter");
			myGreeter.tell(msg, context().sender());
		} else if (msg instanceof Mission){
			if (requestBuilderMaster ==null)
				requestBuilderMaster = context().actorOf(Props.create(Master.class), "masterbuilder");
			requestBuilderMaster.tell(msg, context().sender());
		} else {
			unhandled(msg);
		}
	}
}
