package com.divvy.beans;

import java.util.Arrays;
import java.util.List;

public class StartTimeDuration {

	private List<Double> startTimes;
	private List<Double> durations;

	public StartTimeDuration(List<Double> startTimes, List<Double> durations) {
		super();
		this.startTimes = startTimes;
		this.durations = durations;
	}

	public StartTimeDuration() {
		super();
	}

	public List<Double> getStartTimes() {
		return startTimes;
	}

	public void setStartTimes(List<Double> startTimes) {
		this.startTimes = startTimes;
	}

	public List<Double> getDurations() {
		return durations;
	}

	public void setDurations(List<Double> durations) {
		this.durations = durations;
	}

	@Override
	public String toString() {
		return "StartTimeDuration [startTimes=" + startTimes + ", durations=" + durations + "]";
	}

}
