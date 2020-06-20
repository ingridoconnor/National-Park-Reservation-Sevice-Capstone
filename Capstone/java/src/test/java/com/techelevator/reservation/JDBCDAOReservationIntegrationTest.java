package com.techelevator.reservation;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.time.LocalDate;
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
		
		String testReservation = "INSERT INTO reservation (name, site_id, from_date, to_date, create_date) "
				+ "VALUES (?, ?, ?, ?, ?) RETURNING reservation_id";
		
		Long newResId = jdbcTemplate.queryForObject(testReservation, Long.class, "jesse", 600, 2020-01-01, 2020-01-02, 2020-01-01);
		
		
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void get_all_reservations_test() {
		List<Reservation> expectedReservation = dao.getAllReservations();
		int expectedSize = 47; //this test will fail dependent on size of testers local database
		assertEquals(expectedSize, expectedReservation.size());
	
		
	}
	@Test
	public void search_for_reservation_by_reservation_id_test() {
		
		Reservation testReservation = dao.searchForReservationByReservationId(50L);
		List <Reservation> expectedRes = dao.getAllReservations();
		Reservation isDefTestRes = expectedRes.get(expectedRes.size() - 1);
		assertEquals(isDefTestRes, testReservation);
		
		
	}
	
	@Test
	public void create_reservation_test() throws SQLException {
		Reservation newRes = new Reservation();
		String fDate = "2020-02-26";
		LocalDate fromDate = LocalDate.parse(fDate);
		String tDate = "2020-02-27";
		LocalDate toDate = LocalDate.parse(tDate);
		String cDate = "2020-02-25";
		LocalDate createDate = LocalDate.parse(cDate);
		newRes.setName("walter");
		newRes.setReservationId(700L);
		newRes.setFromDate(fromDate);
		newRes.setToDate(toDate);
		newRes.setCreateDate(createDate);
		newRes.setSiteId(900L);
		dao.createReservation(newRes);
		Reservation shouldBeNewRes = dao.searchForReservationByReservationId(newRes.getReservationId());
		List<Reservation> allNewRes = dao.getAllReservations();
		int expectedSize = 48;
		assertEquals(expectedSize, allNewRes.size());
		assertEquals(newRes, shouldBeNewRes);
		
		
	}
}
