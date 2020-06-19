package com.techelevator.reservation;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.site.Site;

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
	
	@Override
	public Reservation searchForReservationByReservationId(Long resIdSearch) {
		Reservation res = null;
		String querySearchReservationId = "SELECT * FROM reservation WHERE reservation_id IS ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(querySearchReservationId, resIdSearch);
		if (results.next()) {
			res = mapRowToReservation(results);
			
		}
		
		return res;
	}

@Override
public Reservation createReservation(Reservation reservation) {
	String insertNewReservation = "INSERT INTO reservation (name, site_id, from_date, to_date, create_date) VALUES (?, ?, ?, ?, ?) RETURNING reservation_id";
	Long newResId = jdbcTemplate.queryForObject(insertNewReservation, Long.class, reservation.getName(), reservation.getSiteId(),
			reservation.getFromDate(), reservation.getToDate(), reservation.getCreateDate());
	reservation.setReservationId(newResId);
	return reservation;
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
