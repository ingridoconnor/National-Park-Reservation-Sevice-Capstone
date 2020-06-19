package com.techelevator.reservation;

import java.sql.Date;
import java.util.List;

public interface ReservationDAO {

    public List<Reservation> getAllReservations();
    public Reservation createReservation(Reservation reservation);
    public Reservation searchForReservationByReservationId(Long resIdSearch);
    public Reservation createNewReservation(String name, Date startDate, Date endDate);
<<<<<<< HEAD
	
=======
>>>>>>> 9010e58bd6b8d7d00d02d8b5154b84b3e08a576e

}
