package com.techelevator.reservation;

import java.sql.Date;
import java.util.List;

public interface ReservationDAO {

    public List<Reservation> getAllReservations();
    public Reservation createReservation(Reservation reservation);
    public Reservation searchForReservationByReservationId(Long resIdSearch);
    public Reservation createNewReservation(String name, Date startDate, Date endDate);
	

}
