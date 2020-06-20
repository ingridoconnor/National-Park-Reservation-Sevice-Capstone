package com.techelevator.reservation;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;





public class JDBCDAOReservationIntegrationTest {


	private static SingleConnectionDataSource dataSource;
	private JDBCReservationDAO dao;
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

		dao = new JDBCReservationDAO(dataSource);
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void get_all_reservations_test() {
		List<Reservation> expectedReservation = dao.getAllReservations();
		assertEquals(expectedReservation, 0);
	
		
	}
	@Test
	public void search_for_reservation_by_reservation_id_test() {
		
	}
	
	@Test
	public void create_reservation_test() throws SQLException {
		
	}
}
