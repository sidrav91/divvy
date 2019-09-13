package com.divvy.dal;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DivvyDal {
	
	private Properties prop = new Properties();
	
	public DivvyDal() {
		try {
			InputStream input = DivvyDal.class.getClassLoader().getResourceAsStream("config.properties");
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getMostCommonDestination(String stationName) {
		String url = "jdbc:mysql://" + prop.getProperty("db.host") + ":" + prop.getProperty("db.port") + "/" + prop.getProperty("db.schema_name");
		try (Connection connection = DriverManager.getConnection(url, prop.getProperty("db.user"), prop.getProperty("db.password"));
				Statement statement = connection.createStatement();) {
			
			String selectSql = "SELECT count(*) as dest_count, to_station_name, from_station_name \r\n" + 
		    		"FROM preventure.trip \r\n" + 
		    		"where from_station_name = 'Millennium Park'\r\n" + 
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
	

}
