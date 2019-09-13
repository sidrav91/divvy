package com.divvy.services;

import java.util.ArrayList;
import java.util.List;

import com.divvy.beans.StationStatistics;

public class DivvyService {
	
	// gets statistics of a station
	public StationStatistics getStatistics(String fromStationName) {
		StationStatistics stationStatistics = new StationStatistics();
		
		
		
		return stationStatistics;
	}
	
	
	// gets the top 3 station names generating maximum revenue
	public List<String> getTopRevenueStations() {
		List<String> topRevenueStations = new ArrayList<String>();
		
		
		return topRevenueStations;
	}
	
	
	// gets ids of bikes that need repair work
	public List<Integer> getBikesToService() {
		List<Integer> bikesToService = new ArrayList<Integer>();
		
		return bikesToService;
	}

}
