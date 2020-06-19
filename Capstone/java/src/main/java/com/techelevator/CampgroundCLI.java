package com.techelevator;

import com.techelevator.campground.CampgroundDAO;
import com.techelevator.campground.JDBCCampgroundDAO;
import com.techelevator.park.JDBCParkDAO;
import com.techelevator.park.Park;
import com.techelevator.park.ParkDAO;
import com.techelevator.reservation.JDBCReservationDAO;
import com.techelevator.reservation.ReservationDAO;
import com.techelevator.site.JDBCSiteDAO;
import com.techelevator.site.SiteDAO;
import com.techelevator.view.Menu;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.List;

public class CampgroundCLI {

	private Menu menu;
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private SiteDAO siteDAO;
	private ReservationDAO reservationDAO;

	public static void main(String[] args) {

		CampgroundCLI application = new CampgroundCLI();
		application.run();

	}

	public CampgroundCLI() {

		menu = new Menu(System.in, System.out);

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("G0dmanthing");

		parkDAO = new JDBCParkDAO(dataSource);
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		siteDAO = new JDBCSiteDAO(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);
		List<Park> parks = parkDAO.getAllParks();

	}

	public void run() {

		while (true) {

			printHeading("Select a Park for Further Details");

		}
	}


	private void printHeading(String headingText) {
		System.out.println("\n"+headingText);
		for(int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}

}
