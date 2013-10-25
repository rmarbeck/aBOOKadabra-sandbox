package com.abookadabra.akka.requestbuilder;

public class MissionFailed {
	private final String failureReason;

	protected MissionFailed(String failureReason) {
		this.failureReason = failureReason;
	}

	public String getReason() {
		return failureReason;
	}
}
