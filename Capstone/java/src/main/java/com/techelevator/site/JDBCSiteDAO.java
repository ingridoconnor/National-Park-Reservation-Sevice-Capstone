package com.techelevator.site;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCSiteDAO implements SiteDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> getAllSites() {
		List<Site> siteList = new ArrayList<>();
		String queryGetAllSites = "SELECT * FROM site";

		SqlRowSet results = jdbcTemplate.queryForRowSet(queryGetAllSites);
		while (results.next()) {
			Site site = mapRowToSite(results);
			siteList.add(site);
		}

		return siteList;

	}
	@Override
	public List<Site> getSitesByDate(Long campgroundId, LocalDate fromDate, LocalDate toDate) {
		List<Site> siteList = new ArrayList<>();
		String querySearchSitesById = "SELECT DISTINCT * FROM site s " +
				                      "JOIN campground c ON s.campground_id = c.campground_id " +
		                              "WHERE s.campground_id = ? " +
		                              "AND s.site_id NOT IN " +
				                      "(SELECT s.site_id FROM reservation r " +
						              "WHERE (?, ?) OVERLAPS (from_date, to_date) " +
		                              "GROUP BY s.site_id) " +
		                              "LIMIT 5";
		SqlRowSet results = jdbcTemplate.queryForRowSet(querySearchSitesById, campgroundId, fromDate, toDate);
		while (results.next()) {
			Site site = mapRowToSite(results);
			siteList.add(site);
		}

		return siteList;
				
	}
	private Site mapRowToSite(SqlRowSet results) {

		Site site = new Site();
		site.setAccessible(results.getBoolean("accessible"));
		site.setCampgroundId(results.getLong("campground_id"));
		site.setMaxOccupancy(results.getInt("max_occupancy"));
		site.setMaxRvLength(results.getInt("max_rv_length"));
		site.setSiteID(results.getLong("site_id"));
		site.setSiteNumber(results.getInt("site_number"));
		site.setUtilities(results.getBoolean("utilities"));
		return site;
		
	}
}
