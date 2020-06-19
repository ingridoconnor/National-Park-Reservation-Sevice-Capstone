package com.techelevator.reservation;

import java.sql.Date;
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
	String insertNewReservation = "INSERT INTO reservation (name, from_date, to_date) VALUES (?, ?, ?) RETURNING reservation_id";
	Long newResId = jdbcTemplate.queryForObject(insertNewReservation, Long.class, reservation.getName(),
			reservation.getFromDate(), reservation.getToDate());
	reservation.setReservationId(newResId);
	return reservation;
}
@Override
public Reservation createNewReservation(String name, Date startDate, Date endDate) {
	Reservation reservation = new Reservation();
	reservation.setName(name);
	reservation.setFromDate(startDate.toLocalDate());
	reservation.setToDate(endDate.toLocalDate());
	String insertNewReservation = "INSERT INTO reservation (name, from_date, to_date) VALUES (?, ?, ?) RETURNING reservation_id";
	Long newResId = jdbcTemplate.queryForObject(insertNewReservation, Long.class, reservation.getName(),
			reservation.getFromDate(), reservation.getToDate());
	reservation.setReservationId(newResId);
	return reservation;
}

<<<<<<< HEAD


=======
@Override
public Reservation createNewReservation(String name, Date startDate, Date endDate) {
	Reservation reservation = new Reservation();
	reservation.setName(name);
	reservation.setFromDate(startDate.toLocalDate());
	reservation.setToDate(endDate.toLocalDate());
	String insertNewReservation = "INSERT INTO reservation (name, from_date, to_date) VALUES (?, ?, ?) RETURNING reservation_id";
	Long newResId = jdbcTemplate.queryForObject(insertNewReservation, Long.class, reservation.getName(),
			reservation.getFromDate(), reservation.getToDate());
	reservation.setReservationId(newResId);
	return reservation;
}
>>>>>>> 9010e58bd6b8d7d00d02d8b5154b84b3e08a576e
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

<<<<<<< HEAD

=======
>>>>>>> 9010e58bd6b8d7d00d02d8b5154b84b3e08a576e



	
	
	
}
