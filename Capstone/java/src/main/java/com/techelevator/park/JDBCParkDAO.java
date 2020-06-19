package com.techelevator.park;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Park> getAllParks() {
		List<Park> parkList = new ArrayList<>();
		String queryGetAllParks = "SELECT * FROM park";

		SqlRowSet results = jdbcTemplate.queryForRowSet(queryGetAllParks);
		while (results.next()) {
			Park park = mapRowToPark(results);
			parkList.add(park);
		}

		return parkList;

	}

	@Override
	public Park getParkByName(String parkName) {
		Park park = null;
		String queryGetParkByName = "SELECT * FROM park WHERE name = ? ";

		SqlRowSet results = jdbcTemplate.queryForRowSet(queryGetParkByName, parkName);
		if (results.next()) {
			park = mapRowToPark(results);
		}

		return park;
	}
	
	private Park mapRowToPark(SqlRowSet results) {
		Park park = new Park();
		park.setArea(results.getInt("area"));
		park.setDescription(results.getString("description"));
		park.setEstDate(results.getDate("establish_date"));
		park.setLocation(results.getString("location"));
		park.setParkId(results.getLong("park_id"));
		park.setParkName(results.getString("name"));
		park.setVisitors(results.getInt("visitors"));
		return park;
	}

}
