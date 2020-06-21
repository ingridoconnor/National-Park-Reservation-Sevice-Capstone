package com.techelevator.reservation;

import java.sql.Date;
import java.time.LocalDate;
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
		String querySearchReservationId = "SELECT * FROM reservation WHERE reservation_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(querySearchReservationId, resIdSearch);

		if (results.next()) {
			res = mapRowToReservation(results);
		}
		
		return res;
	}
	@Override
	public List<Reservation> searchUpcomingRes(Long parkId) {
		List<Reservation> upcomingResList = new ArrayList<>();
		String querySearchForUpcomingRes = "SELECT * FROM reservation " + 
				"INNER JOIN site ON reservation.site_id = site.site_id " + 
				"INNER JOIN campground ON campground.campground_id = site.campground_id " + 
				"INNER JOIN park ON park.park_id = campground.park_id " + 
				"WHERE campground.park_id = ? AND reservation.from_date >= CURRENT_DATE AND reservation.to_date <= CURRENT_DATE + 30 ";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(querySearchForUpcomingRes, parkId);
		while (results.next()) {
			Reservation reservation = mapRowToReservation(results);
			upcomingResList.add(reservation);
		}

		return upcomingResList;

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
