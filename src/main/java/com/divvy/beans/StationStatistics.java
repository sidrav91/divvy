package com.divvy.beans;

import java.util.Map;

public class StationStatistics {
	
	private String stationName;
	private String mostCommonDestination;
	private AgeGroup ageGroup;
	private Double revenueGenerated;
	private Map<Integer, Double> durationTrend;
	
	public StationStatistics() {
		super();
	}
	
	public StationStatistics(String stationName, String mostCommonDestination, AgeGroup ageGroup,
			Double revenueGenerated, Map<Integer, Double> durationTrend) {
		this.stationName = stationName;
		this.mostCommonDestination = mostCommonDestination;
		this.ageGroup = ageGroup;
		this.revenueGenerated = revenueGenerated;
		this.durationTrend = durationTrend;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getMostCommonDestination() {
		return mostCommonDestination;
	}

	public void setMostCommonDestination(String mostCommonDestination) {
		this.mostCommonDestination = mostCommonDestination;
	}

	public AgeGroup getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(AgeGroup ageGroup) {
		this.ageGroup = ageGroup;
	}

	public Double getRevenueGenerated() {
		return revenueGenerated;
	}

	public void setRevenueGenerated(Double revenueGenerated) {
		this.revenueGenerated = revenueGenerated;
	}

	public Map<Integer, Double> getDurationTrend() {
		return durationTrend;
	}

	public void setDurationTrend(Map<Integer, Double> durationTrend) {
		this.durationTrend = durationTrend;
	}

	@Override
	public String toString() {
		return "StationStatistics [stationName=" + stationName + ", mostCommonDestination=" + mostCommonDestination
				+ ", ageGroup=" + ageGroup + ", revenueGenerated=" + revenueGenerated + ", durationTrend="
				+ durationTrend + "]";
	}

}
