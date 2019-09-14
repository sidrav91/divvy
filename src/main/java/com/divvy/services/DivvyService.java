package com.divvy.services;

import java.util.ArrayList;
import java.util.List;

import com.divvy.beans.StationStatistics;
import com.divvy.dal.DivvyDal;

public class DivvyService {
	
	// gets statistics of a station
	public StationStatistics getStatistics(String fromStationName) {
		StationStatistics stationStatistics = new StationStatistics();
		
		stationStatistics.setStationName(fromStationName);
		
		DivvyDal divvyDal = new DivvyDal();
		stationStatistics.setMostCommonDestination(divvyDal.getMostCommonDestination(fromStationName));
		stationStatistics.setAgeGroup(divvyDal.getPrevalentAgeGroup(fromStationName));
		stationStatistics.setRevenueGenerated(divvyDal.getRevenueGenerated(fromStationName));
		
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
