package com.techelevator.campground;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCampgroundDAO implements CampgroundDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}


	@Override
	public List<Campground> getAllCampgrounds() {
		List<Campground> campgroundList = new ArrayList<>();
		String queryGetAllCampgrounds = "SELECT * FROM CAMPGROUND";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(queryGetAllCampgrounds);
		while (results.next()) {
			Campground campground = mapRowToCampground(results);
			campgroundList.add(campground);
		}
		
		return campgroundList;
	}

	@Override
	public Campground getCampgroundByCampgroundId(Long campgroundId) {
		String querySearchCampgroundId = "SELECT * FROM campground WHERE campground_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(querySearchCampgroundId, campgroundId);
		results.next();
		Campground campground = mapRowToCampground(results);

		return campground;
		
	}
	
	private Campground mapRowToCampground(SqlRowSet results) {
		Campground campground = new Campground();
		campground.setCampgroundId(results.getLong("campground_id"));
		campground.setParkName(results.getString("name"));
		campground.setParkId(results.getLong("park_id"));
		campground.setDailyFee(results.getBigDecimal("daily_fee"));
		campground.setOpenToMonth(results.getInt("open_from_mm"));
		campground.setOpenToMonth(results.getInt("open_to_mm"));
		return campground;
		
		
	}

}
