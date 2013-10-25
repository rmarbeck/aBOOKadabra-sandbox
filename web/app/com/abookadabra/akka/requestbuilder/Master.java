package com.abookadabra.akka.requestbuilder;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;
import static akka.actor.SupervisorStrategy.stop;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import models.SimpleBook;
import play.Logger;
import play.libs.Akka;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.actor.UntypedActor;
import akka.japi.Function;
import akka.japi.Procedure;

import com.abookadabra.akka.requestbuilder.AmazonRequester.Result;
import com.abookadabra.akka.requestbuilder.SubMission.SortingKey;
import com.abookadabra.utils.amazon.api.models.Answer.AnswerIsNotValidException;

public class Master extends UntypedActor {
	private long startTime;
	private long endTime;
	private Mission myMission;
	private ActorRef missioner;
	private Result resultSentByAmazonRequester;
	private AmazonRequester.Error errorSentByAmazonRequester;
	private MissionResult missionResult;
	private MissionFailed missionFailed;
	private int numberOfRequestPending = 0;
	private Map<Integer, ActorRef> amazonRequester = null;

	Procedure<Object> working = new Procedure<Object>() {
		@Override
		public void apply(Object msg) {
			if (msg instanceof Result) {
				resultSentByAmazonRequester = (Result) msg;
				buildResult();
				if (hasFinished() && hasResult()) {
					sendResult();
					getContext().unbecome();
				} else if (hasFinished()) {
					getSelf().tell("Very sorry,  it's a mess !", getSelf());
				}
			} else if (msg instanceof AmazonRequester.Error) {
				// try again once ?
				errorSentByAmazonRequester = (AmazonRequester.Error) msg;
				logInfo("Error from child : "+errorSentByAmazonRequester.getReason());
				missionFailed = new MissionFailed(errorSentByAmazonRequester.getReason());
				sendErrorToMissioner();
				getContext().unbecome();
			} else if (msg instanceof String) {
				if (msg.equals("Mission is incorrect")) {
					missionFailed = new MissionFailed("Look at that mission, seriously !");
				} else if (msg.equals("Very sorry,  it's a mess !")) {
					missionFailed = new MissionFailed("Work failed somewhere");
				} else if (msg.equals("It's time baby")) {
					missionFailed = new MissionFailed("Not as fast as expected");
				} else {
					missionFailed = null;
					unhandled(msg);
				}
				if (missionFailed!=null) {
					sendErrorToMissioner();
					getContext().unbecome();
				}
			} else {
				unhandled(msg);
			}
		}
	};

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Mission) {
			startTime = System.currentTimeMillis();
			logInfo("J'ai du boulot");
			myMission = (Mission) msg;
			missioner = getSender();
			getContext().become(working);
			Akka.system()
					.scheduler()
					.scheduleOnce(getTimeToSucceed(), getSelf(),
							"It's time baby", getContext().dispatcher(),
							getSelf());
			startWorking();
		} else if (msg.equals("It's time baby")) {
			logInfo("Won once again !, in " + (endTime - startTime) + "ms.");
		} else {
			unhandled(msg);
		}
	}

	private void startWorking() {
		if (isMissionIncorrect()) {
			getSelf().tell("Mission is incorrect", getSelf());
		} else {
			missionResult = null;
			numberOfRequestPending = 0;
			askForAmazonAnswers();
		}
	}

	
	private void askForAmazonAnswers() {
		if (amazonRequester == null) {
			amazonRequester = new TreeMap<Integer, ActorRef>();
			for (int index = 1; index <= 5; index++) {
				amazonRequester.put(index, getContext().actorOf(Props.create(AmazonRequester.class, (long)(myMission.getTimeAllocatedForSuccessInMillis() / 1.5), index), "amazonRequester-"+index));
				getContext().watch(amazonRequester.get(index));	
			}
		}
		amazonRequester.get(1).tell(new SubMission(myMission, SortingKey.SalesRank), getSelf());
		numberOfRequestPending++;
		amazonRequester.get(2).tell(new SubMission(myMission, SortingKey.OldestFirst), getSelf());
		numberOfRequestPending++;
		amazonRequester.get(3).tell(new SubMission(myMission, SortingKey.MoreExpensive), getSelf());
		numberOfRequestPending++;
		amazonRequester.get(4).tell(new SubMission(myMission, SortingKey.LessExpensive), getSelf());
		numberOfRequestPending++;
		amazonRequester.get(5).tell(new SubMission(myMission, SortingKey.NotSpecified), getSelf());
		numberOfRequestPending++;
	}

	private boolean isMissionIncorrect() {
		if (myMission.getMissionQuery() == null)
			return true;
		if (myMission.getMissionQuery().equals(""))
			return true;
		return false;
	}

	private boolean oldBuildResult() {
		if (resultSentByAmazonRequester.getBooks().size() <= 5) {
			logInfo("minus 5");
			return false;
		}
		return true;

	}

	private boolean hasFinished() {
		return (numberOfRequestPending == 0);
	}
	
	private boolean hasResult() {
		return (missionResult != null && !missionResult.getBooks().isEmpty());
	}

	private void buildResult() {
		if (missionResult == null)
			missionResult = new MissionResult();
		int index = 1;
		for (SimpleBook book : resultSentByAmazonRequester.getBooks()) {
			missionResult.addBook(resultSentByAmazonRequester.getIdentifier(),book);
			if (index++ == 5)
				break;
		}
		missionResult.putRequest(resultSentByAmazonRequester.getIdentifier(), resultSentByAmazonRequester.getRequest());
		numberOfRequestPending--;
	}

	private void sendResult() {
		missioner.tell(missionResult, getSelf());
		endTime = System.currentTimeMillis();
	}

	private void sendErrorToMissioner() {
		logInfo("Telling to missioner there is an error");
		missioner.tell(missionFailed, getSelf());
		endTime = System.currentTimeMillis();
	}

	private FiniteDuration getTimeToSucceed() {
		return FiniteDuration.create(
				myMission.getTimeAllocatedForSuccessInMillis(),
				TimeUnit.MILLISECONDS);
	}

	private static SupervisorStrategy strategy = new OneForOneStrategy(10,
			Duration.create("1 minute"), new Function<Throwable, Directive>() {
				public Directive apply(Throwable t) {
					if (t instanceof AnswerIsNotValidException) {
						Logger.error("Child received an invalid anwser.");
						return resume();
					} else if (t instanceof NullPointerException) {
						return restart();
					} else if (t instanceof IllegalArgumentException) {
						return stop();
					} else {
						return escalate();
					}
				}
			});

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}

	private void logInfo(String message) {
		Logger.info(getSelf().path().name()+" - "+message);
	}
}
