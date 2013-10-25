package com.abookadabra.akka.requestbuilder;

public class Mission {
	private final String missionQuery;

	private final long timeAllocatedForSuccessInMillis;

	protected Mission(String missionQuery, long timeAllocatedForSuccessInMillis) {
		this.missionQuery = missionQuery;
		this.timeAllocatedForSuccessInMillis = timeAllocatedForSuccessInMillis;
	}
	
	public static Mission createMission(String missionQuery, long timeAllocatedForSuccessInMillis) {
		return new Mission(missionQuery, timeAllocatedForSuccessInMillis);
	}

	protected String getMissionQuery() {
		return missionQuery;
	}

	protected long getTimeAllocatedForSuccessInMillis() {
		return timeAllocatedForSuccessInMillis;
	}
}
