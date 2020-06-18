package com.techelevator.reservation;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationDAO implements ReservationDAO {
	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Reservation> getAllReservations() {
		List<Reservation> reservationList = new ArrayList<>();
		String queryGetAllReservations = "SELECT * FROM reservation";

		SqlRowSet results = jdbcTemplate.queryForRowSet(queryGetAllReservations);
		while (results.next()) {
			Reservation reservation = mapRowToReservation(results);
			reservationList.add(reservation);
		}

		return reservationList;

	}
	
	
	
private Reservation mapRowToReservation(SqlRowSet results) {
	Reservation reservation = new Reservation();
	reservation.setCreateDate(results.getDate("create_date").toLocalDate());
	reservation.setFromDate(results.getDate("from_date").toLocalDate());
	reservation.setToDate(results.getDate("to_date").toLocalDate());
	reservation.setSiteId(results.getLong("site_id"));
	reservation.setReservationId(results.getLong("reservation_id"));
	reservation.setName(results.getString("name"));
	return reservation;
}
	
	
	
	
	
}
