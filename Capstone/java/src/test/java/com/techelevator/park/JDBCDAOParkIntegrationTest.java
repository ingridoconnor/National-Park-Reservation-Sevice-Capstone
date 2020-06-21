package com.techelevator.park;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class JDBCDAOParkIntegrationTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCParkDAO dao;
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
		dao = new JDBCParkDAO(dataSource);
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	
	@Test
	public void get_all_parks_test() throws SQLException{

		List<Park> allParks = dao.getAllParks();
		int expectedSize = 3;
		Park acadia = dao.getParkByName("Acadia");
		Park arches = dao.getParkByName("Arches");
		Park cuyahoga = dao.getParkByName("Cuyahoga Valley");

		assertEquals(acadia, allParks.get(0));
		assertEquals(arches, allParks.get(1));
		assertEquals(cuyahoga, allParks.get(2));
		assertEquals(expectedSize, allParks.size());
		
	}
	
	@Test
	public void get_park_by_name_test() {

		List<Park> allParks = dao.getAllParks();
		// acadia is the first alphabetically so we know it's the object at the 0 index
		Park acadia = allParks.get(0);
		Park acadiaByName = dao.getParkByName("Acadia");

		assertEquals(acadia, acadiaByName);
		
	}
	


}
