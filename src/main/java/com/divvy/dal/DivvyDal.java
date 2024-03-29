package com.divvy.dal;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.divvy.beans.AgeGroup;
import com.divvy.beans.StartTimeDuration;

public class DivvyDal {
	
	private Properties prop = new Properties();
	
	public DivvyDal() {
		try {
			InputStream input = DivvyDal.class.getClassLoader().getResourceAsStream("config.properties");
			prop.load(input);
			Class.forName("com.mysql.jdbc.Driver");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getMostCommonDestination(String stationName) {
		String url = "jdbc:mysql://" + prop.getProperty("db.host") + ":" + prop.getProperty("db.port") + "/" + prop.getProperty("db.schema_name");
		
		try (Connection connection = DriverManager.getConnection(url, prop.getProperty("db.user"), prop.getProperty("db.password"));
				Statement statement = connection.createStatement();) {
			
			String selectSql = "SELECT count(*) as dest_count, to_station_name, from_station_name \r\n" + 
		    		"FROM " + prop.getProperty("db.schema_name") + ".trip \r\n" + 
		    		"where from_station_name = '" + stationName + "'\r\n" + 
		    		"group by to_station_name \r\n" + 
		    		"order by dest_count desc\r\n" + 
		    		"limit 1;";
			
			ResultSet resultSet = null;
            resultSet = statement.executeQuery(selectSql);
            
            resultSet.next();
            return resultSet.getString(2);
            
		} catch (SQLException e) {
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	public AgeGroup getPrevalentAgeGroup(String stationName) {
		String url = "jdbc:mysql://" + prop.getProperty("db.host") + ":" + prop.getProperty("db.port") + "/" + prop.getProperty("db.schema_name");
		try (Connection connection = DriverManager.getConnection(url, prop.getProperty("db.user"), prop.getProperty("db.password"));
				Statement statement = connection.createStatement();) {
			
			String selectSql = "SELECT \r\n" + 
					"	CASE\r\n" + 
					"		WHEN 2019 - birthyear > 0 AND  2019 - birthyear < 16 THEN 'CHILDREN'\r\n" + 
					"        WHEN 2019 - birthyear > 15 AND  2019 - birthyear < 31 THEN 'MID_ADULTS'\r\n" + 
					"        WHEN 2019 - birthyear > 30 AND  2019 - birthyear < 46 THEN 'ADULTS'\r\n" + 
					"        WHEN 2019 - birthyear > 45 THEN 'ELDERLY'\r\n" + 
					"	END as age_group, count(*) as age_group_count\r\n" + 
					"FROM " + prop.getProperty("db.schema_name") + ".trip \r\n" + 
					"where from_station_name = '" + stationName + "'\r\n" + 
					"GROUP BY age_group\r\n" + 
					"ORDER BY age_group_count desc\r\n" + 
					"LIMIT 1;";
			
			ResultSet resultSet = null;
            resultSet = statement.executeQuery(selectSql);
            
            resultSet.next();
            return AgeGroup.valueOf(resultSet.getString(1));
            
		} catch (SQLException e) {
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	
	public Double getRevenueGenerated(String stationName) {
		String url = "jdbc:mysql://" + prop.getProperty("db.host") + ":" + prop.getProperty("db.port") + "/" + prop.getProperty("db.schema_name");
		try (Connection connection = DriverManager.getConnection(url, prop.getProperty("db.user"), prop.getProperty("db.password"));
				Statement statement = connection.createStatement();) {
			
			String selectSql = "SELECT  SUM(CEILING(timestampdiff(SECOND, STR_TO_DATE(start_time, '%d/%m/%Y %T'), STR_TO_DATE(end_time, '%d/%m/%Y %T')) / 60) * 0.10)\r\n" + 
					"FROM " + prop.getProperty("db.schema_name") + ".trip \r\n" + 
					"where from_station_name = '" + stationName + "'\r\n" + 
					"limit 10;";
			
			ResultSet resultSet = null;
            resultSet = statement.executeQuery(selectSql);
            
            resultSet.next();
            return resultSet.getDouble(1);
            
		} catch (SQLException e) {
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	
	
	public StartTimeDuration getDurationForStartTime(String stationName) {
		String url = "jdbc:mysql://" + prop.getProperty("db.host") + ":" + prop.getProperty("db.port") + "/" + prop.getProperty("db.schema_name");
		try (Connection connection = DriverManager.getConnection(url, prop.getProperty("db.user"), prop.getProperty("db.password"));
				Statement statement = connection.createStatement();) {
			
			String selectSql = "SELECT HOUR(STR_TO_DATE(start_time, '%d/%m/%Y %T')) as hours, timestampdiff(SECOND, STR_TO_DATE(start_time, '%d/%m/%Y %T'), STR_TO_DATE(end_time, '%d/%m/%Y %T')) / 60 as duration\r\n" + 
					"FROM " + prop.getProperty("db.schema_name") + ".trip \r\n" + 
					"where from_station_name = '" + stationName + "';";
			
			ResultSet resultSet = null;
            resultSet = statement.executeQuery(selectSql);
            
            
            List<Double> startTimes = new ArrayList<Double>();
            List<Double> durations = new ArrayList<Double>();
            
            while(resultSet.next()) {
            	startTimes.add(Double.valueOf(resultSet.getInt(1)));
            	durations.add(resultSet.getDouble(2));
            }
            
            StartTimeDuration startTimeDuration = new StartTimeDuration(startTimes, durations);
            
            return startTimeDuration;
            
		} catch (SQLException e) {
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	
	public List<String> getTopRevenueGeneratingStations() {
		List<String> topRevenueGeneratingStations = new ArrayList<String>();
		
		String url = "jdbc:mysql://" + prop.getProperty("db.host") + ":" + prop.getProperty("db.port") + "/" + prop.getProperty("db.schema_name");
		try (Connection connection = DriverManager.getConnection(url, prop.getProperty("db.user"), prop.getProperty("db.password"));
				Statement statement = connection.createStatement();) {
			
			String selectSql = "SELECT from_station_name, SUM(CEILING(timestampdiff(SECOND, STR_TO_DATE(start_time, '%d/%m/%Y %T'), STR_TO_DATE(end_time, '%d/%m/%Y %T')) / 60) * 0.10) as revenue, from_station_name\r\n" + 
					"FROM " + prop.getProperty("db.schema_name") + ".trip \r\n" + 
					"GROUP BY from_station_name\r\n" + 
					"ORDER BY revenue desc\r\n" + 
					"limit 3;";
			
			ResultSet resultSet = null;
            resultSet = statement.executeQuery(selectSql);
            
            while(resultSet.next()) {
            	topRevenueGeneratingStations.add(resultSet.getString(1));
            }
            
		} catch (SQLException e) {
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
		
		return topRevenueGeneratingStations;
	}
	
	
	public List<Integer> getBikesToService() {
		List<Integer> bikeIds = new ArrayList<Integer>();
		
		String url = "jdbc:mysql://" + prop.getProperty("db.host") + ":" + prop.getProperty("db.port") + "/" + prop.getProperty("db.schema_name");
		try (Connection connection = DriverManager.getConnection(url, prop.getProperty("db.user"), prop.getProperty("db.password"));
				Statement statement = connection.createStatement();) {
			
			String selectSql = "SELECT bikeid, SUM(timestampdiff(SECOND, STR_TO_DATE(start_time, '%d/%m/%Y %T'), STR_TO_DATE(end_time, '%d/%m/%Y %T'))) as ridetime\r\n" + 
					"FROM " + prop.getProperty("db.schema_name") + ".trip \r\n" +  
					"GROUP BY bikeid\r\n" + 
					"HAVING CEILING(ridetime/60) >= 1000";
			
			ResultSet resultSet = null;
            resultSet = statement.executeQuery(selectSql);
            
            while(resultSet.next()) {
            	bikeIds.add(resultSet.getInt(1));
            }
            
		} catch (SQLException e) {
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
		
		return bikeIds;
	}
	

}
