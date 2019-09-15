package com.divvy.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.divvy.beans.StartTimeDuration;
import com.divvy.beans.StationStatistics;
import com.divvy.dal.DivvyDal;
import com.divvy.util.PolyTrendLine;
import com.divvy.util.TrendLine;

public class DivvyService {
	
	// gets statistics of a station
	public StationStatistics getStatistics(String fromStationName) {
		StationStatistics stationStatistics = new StationStatistics();
		
		stationStatistics.setStationName(fromStationName);
		
		DivvyDal divvyDal = new DivvyDal();
		stationStatistics.setMostCommonDestination(divvyDal.getMostCommonDestination(fromStationName));
		stationStatistics.setAgeGroup(divvyDal.getPrevalentAgeGroup(fromStationName));
		stationStatistics.setRevenueGenerated(divvyDal.getRevenueGenerated(fromStationName));
		
		StartTimeDuration startTimeDuration = divvyDal.getDurationForStartTime(fromStationName);
		TrendLine t = new PolyTrendLine(1);
		t.setValues(startTimeDuration.getDurations().stream().mapToDouble(i->i).toArray(), startTimeDuration.getStartTimes().stream().mapToDouble(i->i).toArray());
		
		Map<Integer, Double> trendline = new HashMap<Integer, Double>();
		for(int i = 0; i <= 23; i++) {
			trendline.put(i, t.predict(i));
		}
		stationStatistics.setDurationTrend(trendline);
		
		return stationStatistics;
	}
	
	
	// gets the top 3 station names generating maximum revenue
	public List<String> getTopRevenueStations() {
		List<String> topRevenueStations = new ArrayList<String>();
		
		DivvyDal divvyDal = new DivvyDal();
		topRevenueStations = divvyDal.getTopRevenueGeneratingStations();
		
		return topRevenueStations;
	}
	
	
	// gets ids of bikes that need repair work
	public List<Integer> getBikesToService() {
		List<Integer> bikesToService = new ArrayList<Integer>();
		
		DivvyDal divvyDal = new DivvyDal();
		bikesToService = divvyDal.getBikesToService();
		
		return bikesToService;
	}

}
