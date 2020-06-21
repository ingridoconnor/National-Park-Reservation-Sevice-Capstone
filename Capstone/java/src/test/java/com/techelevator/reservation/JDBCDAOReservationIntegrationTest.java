package com.techelevator.reservation;

import static org.junit.Assert.assertEquals;

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

public class JDBCDAOReservationIntegrationTest {


	private static SingleConnectionDataSource dataSource;
	private JDBCReservationDAO dao;
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
	public static void closeDataSource() {
		dataSource.destroy();
	}
	

	@Before
	public void setup() {

        jdbcTemplate = new JdbcTemplate(dataSource);

		dao = new JDBCReservationDAO(dataSource);
		
		String testReservation = "INSERT INTO reservation (reservation_id, name, site_id, from_date, to_date, create_date) "
				+ "VALUES (100, 'Jesse', 600, '2020-01-01', '2020-01-02', '2020-01-02')";
		jdbcTemplate.update(testReservation);
		
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void get_all_reservations_test() {
		List<Reservation> expectedReservation = dao.getAllReservations();
		int expectedSize = 45; //this test will fail dependent on size of testers local database
		assertEquals(expectedSize, expectedReservation.size());
	
		
	}
	@Test
	public void search_for_reservation_by_reservation_id_test() {
		
		Reservation testReservation = dao.searchForReservationByReservationId((long) 100);
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
		newRes.setReservationId(99L);
		newRes.setFromDate(fromDate);
		newRes.setToDate(toDate);
		newRes.setCreateDate(createDate);
		newRes.setSiteId(601L);
		dao.createReservation(newRes);
		List<Reservation> allNewRes = dao.getAllReservations();
		Reservation shouldBeTestRest = dao.searchForReservationByReservationId(newRes.getReservationId());
		int expectedSize = 46; //this test will fail dependent on size of testers local database
		assertEquals(expectedSize, allNewRes.size());
		assertEquals(shouldBeTestRest, newRes);
		
		
	}
	
}
