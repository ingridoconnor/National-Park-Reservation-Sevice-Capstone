package com.techelevator.campground;

import static org.junit.Assert.*;

import java.sql.SQLException;

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
		dataSource.setUrl("jdbc:postgresql://localhost:5432/projects");
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

		dao = new JDBCCampgroundDAO(dataSource);
	}
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	@Test
	public void get_all_campgrounds_test() {
		
	}
	@Test
	public void get_campground_by_campground_id_test() {
		
	}
	

	
	
}
