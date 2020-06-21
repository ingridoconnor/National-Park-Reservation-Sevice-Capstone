package com.techelevator.campground;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class JDBCDAOCampgroundIntegrationTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCCampgroundDAO dao;
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
	public static void destoryDataSource() {
		dataSource.destroy();
	}
	
	@Before
	public void setup() {

        jdbcTemplate = new JdbcTemplate(dataSource);

		dao = new JDBCCampgroundDAO(dataSource);
		
	
	}
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void get_campground_by_campground_id_test() {
		Campground testCampground = dao.getCampgroundByCampgroundId((long) 7);
		List<Campground> expectedCampground = dao.getAllCampgrounds();
		Campground isTestCampground = expectedCampground.get(expectedCampground.size() - 1);
		assertEquals(isTestCampground, testCampground);
	}
	
}
