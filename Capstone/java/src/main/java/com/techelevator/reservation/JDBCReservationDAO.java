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
	

	
	


