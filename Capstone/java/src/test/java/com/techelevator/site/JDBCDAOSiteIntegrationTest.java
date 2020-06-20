package com.techelevator.site;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;



public class JDBCDAOSiteIntegrationTest {


	private static SingleConnectionDataSource dataSource;
	private JDBCSiteDAO dao;
	private JdbcTemplate jdbcTemplate;

	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		dataSource.setAutoCommit(false);
	}

	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@Before
	public void setup() {

        jdbcTemplate = new JdbcTemplate(dataSource);

		dao = new JDBCSiteDAO(dataSource);
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void get_sites_by_date_test() {

		// campground 1 is open year round (jan - dec)
		// reservations created by our .sql file only create reservations for the months
		// of june and july, so with that in mind, a search for sites at campground 1
		// for early december should be wide open and produce a list of sites 1 - 5

		Long campgroundId = 1L;
		LocalDate fromDate = LocalDate.parse("2020-12-01");
		LocalDate toDate = LocalDate.parse("2020-12-02");

		List<Site> sites = dao.getSitesByDate(campgroundId, fromDate, toDate);
		int expectedSize = 5;
		Site blackwoods = sites.get(0);

		assertEquals(expectedSize, sites.size());
		assertEquals(1, blackwoods.getSiteNumber());
		assertEquals(6, blackwoods.getMaxOccupancy());
		assertFalse(blackwoods.isAccessible());
		assertEquals(0, blackwoods.getMaxRvLength());
		assertFalse(blackwoods.isUtilities());


		for (int i = 0; i < sites.size(); i++) {
			int helper = i + 1;
			assertEquals(helper, sites.get(i).getSiteNumber());
		}

	}
	

}
