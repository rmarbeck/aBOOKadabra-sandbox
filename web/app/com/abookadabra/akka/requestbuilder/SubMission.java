package com.abookadabra.akka.requestbuilder;

public class SubMission {
	protected enum SortingKey {
		SalesRank,
		OldestFirst,
		MoreExpensive,
		LessExpensive,
		AtoZ,
		ZtoA,
		NotSpecified;
	}
	
	private final String missionQuery;
	private final SortingKey sortBy;

	private final long timeAllocatedForSuccessInMillis;

	protected SubMission(String missionQuery, long timeAllocatedForSuccessInMillis) {
		this.missionQuery = missionQuery;
		this.timeAllocatedForSuccessInMillis = timeAllocatedForSuccessInMillis;
		sortBy = SortingKey.SalesRank;
	}
	
	protected SubMission(String missionQuery, long timeAllocatedForSuccessInMillis, SortingKey key) {
		this.missionQuery = missionQuery;
		this.timeAllocatedForSuccessInMillis = timeAllocatedForSuccessInMillis;
		sortBy = key;
	}
	
	protected SubMission(Mission mission, SortingKey key) {
		this.missionQuery = mission.getMissionQuery();
		this.timeAllocatedForSuccessInMillis = mission.getTimeAllocatedForSuccessInMillis();
		sortBy = key;
	}
	
	public static SubMission createMission(String missionQuery, long timeAllocatedForSuccessInMillis) {
		return new SubMission(missionQuery, timeAllocatedForSuccessInMillis);
	}

	protected String getMissionQuery() {
		return missionQuery;
	}

	protected long getTimeAllocatedForSuccessInMillis() {
		return timeAllocatedForSuccessInMillis;
	}

	protected SortingKey getSortBy() {
		return sortBy;
	}
}
