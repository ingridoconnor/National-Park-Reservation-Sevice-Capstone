package com.techelevator.reservation;

import java.time.LocalDate;
import java.util.List;

import com.techelevator.site.Site;

public interface ReservationDAO {

    public List<Reservation> getAllReservations();
    public Reservation createReservation(Reservation reservation);
    public Reservation searchForReservationByReservationId(Long resIdSearch);
    public List<Reservation> searchUpcomingRes(Long parkId);


}
