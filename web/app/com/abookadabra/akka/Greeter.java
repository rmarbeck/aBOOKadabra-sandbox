package com.abookadabra.akka;

import play.Logger;
import akka.actor.UntypedActor;

public class Greeter extends UntypedActor {
	public static enum Msg {
		GREET, DONE;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg == Msg.GREET) {
			Logger.info("I am working hard !");
			//System.out.println("Hello World!");
			getSender().tell("Salut mec...", getSelf());
		} else
			unhandled(msg);
	}

}
