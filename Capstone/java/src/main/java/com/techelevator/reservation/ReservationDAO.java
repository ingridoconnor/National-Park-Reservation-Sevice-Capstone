package com.techelevator.reservation;

import java.util.List;

public interface ReservationDAO {

    public List<Reservation> getAllReservations();
    public Reservation createReservation(Reservation reservationId);

}